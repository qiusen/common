package com.dihaiboyun.common.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import net.sf.json.JSONObject;

/**
 * 传送消息线程
 * @author qiusen
 *
 */
public class TransmitThread extends Thread {

	private String clientId;
	private Socket s;
	public TransmitThread(String clientId){
		this.clientId = clientId;
		s = SocketManage.clientMap.get(clientId);
	}
	
	/* 处理消息
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		InputStream in;
		BufferedReader br;
		
		OutputStream os;
		PrintWriter pw;
		try {
			in = s.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String content = br.readLine();
			while(content!=null){
				JSONObject json = JSONObject.fromObject(content); 
				String toClientId = json.getString("toClientId"); 
				String message = json.getString("message"); 
				
				Socket ts = SocketManage.clientMap.get(toClientId);
				if(ts==null || ts.isClosed()){	//如果socket不存在或已断开，结束线程
					break;
				}
				os = ts.getOutputStream();
				//发送客户端ID
				pw = new PrintWriter(new OutputStreamWriter(os), true);
				pw.println("{fromClientId:'" + clientId + "', message:'" + message + "'}");
				pw.flush();
				
				content = br.readLine();
			}
			in.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
