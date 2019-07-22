package com.ril.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ril.bean.Machine;
import com.ril.service.MachineService;



@Controller
@RequestMapping("/Machine")
public class MachineController extends BaseController{
	
	@Autowired
	MachineService machineService;
	
	/*
	 * 添加机器
	 */
	@RequestMapping(value ="/addMachine",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public Object addMachine(Long machid ,String name){
		//检查重复
		if(machineService.getMachine(machid)!=null){	
			return fail("机器号已存在");
		}
		if(machineService.addMachine(machid, name))
			return success("添加成功");
		else
			return fail("添加数据失败");
    }
	
	/*
	 * 删除机器
	 */
	@RequestMapping(value ="/deleteMachine",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public Object deleteMachine(@RequestParam(value ="machids[]") List<Long> machids){
		if(machineService.deleteMachine(machids))
			return success("添加成功");
		else
			return fail("添加数据失败");
	}
	
	/*
	 * 查找机器
	 * @param 机器名 机器号范围
	 * */
	@RequestMapping(value ="/selectMachine",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<Machine> selectMachine(String name , Long start , Long end){
		return machineService.getMachine(name,start,end);
	}
	
	/*
	 * 修改机器名称
	 */
	@RequestMapping(value ="/changeMachineName",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public Object changeMachineName(String name , Long machid){
		if(machineService.changeMachineName(name,machid))
		return success("修改成功");
		else
			return fail("修改失败");
	}
	/*
	 * 修改机器NOC
	 */
	@RequestMapping(value ="/changeMachineNOC",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public Object changeMachineNOC(Integer noc,Integer value , String date , Long machid){
		if(machineService.changeMachineNOC(noc,machid,value,date))
		return success("修改成功");
		else
			return fail("修改失败");
	}
	
}
