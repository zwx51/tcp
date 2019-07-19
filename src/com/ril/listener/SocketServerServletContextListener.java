package com.ril.listener;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.factory.annotation.Autowired;

import com.ril.service.TCPRecordService;
import com.ril.socket.Server;

/*
 * 开启SocketServer的Listener，监听数据机计数器的连接。
 *  
*/
public class SocketServerServletContextListener implements
		ServletContextListener {
	// 销毁ServletContext对象的时候自动调用该方法
	private Server s;

	public void contextDestroyed(ServletContextEvent arg0) {
		s.closeSocketServer();
		s.interrupt();
	}

	// 创建SErvletContext对象的时候自动调用该方法
	public void contextInitialized(ServletContextEvent arg0) {
		if (s == null) {
			//此处设置SocketServer端口
			s = new Server(23000);
		}
		s.start();
	}

}
