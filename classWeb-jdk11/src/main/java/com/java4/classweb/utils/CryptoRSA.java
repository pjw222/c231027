package com.java4.classweb.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;

public class CryptoRSA {
	public static void main(String[] args) {
		String text = "이야~ 야근이다~";
		CryptoRSA cr = new CryptoRSA();
		HashMap<String, String> keyPair = cr.createKeyPair();
		String encryptoText = cr.encrypto(text, keyPair.get("publicKey"));
		System.out.println(encryptoText);
		String decryptoText = cr.decrypto(encryptoText, keyPair.get("privateKey"));
		System.out.println(decryptoText);
	}
	
	
	
	// 양방향 암호화 + 개인키/공개키
	public HashMap<String, String> createKeyPair(){
		HashMap<String, String> stringKeyPair = new HashMap<String, String>();
		
		try {
			SecureRandom sr = new SecureRandom();
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024, sr);
			KeyPair kp = kpg.genKeyPair();
			
			PublicKey publicKey = kp.getPublic();
			PrivateKey privateKey = kp.getPrivate();
			
			String sPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded()); 
			String sPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
			
			stringKeyPair.put("publicKey", sPublicKey);
			stringKeyPair.put("privateKey", sPrivateKey);
		}catch(RuntimeException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return stringKeyPair;
	}
	public String encrypto(String text, String publicKey) {
		String encryptoText = null;
		
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			byte[] bytePublicKey = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytePublicKey);
			PublicKey pk = kf.generatePublic(publicKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			
			byte[] encryptedBytes = cipher.doFinal(text.getBytes());
			encryptoText = Base64.getEncoder().encodeToString(encryptedBytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return encryptoText;		
	}
	public String decrypto(String encryptoText, String privateKey) {
		String text = null;
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			byte[] bytePrivateKey = Base64.getDecoder().decode(privateKey.getBytes());
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytePrivateKey);
			PrivateKey pk = kf.generatePrivate(privateKeySpec);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pk);
			
			byte[] encryptedBytes = Base64.getDecoder().decode(encryptoText.getBytes());
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			text = new String(decryptedBytes);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return text;
	}
}
