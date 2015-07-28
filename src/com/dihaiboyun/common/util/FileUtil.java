package com.dihaiboyun.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.io.IOUtils;

/**
 * 文件工具类
 * 
 * @author nathan
 * 
 */
public class FileUtil {

	/**
	 * 文件上传
	 * 
	 * @param absoluteFile
	 * @param serverFile
	 * @return
	 */
	public static boolean uploadFile(File absoluteFile, File serverFile) {
		boolean success = false;
		InputStream is = null;
		OutputStream os = null;

		try {
			is = new FileInputStream(absoluteFile);
			os = new FileOutputStream(serverFile);

			byte buf[] = new byte[512];
			int size = 0;
			while ((size = is.read(buf)) > 0) {
				os.write(buf, 0, size);
			}
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
		return success;
	}

	/**
	 * 写内容到某文件
	 * 
	 * @param filepath
	 * @param content
	 * @param charSet
	 * @return
	 */
	public static boolean writeFile(String filepath, String content,
			String charSet) {
		boolean success = false;
		OutputStream ostream = null;
		BufferedWriter bw = null;

		try {
			ostream = new FileOutputStream(filepath);
			bw = new BufferedWriter(new OutputStreamWriter(ostream, charSet));
			bw.write(content);
			bw.flush();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
			IOUtils.closeQuietly(ostream);
		}
		return success;
	}
}
