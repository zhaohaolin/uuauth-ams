package com.uuauth.ams.controller.dwr;

import org.directwebremoting.ScriptBuffer;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;

public class CheckDwr extends BaseDWR {
	
	public CheckDwr() {
		super();
	}
	
	private EmployeeInfoRepository	employeeInfoRepository;
	private EmployeeInfo			employee1	= null;
	private EmployeeInfo			employee2	= null;
	
	public void setEmployeeInfoRepository(
			EmployeeInfoRepository employeeInfoRepository) {
		this.employeeInfoRepository = employeeInfoRepository;
	}
	
	public void idCheck(int id, String name) {
		employee1 = employeeInfoRepository.getOne(id);
		employee2 = employeeInfoRepository.getUserInfo(name);
		boolean bool = false;
		
		if (id == 0) {
			util.setValue("msg1", "&nbsp;&nbsp;请输入ID");
			util.setStyle("msg1", "display", "");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
			util.addScript(script);
		} else if (employee1 != null) {
			util.setValue("msg1", "&nbsp;&nbsp;这个ID已经被使用,请更换");
			util.setStyle("msg1", "display", "");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
			util.addScript(script);
		} else {
			util.setValue("msg1", "");
			util.setStyle("msg1", "display", "none");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='';");
			util.addScript(script);
			bool = true;
		}
		
		if (name == null || name.equals("")) {
			util.setValue("msg2", "&nbsp;&nbsp;请输入登陆帐号");
			util.setStyle("msg2", "display", "");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
			util.addScript(script);
			
		} else if (employee2 != null) {
			util.setValue("msg2", "&nbsp;&nbsp;这个帐号已经被使用,请更换");
			util.setStyle("msg2", "display", "");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
			util.addScript(script);
			
		} else if (bool) {
			util.setValue("msg2", "");
			util.setStyle("msg2", "display", "none");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='';");
			util.addScript(script);
		}
		
	}
	
	public void nameCheck(String name) {
		EmployeeInfo employee = employeeInfoRepository.getUserInfo(name);
		if (employee != null) {
			util.setValue("msg2", "&nbsp;&nbsp;这个帐号已经被使用,请更换");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
			util.addScript(script);
			
		} else {
			util.setValue("msg2", "&nbsp;&nbsp;这个帐号可以使用");
			ScriptBuffer script = new ScriptBuffer("$('ck').disabled='';");
			util.addScript(script);
		}
		
	}
	
	public void pwdCheck(int id, String pwd) {
		
		EmployeeInfo employee = employeeInfoRepository.getOne(id);
		if (employee.getLoginPassword().equals(SecurityUtil.MD5(pwd))) {
			util.setValue("msg", "&nbsp;&nbsp;原始密码正确");
		} else
			util.setValue("msg", "&nbsp;&nbsp;原始密码不正确，请修正");
		ScriptBuffer script = new ScriptBuffer("$('ck').disabled='true';");
		util.addScript(script);
	}
}
