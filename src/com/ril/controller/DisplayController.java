package com.ril.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/display")
public class DisplayController extends BaseController {
	
	@RequestMapping(value ="/listRecord",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<Map<?,?>> listRecord(String name){
		return null;
    }
}
