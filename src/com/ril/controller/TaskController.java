package com.ril.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ril.bean.Task;
import com.ril.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

	@Autowired
	TaskService taskService;
	
	//列出列表
	@RequestMapping("/listEO")
	@ResponseBody
	public List<Task> list(Long machid){
		return taskService.listEO(machid);
	}
	
	//排期向上移动
	@RequestMapping("/up")
	@ResponseBody
	public Map<?,?> up(Long id){
		if(taskService.up(id)){
			return success("");
		}else{

			return fail("");
		}
	}
	
	//排期向下移动
	@RequestMapping("/down")
	@ResponseBody
	public Map<?,?> down(Long id){
		if(taskService.down(id)){
			return success("");
		}else{

			return fail("");
		}
	}
	//置顶
	@RequestMapping("/top")
	@ResponseBody
	public Map<?,?> top(Long id){
		if(taskService.top(id)){
			return success("");
		}else{

			return fail("");
		}
	}
	
	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Map<?,?> delete(Long id){
		if(taskService.delete(id)){
			return success("删除成功");
		}else{
			return fail("删除失败");
		}
	}
	//添加
	@RequestMapping("/add")
	@ResponseBody
	public Map<?,?> add(Long machid,String noc,
			String stocknumber,Integer value ,String date){
		Task t = new Task();
		t.setDate(date);
		t.setMachid(machid);
		t.setNoc(noc);
		t.setStocknumber(stocknumber);
		t.setValue(value);
		if(taskService.add(t)){
			return success("添加成功");
		}else{

			return fail("添加失败");
		}
	}
	
}
