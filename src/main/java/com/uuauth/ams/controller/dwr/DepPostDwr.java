package com.uuauth.ams.controller.dwr;

import java.util.ArrayList;
import java.util.List;

import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.repository.BaseDepartmentRepository;
import com.uuauth.ams.domain.repository.BasePostRepository;

public class DepPostDwr extends BaseDWR {
	
	public DepPostDwr() {
		super();
	}
	
	public void search(int depId, String name) {
		// 兼容父子部门的功能
		// 得到父部门的ID
		List<BasePost> list = new ArrayList<BasePost>();
		
		List<BasePost> postlist = basePostRepository.list();
		BaseDepartment department = baseDepartmentRepository.getOne(depId);
		int parentDepId = department.getParentId().intValue();
		for (BasePost basePost : postlist) {
			if (basePost.getDepId().intValue() == depId
					|| basePost.getDepId().intValue() == parentDepId) {
				list.add(basePost);
			}
		}
		
		util.removeAllOptions(name);
		if (list.size() > 0) {
			list.add(0, new BasePost(0, "--请选择--", 0, 0));
		} else {
			list.add(new BasePost(0, "--请选择--", 0, 0));
		}
		util.addOptions(name, list, "id", "name");
	}
	
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
	public void setBaseDepartmentRepository(
			BaseDepartmentRepository baseDepartmentRepository) {
		this.baseDepartmentRepository = baseDepartmentRepository;
	}
	
	private BasePostRepository			basePostRepository;
	private BaseDepartmentRepository	baseDepartmentRepository;
	
}
