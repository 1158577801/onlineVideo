package cn.com.onlineVideoCoreApp.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class LoginDao {
	public Record doLoginDao(Object userName,Object userPwd) {
		return Db.findFirst("select * from user where userName=? and userPwd=?",userName,userPwd);
	}
}
