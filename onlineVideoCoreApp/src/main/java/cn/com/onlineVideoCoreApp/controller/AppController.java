package cn.com.onlineVideoCoreApp.controller;

import com.alibaba.druid.util.StringUtils;

import cn.com.onlineVideoCoreApp.base.BaseController;
import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.InvokeMethodEnum;

public class AppController extends BaseController{
	public void doApp() {
		String invokeMethod=getPara("invokeMethod");
		if(StringUtils.isEmpty(invokeMethod)){
			return ;
		}
		String userName=getPara("userName");
		if(StringUtils.isEmpty(userName)){
			return ;
		}
		String signature=getPara("signature");//加密签名内容
		String timestamp=getPara("timestamp");//时间戳
		
		Class<? extends BaseService> serviceClass=InvokeMethodEnum.getService(invokeMethod);
		if(null==serviceClass) {
			 renderJson("方法异常");
			 return ;
		}
		BaseService.getInstance(serviceClass,this);
		renderJson(BaseService.INSTANCE_MAP.get(serviceClass).invokeMethod());
	}
}
