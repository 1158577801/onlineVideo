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
	/**
	 * 成功操作返回
	 * @param message 返回提示
	 * @param value 返回值
	 * @return Result
	 */
	public void successResultJSON(String message , Object value){
		renderJson(new Result(message,value)); 
	}
	
	/**
	 * 失败操作返回
	 * @param message 返回提示
	 * @return Result
	 */
	public void errorResultJSON(String message){
		 renderJson(new Result(message)); 
	}
	
	/**操作提示
	 * @param true 成功  flase 失败
	 * @return
	 */
	public void operateMessageJSON(boolean bl){
		if(bl){
			renderJson(new Result());
		}else{
			renderJson(new Result("操作失败"));
		}
	}
	public void operateMessageJSON(int bl){
		if(bl>0){
			renderJson(new Result());
		}else{
			renderJson(new Result("操作失败"));
		}
	}
}
