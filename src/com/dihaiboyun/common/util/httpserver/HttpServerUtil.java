package com.dihaiboyun.common.util.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.http.HttpStatus;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * java响应http请求 com.sun.net.httpserver.HttpServer rt.jar中
 * 
 * @author qiusen
 *
 */
@SuppressWarnings("restriction")
public class HttpServerUtil {

	public static class MyResponseHandler implements HttpHandler {
		@Override
		public void handle(com.sun.net.httpserver.HttpExchange httpExchange)
				throws IOException {
			// 针对请求的处理部分
			// 返回请求响应时，遵循HTTP协议
			String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>";
			// 设置响应头
			httpExchange.sendResponseHeaders(HttpStatus.SC_OK,
					responseString.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(responseString.getBytes());
			os.close();
		}
	}
	

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(
				"127.0.0.1", 9999), 100);	//监听端口9999,能同时接受100个请求 
		server.createContext("/", new MyResponseHandler());
		server.setExecutor(null); // creates a default executor
		server.start();

	}
}
