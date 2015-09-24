package com.dihaiboyun.common.util.ikanalyzer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 分析工具
 * 将 IKAnalyzer.cfg.xml LICENSE.txt	NOTICE.txt	stopword.dic [ext.dic]放到src目录下
 * 导入IKAnalyzer2012_FF.jar
 * @author qiusen
 *
 */
public class AnalyzerUtil {

	/**
	 * 解析字符串出分词
	 * @param text
	 * @return
	 */
	public static List<String> analyzerText(String text){
		List<String> wordList = new ArrayList<String>();
		
		byte[] bt = text.getBytes();  
        InputStream ip = new ByteArrayInputStream(bt);  
        Reader read = new InputStreamReader(ip);  
		IKSegmenter iks = new IKSegmenter(read, true);
		Lexeme t;  
        try {
			while ((t = iks.next()) != null) {  
					System.out.println(t.getLexemeText());
					wordList.add(t.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
        
        return wordList;
	}
	
	public static void main(String[] args) throws IOException {
		String text = "谢霆锋跟王菲要生孩子了，IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
		analyzerText(text);
	}

}
