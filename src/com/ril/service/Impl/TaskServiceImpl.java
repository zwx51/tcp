package com.ril.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ril.bean.Task;
import com.ril.bean.TaskExample;
import com.ril.mapper.TaskMapper;
import com.ril.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskMapper taskMapper;
	
	public Boolean add(Task t) {
		
		return null;
	}

	public Boolean delete(Long id) {
		Task t = taskMapper.selectByPrimaryKey(id);
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
		String sn =t.getStocknumber();
		String noc = t.getNoc();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderEqualTo(eo+1)
		.andNocEqualTo(noc)
		.andStocknumberEqualTo(sn)
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
		String sn =t.getStocknumber();
		String noc = t.getNoc();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderEqualTo(eo-1)
		.andNocEqualTo(noc)
		.andStocknumberEqualTo(sn)
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
		String sn =t.getStocknumber();
		String noc = t.getNoc();
		int eo = t.getExecutionorder();
		
		TaskExample e=new TaskExample();
		e.createCriteria()
		.andExecutionorderLessThan(eo)
		.andStatusEqualTo(0);
		e.setOrderByClause(" executionorder ASC ");
		List<Task> list = taskMapper.selectByExample(e);
		if(!list.isEmpty()){
			for(Task task: list){
				task.setExecutionorder((task.getExecutionorder()+1));
				taskMapper.updateByPrimaryKey(task);
			}
		}
		t.setExecutionorder(1);
		taskMapper.updateByPrimaryKey(t);
		return true;
	}

	//获取下一个任务
	public Boolean next(Long machid) {
		
		return null;
	}

}
