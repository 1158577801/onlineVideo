package cn.com.onlineVideoCoreApp.base;

/**
 * 统一消息返回格式
 * 
 * @author wct
 * 
 */
public class Result {
	public static final String SYS_ERRPR="系统异常，请稍后重试!";
	
	/** 处理结果 **/
	
	private String message = "操作成功";
	private String status = "success";
	private Object value = null;

	/**
	 * 默认操作成功
	 */
	public Result() {

	}

	/**
	 * 操作失败构造
	 * 
	 * @param msg
	 */
	public Result(String message) {
		this.message = message;
		this.status = "error";
	}

	/**
	 * 自定义构造
	 * 
	 * @param msg
	 * @param status
	 */
	public Result(String message, Object value) {
		this.message = message;
		this.status = "success";
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Result(String message, String status) {
		this.message = message;
		this.status = status;
	}
}