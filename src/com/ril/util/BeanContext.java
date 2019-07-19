package com.ril.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
 
@Configuration
public class BeanContext implements ApplicationContextAware {
 
	private static ApplicationContext applicationContext = null;
	
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanContext.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}
	
	public static Object getBean(Class c) throws BeansException {
	    return applicationContext.getBean(c);
	}
}