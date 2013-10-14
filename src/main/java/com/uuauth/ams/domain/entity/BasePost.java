package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class BasePost implements java.io.Serializable {
	// Fields
	
	/**  */
	private static final long					serialVersionUID	= -3224319176848860500L;
	/**
        *
        */
	private Integer								id;
	private String								name;
	private Integer								roleTmpId;
	private Integer								depId;
	public final static Map<Integer, String>	list				= new HashMap<Integer, String>();
	public final static Map<Integer, String>	templist			= new HashMap<Integer, String>();
	
	/** default constructor */
	public BasePost() {
		//
	}
	
	/** minimal constructor */
	public BasePost(Integer id) {
		this.id = id;
	}
	
	/** full constructor */
	public BasePost(Integer id, String name, Integer roleTmpId, Integer depId) {
		this.id = id;
		this.name = name;
		this.roleTmpId = roleTmpId;
		this.depId = depId;
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
	
	public Integer getRoleTmpId() {
		return this.roleTmpId;
	}
	
	public void setRoleTmpId(Integer roleTmpId) {
		this.roleTmpId = roleTmpId;
	}
	
	public Integer getDepId() {
		return this.depId;
	}
	
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	
}
