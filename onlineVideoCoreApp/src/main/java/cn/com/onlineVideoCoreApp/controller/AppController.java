package cn.com.onlineVideoCoreApp.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cn.wct.encrypt.SignUtil;
import com.jfinal.plugin.activerecord.Record;

import cn.com.onlineVideoCoreApp.base.AuthUtil;
import cn.com.onlineVideoCoreApp.base.BaseController;
import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.InvokeMethodEnum;

public class AppController extends BaseController {

	public void doApp() {

		String invokeMethod = getPara("invokeMethod");
		if (StringUtils.isEmpty(invokeMethod)) {
			renderJson("invokeMethod is null");
			return;
		}
		String userName = getPara("userName");
		if (StringUtils.isEmpty(userName)) {
			renderJson("userName is null");
			return;
		}
		String timestamp = getPara("timestamp");// 时间戳
		if (StringUtils.isEmpty(timestamp)) {
			renderJson("timestamp is null");
			return;
		}
		String nonce = getPara("nonce");// 随机数
		if (StringUtils.isEmpty(timestamp)) {
			renderJson("nonce is null");
			return;
		}
		Class<? extends BaseService> serviceClass = InvokeMethodEnum.getService(invokeMethod);
		if (null == serviceClass) {
			renderJson("serviceClass is error,class not found");
			return;
		}
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String dataMap = getPara("dataMap");
		if (StringUtils.isEmpty(dataMap)) {
			renderJson("dataMap is null");
			return;
		}
		try {
			reqMap = JSON.parseObject(dataMap, new TypeReference<Map<String, Object>>() {});
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson("error:json conversion anomaly");
			return;
		}

		BaseService baseService = BaseService.getInstance(serviceClass, this);
		if (InvokeMethodEnum.getIsVerification(invokeMethod)) {
			
			String tokenId = getPara("tokenId");// tokenId
			if (StringUtils.isEmpty(tokenId)) {
				renderJson("tokenId is null");
				return;
			}
			Object user = getSessionAttr("user");
			if (null == user) {
				renderJson("请重新登录");
				return;
			}
			Record rc = (Record) user;
			String tid = rc.getStr("tokenId");
			if (!tokenId.equals(tid)) {
				renderJson("error:tokenId is Inconsistent");
				return;
			}
			AuthUtil.validateSignature("", timestamp, nonce, tokenId);
			String sign = SignUtil.createSign(reqMap, true, tid);
			if (!sign.equals(nonce)) {
				renderJson("validation failed");
				return;
			}

		}
		Object ob = baseService.INSTANCE_MAP.get(serviceClass).invokeMethod(reqMap);
		System.out.println(getSessionAttr("user").toString());
		renderJson(ob);
	}
	
	public static void main(String[] args) {
		String a=System.currentTimeMillis()+"";
		System.out.println(AuthUtil.generateSignature(a, "1565434", "145555"));
		System.out.println(AuthUtil.generateSignature(a, "565434", "145555"));
	}

}
