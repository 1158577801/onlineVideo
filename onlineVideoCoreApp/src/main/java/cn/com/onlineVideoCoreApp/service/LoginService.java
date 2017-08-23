package cn.com.onlineVideoCoreApp.service;

import java.util.Map;

import com.cn.wct.data.GenerateRandomUtil;
import com.cn.wct.encrypt.Md5Util;
import com.jfinal.plugin.activerecord.Record;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.NewDaoUtil;

public class LoginService extends BaseService {
	@Override
	public Object invokeMethod(Map<String, Object> reqMap) {
		Record user = NewDaoUtil.loginDao.doLoginDao(getUserName(), reqMap.get("userPwd"));
		if (null != user) {
			String md5Key = GenerateRandomUtil.getPswd(8, false);
			user.set("tokenId", Md5Util.hmacSign(user.getStr("id") + user.getStr("userName"), md5Key).toUpperCase());
			super.controller.setSessionAttr("user", user);
			return user;
		} else {
			return super.errorResult("用户名与密码不一致");
		}
	}
}
