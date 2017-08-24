package cn.com.onlineVideoCoreApp.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cn.wct.data.Base64Util;
import com.cn.wct.data.MapSortUtil;
import com.cn.wct.encrypt.SignUtil;
import com.jfinal.kit.PropKit;

import cn.com.onlineVideoCoreApp.base.AuthUtil;
import cn.com.onlineVideoCoreApp.base.BaseController;
import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.InvokeMethodEnum;

public class AppController extends BaseController {

	public void doApp() {

		String invokeMethod = getPara("invokeMethod");
		if (StringUtils.isEmpty(invokeMethod)) {
			errorResultJSON("invokeMethod is null");
			return;
		}
		String userName = getPara("userName");
		if (StringUtils.isEmpty(userName)) {
			errorResultJSON("userName is null");
			return;
		}
		String timestamp = getPara("timestamp");// 时间戳
		if (StringUtils.isEmpty(timestamp)) {
			errorResultJSON("timestamp is null");
			return;
		}
		if (StringUtils.isEmpty(timestamp)) {
			errorResultJSON("nonce is null");
			return;
		}
		Class<? extends BaseService> serviceClass = InvokeMethodEnum.getService(invokeMethod);
		if (null == serviceClass) {
			errorResultJSON("serviceClass is error,class not found");
			return;
		}
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String dataMap = getPara("dataMap");
		if (StringUtils.isEmpty(dataMap)) {
			errorResultJSON("dataMap is null");
			return;
		}
		try {
			dataMap = Base64Util.decode(dataMap);
			reqMap = JSON.parseObject(dataMap, new TypeReference<Map<String, Object>>() {
			});

		} catch (Exception e) {
			e.printStackTrace();
			errorResultJSON("error:json conversion anomaly");
			return;
		}

		BaseService baseService = BaseService.getInstance(serviceClass, this);
		if (InvokeMethodEnum.getIsVerification(invokeMethod)) {
			String signature = getPara("signature");// 参数签名
			String tokenId = getPara("tokenId");// tokenId
			if (StringUtils.isEmpty(signature)) {
				errorResultJSON("signature is null");
				return;
			}
			if (StringUtils.isEmpty(tokenId)) {
				errorResultJSON("tokenId is null");
				return;
			}

			if (!reqMap.isEmpty()) {
				reqMap.put("tokenId", tokenId);
				reqMap.put("timestamp", timestamp);
				reqMap = MapSortUtil.sortMapByKey(reqMap);// 排序key
			}
			String sign = SignUtil.createSign(reqMap, true, PropKit.get("token_private_key"));
			if (!sign.equals(signature)) {
				errorResultJSON("validation failed");
				return;
			}
			if (!AuthUtil.validateTimestamp(timestamp)) {
				errorResultJSON("session  Invalid");
				return;
			}
		}
		Object ob = baseService.INSTANCE_MAP.get(serviceClass).invokeMethod(reqMap);
		renderJson(ob);
	}
}
