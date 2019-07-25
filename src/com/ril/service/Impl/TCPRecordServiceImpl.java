package com.ril.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.bean.TCPRecord;
import com.ril.bean.TCPRecordExample;
import com.ril.mapper.MachineMapper;
import com.ril.mapper.TCPRecordMapper;
import com.ril.service.TCPRecordService;

@Service
public class TCPRecordServiceImpl implements TCPRecordService {
	@Autowired
	TCPRecordMapper tCPRecordMapper;
	@Autowired
	MachineMapper machineMapper;
	
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
	
	
}
