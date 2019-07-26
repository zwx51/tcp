package com.ril.service.Impl;

import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.mapper.MachineMapper;
import com.ril.mapper.TCPRecordMapper;
import com.ril.service.DisplayService;
import com.ril.socket.ServerThread;

@Service
public class DisplayServiceImpl implements DisplayService {

	private Set<Long> set;
	
	@Autowired
	MachineMapper machineMapper;
	@Autowired
	TCPRecordMapper tCPRecordMapper;
	
	
	public List<Map<?, ?>> getMachinestatus(String name) {
		synchronized(ServerThread.set)
		{
			set=new TreeSet<Long>(ServerThread.set);
		}
		List<Map<?,?>> list = new ArrayList<Map<?,?>>();
		for(Long machid:set){

			Map<String,String> map = new HashMap<String,String>();
			Machine m = machineMapper.selectByPrimaryKey(machid);
			if(null==m){
				continue;
			}
			map.put("machid", m.getMachid().toString());
			map.put("name", m.getName());
			map.put("noc", m.getNoc());
			map.put("stocknumber", m.getStocknumber());
			map.put("value",m.getValue().toString());
			map.put("count", m.getCount().toString());
			map.put("mode", m.getMode().toString());
			
			Double speed=(double)0;
			DecimalFormat df = new DecimalFormat( "0.00");  

			if(!m.getNoc().equals(0)){
				speed=tCPRecordMapper.getSpeed(m.getNoc(), m.getStocknumber(), m.getMachid(), 4);

			}
			if(null!=speed){
				map.put("speed", df.format(speed));
			}else{
				map.put("speed",Long.toString((long)0));
			}
			list.add(map);
		}
		return list;
	}

}
