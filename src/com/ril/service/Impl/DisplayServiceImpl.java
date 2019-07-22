package com.ril.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.bean.TCPRecord;
import com.ril.bean.TCPRecordExample;
import com.ril.mapper.MachineMapper;
import com.ril.mapper.TCPRecordMapper;
import com.ril.service.DisplayService;
import com.ril.socket.ServerThread;

@Service
public class DisplayServiceImpl implements DisplayService {

	@SuppressWarnings("unchecked")
	private Set<Long> set;
	
	@Autowired
	MachineMapper machineMapper;
	@Autowired
	TCPRecordMapper tCPRecordMapper;
	
	
	public List<Map<?, ?>> getMachinestatus(String name) {
		Date now=new Date(); 
		Date anhourago;
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(now);  
		calendar.add(Calendar.HOUR, -1);  
		anhourago = calendar.getTime(); 
		synchronized(ServerThread.set)
		{
			set=new TreeSet<Long>(ServerThread.set);
		}
		List<Map<?,?>> list = new ArrayList<Map<?,?>>();
		for(Long machid:set){
			System.out.println(machid);
			Map<String,String> map = new HashMap<String,String>();
			Machine m = machineMapper.selectByPrimaryKey(machid);
			if(null==m){
				continue;
			}
			map.put("machid", m.getMachid().toString());
			map.put("noc", m.getNoc().toString());
			map.put("value",m.getValue().toString());
			
			Long speed =tCPRecordMapper.getSpeed(anhourago,now, m.getNoc(), m.getMachid(),m.getMode());
			if(null!=speed){
				map.put("speed", speed.toString());
			}else{
				map.put("speed",Long.toString((long)0));
			}
			list.add(map);
		}
		return list;
	}

}
