package com.dihaiboyun.common.socket;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 连接管理
 * @author qiusen
 *
 */
public class SocketManage {

	//客户端所有连接
	public static Map<String, Socket> clientMap = new HashMap<String, Socket>();
		
}
