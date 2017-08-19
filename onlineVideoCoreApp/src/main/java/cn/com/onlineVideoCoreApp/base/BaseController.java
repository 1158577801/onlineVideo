package cn.com.onlineVideoCoreApp.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class BaseController extends Controller {
	public BaseController() {
		Field [] fields=this.getClass().getDeclaredFields();
		for(Field field:fields) {
			Class<?> clazz=field.getType();
			if(BaseService.class.isAssignableFrom(clazz) && clazz!=BaseService.class ) {
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
	 * Record+Db模式，json分页查询
	 * @param Page<Record> record
	 * @return Map<String, Object>
	 */
	public Map<String, Object> dbOrPage(Page<Record> record){
		Map<String, Object> data=new HashMap<String, Object>();
		if(null!=record){
			data.put("total",record.getTotalRow());
			data.put("rows", record.getList());
			data.put("page",record.getPageNumber());
		}
		return data;
	}
	public Record getUserInfo() {
		return getSessionAttr("user");
	}
}
