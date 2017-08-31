package cn.com.onlineVideoCoreApp.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
		if(null==reqMap.get("userPwd") || StringUtils.isBlank(reqMap.get("userPwd").toString())) {
			return errorResult(NewDaoUtil.userPwd_null);
		}
		Record user = NewDaoUtil.userDao.getUserByUName(getUserName());
		if (null != user) {
			String userPwd=Md5Util.hmacSign(reqMap.get("userPwd").toString(),user.getStr("pwdKey"));
			if(userPwd.equals(user.getStr("userPwd"))) {
				String nonce = GenerateRandomUtil.getPswd(8, false);
				String tokenId=Md5Util.hmacSign(user.getStr("id") + user.getStr("userName"), nonce).toUpperCase();
				Redis.use(PropKit.get("redis_cacheName")).setex(user.getStr("userName"),PropKit.getInt("session_time"),tokenId);
				user.set("tokenId", tokenId);
				return user;
			}else {
				return super.errorResult(NewDaoUtil.pwd_error);
			}
		} else {
			return super.errorResult(NewDaoUtil.user_pwd_error);
		}
	}
}
