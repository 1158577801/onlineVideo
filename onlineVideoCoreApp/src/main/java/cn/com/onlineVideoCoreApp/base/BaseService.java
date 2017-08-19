package cn.com.onlineVideoCoreApp.base;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;

public abstract class BaseService implements AppInvoke{
	public Controller controller;
	public static Map<Class<? extends BaseService>,BaseService> INSTANCE_MAP=new HashMap<Class<? extends BaseService>,BaseService>();
	public static <Ser extends BaseService> Ser getInstance(Class<Ser> clazz, Controller controller){
        @SuppressWarnings("unchecked")
		Ser service = (Ser) INSTANCE_MAP.get(clazz);
        if (service == null){
            try {
                service = clazz.newInstance();
                INSTANCE_MAP.put(clazz, service);
            } catch (InstantiationException e) {
               
            } catch (IllegalAccessException e) {
               
            }
        }
        service.controller = controller;
        return service;
    }  
	public String getInvokeMethod() {
		return controller.getPara("invokeMethod");
	}
	public String getUserName() {
		return controller.getPara("userName");
	}
	public String getDataMap() {
		return controller.getPara("dataMap");
	}
}
