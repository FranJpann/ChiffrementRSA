package key;

import java.math.BigInteger;

public class PrivateKey {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger m;
    private BigInteger e;
    private BigInteger u;
    private BigInteger v;

    public PrivateKey(BigInteger e, BigInteger m,BigInteger n) {
        this.e = e;
        this.m = m;
        this.n = n;
        if(e.equals(BigInteger.valueOf(0))){
            this.u = BigInteger.valueOf(1);
        }
        else if(m.equals(BigInteger.valueOf(0))){
            this.u = BigInteger.valueOf(0);
        }
        else{
            this.u = algoEuclide(e,BigInteger.valueOf(1),BigInteger.valueOf(0),m,BigInteger.valueOf(0),BigInteger.valueOf(1),m);
        }
    }

    private BigInteger algoEuclide(BigInteger r, BigInteger u, BigInteger v, BigInteger old_r, BigInteger old_u, BigInteger old_v,BigInteger m) {
        if(r.equals(BigInteger.valueOf(0))){
            if(u.compareTo(BigInteger.valueOf(2))>0 && u.compareTo(m)<0){
                this.v=v;
                return old_u;
            }
            else{
                BigInteger k= BigInteger.valueOf(-1);
                while (old_u.compareTo(BigInteger.valueOf(2))<1 && old_u.compareTo(m)<0){
                    old_u=old_u.subtract(k.multiply(m));
                    k=k.subtract(BigInteger.valueOf(1));
                }
                this.v=v;
                return old_u;
            }
        }
        else{
            return algoEuclide(old_r.subtract(old_r.divideAndRemainder(r)[0].multiply(r)),old_u.subtract(old_r.divideAndRemainder(r)[0].multiply(u)),old_v.subtract(old_r.divideAndRemainder(r)[0].multiply(v)),r,u,v,m);
        }
    }

    public String getKey() {
        return this.getN().toString()+","+ this.getU().toString();
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getU() {
        return u;
    }
}