package com.learn.management.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.learn.management.constant.OrderEnum;
import com.learn.management.util.DateUtil;
import com.learn.management.system.rpc.vo.LearnSysUserVO;
import com.learn.management.system.rpc.dto.LearnSysUserDTO;
import com.learn.management.system.rpc.service.ILearnSysUserService;
import com.learn.management.system.rpc.dto.LearnSysUserDTO;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * @author HRH
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/learnsysuser")
public class LearnSysUserController{

	private final Logger logger = LoggerFactory.getLogger(LearnSysUserController.class);

	@Autowired
	private ILearnSysUserService iLearnSysUserService;

	@ApiOperation(value = "新增记录", notes = "新增记录")
	@PostMapping
	public LearnSysUserVO add(@RequestBody LearnSysUserDTO dto){
		//todo 以下需要开发人员手动检查新增字段
		LearnSysUserDTO ddl = new LearnSysUserDTO();
		BeanUtils.copyProperties(dto, ddl);
		//ddl.setCreateBy(UserDetailHelper.getUserId().toString());
		//ddl.setCreateTime(DateUtil.nowTime());
		try {
			return iLearnSysUserService.modify(ddl);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "编辑记录", notes = "编辑记录")
	@PutMapping
	public LearnSysUserVO update(@RequestBody LearnSysUserDTO dto){
		//todo 以下需要开发人员手动检查编辑字段
		if (dto.getId() == null || dto.getId() <= 0) {
			throw new IllegalArgumentException("请求参数错误,id不能为空");
		}
		LearnSysUserDTO ddl = new LearnSysUserDTO();
		BeanUtils.copyProperties(dto, ddl);
		//ddl.setModifyBy(UserDetailHelper.getUserId().toString());
		//ddl.setModifyTime(DateUtil.nowTime());
		try {
			return iLearnSysUserService.modify(ddl);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "删除记录", notes = "删除记录")
	@DeleteMapping("/{id}")
	public void delete(@ApiParam(value = "记录ID", required = true) @PathVariable Long id) {
		if (id == null) {
			throw new IllegalArgumentException("请求参数错误");
		}
		LearnSysUserDTO ddl = new LearnSysUserDTO();
		//ddl.setModifyBy(UserDetailHelper.getUserId().toString());
		//ddl.setModifyTime(DateUtil.nowTime());
		ddl.setId(id);
		try {
			iLearnSysUserService.deleteByDTO(ddl);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@ApiOperation(value = "查询记录", notes = "查询记录")
	@GetMapping("/{pageNum}/{pageSize}")
	public PageInfo<LearnSysUserVO> listPage(@ApiParam(value = "分页索引", required = true, defaultValue = "1") @PathVariable int pageNum,
	                                                @ApiParam(value = "分页大小", required = true, defaultValue = "20") @PathVariable int pageSize,
	                                                @ApiParam(value = "排序字段[createTime,id,deleted]，索引为1开始，不填写默认按照创建时间降序排序", required = false) @RequestParam(value = "sort", required = false, defaultValue = "-1") Integer sort,
	                                                @ApiParam(value = "记录ID", required = false) @RequestParam(value = "id", required = false) Long id) {
		if (pageSize > 50) {
			throw new IllegalArgumentException("请求参数错误,pageSize最大50");
		}
		//排序处理
		if (sort == null || sort == 0) {
			sort = 1;
		}
		//该字符串要参考数据库实际字段名称
		String orderByStr = "create_time,id,deleted";
		String[] orderByList = orderByStr.split(",");
		if (orderByList.length < (Math.abs(sort))) {
			throw new IllegalArgumentException("请求参数错误,sort参数错误");
		}
		String orderBy = orderByList[Math.abs(sort) - 1];
		if (sort > 0) {
			orderBy = orderBy + OrderEnum.ASC;
		}
		if (sort < 0) {
			orderBy = orderBy + OrderEnum.DESC;
		}
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			//todo 以下需要开发人员手动改动查询字段
			if (id != null){
				map.put("id", id);
			}
			map.put("deleted", 0);
			return iLearnSysUserService.listPage(pageNum, pageSize, orderBy, map);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}