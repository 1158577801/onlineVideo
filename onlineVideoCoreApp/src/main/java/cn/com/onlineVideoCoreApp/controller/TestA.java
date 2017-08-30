package cn.com.onlineVideoCoreApp.controller;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.wct.data.GenerateRandomUtil;
import com.cn.wct.data.MapSortUtil;
import com.cn.wct.encrypt.SignUtil;
import com.cn.wct.http.HttpClientObject;
import com.cn.wct.http.Json2JavaUtil;


public class TestA {
	public static void main(String[] args) throws Exception {
    	
    		
    		HttpClientObject s=new HttpClientObject();
    		Map<String,Object> param =new HashMap<String,Object>();
    		param.put("userName", "admin");
    		param.put("nonce", GenerateRandomUtil.getPswd(5, false));
    		param.put("invokeMethod", "login");
    		param.put("timestamp", System.currentTimeMillis()+"");
    		
    		JSONObject json=new JSONObject();
    		json.put("userPwd", "242343434");
    		
    		param.put("dataMap",json);
			String a=s.post("http://localhost:8080/onlineVideoWebApp/doApp", param);

			Map<String,String> paramRes=(Map<String, String>) Json2JavaUtil.Json2Java(a, Map.class);
			if(paramRes.isEmpty() || "error".equals(paramRes.get("status"))) {
				return ;
			}
			
			for(int i=0;i<100;i++) {
				
			
    		HttpClientObject s2=new HttpClientObject();
    		Map<String,Object> param2 =new HashMap<String,Object>();
    		param2.put("userName", "admin");
    		param2.put("invokeMethod", "log");
    		param2.put("timestamp", param.get("timestamp"));
    		param2.put("tokenId", paramRes.get("tokenId"));
    		param2.put("nonce", GenerateRandomUtil.getPswd(5, false));
    		
    		
    		
    		Map<String, Object> sigMap=new HashMap<String, Object>();
    		sigMap.put("age", "14");
    		sigMap.put("timestamp", param2.get("timestamp"));
    		sigMap.put("nonce", param2.get("nonce"));
    		sigMap.put("userName", param2.get("userName"));
    		
    		sigMap = MapSortUtil.sortMapByKey(sigMap);// 排序key
    		String sign = SignUtil.createSign(sigMap, true, paramRes.get("tokenId"));
    		param2.put("signature", sign);
    		param2.put("dataMap",JSON.toJSONString(sigMap));
			String a2=s2.post("http://localhost:8080/onlineVideoWebApp/doApp", param2);
			System.out.println(a2+"================================================================="+i);
			
			Thread.sleep(10000);
			}	
			
    
	}
}
