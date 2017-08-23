package cn.com.onlineVideoCoreApp.controller;

import java.text.CollationKey;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cn.wct.data.Base64Util;
import com.cn.wct.data.MapSortUtil;
import com.cn.wct.encrypt.SignUtil;
import com.jfinal.kit.PropKit;
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
			dataMap=Base64Util.decode(dataMap);
			reqMap = JSON.parseObject(dataMap, new TypeReference<Map<String, Object>>() {});
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson("error:json conversion anomaly");
			return;
		}

		BaseService baseService = BaseService.getInstance(serviceClass, this);
		if (InvokeMethodEnum.getIsVerification(invokeMethod)) {
			String signature= getPara("signature");//参数签名
			String tokenId = getPara("tokenId");// tokenId
			if (StringUtils.isEmpty(signature)) {
				renderJson("signature is null");
				return;
			}
			if (StringUtils.isEmpty(tokenId)) {
				renderJson("tokenId is null");
				return;
			}
			
			if(!reqMap.isEmpty()) {
				reqMap.put("tokenId", tokenId);
				reqMap.put("timestamp", timestamp);
				reqMap=MapSortUtil.sortMapByKey(reqMap);//排序key
			}
			/*	Object user = getSessionAttr("user");
			if (null == user) {
				renderJson("请重新登录");
				return;
			}
			Record rc = (Record) user;
			String tid = rc.getStr("tokenId");
			if (!tokenId.equals(tid)) {
				renderJson("error:tokenId is Inconsistent");
				return;
			}*/
			
			//AuthUtil.validateSignature(signature, timestamp, , tokenId);
			String sign = SignUtil.createSign(reqMap, true, PropKit.get("token_private_key"));
			if (!sign.equals(signature)) {
				renderJson("validation failed");
				return;
			}
			if(!AuthUtil.validateSignature(timestamp)) {
				renderJson("session  Invalid");
				return;
			}
		}
		Object ob = baseService.INSTANCE_MAP.get(serviceClass).invokeMethod(reqMap);
		renderJson(ob);
	}

}
