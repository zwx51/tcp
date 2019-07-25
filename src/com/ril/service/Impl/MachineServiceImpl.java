package com.ril.service.Impl;

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
		m.setName(name);
		m.setMode(0);
		m.setNoc("0");
		m.setStocknumber("0");
		m.setValue(0);
		m.setCount((long)0);
		int c = machineMapper.insert(m);
		if(c!=0)
			return true;
		else
			return false;
	}

	//删除机器
	public Boolean deleteMachine(Long machid) {
		
		int c = machineMapper.deleteByPrimaryKey(machid);
		if(c!=0)
			return true;
		else
			return false;
	}

	//查询机器
	public List<Machine> getMachine(String name) {
		MachineExample e =new MachineExample();
		e.createCriteria()
		.andNameLike('%'+name+'%');
		e.setOrderByClause(" machid ASC ");
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



}
