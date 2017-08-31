package cn.com.onlineVideoCoreApp.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class UserDao {
	public Record getUserByUName(Object userName) {
		return Db.findFirst("select * from user where userName=?",userName.toString().trim());
	}
	public boolean doRegisterDao(Record param) {
		return Db.save("user","id", param);
	}
}
