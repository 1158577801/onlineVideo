package cn.com.onlineVideoCoreApp.common;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.service.LogService;
import cn.com.onlineVideoCoreApp.service.LoginService;

public enum InvokeMethodEnum {
	ONE("login", LoginService.class,"登录"), 
	ONE2("log", LogService.class,"日志");

	
	
	
	private String serviceName;
	private Class<? extends BaseService> serviceClass;
	private String serviceDescribe;

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceDescribe() {
		return serviceDescribe;
	}

	public Class<? extends BaseService> getServiceClass() {
		return serviceClass;
	}

	private InvokeMethodEnum(String serviceName, Class<? extends BaseService> serviceClass, String serviceDescribe) {
		this.serviceName = serviceName;
		this.serviceClass = serviceClass;
		this.serviceDescribe = serviceDescribe;
	}

	public static Class<? extends BaseService> getService(String methodName) {
		for (InvokeMethodEnum method : InvokeMethodEnum.values()) {
			if (method.getServiceName().equals(methodName)) {
				return method.serviceClass;
			}
		}
		return null;
	}
}
