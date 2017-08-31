package cn.com.onlineVideoCoreApp.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cn.wct.data.GenerateRandomUtil;
import com.cn.wct.data.UUIDUtil;
import com.cn.wct.encrypt.Md5Util;
import com.jfinal.plugin.activerecord.Record;

import cn.com.onlineVideoCoreApp.base.BaseService;
import cn.com.onlineVideoCoreApp.common.NewDaoUtil;

public class RegisterService extends BaseService {

	@Override
	public Object invokeMethod(Map<String, Object> reqMap) {
		if (null == reqMap.get("userName") || StringUtils.isBlank(reqMap.get("userName").toString())) {
			return errorResult(NewDaoUtil.userName_null);
		}
		if (null == reqMap.get("userPwd") || StringUtils.isBlank(reqMap.get("userPwd").toString())) {
			return errorResult(NewDaoUtil.userPwd_null);
		}
		if (null == reqMap.get("accountName") || StringUtils.isBlank(reqMap.get("accountName").toString())) {
			return errorResult(NewDaoUtil.system_error);
		}
		if (null == reqMap.get("sex") || StringUtils.isBlank(reqMap.get("sex").toString())) {
			return errorResult(NewDaoUtil.sex_null);
		}
		if (null == reqMap.get("email") || StringUtils.isBlank(reqMap.get("email").toString())) {
			return errorResult(NewDaoUtil.email_null);
		}
		Record oneUserId=NewDaoUtil.userDao.getUserByUName(reqMap.get("userName"));
		if(null!=oneUserId && StringUtils.isNotBlank(oneUserId.getStr("id"))) {
			return errorResult(NewDaoUtil.one_user_cz);
		}
		Record user = new Record();
		user.setColumns(reqMap);
		user.set("id", UUIDUtil.getUUID());
		String pwdKey = GenerateRandomUtil.getPswd(8, true).toUpperCase();
		user.set("pwdKey", pwdKey);
		user.set("create_date", new Date());
		user.set("userPwd", Md5Util.hmacSign(user.getStr("userPwd"), pwdKey));
		return operateMessage(NewDaoUtil.userDao.doRegisterDao(user));
	}

}
