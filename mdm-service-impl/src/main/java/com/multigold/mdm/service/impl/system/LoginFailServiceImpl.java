package com.multigold.mdm.service.impl.system;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multigold.common.dao.BaseMapper;
import com.multigold.common.entity.BaseEntity;
import com.multigold.common.service.impl.BaseServiceImpl;
import com.multigold.mdm.dao.system.LoginFailMapper;
import com.multigold.mdm.service.api.system.LoginFailService;

/**
 * 
 * @author guoqiushi
 *
 */
@Service
public class LoginFailServiceImpl<T extends BaseEntity, ID extends Serializable>
extends BaseServiceImpl<T, ID> implements LoginFailService<T, ID>{
	
	@Autowired
	LoginFailMapper<T, ID> loginFailMapper;

	@Override
	public BaseMapper<T, ID> getMapper() {
		return loginFailMapper;
	}
}
