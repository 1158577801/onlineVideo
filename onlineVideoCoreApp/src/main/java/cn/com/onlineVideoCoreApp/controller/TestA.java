package cn.com.onlineVideoCoreApp.controller;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.wct.http.Json2JavaUtil;

import com.cn.wct.data.Base64Util;
import com.cn.wct.encrypt.SignUtil;
import com.cn.wct.http.HttpClientObject;


public class TestA {
	public static void main(String[] args) {
    	//http://localhost:8080/onlineVideoWebApp/doApp?userName=admin&invokeMethod=login&dataMap={"userPwd":"242343434"}
    	try {	
    		
    		HttpClientObject s=new HttpClientObject();
    		Map<String,String> param =new HashMap<String,String>();
    		param.put("userName", "admin");
    		param.put("userName2", "我安康你");
    		param.put("invokeMethod", "login");
    		param.put("timestamp", System.currentTimeMillis()+"");
    		JSONObject json=new JSONObject();
    		json.put("userPwd", "242343434");
    		
    		param.put("dataMap",Base64Util.encoded(JSON.toJSONString(json)));
			String a=s.post("http://localhost:8080/onlineVideoWebApp/doApp", param);
			System.out.println("========1====="+a);
			Map<String,String> paramRes=(Map<String, String>) Json2JavaUtil.Json2Java(a, Map.class);
			
			
			
    		HttpClientObject s2=new HttpClientObject();
    		Map<String,String> param2 =new HashMap<String,String>();
    		param2.put("userName", "admin");
    		param2.put("invokeMethod", "log");
    		param2.put("timestamp", param.get("timestamp"));
    		param2.put("tokenId", paramRes.get("tokenId"));
    		
    		
    		
    		Map<String, Object> sigMap=new HashMap<String, Object>();
    		sigMap.put("age", "14");
    		sigMap.put("timestamp", param2.get("timestamp"));
    		sigMap.put("tokenId", param2.get("tokenId"));
    		
    		
    		String sign = SignUtil.createSign(sigMap, true, "d10811f4eb0fe8746b8626d3bd6bad9ffc6924e5");
    		param2.put("signature", sign);
    		
    		param2.put("dataMap",Base64Util.encoded(JSON.toJSONString(sigMap)));
			String a2=s2.post("http://localhost:8080/onlineVideoWebApp/doApp", param2);
			System.out.println("========2====="+a2);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    
	}
}
