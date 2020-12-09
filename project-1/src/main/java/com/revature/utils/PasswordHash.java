package com.revature.utils;

import org.mindrot.jbcrypt.BCrypt;

public interface PasswordHash {
	
	static String generateHash(String original) {
//		SecureRandom random = new SecureRandom();
//		byte[] salt = new byte[16];
//		random.nextBytes(salt);
//		
//		KeySpec spec = new PBEKeySpec(original.toCharArray(), salt, 65536, 256);
//		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//
//		byte[] hash = factory.generateSecret(spec).getEncoded();
//
//		
//		return hash.toString();
		
		return BCrypt.hashpw(original, BCrypt.gensalt(12));
	}
	
	static boolean checkMatch(String candidate, String hashed) {
		return BCrypt.checkpw(candidate, hashed);
	}
}
