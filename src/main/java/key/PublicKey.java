package key;

import java.math.BigInteger;
import java.util.Random;

public class PublicKey {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger e;

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getQ() {
        return this.q;
    }

    public BigInteger getN() {
        return this.n;
    }

    public BigInteger getM() {
        return this.m;
    }

    public BigInteger getE() {
        return this.e;
    }

    public PublicKey(){

        // p et q grands entiers premiers
        // p != q
        this.p=BigInteger.probablePrime(2048, new Random());
        this.q=this.p.nextProbablePrime();

        // n = q x p
        this.n=this.q.multiply(this.p);

        // m est égal au nombre d'entiers naturels <= n
        this.m=(this.p.subtract(BigInteger.valueOf(1))).multiply((this.q.subtract(BigInteger.valueOf(1))));

        // e un nombre aléatoire premier à m
        this.e=new BigInteger(512,new Random());
        if(this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))){
            this.e=this.e.subtract(BigInteger.valueOf(1));
        }
        while(!(this.m.gcd(this.e).equals(BigInteger.valueOf(1)))){
            this.e=new BigInteger(512,new Random());
            if(this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))){
                this.e=this.e.subtract(BigInteger.valueOf(1));
            }
        }
    }

    public PublicKey( BigInteger n, BigInteger e){
        this.n = n;
        this.e = e;
    }

    public String getKey() {
        return "{n:"+this.n.toString()+",e:"+this.e.toString()+"}";
    }
}
