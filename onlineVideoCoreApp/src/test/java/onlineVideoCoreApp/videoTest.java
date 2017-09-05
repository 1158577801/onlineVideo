package onlineVideoCoreApp;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.wct.data.GenerateRandomUtil;
import com.cn.wct.data.MapSortUtil;
import com.cn.wct.encrypt.SignUtil;
import com.cn.wct.http.HttpClientObject;
import com.cn.wct.http.Json2JavaUtil;

public class videoTest {
	private static final HttpClientObject httpClientObject = new HttpClientObject();
	private static final String url="http://172.168.1.106:8080/onlineVideoWebApp/doApp";
	@Test
	public void LoginService() {
		login();
	}

	public Map<String, String> login() {
		// login
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userName", "2381766143@qq.com");
		param.put("nonce", GenerateRandomUtil.getPswd(5, false));
		param.put("invokeMethod", "login");
		param.put("timestamp", System.currentTimeMillis() + "");

		JSONObject json = new JSONObject();
		json.put("userPwd", "2381766143");

		param.put("dataMap", json);
		try {
			String a = httpClientObject.post(url, param);

			Map<String, String> paramRes = (Map<String, String>) Json2JavaUtil.Json2Java(a, Map.class);
			if (paramRes.isEmpty() || "error".equals(paramRes.get("status"))) {
				return null;
			}
			return paramRes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void logService() throws Exception {
		Map<String, String> paramRes = login();
		Map<String, Object> paramRegister = new HashMap<String, Object>();
		paramRegister.put("userName", "2381766143@qq.com");
		paramRegister.put("invokeMethod", "log");
		paramRegister.put("timestamp", System.currentTimeMillis() + "");
		paramRegister.put("tokenId", paramRes.get("tokenId"));
		paramRegister.put("nonce", GenerateRandomUtil.getPswd(5, false));

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("age", "14");
		dataMap.put("timestamp", paramRegister.get("timestamp"));
		dataMap.put("nonce", paramRegister.get("nonce"));
		dataMap.put("userName", paramRegister.get("userName"));

		dataMap = MapSortUtil.sortMapByKey(dataMap);// 排序key
		String sign = SignUtil.createSign(dataMap, true, paramRes.get("tokenId"));
		paramRegister.put("signature", sign);
		paramRegister.put("dataMap", JSON.toJSONString(dataMap));
		httpClientObject.post(url, paramRegister);
	}

	@Test
	public void registerService() throws Exception {
		Map<String, Object> paramRegister = new HashMap<String, Object>();
		paramRegister.put("userName", "2381766143@qq.com");
		paramRegister.put("invokeMethod", "register");
		paramRegister.put("timestamp", System.currentTimeMillis() + "");
		paramRegister.put("nonce", GenerateRandomUtil.getPswd(5, false));

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userPwd", "2381766143");
			dataMap.put("accountName", "小花花");
			dataMap.put("sex", "1");
			dataMap.put("email", "2381766143@qq.com");
			dataMap.put("userName", paramRegister.get("userName"));
		paramRegister.put("dataMap", JSON.toJSONString(dataMap));
		httpClientObject.post(url, paramRegister);
	}
}
