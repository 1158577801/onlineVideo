package cn.com.onlineVideoCoreApp.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.cn.wct.data.MapSortUtil;
import com.cn.wct.encrypt.SignUtil;
import com.cn.wct.http.Json2JavaUtil;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.redis.Redis;
import cn.com.onlineVideoCoreApp.base.BaseController;
import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.InvokeMethodEnum;

public class AppController extends BaseController {

	public void doApp() {
		try {
			String invokeMethod = getPara("invokeMethod");// 调用方法
			if (StringUtils.isEmpty(invokeMethod)) {
				errorResultJSON("invokeMethod is null");
				return;
			}
			String userName = getPara("userName");// 用户名
			if (StringUtils.isEmpty(userName)) {
				errorResultJSON("userName is null");
				return;
			}
			String timestamp = getPara("timestamp");// 时间戳
			if (StringUtils.isEmpty(timestamp)) {
				errorResultJSON("timestamp is null");
				return;
			}
			String nonce = getPara("nonce");// 随机数
			if (StringUtils.isEmpty(nonce)) {
				errorResultJSON("nonce is null");
				return;
			}

			Class<? extends BaseService> serviceClass = InvokeMethodEnum.getService(invokeMethod);// 调用service
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
			reqMap = (Map<String, Object>) Json2JavaUtil.Json2Java(dataMap, Map.class);
			//start 验证需要验证的接口
			BaseService baseService = BaseService.getInstance(serviceClass, this);// 实例化service对象
			if (InvokeMethodEnum.getIsVerification(invokeMethod)) {
				String signature = getPara("signature");// 参数签名
				if (StringUtils.isEmpty(signature)) {
					errorResultJSON("signature is null");
					return;
				}
				String tokenId = getPara("tokenId");// 参数签名
				if (StringUtils.isEmpty(tokenId)) {
					errorResultJSON("tokenId is null");
					return;
				}
				if (!reqMap.isEmpty()) {
					reqMap.put("userName", userName);
					reqMap.put("timestamp", timestamp);
					reqMap.put("nonce", nonce);
					reqMap = MapSortUtil.sortMapByKey(reqMap);// 排序key
				}
				String tokenIdCache = Redis.use(PropKit.get("redis_cacheName")).get(userName);// 获取缓存tokenId
				if (null == tokenIdCache) {
					errorResultJSON("session Invalid");
					return;
				}
				String sign = SignUtil.createSign(reqMap, true, tokenIdCache);// 生成签名
				if (!sign.equals(signature)) {
					errorResultJSON("validation failed");
					return;
				}
			}
			renderJson(baseService.INSTANCE_MAP.get(serviceClass).invokeMethod(reqMap));
		} catch (Exception e) {
			e.printStackTrace();
			errorResultJSON("error:system error");
			return;
		}
	}
}
