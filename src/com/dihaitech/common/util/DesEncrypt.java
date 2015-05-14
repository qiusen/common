package com.dihaitech.common.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class DesEncrypt {
	private static final String ALGORITHM = "DESede";
	private static final String SEED = "MQt2Kb96mG5YAowliePIUdmrp1J6CxlR";
	private static DesEncrypt instance;
	private static final Logger log = Logger.getLogger(DesEncrypt.class);

	private SecretKey secretKey;

	private String key;

	private final static byte[] hex = "0123456789ABCDEF".getBytes();

	/**
	 * @param strKey
	 */
	public static String byte2BCDStr(final byte[] b) {
		final byte[] buff = new byte[2 * b.length];
		for (int i = 0; i < b.length; i++) {
			buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
			buff[2 * i + 1] = hex[b[i] & 0x0f];
		}
		return new String(buff, Charset.forName("UTF-8"));
	}

	private static int parse(final char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	/**
	 * 
	 * @param strKey
	 */
	public static byte[] bdcStr2Byte(final String hexstr) {
		final byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			final char c0 = hexstr.charAt(j++);
			final char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	private DesEncrypt() {
		readKey();
	}

	private DesEncrypt(String strkey) {
		key = strkey;
		readKey();
	}

	public static synchronized DesEncrypt getIntance() {
		if (instance == null) {
			instance = new DesEncrypt();
		}
		return instance;
	}

	public static synchronized DesEncrypt getIntance(String strkey) {
		if (instance == null) {
			if (strkey.length() < 48) {
				int l = 48 - strkey.length();
				StringBuffer strbuf = new StringBuffer(strkey);
				for (int i = 0; i < l; i++) {
					strbuf.append("0");
				}
				strkey = strbuf.toString();
			}

			System.out.println("strkey=======" + strkey);
			instance = new DesEncrypt(strkey);
		}
		return instance;
	}

	/**
	 * @param strKey
	 */
	public void createKey() {
		try {
			final KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
			generator.init(new SecureRandom(SEED.getBytes("UTF-8")));
			final byte[] keys = generator.generateKey().getEncoded();
			System.out.println(byte2BCDStr(keys));
		} catch (final Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 
	 * @param strKey
	 */
	private void readKey() {
		try {
			final byte[] keyByte = bdcStr2Byte(key);
			secretKey = new SecretKeySpec(keyByte, ALGORITHM);
		} catch (final Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 
	 * @param strMing
	 * @return
	 */
	public String encode(final String strMing) {
		String strMi = "";
		try {
			final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final byte[] byteMi = cipher.doFinal(strMing.getBytes("UTF-8"));
			strMi = byte2BCDStr(byteMi);
		} catch (final Exception e) {
			log.error("", e);
		}
		return strMi;
	}

	/**
	 * @param strMi
	 * @return
	 */
	public String decode(final String strMi) {
		String strMing = "";
		try {
			final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final byte[] byteMing = cipher.doFinal(bdcStr2Byte(strMi));
			strMing = new String(byteMing, "UTF-8");
		} catch (final Exception e) {
			log.error("", e);
		}
		return strMing;
	}

	public static void main(final String[] args) {

		String strkey = "1234567";
		// String strkey = "Asiainfo";
		// final DesEncrypt des =
		// DesEncrypt.getIntance("Asiainfo3536373831323334353637383132333435363738");

		final DesEncrypt des = DesEncrypt.getIntance(strkey);
		// des.createKey();
		String plainText = "qiusen";
		System.out.println("plainText = " + plainText);
		String strEnc = des.encode(plainText);
		System.out.println("encode = " + strEnc);

		String strDes = des.decode(strEnc);
		System.out.println("decode = " + strDes);

		// plainText = "1qaz!QAZ";
		// System.out.println("plainText = " + plainText);
		// strEnc = des.encode(plainText);
		// System.out.println("encode = " +strEnc);
		//
		// strDes = des.decode(strEnc);
		// System.out.println("decode = " + strDes);
		//
		// plainText = "1qaz!QA";
		// System.out.println("plainText = " + plainText);
		// strEnc = des.encode(plainText);
		// System.out.println("encode = " +strEnc);
		//
		// strDes = des.decode(strEnc);
		// System.out.println("decode = " + strDes);

	}

}
