package com.ril.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ril.bean.TCPRecord;
import com.ril.service.MachineService;
import com.ril.service.TCPRecordService;
import com.ril.util.BeanContext;

public class ServerThread extends Thread {
	//信息匹配正则式
	final Pattern r = Pattern.compile("\\{\"(.*?)\":\"(.*?)\",\"(.*?)\":\"(.*?)\",\"(.*?)\":\"(.*?)\"\\}");
	
	static public HashSet<Long> set = new HashSet<Long>();
	
	Long Machid;
	Socket socket = null;
	
	TCPRecordService tCPRecordService = (TCPRecordService)BeanContext.getBean(TCPRecordService.class);
	MachineService machineService = (MachineService)BeanContext.getBean(MachineService.class);
	
	public ServerThread(Socket socket) {
		this.socket = socket;
		try {
			//超时时间180秒
			this.socket.setSoTimeout(180000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	// 线程执行处理收到的信息
	public void run() {
		//线程用额外方法获取Spring中的类
		Boolean flag = true;				//线程运行标志符
		DataInputStream in = null;			//输入流
		DataOutputStream out = null; 		//输出流
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			while (flag) {
				byte[] b = new byte[50];
				in.read(b);					//读取
				String s = new String(b).trim();		//转化为String
				// 判断停止信号，接受到字符"s"
				if (s.equals("s")) {
					System.out
							.println(socket.getRemoteSocketAddress() + "请求断开");
					System.out.println("socketclose");
					socket.close();
					System.out.println("threadclose");
					
					// 设置线程结束标志
					flag = false;
					// "z"返回表示已接受到数据
					out.write('z');
				}
				//检测是否匹配格式{"?":"?","?":"?","?":"?"}
				else if(r.matcher(s).matches() ){
					JSONObject tcpRecordJson = JSONObject.parseObject(s);
					TCPRecord record = JSON.toJavaObject(tcpRecordJson,TCPRecord.class);		//将获取的JSON字符串转化为类
					record.setRecordtime(new Date());											//获取当前时间
					synchronized(set) 															// 这里会保证, set每次只会被一个thread调用
					{
						set.add(record.getMachid());
					}
					
					if(Machid==null){
						Machid=record.getMachid();
					}

					tCPRecordService.addRecord(record);
					out.write('z');
				}
				//机器获取NOC
				else if(s.equals("noc")){
					out.write('z');
					
				}
				else {
					out.write('z');
				}
			}
		} catch (SocketException e) {
			// 连接断开
			// e.printStackTrace();
			System.out.println(socket.getRemoteSocketAddress() + "连接断开");
			e.getMessage().equals(
					"Software caused connection abort: socket write error");
			if(Machid!=null){
				set.remove(Machid);
			}
			
		} catch (SocketTimeoutException e) {
			// 超时
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println(socket.getRemoteSocketAddress() + "IO错误");
				e1.printStackTrace();
			}
			System.out.println(socket.getRemoteSocketAddress() + "连接超时");
			if(Machid!=null){
				set.remove(Machid);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(socket.getRemoteSocketAddress() + "IO错误");
			e.printStackTrace();
			if(Machid!=null){
				set.remove(Machid);
			}
		}
		

	}

}