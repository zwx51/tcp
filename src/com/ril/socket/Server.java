package com.ril.socket;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
	private ServerSocket ss;
	// 线程池第二个参数限定最大线程数，这里设定了300，一个线程一个连接
	ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 300, 60,
			TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

	public void run() {
		try {
			while (true) {
				//接受连接后开一个新线程处理
				Socket s = ss.accept();
				ServerThread x = new ServerThread(s);
				threadPool.execute(x);
				System.out.println(threadPool.getActiveCount()+" active");
				System.out.println(x.getName() + "start");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Socket服务器初始化
	public Server(int port) {
		try {
			//初始化ServerSocket设置端口
			this.ss = new ServerSocket(port);
			System.out
					.println("--------------ServerSocket start--------------");
		} catch (Exception e) {
			System.out.println("SocketThread创建socket服务出错");
			e.printStackTrace();
		}

	}
	
	
	//外部调用关闭SocketServer方法
	public void closeSocketServer() {
		try {
			if (null != ss && !ss.isClosed()) {
				ss.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}