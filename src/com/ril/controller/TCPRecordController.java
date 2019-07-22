package com.ril.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ril.bean.TCPRecord;
import com.ril.service.TCPRecordService;



@Controller
@RequestMapping("/tcprecord")
public class TCPRecordController extends BaseController{

	@Autowired
	TCPRecordService tCPRecordService;
	
	
	/*
	 * 查询记录
	 * */
	@RequestMapping(value ="/listRecord",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<TCPRecord> listRecord(
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date start,
    		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date end,
    		Long machid
    		){
		return tCPRecordService.listRecord(start,end,machid);
    }
}