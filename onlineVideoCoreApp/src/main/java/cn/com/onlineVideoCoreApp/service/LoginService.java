package cn.com.onlineVideoCoreApp.service;

import cn.com.onlineVideoCoreApp.base.BaseService;

public class LoginService extends BaseService{

	@Override
	public Object invokeMethod() {
		// TODO Auto-generated method stub
		System.out.println("=login===");
		return getInvokeMethod()+"=========="+getUserName()+"=========="+getDataMap();
	}
	
}
