package com.learn.management.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learn.management.dao.LearnSysTimetaskDao;
import com.learn.management.domain.LearnSysTimetask;
import com.learn.management.system.rpc.vo.LearnSysTimetaskVO;
import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;
import com.learn.management.system.rpc.service.ILearnSysTimetaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.learn.management.util.BeanMapper;
/**
 * @author HRH
 */
@Service
public class LearnSysTimetaskServiceImpl implements ILearnSysTimetaskService{

	@Autowired
	LearnSysTimetaskDao learnSysTimetaskDao;

	@Override
	public int deleteByDTO(LearnSysTimetaskDTO dto) {
		//逻辑删除操作
		dto.setDeleted(1);
		return learnSysTimetaskDao.update(dto);
 	}

	@Override
	public int deleteById(Long id) {
		LearnSysTimetaskDTO learnSysTimetaskDTO = new LearnSysTimetaskDTO();
		learnSysTimetaskDTO.setId(id);
		return deleteByDTO(learnSysTimetaskDTO);
 	}

	@Override
	public LearnSysTimetaskVO selectByDTO(LearnSysTimetaskDTO dto) {
		LearnSysTimetaskVO vo = (LearnSysTimetaskVO)learnSysTimetaskDao.select(BeanMapper.toMap(dto));
		return vo;
	}

	@Override
	public LearnSysTimetaskVO selectById(Long id) {
		LearnSysTimetaskDTO learnSysTimetaskDTO = new LearnSysTimetaskDTO();
		learnSysTimetaskDTO.setId(id);
		return selectByDTO(learnSysTimetaskDTO);
	}

	@Override
	public LearnSysTimetaskVO modify(LearnSysTimetaskDTO dto) {
		if (dto.getId() == null || dto.getId() <= 0) {
			LearnSysTimetask domain = new LearnSysTimetask(dto);
			learnSysTimetaskDao.insertPart(domain);
			return domain.toConvertBeanVO();
		} else {
			Map map=new HashMap<>();
			map.put("id", dto.getId());
			LearnSysTimetaskVO vo = (LearnSysTimetaskVO)learnSysTimetaskDao.select(map);
			if(vo == null){
				throw new IllegalArgumentException("请求参数异常");
			}
			BeanMapper.copy(dto, vo);
			learnSysTimetaskDao.update(vo);
			return vo;
		}
	}

	@Override
	public PageInfo<LearnSysTimetaskVO> listPage(int pageNum, int pageSize, String orderBy, Map map) {
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<LearnSysTimetaskVO> learnSysTimetaskVOList = learnSysTimetaskDao.list(map);
		return new PageInfo<>(learnSysTimetaskVOList);
	}

	@Override
	public List<LearnSysTimetaskVO> listAll(Map map) {
		return learnSysTimetaskDao.list(map);
	}
}