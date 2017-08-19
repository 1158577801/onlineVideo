package cn.com.onlineVideoCoreApp.base;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public abstract class BaseService implements AppInvoke{
	public Controller controller;
	private boolean isVerification;
	public boolean isVerification() {
		return isVerification;
	}
	public void setVerification(boolean isVerification) {
		this.isVerification = isVerification;
	}
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
	
	/**
	 * Record+Db模式，json分页查询
	 * @param Page<Record> record
	 * @return Map<String, Object>
	 */
	public Map<String, Object> dbOrPage(Page<Record> record){
		Map<String, Object> data=new HashMap<String, Object>();
		if(null!=record){
			data.put("total",record.getTotalRow());
			data.put("rows", record.getList());
			data.put("page",record.getPageNumber());
		}
		return data;
	}
	public Record getUserInfo() {
		return controller.getSessionAttr("user");
	}
	/**
	 * 成功操作返回
	 * @param message 返回提示
	 * @param value 返回值
	 * @return Result
	 */
	public Result successResult(String message , Object value){
		return new Result(message,value); 
	}
	
	/**
	 * 失败操作返回
	 * @param message 返回提示
	 * @return Result
	 */
	public Result errorResult(String message){
		return new Result(message); 
	}
	
	/**操作提示
	 * @param true 成功  flase 失败
	 * @return
	 */
	public void operateMessage(boolean bl){
		if(bl){
			controller.renderJson(new Result());
		}else{
			controller.renderJson(new Result("操作失败"));
		}
	}
	public void operateMessage(int bl){
		if(bl>0){
			controller.renderJson(new Result());
		}else{
			controller.renderJson(new Result("操作失败"));
		}
	}
}
