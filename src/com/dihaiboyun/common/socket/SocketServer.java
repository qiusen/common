package com.dihaiboyun.common.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import net.sf.json.JSONObject;

/**
 * Socket连接服务端
 * @author qiusen
 *
 */
public class SocketServer {

	private int port = 9999;		//端口号
	
	private ServerSocket socket;
	
	
	public void launch(){
		
		//注册端口,开启Socket 监听
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//防止失败继续
		if(socket==null || socket.isClosed()){
			System.out.println("Socket服务启动失败");
			return ;
		}
		
		System.out.println("Socket服务启动成功，端口：" + port);
		
		while(true){
			try {
				Socket s = socket.accept();	//接受到1个连接
				String clientId = readClientId(s);
				System.out.println("客户端 " + clientId + " 接入成功...");
				SocketManage.clientMap.put(clientId, s);
				
				//开启消息转发线程
				new TransmitThread(clientId).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 获取客户端ID
	 * @param s
	 * @return
	 */
	private String readClientId(Socket s){
		String clientId = null;
		
		InputStream in = null;
		BufferedReader br = null;
		try {
			in = s.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String message = br.readLine();
			if(message!=null && message.trim().length()>0){
				//解析出客户端ID
				JSONObject json = JSONObject.fromObject(message); 
				clientId = json.getString("clientId"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return clientId;
	}
	
	public static void main(String args[]) {
		new SocketServer().launch();
	}
}
