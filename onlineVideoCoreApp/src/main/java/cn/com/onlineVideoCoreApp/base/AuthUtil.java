package cn.com.onlineVideoCoreApp.base;

import com.cn.wct.encrypt.Sha1Util;

public class AuthUtil {
	// 时间戳时间设置不能超过某个时间，防止传输过程中signature窃取后的连续攻击，这个值也不能设置太小，因为可能存在网络缓慢或者不稳定
	private static int time_out = 3000;

	/**
	 * 时间戳
	 * 
	 * @return
	 */
	public static String generateTimestamp() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * 利用token，时间戳和随机数生成加密签名
	 * 
	 * @return
	 */
	public static String generateSignature(String timestamp, String nonce, String app_token) {
		return Sha1Util.sha1(app_token + timestamp + nonce);
	}

	/**
	 * 验证请求的签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean validateSignature(String signature, String timestamp, String nonce,String appToken) {
		long ctime = System.currentTimeMillis();
		long ttime = Long.valueOf(timestamp);
		if ((ctime - ttime) < time_out && signature.equals(generateSignature(timestamp, nonce, appToken))) {
			return true;
		}
		return false;

	}

}
