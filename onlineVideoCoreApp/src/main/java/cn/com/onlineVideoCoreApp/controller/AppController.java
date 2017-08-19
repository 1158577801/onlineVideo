package cn.com.onlineVideoCoreApp.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cn.wct.encrypt.Md5Util;

import cn.com.onlineVideoCoreApp.base.BaseController;
import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.InvokeMethodEnum;

public class AppController extends BaseController{
	
	public void doApp() {
		
		String invokeMethod=getPara("invokeMethod");
		if(StringUtils.isEmpty(invokeMethod)){
			renderJson("方法异常");
			return ;
		}
		String userName=getPara("userName");
		if(StringUtils.isEmpty(userName)){
			renderJson("方法异常");
			return ;
		}
		
		Class<? extends BaseService> serviceClass=InvokeMethodEnum.getService(invokeMethod);
		if(null==serviceClass) {
			 renderJson("方法异常");
			 return ;
		}
		Map<String, Object> reqMap =new HashMap<String, Object>();
		BaseService baseService=BaseService.getInstance(serviceClass,this);
		if(InvokeMethodEnum.getIsVerification(invokeMethod)) {
			String signature=getPara("signature");//加密签名内容
			String timestamp=getPara("timestamp");//时间戳
			if(StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)) {
				renderJson("signature and timestamp is null");
				 return ;
			}
			if(!"4353545".equals(signature)) {
				renderJson("signature不一致");
				 return ;
			}
			
			
			String dataMap=getPara("dataMap");
			if(StringUtils.isEmpty(dataMap)) {
				renderJson("signature and timestamp is null");
				return ;
			}
			try {
				reqMap = JSON.parseObject(dataMap,new TypeReference<Map<String, Object>>(){} );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				renderJson("json异常");
				return ;
			}
		}
		renderJson(baseService.INSTANCE_MAP.get(serviceClass).invokeMethod(reqMap));
	}
	
	//签名实现
	public static String createSign(Map<String, String> params, boolean encode)
            throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }

        return Md5Util.hmacSign(temp.toString(),"12345678").toUpperCase();
    }
}
