package com.sz.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA 加解密工具类
 *
 * @author Tellsea
 * @date 2023/8/9
 */
public class RsaUtils {

    public static KeyPair generateKeyPair() throws Exception {
        // 生成RSA密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static String encrypt(String message, String publicKey) throws Exception {
        return encrypt(message, convertStringToPublicKey(publicKey));
    }

    public static String encrypt(String message, PublicKey publicKey) throws Exception {
        // 使用公钥加密消息
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage, String privateKey) throws Exception {
        return decrypt(encryptedMessage, convertStringToPrivateKey(privateKey));
    }

    public static String decrypt(String encryptedMessage, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder()
                .decode(encryptedMessage.replaceAll("\\s+", ""));

        System.out.println("RSA input length = " + encryptedBytes.length);
        System.out.println("RSA input content = " + encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decrypted = cipher.doFinal(encryptedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public static PublicKey convertStringToPublicKey(String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey convertStringToPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Public Key: " + publicKeyString);
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("Private Key: " + privateKeyString);
        // Public Key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkXY5nh0NDo+Cc03kJxQzAWT8LPFSmOsDcolqDXLKW/tONg5EVxtMCj8sPUtnycg6jx7VymWaFMaX2hVQJKLkNfGY5ryezWtDTnCSUCLHhXRRAB8+5QFeL9PVpRHQPtpnpYPHpvzbG+1Bt9t/NN9pjegwyRUfy/s49iSmQqJMlx5kaRi/oykShyKLSq28E+eJfhwZW2wOr/CO4yJKMfmpWcHx6wqk+74ni2bgbMUAWo6GG07vDFddAo79YsI+KZ7LCBFszvNIjm6JRYyiLX73tCNRbEkC5xWBOIo/aubVxgu9b/G1i5kBPaCFRFp3egayM6QrdTIBd9qbUs6d+ObR3QIDAQAB
        // Private Key: MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCRdjmeHQ0Oj4JzTeQnFDMBZPws8VKY6wNyiWoNcspb+042DkRXG0wKPyw9S2fJyDqPHtXKZZoUxpfaFVAkouQ18ZjmvJ7Na0NOcJJQIseFdFEAHz7lAV4v09WlEdA+2melg8em/Nsb7UG3238032mN6DDJFR/L+zj2JKZCokyXHmRpGL+jKRKHIotKrbwT54l+HBlbbA6v8I7jIkox+alZwfHrCqT7vieLZuBsxQBajoYbTu8MV10Cjv1iwj4pnssIEWzO80iObolFjKItfve0I1FsSQLnFYE4ij9q5tXGC71v8bWLmQE9oIVEWnd6BrIzpCt1MgF32ptSzp345tHdAgMBAAECggEAVwTo9yPd+cX0flIsmQxGbyEgkypTBVQindSC80Smtlwir8fdLlcQKFkeeBSyJE56acBCmXAqux8LHNX/OcuQxoyfsflI7w6qFzTU8se7oKSzCGWo1Ir685XAi52afN9ffe7V85n04kIPPJQ9Bz5YASLUfJb8jKTfsLDAHcDGHOiT+JUJ8kZJOCVPHTUUGigM9qZVWFI2LK+uzQ952cHjwoMSvC5TTE2+TJpIHbj+C04dfvu/tVbx9ZpMT4QZDorkpX1atBLOoScsdlZuS/1q8Tj4pSOdXC9xRzUxL8Gi0YERtfBEGrI31U3ZF71jKPzP/31yMDx8Ju9K+bCtHohHgQKBgQDRPqjdP2yZ3bZ55gAdTZoX+QWzG5h23nvJtT77wzeRiahAUktcT5JPued19giHjJKcPAECF1x/tfImEqaUdYkIq4kbdzUd4Fb2o9/Q44OdGwQ4uvUQbYPj2ZWGEEGU20H8edbH/s5sZOQPdqxiAeSxHAW/auiictxhak8E/OZT8QKBgQCx9wQrG5vM6se1mLskGetz57ch5J+xQ8cL9JUMrzhfotGPOsrONkq5Y1o38Ak/pHe8ZJQnUxfwWV6uAf+Hk1UxdCtwdBxVC2AbMe87jJX0u2Oyiu6eDFzBJiHXUZ3A+0UDCNG3tquP89lllimAL9L2oT2XCIGTVqtJo117SvmYrQKBgQCn/AwJg4+mf26tsk7JN7HMKSnAPxlJ5RewOggUiwcjmkYvTWIe++rUgsUl2J3EZMCXGT0d9q9WADYlBrghW7Ll+QCbwjosgByQ6cMXqm/RQw1CliWESMenttjpxkdAQD5beV1LNm92ahEFsRp3nFmDLqIWqLeXI2t18aj6siYfoQKBgDUVFO02wvYQYZlNAST3hj+AMiOt6eQZ71kljv/Fk+8SxacPRkbT8t3xQIvG0Ttmd0B+T6KbKlzKi28jsXBjBdpGaF6Wg4zqY7Q45L7CZEgnTMmInzwNO+1wzk9HTOKvEBqjmspNdkbt2T5v19kgarCdz50pMF0eUOEaFVwdNO3RAoGAFTTkzgzQMs5K6nwoiKkbBdvJZk/iw732v6SORV27twdSAu/5riFpHxZ7+51PMVqVsYLy/ZF4JykgEe/0Q0ZEIeK8GY78LplcWEbRtYEm7LeQLHdvyjj1Fc+MnYi/yJozfiHKMS8TyzPOtwUarIjJD1BIw0jWr/tqCEYAC4ciwNM=
    }
}
