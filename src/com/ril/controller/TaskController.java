package com.ril.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

	//列出列表
	@RequestMapping("/list")
	public Map<?,?> list(Long machid){
		return success("");
	}
	
	//排期向上移动
	@RequestMapping("/up")
	public Map<?,?> up(Long id){
		return success("");
	}
	
	//排期向下移动
	@RequestMapping("/down")
	public Map<?,?> down(Long id){
		return success("");
	}
	//置顶
	@RequestMapping("/top")
	public Map<?,?> top(Long id){
		return success("");
	}
	
	//删除
	@RequestMapping("/delete")
	public Map<?,?> delete(Long id){
		return success("");
	}
	//添加
	@RequestMapping("/add")
	public Map<?,?> add(Long machid,String nOC,String stocknumber,Integer value ,Date date){
		return success("");
	}
	
}
