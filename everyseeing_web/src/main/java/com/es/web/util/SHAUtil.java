package com.es.web.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class SHAUtil {

	/**
	 * SHA-256 암호화
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		String enc = null;
		
		try {
			// salt
			String salt = getSalt();
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((str + salt).getBytes());
			
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
	
	/**
	 * salt 생성
	 * @return
	 */
	private static String getSalt() {
        // 랜덤 salt 생성
        SecureRandom randomNum = new SecureRandom();
        byte[] salt = new byte[20];

        // 난수 생성
        randomNum.nextBytes(salt);

        // 10진수 문자열로 변경
        StringBuffer sb = new StringBuffer();
        for(byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
