package com.ril.service;

import com.ril.bean.Task;

public interface TaskService {
	Boolean up(Long id );
	Boolean down(Long id);
	Boolean top(Long id);
	Boolean delete(Long id);
	Boolean add(Task t);
	Boolean next(Long machid);
}
