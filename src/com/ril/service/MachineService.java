package com.ril.service;

import java.util.Date;
import java.util.List;

import com.ril.bean.Machine;

public interface MachineService {
	public Machine getMachine(Long machid);
	public Boolean addMachine(Long machid, String name);
	public Boolean deleteMachine(List<Long> machids);
	public List<Machine> getMachine(String name, Long start, Long end);
	public Boolean changeMachineName(String name, Long machid);
	public Boolean changeMachineNOC(String noc, Long machid, Integer value,
			String date);
	public Boolean changeMachineMode(Long machid,Integer mode);
}
