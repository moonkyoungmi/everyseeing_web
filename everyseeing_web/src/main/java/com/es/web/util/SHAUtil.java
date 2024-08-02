package com.es.web.util;

import java.security.MessageDigest;

public class SHAUtil {

	/**
	 * SHA-256 암호화
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		String enc = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((str).getBytes());
			
			byte[] pwByte = md.digest();
			
			// 10진수로 변경
			StringBuffer sb = new StringBuffer();
			for (byte b : pwByte) {
				sb.append(String.format("%02x", b));
			}
			
			enc = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return enc;
	}
}
