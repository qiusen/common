package com.dihaiboyun.common.util.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件工具
 * 
 * pom.xml添加：
 * 
 * <dependency>
 * 
 * <groupId>javax.mail</groupId>
 * 
 * <artifactId>mail</artifactId>
 * 
 * <version>1.4.7</version>
 * 
 * </dependency>
 * 
 * @author qiusen
 *
 */
public class MailUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String mailHost = "smtp.126.com";
		String mailTransportProtocol = "smtp";
		String mailSmtpAuth = "true";

		String mailUser = "qiu_sen";
		String password = "密码";

		String fromMail = "qiu_sen@126.com";
		String toMail = "qiu_sen@126.com";

		String title = "创建邮件的测试2222";
		String content = "你好~~~";

		boolean foo = sendMail(mailHost, mailTransportProtocol, mailSmtpAuth,
				mailUser, password, fromMail, toMail, title, content);
		System.out.println(foo);
	}

	/**
	 * 发送
	 * 
	 * @param mailHost
	 * @param mailTransportProtocol
	 * @param mailSmtpAuth
	 * @param mailUser
	 * @param password
	 * @param fromMail
	 * @param toMail
	 * @param title
	 * @param content
	 * @return
	 */
	public static boolean sendMail(String mailHost,
			String mailTransportProtocol, String mailSmtpAuth, String mailUser,
			String password, String fromMail, String toMail, String title,
			String content) {
		boolean foo = false;
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", mailHost);
			prop.setProperty("mail.transport.protocol", mailTransportProtocol);
			prop.setProperty("mail.smtp.auth", mailSmtpAuth);
			// 使用JavaMail发送邮件的5个步骤
			// 1、创建session
			Session session = Session.getInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(true);
			// 2、通过session得到transport对象
			Transport ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect(mailHost, mailUser, password);
			// 4、创建邮件
			Message message = createSimpleMail(session, fromMail, toMail,
					title, content);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
			foo = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return foo;

	}

	/**
	 * 创建一封只包含文本的邮件
	 * 
	 * @param session
	 * @param fromMail
	 * @param toMail
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createSimpleMail(Session session,
			String fromMail, String toMail, String title, String content)
			throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(toMail));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				fromMail));
		// 邮件的标题
		message.setSubject(title);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}

}
