package com.ril.service.Impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.bean.TCPRecord;
import com.ril.bean.TCPRecordExample;
import com.ril.bean.Task;
import com.ril.mapper.MachineMapper;
import com.ril.mapper.TCPRecordMapper;
import com.ril.mapper.TaskMapper;
import com.ril.service.TCPRecordService;

@Service
public class TCPRecordServiceImpl implements TCPRecordService {
	@Autowired
	TCPRecordMapper tCPRecordMapper;
	@Autowired
	MachineMapper machineMapper;
	@Autowired
	TaskMapper taskMapper;
	public Boolean addRecord(TCPRecord record) {
		//检查是否有登记机器号
		Machine m=null;
		try{
		m =machineMapper.selectByPrimaryKey(record.getMachid());
		
		if(null==m){
			return false;
		}
		if(record.getMode()!=m.getMode()){
			m.setMode(record.getMode());
			machineMapper.updateByPrimaryKey(m);
		}
		record.setNoc(m.getNoc());
		record.setStocknumber(m.getStocknumber());
		int c =tCPRecordMapper.insert(record);
		if(c!=0)
			return true;
		else
			return false;
		}catch(NullPointerException e){
			e.printStackTrace();
			return false;
		}
	}

	public List<TCPRecord> listRecord(Date start, Date end, Long machid) {
		TCPRecordExample e = new TCPRecordExample();
		e.createCriteria().andRecordtimeBetween(start, end).andMachidEqualTo(machid);

		return tCPRecordMapper.selectByExample(e);
	}

	public List<Map<String, String>> listhistory(Long machid,
			String stocknumber, String noc, Date startFront, Date startBack,
			Date endFront, Date endBack) {
		List<Task> listTask= taskMapper.selectTask(machid, stocknumber, noc);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if(listTask.isEmpty()){
			System.out.println("listTask.isEmpty()");
			return null;
		}
		/*
		 * 获取生产记录
		 * */
		for(Task task:listTask){
			Map<String,String> map=tCPRecordMapper.selectHistorybyTime(task.getNoc(), task.getStocknumber(), 
					task.getMachid(), 4, endFront, endBack, startFront, startBack);
			if(null==map||map.isEmpty()){
				continue;
			}
			Machine m = machineMapper.selectByPrimaryKey(task.getMachid());
			Map<String,String> remap = new HashMap<String,String>();
			
			Double speed=(double)0;
			DecimalFormat df = new DecimalFormat( "0.00");  

			if(!m.getNoc().equals(0)){
				speed=tCPRecordMapper.getSpeed(task.getNoc(), task.getStocknumber(), task.getMachid(), 4);

			}
			if(null!=speed){
				remap.put("speed", df.format(speed));
			}else{
				remap.put("speed",Long.toString((long)0));
			}
			
			
			remap.putAll(map);
			remap.put("value", task.getValue().toString());
			remap.put("name", m.getName());
			remap.put("noc", task.getNoc());
			remap.put("mode", "生产");
			remap.put("machid", task.getMachid().toString());
			remap.put("stocknumber", task.getStocknumber());
			result.add(remap);
		}
		/*
		 * 获取调试记录
		 * */
		for(Task task:listTask){
			Map<String,String> map=tCPRecordMapper.selectHistorybyTime(task.getNoc(), task.getStocknumber(), 
					task.getMachid(), 3, endFront, endBack, startFront, startBack);
			if(null==map||map.isEmpty()){
				continue;
			}
			Machine m = machineMapper.selectByPrimaryKey(task.getMachid());
			Map<String,String> remap = new HashMap<String,String>();
			
			Double speed=(double)0;
			DecimalFormat df = new DecimalFormat( "0.00");  

			if(!m.getNoc().equals(0)){
				speed=tCPRecordMapper.getSpeed(task.getNoc(), task.getStocknumber(), task.getMachid(), 3);

			}
			if(null!=speed){
				remap.put("speed", df.format(speed));
			}else{
				remap.put("speed",Long.toString((long)0));
			}
			
			
			remap.putAll(map);
			remap.put("value", task.getValue().toString());
			remap.put("name", m.getName());
			remap.put("noc", task.getNoc());
			remap.put("mode", "调试");
			remap.put("machid", task.getMachid().toString());
			remap.put("stocknumber", task.getStocknumber());
			result.add(remap);
		}
		/*
		 * 查询坏机
		 * */
		for(Task task:listTask){
			Map<String,String> map=tCPRecordMapper.selectHistorybyTime(task.getNoc(), task.getStocknumber(), 
					task.getMachid(), 3, endFront, endBack, startFront, startBack);
			if(null==map||map.isEmpty()){
				continue;
			}
			
			Machine m = machineMapper.selectByPrimaryKey(task.getMachid());
			Map<String,String> remap = new HashMap<String,String>();
			
			remap.put("speed","/");
			remap.putAll(map);
			remap.put("value", task.getValue().toString());
			remap.put("name", m.getName());
			remap.put("noc", task.getNoc());
			remap.put("mode", "坏机");
			remap.put("machid", task.getMachid().toString());
			remap.put("stocknumber", task.getStocknumber());
			result.add(remap);
		}
		return result;
	}
	
	
}
