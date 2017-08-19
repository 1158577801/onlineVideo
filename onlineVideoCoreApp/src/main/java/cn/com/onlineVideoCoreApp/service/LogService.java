package cn.com.onlineVideoCoreApp.service;

import cn.com.onlineVideoCoreApp.base.BaseService;

public class LogService  extends BaseService{

	@Override
	public Object invokeMethod() {
		System.out.println("=log===");
		
		return getInvokeMethod()+"=========="+getUserName()+"=========="+getDataMap();
	}

}
 