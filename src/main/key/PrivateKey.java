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
        this.p = BigInteger.probablePrime(2048, new Random());
        this.q = this.p.nextProbablePrime();

        // n = q x p
        this.n = this.q.multiply(this.p);

        // m est égal au nombre d'entiers naturels <= n
        this.m = (this.p.subtract(BigInteger.valueOf(1)).multiply(this.q.subtract(BigInteger.valueOf(1))));

        // e un nombre aléatoire premier à m
        this.e = new BigInteger(512, new Random());
        if (this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))) {
            this.e = this.e.subtract(BigInteger.valueOf(1));
        }
        while (!this.m.gcd(this.e).equals(BigInteger.valueOf(1))) {
            this.e = new BigInteger(512, new Random());
            if (this.e.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0))) {
                this.e = this.e.subtract(BigInteger.valueOf(1));
            }
        }

        this.u = algoEuclide(e, m, e.subtract(m.multiply(e.divide(m))), BigInteger.valueOf(1), BigInteger.valueOf(0), BigInteger.valueOf(1).subtract(BigInteger.valueOf(0).multiply(e.divide(m))));
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
        return "("+this.n+","+this.u+")";
    }
}
