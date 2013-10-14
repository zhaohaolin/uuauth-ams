package com.uuauth.ams.controller.dwr;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeRole;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.ProjectRole;
import com.uuauth.ams.domain.entity.TemplateContent;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;
import com.uuauth.ams.domain.repository.ProjectRoleRepository;
import com.uuauth.ams.domain.repository.TemplateContentRepository;

public class TemplateDwr extends BaseDWR {
	
	public TemplateDwr() {
		super();
	}
	
	public void getContent(int templateId) {
		
		projects = projectInfoRepository.list();
		
		for (int i = 0; i < projects.size(); i++) {
			sb = new StringBuilder();
			token = projects.get(i).getToken();
			roles = projectRoleRepository.list(token);
			contents = templateContentRepository.listByTemplateIdAndToken(
					templateId, projects.get(i).getToken());
			
			for (int j = 0; j < roles.size(); j++) {
				for (int c = 0; c < contents.size(); c++) {
					if (contents.get(c).getRoleName()
							.equals(roles.get(j).getRoleName())) {
						checked = "checked";
						display++;
						break;
					}
				}
				sb.append("<div style='float:left; width:200px'><input type='checkbox' name='"
						+ roles.get(j).getRoleName()
						+ "' value='"
						+ token
						+ "-"
						+ roles.get(j).getRoleName()
						+ "' "
						+ checked
						+ "/>&nbsp;" + roles.get(j).getRoleDesc() + "</div>");
				checked = "";
			}
			util.setValue(token, sb.toString());
			if (display == 0)
				util.setStyle(token, "display", "none");
			display = 0;
		}
	}
	
	public void getRole(int employeeId, int postId) {
		projects = projectInfoRepository.list();
		int templateId = basePostRepository.getOne(postId).getRoleTmpId();
		int spot = 0;
		for (int i = 0; i < projects.size(); i++) {
			
			sb = new StringBuilder();
			token = projects.get(i).getToken();
			roles = projectRoleRepository.list(token);
			employeeroles = employeeRoleRepository.listByEmployeeIdAndToken(
					employeeId, projects.get(i).getToken());
			
			if (templateId != 0) {
				contents = templateContentRepository.listByTemplateIdAndToken(
						templateId, projects.get(i).getToken());
			}
			
			for (int j = 0; j < roles.size(); j++) {
				
				if (templateId != 0) {// 获取模版的权限
					for (int k = 0; k < contents.size(); k++) {
						
						if (contents.get(k).getRoleName()
								.equals(roles.get(j).getRoleName())) {
							roledesc = roles.get(j).getRoleDesc();
							sb.append("<div style='float:left; width:200px'><input type='checkbox' disabled name='"
									+ contents.get(k).getRoleName()
									+ "' value='"
									+ token
									+ "-"
									+ contents.get(k).getRoleName()
									+ "' checked/>&nbsp;" + roledesc + "</div>");
							spot++;
							display++;
							break;
						}
					}
				}
				
				if (spot == 0) {// 获取员工权限表里的权限
					for (int l = 0; l < employeeroles.size(); l++) {
						if (employeeroles.get(l).getRoleName()
								.equals(roles.get(j).getRoleName())) {
							checked = "checked";
							display++;
							break;
						}
					}
					
					sb.append("<div style='float:left; width:200px'><input type='checkbox'  name='"
							+ roles.get(j).getRoleName()
							+ "' value='"
							+ token
							+ "-"
							+ roles.get(j).getRoleName()
							+ "' "
							+ checked
							+ "/>&nbsp;"
							+ roles.get(j).getRoleDesc()
							+ "</div>");
					checked = "";
				}
				spot = 0;
			}
			
			util.setValue(token, sb.toString());
			if (display == 0)
				util.setStyle(token, "display", "none");
			display = 0;
		}
	}
	
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
	public void setProjectRoleRepository(
			ProjectRoleRepository projectRoleRepository) {
		this.projectRoleRepository = projectRoleRepository;
	}
	
	public void setTemplateContentRepository(
			TemplateContentRepository templateContentRepository) {
		this.templateContentRepository = templateContentRepository;
	}
	
	public void setEmployeeRoleRepository(
			EmployeeRoleRepository employeeRoleRepository) {
		this.employeeRoleRepository = employeeRoleRepository;
	}
	
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
	private String						token;
	private String						checked		= "";
	// private String disabled = "";
	private String						roledesc	= "";
	private List<ProjectInfo>			projects;
	private List<ProjectRole>			roles;
	private List<TemplateContent>		contents;
	private List<EmployeeRole>			employeeroles;
	private StringBuilder				sb;
	private int							display		= 0;
	private ProjectInfoRepository		projectInfoRepository;
	private ProjectRoleRepository		projectRoleRepository;
	private TemplateContentRepository	templateContentRepository;
	private EmployeeRoleRepository		employeeRoleRepository;
	private BasePostRepository			basePostRepository;
}
