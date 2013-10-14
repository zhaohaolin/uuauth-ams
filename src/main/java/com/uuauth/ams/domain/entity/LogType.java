package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工操作日志类型
 * 
 * @author root
 * 
 */
public class LogType {
	public final static int						LOGIN		= 0;								// 换岗位
	public final static int						MODIFY_PWD	= 1;								// 新员工入职
	public final static Map<Integer, String>	list		= new HashMap<Integer, String>();
	
	static {
		list.put(LOGIN, "登陆");
		list.put(MODIFY_PWD, "修改密码");
	}
}
