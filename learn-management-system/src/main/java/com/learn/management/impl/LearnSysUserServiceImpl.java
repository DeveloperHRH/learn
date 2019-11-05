package com.learn.management.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learn.management.dao.LearnSysUserDao;
import com.learn.management.domain.LearnSysUser;
import com.learn.management.system.rpc.vo.LearnSysUserVO;
import com.learn.management.system.rpc.dto.LearnSysUserDTO;
import com.learn.management.system.rpc.service.ILearnSysUserService;
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
public class LearnSysUserServiceImpl implements ILearnSysUserService{

	@Autowired
	LearnSysUserDao learnSysUserDao;

	@Override
	public int deleteByDTO(LearnSysUserDTO dto) {
		//逻辑删除操作
		dto.setDeleted(1);
		return learnSysUserDao.update(dto);
 	}

	@Override
	public int deleteById(Long id) {
		LearnSysUserDTO learnSysUserDTO = new LearnSysUserDTO();
		learnSysUserDTO.setId(id);
		return deleteByDTO(learnSysUserDTO);
 	}

	@Override
	public LearnSysUserVO selectByDTO(LearnSysUserDTO dto) {
		LearnSysUserVO vo = (LearnSysUserVO)learnSysUserDao.select(BeanMapper.toMap(dto));
		return vo;
	}

	@Override
	public LearnSysUserVO selectById(Long id) {
		LearnSysUserDTO learnSysUserDTO = new LearnSysUserDTO();
		learnSysUserDTO.setId(id);
		return selectByDTO(learnSysUserDTO);
	}

	@Override
	public LearnSysUserVO modify(LearnSysUserDTO dto) {
		if (dto.getId() == null || dto.getId() <= 0) {
			LearnSysUser domain = new LearnSysUser(dto);
			learnSysUserDao.insertPart(domain);
			return domain.toConvertBeanVO();
		} else {
			Map map=new HashMap<>();
			map.put("id", dto.getId());
			LearnSysUserVO vo = (LearnSysUserVO)learnSysUserDao.select(map);
			if(vo == null){
				throw new IllegalArgumentException("请求参数异常");
			}
			BeanMapper.copy(dto, vo);
			learnSysUserDao.update(vo);
			return vo;
		}
	}

	@Override
	public PageInfo<LearnSysUserVO> listPage(int pageNum, int pageSize, String orderBy, Map map) {
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<LearnSysUserVO> learnSysUserVOList = learnSysUserDao.list(map);
		return new PageInfo<>(learnSysUserVOList);
	}
}