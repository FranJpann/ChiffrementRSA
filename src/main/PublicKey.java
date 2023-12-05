import java.math.BigInteger;
import java.util.Random;

public class PublicKey {

    // p et q grands entiers premiers
    // p != q
    private BigInteger p;
    private BigInteger q;

    // n = p x q
    private BigInteger n;

    // m -> indice euler, m = (p-1) x (q-1)
    private BigInteger m;

    // e -> exposant public
    private BigInteger e;

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getM() {
        return m;
    }

    public BigInteger getE() {
        return e;
    }

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
