package chiffrement;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import key.*;

public class Chiffrement {

    public static BigInteger[] chiffrer(PublicKey publicKey, String str) {
        BigInteger[] chiffre = new BigInteger[str.length()];
        for(int i=0; i<str.length(); i++){
            chiffre[i] = BigInteger.valueOf((int)str.charAt(i)).modPow(publicKey.getE(),publicKey.getN());
        }
        return chiffre;
    }

    public static String dechiffrer(PrivateKey privateKey, BigInteger[] encryptedMessage) {
        String decryptedMessage = "";
        for(int i=0; i<encryptedMessage.length; i++){
            decryptedMessage=decryptedMessage.concat(String.valueOf((char)Integer.parseInt(String.valueOf(encryptedMessage[i]
                    .modPow(privateKey.getU(), privateKey.getN())))));
        }
        return decryptedMessage;
    }
}
