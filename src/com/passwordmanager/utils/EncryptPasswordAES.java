package com.passwordmanager.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author Patrick Kelly
 * Class which encrypts the passwords that are stored in our database.
 * This works well for now but i'm trying to find a better solution
 * before the project is frozen
 */
public class EncryptPasswordAES
{
    private static SecretKeySpec secKey;
    private static byte[] byteKey;

    /**
     *
     * @param inputKey takes the input secret key and generates a new string using padding
     *                 by SHA-1 algorithm for better security
     */
    private static void generateKey(String inputKey)
    {
        MessageDigest sha1 = null;

        try
        {
            byteKey = inputKey.getBytes(StandardCharsets.UTF_8); //convert String input to byte array
            sha1 = MessageDigest.getInstance("SHA-1"); //instantiate SHA-1 algorithm for extra padding bits
            byteKey = sha1.digest(byteKey); //pad the byte array using MessageDigest Class
            byteKey = Arrays.copyOf(byteKey, 16);
            secKey = new SecretKeySpec(byteKey, "AES");
        }
        catch (NoSuchAlgorithmException e)
        {
            System.err.println("Error using SHA-1 algorithm " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param plainText enter the plaintext password to be encrypted into the database
     * @param secretKey the secret key message passed which is used to encrypt/decrypt
     * @return String encoded in base64 UTF-8 format. If exception occurs, return null
     */
    public static String passwordEncrypt(String plainText, String secretKey)
    {
        try
        {
            generateKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e) //generates a lot of Exceptions so catch and see which one is caught
        {
            System.err.println("Error Encrypting String: " + e.toString());
        }
        return null;
    }

    /**
     *
     * @param encryptedString the encrypted string to decrypt using the secret key
     * @param secretKey the secret key message passed which is used to encrypt/decrypt
     * @return decrypted String encoded in base64 UTF-8 format
     */
    public static String passwordDecrypt(String encryptedString, String secretKey)
    {
        try
        {
            generateKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString)));
        }
        catch (Exception e) //generates a lot of Exceptions so catch and see which one is caught
        {
            System.err.println("Error Decrypting String: " + e.toString());
        }
        return null;
    }
    //main method for testing
    public static void main(String[] args)
    {
        final String ourSecretKey = "WGHa!4TrQA!VcMM?FPooZ!"; //this can be changed to whatever if we want more security

        String plainTextString = "Testing to see if this works properly";
        String encryptedString = EncryptPasswordAES.passwordEncrypt(plainTextString, ourSecretKey);
        String decryptedString = EncryptPasswordAES.passwordDecrypt(encryptedString, ourSecretKey);

        System.out.println(plainTextString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
