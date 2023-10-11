package org.example;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Des {
    private SecretKey key;

    private SecretKey createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        key = keyGenerator.generateKey();
        return key;
    }

    private byte[] encrypt(String plainText) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] byteText = plainText.getBytes("UTF-8");
        return cipher.doFinal(byteText);
    }
    private String encryptBase64(String plainText) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] byteText = plainText.getBytes("UTF-8");
        byte[] byteEncrypt = cipher.doFinal(byteText);
        return Base64.getEncoder().encodeToString(byteEncrypt);
    }
    private String decrypt(byte[] byteEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }
    private String decrypt(String encryptText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key);
        byte[] byteEncrypt = Base64.getDecoder().decode(encryptText);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }

    private boolean encryptFile(String sourceFile, String desFile) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        File source = new File(sourceFile);
        if(!source.exists()) return false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] arr = new byte[64];
        int byteRead;
        while((byteRead = bis.read(arr))!=-1){
            byte[] byteEncrypt = cipher.update(arr, 0, byteRead);
            bos.write(byteEncrypt);
        }
        byte[] output = cipher.doFinal();
        if(output!=null) bos.write(output);
        bis.close();
        bos.flush();
        bos.close();
        return true;
    }
    private boolean decryptFile(String sourceFile, String desFile) throws IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        File source = new File(sourceFile);
        if(!source.exists()) return false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2 , key);
        byte[] arr = new byte[64];
        int byteRead;
        while((byteRead = bis.read(arr))!=-1){
            byte[] byteDecrypt = cipher.update(arr, 0, byteRead);
            bos.write(byteDecrypt);
        }
        byte[] output = cipher.doFinal();
        if(output!=null) bos.write(output);
        bis.close();
        bos.flush();
        bos.close();
        return true;
    }




    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Des des = new Des();
        System.out.println(des.createKey().toString());
        String plainText = "Nguyễn Đắc Cường";
        System.out.println(plainText);
        System.out.println();
        byte[] encrypt = des.encrypt(plainText);
        System.out.println(Arrays.toString(encrypt));
        String decrypt = des.decrypt(encrypt);
        System.out.println(decrypt);
        System.out.println();
        String encryptText = des.encryptBase64(plainText);
        System.out.println(encryptText);
        String decryptText = des.decrypt(encryptText);
        System.out.println(decryptText);
        System.out.println();
        String fileSource = "D://ndc.zip";
        String fileDesEncrypt = "D://ndc_encrypt.zip";
        String fileDesDecrypt = "D://ndc_decrypt.zip";
        System.out.println(des.encryptFile(fileSource, fileDesEncrypt));
        System.out.println(des.decryptFile(fileDesEncrypt, fileDesDecrypt));
    }


}

