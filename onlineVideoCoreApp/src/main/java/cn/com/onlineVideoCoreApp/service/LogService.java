package cn.com.onlineVideoCoreApp.service;

import java.util.Map;

import cn.com.onlineVideoCoreApp.base.BaseService;

public class LogService  extends BaseService{

	@Override
	public Object invokeMethod(Map<String, Object> reqMap) {
		System.out.println("=log===");
		
		return getInvokeMethod()+"=========="+getUserName()+"=========="+getDataMap();
	}

}
 