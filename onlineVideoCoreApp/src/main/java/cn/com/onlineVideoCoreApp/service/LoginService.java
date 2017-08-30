package cn.com.onlineVideoCoreApp.service;

import java.util.Map;

import com.cn.wct.data.GenerateRandomUtil;
import com.cn.wct.encrypt.Md5Util;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.NewDaoUtil;

public class LoginService extends BaseService {
	@Override
	public Object invokeMethod(Map<String, Object> reqMap) {
		Record user = NewDaoUtil.loginDao.doLoginDao(getUserName(), reqMap.get("userPwd"));
		if (null != user) {
			String nonce = GenerateRandomUtil.getPswd(8, false);
			String tokenId=Md5Util.hmacSign(user.getStr("id") + user.getStr("userName"), nonce).toUpperCase();
			Redis.use(PropKit.get("redis_cacheName")).setex(user.getStr("userName"),PropKit.getInt("session_time"),tokenId);
			user.set("tokenId", tokenId);
			return user;
		} else {
			return super.errorResult("用户名与密码不一致");
		}
	}
}
