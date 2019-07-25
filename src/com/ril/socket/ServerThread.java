package com.ril.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ril.bean.Machine;
import com.ril.bean.TCPRecord;
import com.ril.mapper.MachineMapper;
import com.ril.service.MachineService;
import com.ril.service.TCPRecordService;
import com.ril.service.TaskService;
import com.ril.util.BeanContext;

public class ServerThread extends Thread {
	//信息匹配正则式
	final Pattern r = Pattern.compile("\\{\"(.*?)\":\"(.*?)\",\"(.*?)\":\"(.*?)\",\"(.*?)\":\"(.*?)\"\\}");
	
	static public Set<Long> set = new TreeSet<Long>();
	
	private Long machid;
	private int mode=0;
	private Socket socket = null;
	//加载Service
	private TCPRecordService tCPRecordService = (TCPRecordService)BeanContext.getBean(TCPRecordService.class);
	private MachineService machineService = (MachineService)BeanContext.getBean(MachineService.class);
	private TaskService taskService = (TaskService)BeanContext.getBean(TaskService.class);
	private MachineMapper machineMapper = (MachineMapper)BeanContext.getBean(MachineMapper.class);
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
		try {
			Machine m =null;					//
			
			Boolean flag = true;				//线程运行标志符
			DataInputStream in = null;			//输入流
			DataOutputStream out = null; 		//输出流

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			while (flag) {
				String s ="";
				while(true){
					char a =(char)in.readByte();
				
					//停止符’/‘
					if(a=='/')
						break;
					s+=a;
				}
				
				//跳过空字符
				if(s.equals(""))
					continue;
				
				System.out.println("收到："+s);
				
				
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
				
				
				//m开头的为机器号指令，获取机器的机器号
				else if(s.startsWith("m")){
					machid = Long.valueOf(s.replace("m", "0"));
				}
				
				
				//检测是否匹配格式{"?":"?","?":"?","?":"?"}，是则是计数模式信息
				else if(r.matcher(s).matches() ){
					JSONObject tcpRecordJson = JSONObject.parseObject(s);
					TCPRecord record = JSON.toJavaObject(tcpRecordJson,TCPRecord.class);		//将获取的JSON字符串转化为类
					record.setRecordtime(new Date());											//获取当前时间
					
					if(machid==null){
						synchronized(set) 															// set需要一个同步锁
						{
							set.add(record.getMachid());
						}
						machid=record.getMachid();
					}
					
					//更新机器状态
					Machine m1=machineMapper.selectByPrimaryKey(machid);
					//模式与计数
					if(mode!=record.getMode()){
						mode=record.getMode();
						m1.setMode(mode);
						m1.setCount(record.getCount());
						machineMapper.updateByPrimaryKey(m1);
					}
					//计数
					else{
						m1.setCount(record.getCount());
						machineMapper.updateByPrimaryKey(m1);
					}
					
					
					//添加记录信息
					Boolean ar = tCPRecordService.addRecord(record);
					if(ar){
						synchronized(set) 															
						{
							set.add(record.getMachid());
						}
					}
					out.write('z');
				}
				
				//机器获取NOC
				else if(s.equals("noc")){
					//System.out.println("getNOC");
					if( null == m)
						m = machineService.getMachine(machid);
					out.writeBytes(m.getNoc()+"/");
				}
				
				//机器获取物料编号
				else if(s.equals("sn")){
					//System.out.println("getsn");
					if( null == m)
						m = machineService.getMachine(machid);
					out.writeBytes(m.getStocknumber().toString()+"/");
				}
				
				//机器获取数量
				else if(s.equals("value")){
					//System.out.println("getValue");
					if( null == m )
						m = machineService.getMachine(machid);
					out.writeBytes(m.getValue().toString()+"/");
				}
				
				
				else if(s.equals("end")){
					taskService.next(machid);
					m=machineService.getMachine(machid);
				}
				
				else {
					//out.write('z');
				}
			}
		} catch (SocketException e) {
			// 连接断开
			// e.printStackTrace();
			System.out.println(socket.getRemoteSocketAddress() + "连接断开");
			e.getMessage().equals(
					"Software caused connection abort: socket write error");

		} catch (SocketTimeoutException e) {
			// 超时
			System.out.println(socket.getRemoteSocketAddress() + "连接超时");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(socket.getRemoteSocketAddress() + "IO错误");
			e.printStackTrace();

		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(socket.getRemoteSocketAddress() + "未知错误");
			e.printStackTrace();
		}finally{
			try {
				if(machid!=null){
					synchronized(set){
						set.remove(machid);
					}
				}
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}