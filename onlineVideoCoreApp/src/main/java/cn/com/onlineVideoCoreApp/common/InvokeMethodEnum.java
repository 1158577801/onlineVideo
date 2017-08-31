package cn.com.onlineVideoCoreApp.common;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.service.*;

public enum InvokeMethodEnum {
	code1("login", LoginService.class,false,"登录"), 
	code2("register", RegisterService.class,false,"注册"),
	code3("log", LogService.class,"日志");

	
	
	
	private String serviceName;
	private Class<? extends BaseService> serviceClass;
	private String serviceDescribe;
	private boolean isVerification;
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
		this.isVerification = true;
		this.serviceDescribe = serviceDescribe;
	}
	private InvokeMethodEnum(String serviceName, Class<? extends BaseService> serviceClass,boolean isVerification, String serviceDescribe) {
		this.serviceName = serviceName;
		this.serviceClass = serviceClass;
		this.isVerification = isVerification;
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
	public static boolean getIsVerification(String methodName) {
		for (InvokeMethodEnum method : InvokeMethodEnum.values()) {
			if (method.getServiceName().equals(methodName)) {
				return method.isVerification;
			}
		}
		return true;
	}
}
