package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class EmployeeInfo extends Employee {
	
	/**  */
	private static final long			serialVersionUID	= 7536698883379636788L;
	/**
        *
        */
	private Integer						id;
	private Integer						oldid;
	private boolean						needinit			= false;
	public static Map<Integer, String>	list				= new HashMap<Integer, String>();
	
	public EmployeeInfo() {
		//
	}
	
	public EmployeeInfo(Integer id) {
		this.id = id;
	}
	
	public boolean isNeedinit() {
		return this.needinit;
	}
	
	public void setNeedinit(boolean needinit) {
		this.needinit = needinit;
	}
	
	public Integer getOldid() {
		if (this.oldid == 0)
			this.oldid = this.id;
		return this.oldid;
	}
	
	public void setOldid(Integer oldid) {
		this.oldid = oldid;
	}
	
}
