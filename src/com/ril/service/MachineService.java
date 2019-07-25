package com.ril.service;

import java.util.List;

import com.ril.bean.Machine;

public interface MachineService {
	public Machine getMachine(Long machid);
	public Boolean addMachine(Long machid, String name);
	public Boolean deleteMachine(Long machid);
	public List<Machine> getMachine(String name);
	public Boolean changeMachineName(String name, Long machid);

}
