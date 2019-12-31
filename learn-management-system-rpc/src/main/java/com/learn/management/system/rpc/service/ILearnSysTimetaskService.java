package com.learn.management.system.rpc.service;

import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;
import com.learn.management.system.rpc.vo.LearnSysTimetaskVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
/**
 * @author HRH
 */
public interface ILearnSysTimetaskService {

	 int deleteByDTO(LearnSysTimetaskDTO dto);

	 int deleteById(Long id);

	 LearnSysTimetaskVO selectByDTO(LearnSysTimetaskDTO dto);

	 LearnSysTimetaskVO  selectById(Long id);

	 LearnSysTimetaskVO modify(LearnSysTimetaskDTO dto);

	 PageInfo<LearnSysTimetaskVO > listPage(int pageNum, int pageSize, String orderBy, Map map);

	List<LearnSysTimetaskVO> listAll(Map map);


}