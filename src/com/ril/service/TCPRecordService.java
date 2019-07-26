package com.ril.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ril.bean.TCPRecord;

public interface TCPRecordService {
	Boolean addRecord(TCPRecord record);

	List<TCPRecord> listRecord(Date start, Date end, Long machid);
	
	List<Map<String,String>> listhistory(Long machid, String stocknumber, 
			String noc, Date startFront, Date startBack, 
			Date endFront,Date endBack);
}
