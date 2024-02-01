package java.chiffrement;

import java.key.PublicKey;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import key.*;

public class Chiffrement {
    private BigInteger[] chiffre;

    public BigInteger[] getChiffre() {
        return chiffre;
    }

    public Chiffrement(String str, BigInteger e, BigInteger n){
        this.chiffre=new BigInteger[str.length()];
        for(int i=0;i<str.length();i++){
            this.chiffre[i]=BigInteger.valueOf((int)str.charAt(i)).modPow(e,n);
        }
    }



    /*public static void main(String[] args) {
        PublicKey pb = new PublicKey();
        PrivateKey pk = new PrivateKey(pb.getE(),pb.getM(),pb.getN());
        Chiffrement test=new Chiffrement("Ceci est un test",pb.getE(),pb.getN());
        System.out.println(test.chiffre[0]);
        new Dechiffrement(new BigInteger(pk.getKey().split(",")[1]),new BigInteger(pk.getKey().split(",")[0]),test.chiffre);
    }*/
}
