package com.ril.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.bean.MachineExample;
import com.ril.mapper.MachineMapper;
import com.ril.service.MachineService;

@Service
public class MachineServiceImpl implements MachineService {
	
	@Autowired
	MachineMapper machineMapper;
	
	//id获取机器
	public Machine getMachine(Long machid) {
		return machineMapper.selectByPrimaryKey(machid);
	}
	
	//添加机器
	public Boolean addMachine(Long machid, String name) {
		Machine m=new Machine();
		m.setMachid(machid);
		System.out.println(name);
		m.setName(name);
		int c = machineMapper.insert(m);
		if(c!=0)
			return true;
		else
			return false;
	}

	//删除机器
	public Boolean deleteMachine(List<Long> machids) {
		MachineExample e = new MachineExample();
		e.createCriteria().andMachidIn(machids);
		
		int c = machineMapper.deleteByExample(e);
		if(c!=0)
			return true;
		else
			return false;
	}

	//查询机器
	public List<Machine> getMachine(String name, Long start, Long end) {
		MachineExample e =new MachineExample();
		e.createCriteria()
		.andNameLike('%'+name+'%')
		.andMachidBetween(start, end);
		return machineMapper.selectByExample(e);
	}

	//变成机器名
	public Boolean changeMachineName(String name, Long machid) {
		Machine m = machineMapper.selectByPrimaryKey(machid);
		m.setName(name);
		int c = machineMapper.updateByPrimaryKey(m);
		if(c!=0)
			return true;
		else
			return false;
	}

	//变更NOC
	public Boolean changeMachineNOC(String noc, Long machid, Integer value,
			String date) {
		Machine m = machineMapper.selectByPrimaryKey(machid);
		m.setNoc(noc);
		m.setValue(value);
		int c = machineMapper.updateByPrimaryKey(m);
		if(c!=0)
			return true;
		else
			return false;
	}
	//变更模式
	public Boolean changeMachineMode(Long machid, Integer mode) {
		Machine m = machineMapper.selectByPrimaryKey(machid);
		m.setMode(mode);
		int c = machineMapper.updateByPrimaryKey(m);
		if(c!=0)
			return true;
		else
			return false;
	}

}
