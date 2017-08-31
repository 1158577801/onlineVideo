package cn.com.onlineVideoCoreApp.common;

import cn.com.onlineVideoCoreApp.dao.UserDao;

public class NewDaoUtil {
	public static final String system_error="系统异常,请稍后!";
	public static final String userName_null="用户名不能为空";
	public static final String userPwd_null="密码不能为空";
	public static final String accountName_null="昵称不能为空";
	public static final String sex_null="性别不能为空";
	public static final String email_null="邮箱不能为空";
	public static final String user_pwd_error="用户名与密码错误";
	public static final String pwd_error="密码错误";
	public static final String one_user_cz="用户名已存在";
		
	
	
	
	
	
	public static final UserDao userDao=new UserDao();
	
}
