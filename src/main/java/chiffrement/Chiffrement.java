package chiffrement;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class Chiffrement {
    private BigInteger[] chiffre;

    public BigInteger[] getChiffre() {
        return chiffre;
    }

    public Chiffrement(String str, BigInteger e, BigInteger n){
        System.out.println("chiffrement.Chiffrement -> Begin");
        this.chiffre=new BigInteger[str.length()];
        for(int i=0;i<str.length();i++){
            chiffre[i]=BigInteger.valueOf((int)str.charAt(i)).modPow(e,n);
        }
    }



    public static void main(String[] args) {
        Chiffrement test=new Chiffrement("Ceci est un test",new BigInteger(50,new Random()),new BigInteger(50,new Random()));
        System.out.println(Arrays.toString(test.chiffre));
    }
}
