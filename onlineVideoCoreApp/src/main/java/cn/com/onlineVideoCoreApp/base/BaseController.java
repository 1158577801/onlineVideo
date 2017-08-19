package cn.com.onlineVideoCoreApp.base;

import java.lang.reflect.Field;

import com.jfinal.core.Controller;

public class BaseController extends Controller {
	public BaseController() {
		Field [] fields=this.getClass().getDeclaredFields();
		for(Field field:fields) {
			Class<?> clazz=field.getType();
			if(BaseService.class.isAssignableFrom(clazz) && clazz!=BaseService.class) {
				try {
					field.set(this, BaseService.getInstance((Class<? extends BaseService>) clazz,this));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
