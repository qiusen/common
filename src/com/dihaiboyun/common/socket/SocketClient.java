package com.dihaiboyun.common.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

/**
 * Socket客户端
 * @author qiusen
 *
 */
public class SocketClient extends Thread {

	public static String serverIp = "127.0.0.1";
	public static int port = 9999;
	
	private Socket s = null;
	
	/**
	 * 连接服务器
	 * @param clientId
	 * @return
	 */
	public PrintWriter connectServer(String clientId){
		PrintWriter pw = null;
		try {
			//连接
			s = new Socket(serverIp, port);
			OutputStream os = s.getOutputStream();
			
			//发送客户端ID
			pw = new PrintWriter(new OutputStreamWriter(os), true);
			pw.println("{clientId:'" + clientId + "'}");
			pw.flush();
		} catch (IOException e) {
			System.out.println("连接失败！！！");
			e.printStackTrace();
		}
		
		return pw;
	}
	
	/* 接收消息
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		InputStream in;
		BufferedReader br;
		try {
			in = s.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String content = br.readLine();
System.out.println(content);
			while (content != null) {
				JSONObject json = JSONObject.fromObject(content); 
				String fromClientId = json.getString("fromClientId"); 
				String message = json.getString("message"); 
				System.out.println("收到来自：" + fromClientId + " 的消息，内容：" + URLDecoder.decode(message, "UTF-8"));
				content = br.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		String clientId = "qiusen";
		SocketClient sc = new SocketClient();
		PrintWriter pw = sc.connectServer(clientId);
		if(pw!=null){
			System.out.println(clientId + "连接" + serverIp + " : " + port + " 成功");
		}else{
			System.out.println(clientId + "连接" + serverIp + " : " + port + " 失败");
			return ;
		}
		
		//接收消息
		sc.start();
		
		//发消息
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try {
				System.out.print("发给：");
				String toClientId = br.readLine();
				System.out.print("内容：");
				String message = br.readLine();
				System.out.println(message);
				pw.println("{toClientId:'" + toClientId + "',message:'" + URLEncoder.encode(message, "UTF-8") + "'}");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
