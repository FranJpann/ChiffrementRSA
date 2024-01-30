package java.chiffrement;

import java.math.BigInteger;

public class Dechiffrement {
    private BigInteger n;
    private BigInteger u;
    public Dechiffrement(BigInteger u,BigInteger n,BigInteger code[]){

        String texteConvert="";
        for(int i=0;i<code.length;i++){
            texteConvert=texteConvert.concat(String.valueOf((char)Integer.parseInt(String.valueOf(code[i].modPow(u,n)))));
        }
    }
}
