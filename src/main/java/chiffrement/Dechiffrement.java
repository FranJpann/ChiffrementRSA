package chiffrement;

import java.math.BigInteger;

public class Dechiffrement {
    private BigInteger n;
    private BigInteger u;
    public Dechiffrement(BigInteger u, BigInteger n, BigInteger[] code){
        System.out.println("Dechiffrement Key -> Begin");

        String texteConvert="";
        for (BigInteger bigInteger : code) {
            System.out.println(bigInteger.modPow(u, n));
            texteConvert = texteConvert.concat(String.valueOf(bigInteger.modPow(u, n)));
        }
        System.out.println(texteConvert);
    }
}
