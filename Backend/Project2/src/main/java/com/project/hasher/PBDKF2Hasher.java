package com.project.hasher;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 
 * @author Joe Shannon
 * 
 *         This class will be used for the hashing of passwords to be stored in
 *         the DB, as well as comparing input passwords to DB passwords upon a login attempt
 *         from the front end.
 *
 */
public final class PBDKF2Hasher {

	// NECESSARY FIELDS

	/**
	 * These are the fields that are required for hashing a character array
	 */
	// this one was in online implementation examples, not sure if we really need to
	// make use of it here
	// public static final int cost = 16;
	public static final String algorithm = "PBKDF2WithHmacSHA1";
	private static final int size = 128;
	private final SecureRandom random;

	// CONSTRUCTOR
	public PBDKF2Hasher() {

		this.random = new SecureRandom();

	}

	// METHODS

	/**
	 * This method generates a new random salt byte array that can be used in the
	 * hashing algorithm
	 * 
	 * @return
	 */
	public String newSalt() {

		byte[] salt = new byte[16];

		random.nextBytes(salt);

		return new String(salt);
	}

	/**
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public String hash(String password, String salt) {

		char[] passwordCharArray = password.toCharArray();

		byte[] saltArray = salt.getBytes();

		byte[] hashed = pbkfd2(passwordCharArray, saltArray);

		// converting hashed password bytearray to a string to return
		String hashedString = new String(hashed);

		return hashedString;

	}

	/**
	 * 
	 * @param password - password char array to hash
	 * @param salt     - salt byte array to be used in the hashing algorithm
	 * @return - returns the hashed password or a default byte array depending on
	 *         the outcome of the try/catch block
	 */
	private static byte[] pbkfd2(char[] password, byte[] salt) {
		KeySpec spec = new PBEKeySpec(password, salt, 65536, size);

		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
			return factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		return new byte[16];

	}
}
