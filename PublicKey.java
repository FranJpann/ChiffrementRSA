import java.math.BigInteger;
import java.util.Random;

public class PublicKey {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger e;
    PublicKey(){
        this.p=BigInteger.probablePrime(2048, new Random());
        this.q=this.p.nextProbablePrime();
        this.n=this.q.multiply(this.p);
        this.m=(this.p.subtract(BigInteger.valueOf(1)).multiply(this.q.subtract(BigInteger.valueOf(1))));
        this.e=new BigInteger(512,new Random());
        if(this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))){
            this.e=this.e.subtract(BigInteger.valueOf(1));
        }
        while(!this.m.gcd(this.e).equals(BigInteger.valueOf(1))){
            this.e=new BigInteger(512,new Random());
            if(this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))){
                this.e=this.e.subtract(BigInteger.valueOf(1));
            }
        }
    }

}
