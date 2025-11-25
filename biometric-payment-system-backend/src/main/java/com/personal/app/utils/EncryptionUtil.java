package com.personal.app.utils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class EncryptionUtil {

    private static final String ALGORITHM = "AES";

    //getting secretkey from byte[]
    public static SecretKey getSecretKeyFromBytes(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }

    // Encrypt byte array
    public static byte[] encrypt(byte[] data, byte[] keyBytes) throws Exception {
        SecretKey key = getSecretKeyFromBytes(keyBytes);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // Decrypt byte array
    public static byte[] decrypt(byte[] data, byte[] keyBytes) throws Exception {
        SecretKey key = getSecretKeyFromBytes(keyBytes);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

}