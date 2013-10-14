package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 部门数据模型对象
 * 
 * @author qiaofeng
 * @version $Id: BaseDepartment, v 0.1 2012-10-8 下午02:21:58 Exp $
 */
public class BaseDepartment implements java.io.Serializable {
	
	/**  */
	private static final long			serialVersionUID	= 8169158846996522424L;
	/**
        *
        */
	private Integer						id;
	private Integer						parentId			= 0;
	private String						name;
	private String						parentName;
	// use memory cache
	public static Map<Integer, String>	list				= new HashMap<Integer, String>();
	
	// Constructors
	public BaseDepartment() {
		//
	}
	
	public BaseDepartment(Integer id) {
		this.id = id;
	}
	
	/** full constructor */
	public BaseDepartment(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// Property accessors
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		if (!list.isEmpty()) {
			for (Integer did : list.keySet()) {
				if (this.parentId == did) {
					this.parentName = list.get(did);
					break;
				}
			}
		}
		return this.parentName;
	}
	
}
