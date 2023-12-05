import java.math.BigInteger;
import java.util.Arrays;

public class Chiffrement {
    private BigInteger[] chiffre;

    Chiffrement(String str,BigInteger p,BigInteger m){
        this.chiffre=new BigInteger[str.length()];
        for(int i=0;i<str.length();i++){
            chiffre[i]=BigInteger.valueOf((int)str.charAt(i)).modPow(p,m);
        }
    }
}
