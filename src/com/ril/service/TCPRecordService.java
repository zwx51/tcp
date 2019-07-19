package com.ril.service;

import java.util.Date;
import java.util.List;

import com.ril.bean.TCPRecord;

public interface TCPRecordService {
	Boolean addRecord(TCPRecord record);

	List<TCPRecord> listRecord(Date start, Date end, Long machid);
}
