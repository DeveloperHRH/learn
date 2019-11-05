package com.learn.management.system.rpc.service;

import com.learn.management.system.rpc.dto.LearnSysUserDTO;
import com.learn.management.system.rpc.vo.LearnSysUserVO;
import com.github.pagehelper.PageInfo;
import java.util.Map;
/**
 * @author HRH
 */
public interface ILearnSysUserService {

	 int deleteByDTO(LearnSysUserDTO dto);

	 int deleteById(Long id);

	 LearnSysUserVO selectByDTO(LearnSysUserDTO dto);

	 LearnSysUserVO  selectById(Long id);

	 LearnSysUserVO modify(LearnSysUserDTO dto);

	 PageInfo<LearnSysUserVO > listPage(int pageNum, int pageSize, String orderBy, Map map);

}