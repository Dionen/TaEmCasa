package com.usp.icmc.taemcasa.Autenticacao;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by Joao on 16/05/2017.
 *
 */

public class PasswordAuthentication {

    /* Verifica se o Hash gerado pela senha inserida é igual ao Hash obtido pela database */
    public static boolean check_password_hash(String Password, String salt, String OriginalHash){
        try {
            return OriginalHash.equals(generateStrongPasswordHash(Password, salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* Cria um Hash a partir de uma senha. Retorna um Salt e o Hash */
    public static String[] create_salt_hash(String originalPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();

        String[] result = new String[2];
        result[0] = new BigInteger(128, random).toString(32);
        result[1] = generateStrongPasswordHash(originalPassword, result[0]);
        return result;
    }

    /* Cria um Hash a partir de uma senha e um Salt. Retorna o Hash */
    private static String generateStrongPasswordHash(String password, String Stringsalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 2000;
        char[] chars = password.toCharArray();
        byte[] salt = Stringsalt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(hash);
    }

    /* Converte um byte[] para String */
    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}