package com.ril.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ril.service.DisplayService;

/*
 * 
 * 用于实时显示的Controller
 * */
@Controller
@RequestMapping("/display")
public class DisplayController extends BaseController {
	
	@Autowired
	DisplayService displayService;
	
	
	//获取当前连接的机器状态
	@RequestMapping(value ="/listMachinestatus",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<Map<?,?>> getMachinestatus(String name){
		return displayService.getMachinestatus(name);
    }

	
}
