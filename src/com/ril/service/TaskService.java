package com.ril.service;

import java.util.List;

import com.ril.bean.Task;

public interface TaskService {
	Boolean up(Long id);
	
	Boolean down(Long id);
	
	Boolean top(Long id);
	
	Boolean delete(Long id);
	
	Boolean add(Task t);
	
	List<Task> listEO(Long machid);
	
	Boolean next(Long machid);
}
