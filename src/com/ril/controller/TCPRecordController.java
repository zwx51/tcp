package com.ril.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/*
	 * 查询记录
	 * */
	@RequestMapping(value ="/listRecord",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<TCPRecord> listRecord(String front,String back,Long machid){
		Date start=null,end=null;
		if(null==front||front.equals("")){
			front="1980-01-01 01:01:01";
		}
		if(null==back||back.equals("")){
			back="2099-01-01 01:01:01";
		}
		try {
			start=format.parse(front);
			end=format.parse(back);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tCPRecordService.listRecord(start,end,machid);
    }
	
	@RequestMapping(value ="/listHistory",produces = {"application/json;charset=utf-8"})
	@ResponseBody
    public List<Map<String,String>> listHistory(
    		String strstartFront,
    		String strendFront,
    		String strstartBack,
    		String strendBack,
    		Long machid, String stocknumber, String noc
    		) {
		String sstartFront=strstartFront;
		String sendFront=strendFront;
		String sstartBack=strstartBack;
		String sendBack=strendBack;
		if(null==sstartFront||sstartFront.equals("")){
			sstartFront="1980-01-01 01:01:01";
		}
		if(null==sendFront||sendFront.equals("")){
			sendFront="1980-01-01 01:01:01";
		}
		if(null==sstartBack||sstartBack.equals("")){
			sstartBack="2099-01-01 01:01:01";
		}
		if(null==sendBack||sendBack.equals("")){
			sendBack="2099-01-01 01:01:01";
		}
		Date startFront=null, endFront=null, startBack=null, endBack=null;
		try {
			startFront=format.parse(sstartFront);
			endFront=format.parse(sendFront);
			startBack=format.parse(sstartBack);
			endBack=format.parse(sendBack);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tCPRecordService.listhistory(machid, stocknumber, 
				noc, startFront, startBack, endFront, endBack);
    }
	
	
	
}