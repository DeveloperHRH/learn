package com.learn.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.learn.management.constant.SystemEnum;
import com.learn.management.domain.LearnSysTimetask;
import com.learn.management.model.AjaxJson;
import com.learn.management.util.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.learn.management.constant.OrderEnum;
import com.learn.management.system.rpc.vo.LearnSysTimetaskVO;
import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;
import com.learn.management.system.rpc.service.ILearnSysTimetaskService;
import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * @author HRH
 */
@Api(value = "LearnSysTimetask" , description = "定时任务管理" , tags = "LearnSysTimetaskController")
@RestController
@RequestMapping("/LearnSysTimetaskController")
public class LearnSysTimetaskController{

	@Autowired
	private ILearnSysTimetaskService sysTimetaskService;

	@Autowired(required=false)
	private Scheduler schedulerFactory;


	private final Logger logger = LoggerFactory.getLogger(LearnSysTimetaskController.class);

	@Autowired
	private ILearnSysTimetaskService iLearnSysTimetaskService;

	@ApiOperation(value = "新增记录", notes = "新增记录")
	@PostMapping
	public LearnSysTimetaskVO add(@RequestBody LearnSysTimetaskDTO dto){
		//todo 以下需要开发人员手动检查新增字段
		LearnSysTimetaskDTO ddl = new LearnSysTimetaskDTO();
		BeanUtils.copyProperties(dto, ddl);
		//ddl.setCreateBy(UserDetailHelper.getUserId().toString());
		//ddl.setCreateTime(DateUtil.nowTime());
		try {
			return iLearnSysTimetaskService.modify(ddl);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	@ApiOperation(value = "编辑记录", notes = "编辑记录")
	@PutMapping
	public LearnSysTimetaskVO update(@RequestBody LearnSysTimetaskDTO dto){
		//todo 以下需要开发人员手动检查编辑字段
		if (dto.getId() == null || dto.getId() <= 0) {
			throw new IllegalArgumentException("请求参数错误,id不能为空");
		}
		LearnSysTimetaskDTO ddl = new LearnSysTimetaskDTO();
		BeanUtils.copyProperties(dto, ddl);
		//ddl.setModifyBy(UserDetailHelper.getUserId().toString());
		//ddl.setModifyTime(DateUtil.nowTime());
		try {
			return iLearnSysTimetaskService.modify(ddl);
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
		LearnSysTimetaskDTO ddl = new LearnSysTimetaskDTO();
		//ddl.setModifyBy(UserDetailHelper.getUserId().toString());
		//ddl.setModifyTime(DateUtil.nowTime());
		ddl.setId(id);
		try {
			iLearnSysTimetaskService.deleteByDTO(ddl);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@ApiOperation(value = "查询记录", notes = "查询记录")
	@GetMapping("/{pageNum}/{pageSize}")
	public PageInfo<LearnSysTimetaskVO> listPage(@ApiParam(value = "分页索引", required = true, defaultValue = "1") @PathVariable int pageNum,
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
			return iLearnSysTimetaskService.listPage(pageNum, pageSize, orderBy, map);
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}



	/**
	 * 启动或者停止任务
	 */
	@ApiOperation(value = "启动或停止任务", notes = "启动或停止任务")
	@PostMapping("/startOrStopTask/{id}/{ident}")
	public @ResponseBody AjaxJson startOrStopTask(HttpServletRequest request,
												  @ApiParam(value = "记录ID", required = true) @PathVariable Long id,
												  @ApiParam(value = "标识{start : 启动 ,stop 停止}",required = true) @PathVariable String ident) {

		AjaxJson j = new AjaxJson();

		boolean isSuccess = false;
		LearnSysTimetaskVO vo = sysTimetaskService.selectById(id);

		if("start".equals(ident)){

			if (SystemEnum.TASK_EFFECT_YES.equals(vo.getIsEffect().toString())) {
				j.setMsg("该任务为禁用状态，请解除禁用后重新启动");
				return j;
			}

			if (SystemEnum.TASK_START.equals(vo.getIsStart().toString())) {
				j.setMsg("该任务当前已经启动，请停止后再试");
				return j;
			}

			isSuccess = startOrStop(vo,true);

		}else if("stop".equals(ident)){

			if (SystemEnum.TASK_STOP.equals(vo.getIsStart().toString())) {
				j.setMsg("该任务当前已经停止，重复操作");
				return j;
			}

			isSuccess = startOrStop(vo,false);

		}else{
			j.setMsg("未知的指令标识,请前端开发人员确认好API文档再进行对接!!!");
		}

		j.setMsg(isSuccess?"定时任务更新成功":"定时任务更新失败");
		return j;
	}




	//-------------------------------------------------------------------------------动态调整Spring的任务--------------------------------------------------------------------------------------

	/**
	 * 启动定时任务
	 * @param LearnSysTimetaskVO
	 * @throws SchedulerException
	 */
	private boolean startTask(LearnSysTimetaskVO vo){
		try {
			//向调度器中添加任务
			scheduleJob(vo);
			return true;
		} catch (SchedulerException e) {
			logger.error("startTask SchedulerException"+" cron_" + vo.getTaskCode() + e.getMessage());
		}
		return false;
	}

	/**
	 * 结束计划任务
	 * @param LearnSysTimetaskVO
	 * @throws SchedulerException
	 */
	private boolean endTask(LearnSysTimetaskVO vo){
		try{
			//quartz 2.2
			TriggerKey triggerKey = new TriggerKey("cron_" + vo.getTaskCode());
			//停止触发器
			schedulerFactory.pauseTrigger(triggerKey);
			//移除触发器
			schedulerFactory.unscheduleJob(triggerKey);
			JobKey jobKey = new JobKey(vo.getTaskCode());
			//删除任务
			schedulerFactory.deleteJob(jobKey);

			return true;
		}catch (SchedulerException e) {
			logger.error("endTask SchedulerException" + " cron_" + vo.getTaskCode() + e.getMessage());
		}
		return false;
	}

	/**
	 * 开关定时任务
	 * @param taskCode  任务code
	 * @param className 任务类名
	 * @param cronExpression  cron表达式
	 * @param start 启动或借宿:true-启动 false-结束
	 * @throws SchedulerException
	 */
	public boolean startOrStop(LearnSysTimetaskVO vo, boolean start){
		boolean isSuccess = start ? startTask(vo) : endTask(vo);
		if(isSuccess){
			vo.setIsStart(start?1:0);
			vo.setIsEffect(1);
			LearnSysTimetaskDTO dto = new LearnSysTimetaskDTO();
			BeanUtils.copyProperties(vo,dto);
			iLearnSysTimetaskService.modify(dto);
		}
		return isSuccess;
	}

	/**
	 * 注册 定时任务
	 * @param LearnSysTimetaskVO
	 * @throws SchedulerException
	 */
	private void scheduleJob(LearnSysTimetaskVO vo) throws SchedulerException {
		//build 要执行的任务
		JobDetail jobDetail = JobBuilder.newJob(MyClassLoader.getClassByScn(vo.getClassName()))
				.withIdentity(vo.getTaskCode())
				.storeDurably()
				.requestRecovery()
				.build();
		//根据Cron表达式 build 触发时间对象
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(vo.getCronExpression());
		//build 任务触发器
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity("cron_" + vo.getTaskCode())
				.withSchedule(cronScheduleBuilder)//标明触发时间
				.build();
		//向调度器注册 定时任务
		schedulerFactory.scheduleJob(jobDetail, cronTrigger);
	}




}