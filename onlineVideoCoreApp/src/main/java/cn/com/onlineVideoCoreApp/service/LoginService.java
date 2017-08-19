package cn.com.onlineVideoCoreApp.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.NewDaoUtil;

public class LoginService extends BaseService{
	@Override
	public Object invokeMethod(Map<String, Object> reqMap) {
		Record user=NewDaoUtil.loginDao.doLoginDao(getUserName(), reqMap.get("userPwd"));
		if(null!=user) {
			super.controller.setSessionAttr("user", user);
			return user;
		}else {
			return super.errorResult("用户名与密码不一致");
		}
	}
}
