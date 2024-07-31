package com.es.web.util;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	// 초기화 벡터
	private static final String iv = "0000000000000000"; 

	/**
	 * 복호화
	 * @param str
	 * @param secretKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String str, String secretKey) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
		byte[] keyByte = Base64.getDecoder().decode(secretKey);
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] decBytes = Base64.getDecoder().decode(str);
		return new String(cipher.doFinal(decBytes), "UTF-8");
	}
}
