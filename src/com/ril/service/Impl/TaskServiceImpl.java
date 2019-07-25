package com.ril.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Machine;
import com.ril.bean.Task;
import com.ril.bean.TaskExample;
import com.ril.mapper.MachineMapper;
import com.ril.mapper.TaskMapper;
import com.ril.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskMapper taskMapper;
	@Autowired
	MachineMapper machineMapper;
	
	public Boolean add(Task t) {
		
		int max =0;
		TaskExample e = new TaskExample();
		e.createCriteria().andMachidEqualTo(t.getMachid()).andStatusEqualTo(0);
		if(!taskMapper.selectByExample(e).isEmpty())
			max=taskMapper.getMax(t.getMachid());
		t.setExecutionorder(max+1);
		t.setStatus(0);
		int b =taskMapper.insert(t);
		if(b!=0){
			return true;
		}else{
			return false;
		}
	}

	public Boolean delete(Long id) {
		Task t = taskMapper.selectByPrimaryKey(id);
		Long machid=t.getMachid();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderGreaterThan(eo)
		.andMachidEqualTo(machid)
		.andStatusEqualTo(0);
		e.setOrderByClause(" executionorder ASC ");
		List<Task> list = taskMapper.selectByExample(e);
		//后面的都加1
		if(!list.isEmpty()){
			for(Task task: list){
				task.setExecutionorder((task.getExecutionorder()-1));
				taskMapper.updateByPrimaryKey(task);
			}
		}
		
		//设置状态2为删除
		t.setStatus(2);
		int b =taskMapper.updateByPrimaryKey(t);
		if(b!=0){
			return true;
		}else{
			return false;
		}
	}

	public Boolean down(Long id) {
		Task t = taskMapper.selectByPrimaryKey(id);
		Long machid=t.getMachid();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderEqualTo(eo+1)
		.andMachidEqualTo(machid)
		.andStatusEqualTo(0);
		List<Task> list = taskMapper.selectByExample(e);
		
		//与下面互换排序
		if(list.isEmpty()){
			t.setExecutionorder(eo+1);
			taskMapper.updateByPrimaryKey(t);
		}else{
			Task next = list.get(0);
			next.setExecutionorder(eo);
			t.setExecutionorder(eo+1);
			taskMapper.updateByPrimaryKey(t);
			taskMapper.updateByPrimaryKey(next);
		}
		//可添加判断条件
		return true;
	}


	public Boolean up(Long id) {
		Task t = taskMapper.selectByPrimaryKey(id);
		Long machid=t.getMachid();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderEqualTo(eo-1)
		.andMachidEqualTo(machid)
		.andStatusEqualTo(0);
		List<Task> list = taskMapper.selectByExample(e);
		
		//与上面互换排序
		if(list.isEmpty()){
			t.setExecutionorder(eo-1);
			taskMapper.updateByPrimaryKey(t);
		}else{
			Task next = list.get(0);
			next.setExecutionorder(eo);
			t.setExecutionorder(eo-1);
			taskMapper.updateByPrimaryKey(t);
			taskMapper.updateByPrimaryKey(next);
		}
		//可添加判断条件
		return true;
	}
	
	public Boolean top(Long id) {
		Task t = taskMapper.selectByPrimaryKey(id);
		Long machid=t.getMachid();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderLessThan(eo)
		.andMachidEqualTo(machid)
		.andStatusEqualTo(0);
		e.setOrderByClause(" executionorder ASC ");
		List<Task> list = taskMapper.selectByExample(e);
		
		//前面的都加1
		if(!list.isEmpty()){
			for(Task task: list){
				task.setExecutionorder((task.getExecutionorder()+1));
				taskMapper.updateByPrimaryKey(task);
			}
		}
		//置顶
		t.setExecutionorder(1);
		taskMapper.updateByPrimaryKey(t);
		
		return true;
	}

	
	//获取下一个任务
	public Boolean next(Long machid) {
		Machine m =  machineMapper.selectByPrimaryKey(machid);
		TaskExample te = new TaskExample();
		te.createCriteria()
		.andMachidEqualTo(machid)
		.andExecutionorderEqualTo(1)
		.andStatusEqualTo(0);
		List<Task> listT = taskMapper.selectByExample(te);
		if(listT.isEmpty()){
			m.setNoc("0");
			m.setStocknumber("0");
			m.setValue(0);
			m.setCount((long)0);
			machineMapper.updateByPrimaryKey(m);
		}else{
			Task t = listT.get(0);
			m.setNoc(t.getNoc());
			m.setStocknumber(t.getStocknumber());
			m.setValue(t.getValue());
			m.setCount((long)0);
			m.setMode(0);
			machineMapper.updateByPrimaryKey(m);
			t.setStatus(1);
			t.setExecutionorder(0);
			taskMapper.updateByPrimaryKey(t);
		}


		TaskExample te2 = new TaskExample();
		te2.createCriteria()
		.andStatusEqualTo(0)
		.andMachidEqualTo(machid);
		List<Task> listT2 = taskMapper.selectByExample(te2);
		for(Task t:listT2){
			t.setExecutionorder((t.getExecutionorder()-1));
			taskMapper.updateByPrimaryKey(t);
		}
		return true;
	}

	public List<Task> listEO(Long machid) {
		TaskExample e = new TaskExample();
		e.createCriteria()
		.andStatusEqualTo(0)
		.andMachidEqualTo(machid);
		e.setOrderByClause(" executionorder ASC ");
		return taskMapper.selectByExample(e);
	}

}
