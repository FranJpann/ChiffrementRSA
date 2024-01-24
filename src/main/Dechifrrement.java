import java.math.BigInteger;

public class Dechifrrement {
    private BigInteger n;
    private BigInteger u;
    public Dechifrrement(BigInteger u,BigInteger n,BigInteger code[]){
        System.out.println("Dechiffrement Key -> Begin");

        String texteConvert="";
        for(int i=0;i<code.length;i++){
            System.out.println(code[i].modPow(u,n));
            texteConvert=texteConvert.concat(String.valueOf(code[i].modPow(u,n)));
        }
        System.out.println(texteConvert);
    }
}
