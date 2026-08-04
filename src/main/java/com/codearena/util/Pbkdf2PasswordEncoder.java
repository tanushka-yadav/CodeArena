package com.codearena.util;

import com.codearena.interfaces.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

/**
 * PBKDF2 password encoder that can later be replaced by BCrypt behind the same interface.
 */
public class Pbkdf2PasswordEncoder implements PasswordEncoder {

    private static final int HASH_PART_COUNT = 4;
    private static final int ALGORITHM_INDEX = 0;
    private static final int ITERATIONS_INDEX = 1;
    private static final int SALT_INDEX = 2;
    private static final int HASH_INDEX = 3;
    private static final int PASSWORD_HASH_ITERATIONS = 120_000;
    private static final int PASSWORD_SALT_BYTES = 16;
    private static final int PASSWORD_HASH_BITS = 256;
    private static final String PASSWORD_HASH_ALGORITHM = "PBKDF2WithHmacSHA256";

    private final SecureRandom secureRandom;

    public Pbkdf2PasswordEncoder() {
        this.secureRandom = new SecureRandom();
    }

    @Override
    public String encode(char[] rawPassword) {
        byte[] salt = new byte[PASSWORD_SALT_BYTES];
        secureRandom.nextBytes(salt);
        byte[] hash = hash(rawPassword, salt, PASSWORD_HASH_ITERATIONS, PASSWORD_HASH_BITS);
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            return PASSWORD_HASH_ALGORITHM
                    + "$" + PASSWORD_HASH_ITERATIONS
                    + "$" + encoder.encodeToString(salt)
                    + "$" + encoder.encodeToString(hash);
        } finally {
            Arrays.fill(salt, (byte) 0);
            Arrays.fill(hash, (byte) 0);
        }
    }

    @Override
    public boolean matches(char[] rawPassword, String encodedPassword) {
        if (rawPassword == null || StringUtils.isBlank(encodedPassword)) {
            return false;
        }

        String[] parts = encodedPassword.split("\\$");
        if (parts.length != HASH_PART_COUNT || !PASSWORD_HASH_ALGORITHM.equals(parts[ALGORITHM_INDEX])) {
            return false;
        }

        try {
            int iterations = Integer.parseInt(parts[ITERATIONS_INDEX]);
            byte[] salt = Base64.getDecoder().decode(parts[SALT_INDEX]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[HASH_INDEX]);
            byte[] actualHash = hash(rawPassword, salt, iterations, expectedHash.length * Byte.SIZE);
            try {
                return MessageDigest.isEqual(expectedHash, actualHash);
            } finally {
                Arrays.fill(salt, (byte) 0);
                Arrays.fill(expectedHash, (byte) 0);
                Arrays.fill(actualHash, (byte) 0);
            }
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private byte[] hash(char[] rawPassword, byte[] salt, int iterations, int hashBits) {
        PBEKeySpec keySpec = new PBEKeySpec(rawPassword, salt, iterations, hashBits);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PASSWORD_HASH_ALGORITHM);
            return keyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new IllegalStateException("Password hashing is not available.", exception);
        } finally {
            keySpec.clearPassword();
        }
    }
}
