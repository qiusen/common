package com.dihaiboyun.common.util.chineseToPinyin;

/**
 * 中文转英文
 * @author qiusen
 *
 */
public class ChineseToPinyin {

	/**
	 * 获取Ascii值
	 * @param chs
	 * @return
	 */
	private static int getChsAscii(String chs) {  
        int asc = 0;  
        try {  
            byte[] bytes = chs.getBytes("gb2312");  
            if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // 错误  
                // log  
                throw new RuntimeException("illegal resource string");  
                // System.out.println("error");  
            }  
            if (bytes.length == 1) { // 英文字符  
                asc = bytes[0];  
            }  
            if (bytes.length == 2) { // 中文字符  
                int hightByte = 256 + bytes[0];  
                int lowByte = 256 + bytes[1];  
                asc = (256 * hightByte + lowByte) - 256 * 256;  
            }  
        } catch (Exception e) {  
            System.out  
                    .println("ERROR:ChineseSpelling.class-getChsAscii(String chs)"  
                            + e);  
            // e.printStackTrace();  
        }  
        return asc;  
    } 
	
	/** 
     * 转换单个汉字 
     * @param str 
     * @return 
     */  
    public static String convertSingle(String str) {  
        String result = null;  
        int ascii = getChsAscii(str);  
        if (ascii > 0 && ascii < 160) {  
            result = String.valueOf((char) ascii);  
        } else {  
            for (int i = (CharCode.PYValue.length - 1); i >= 0; i--) {  
                if (CharCode.PYValue[i] <= ascii) {  
                    result = CharCode.PY[i];  
                    break;  
                }  
            }  
        }  
        return result;  
    }  
    
    
    
    /** 
     * 转换一个或多个汉字,只保留原英文、英文标点符号和中文转换后的拼音
     * @param str 
     * @return 
     */  
    public static String convertAll(String str) {  
        String result = "";  
        String strTemp = null;  
        for (int j = 0; j < str.length(); j++) {  
            strTemp = str.substring(j, j + 1);  
            int ascii = getChsAscii(strTemp);  
            if (ascii > 0 && ascii < 160) {  
                result += String.valueOf((char) ascii);  
            } else {  
                for (int i = (CharCode.PYValue.length - 1); i >= 0; i--) {  
                    if (CharCode.PYValue[i] <= ascii) {  
                        result += CharCode.PY[i];  
                        break;  
                    }  
                }  
            }  
        }  
        return result;  
    }  
    
    /**
     * 转换一个或多个汉字，并保留中文标点符号
     * @param str
     * @return
     */
    public static String convertAllKeepChinesePunctuation(String str) {  
        String result = "";  
        String strTemp = null;  
        for (int j = 0; j < str.length(); j++) {  
            strTemp = str.substring(j, j + 1);  
            int ascii = getChsAscii(strTemp);  
            int fool = 0;
            if (ascii > 0 && ascii < 160) {  
                result += String.valueOf((char) ascii);  
            } else {  
                for (int i = (CharCode.PYValue.length - 1); i >= 0; i--) {  
                    if (CharCode.PYValue[i] <= ascii) {  
                        result += CharCode.PY[i];  
                        fool = 1;
                        break;  
                    }  
                }  
            }  
            
            if(fool==0){
            		result += strTemp;  
            }
        }  
        return result;  
    } 
    
    /**
     * Test
     * @param args
     */
    public static void main(String[] args){
    		System.out.println(convertSingle("缘"));
    		System.out.println(convertAll("世界，你好！Hello world!"));
    		System.out.println(convertAllKeepChinesePunctuation("世界，你好！Hello world!"));
    }
}
