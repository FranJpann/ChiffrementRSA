package key;

import java.math.BigInteger;
import java.util.Random;

public class PrivateKey {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger e;
    private BigInteger u;

    public PrivateKey() {

        // p et q grands entiers premiers
        // p != q
        this.setP(BigInteger.probablePrime(2048, new Random()));
        this.setQ(this.getP().nextProbablePrime());

        // n = q x p
        this.setN(this.getQ().multiply(this.getP()));

        // m est égal au nombre d'entiers naturels <= n
        this.setM((this.getP().subtract(BigInteger.valueOf(1)).multiply(this.getQ().subtract(BigInteger.valueOf(1)))));

        // e un nombre aléatoire premier à m
        this.setE(new BigInteger(512, new Random()));
        if (this.getE().mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))) {
            this.setE(this.getE().subtract(BigInteger.valueOf(1)));
        }
        while (!this.getM().gcd(this.getE()).equals(BigInteger.valueOf(1))) {
            this.setE(new BigInteger(512, new Random()));
            if (this.getE().mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))) {
                this.setE(this.getE().subtract(BigInteger.valueOf(1)));
            }
        }

        this.setU(algoEuclide(getE(), getM(), getE().subtract(getM().multiply(getE().divide(getM()))), BigInteger.valueOf(1), BigInteger.valueOf(0), BigInteger.valueOf(1).subtract(BigInteger.valueOf(0).multiply(getE().divide(getM())))));
    }

    private BigInteger algoEuclide(BigInteger a, BigInteger b, BigInteger r, BigInteger u0, BigInteger u1, BigInteger u) {
        if (r.equals(BigInteger.valueOf(0))){
            BigInteger k = BigInteger.valueOf(-1);
            BigInteger uFinal = u1;
            while(uFinal.compareTo(BigInteger.valueOf(2))<0 || u.compareTo(uFinal)<0) {
                uFinal = uFinal.subtract(k.multiply(u));
                k = k.subtract(BigInteger.valueOf(1));
            }
            return uFinal;
        }
        else {
            return algoEuclide(b, r, b.subtract(r.multiply(b.divide(r))), u1, u, u1.subtract(u.multiply(b.divide(r))));
        }
    }

    public String getKey() {
        return "{n:"+ this.getN().toString()+",u:"+ this.getU().toString()+"}";
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getM() {
        return m;
    }

    public void setM(BigInteger m) {
        this.m = m;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getU() {
        return u;
    }

    public void setU(BigInteger u) {
        this.u = u;
    }
}
