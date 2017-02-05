package cz.muni.fi.pv079.elgamal;

import java.math.BigInteger;
import java.util.Objects;

/**
 * ElGamal cryptosystem parameters (p, g, q, h, x)
 * @author Martin
 */
public class ElGamalSystem {

    private BigInteger p;
    private BigInteger g;
    private BigInteger q;
    private BigInteger h;
    private BigInteger x;

    public ElGamalSystem() {}
    
    public ElGamalSystem(BigInteger p, BigInteger g, BigInteger q, BigInteger h, BigInteger x) {
        this.p = p;
        this.g = g;
        this.q = q;
        this.h = h;
        this.x = x;        
    }
    
    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getG() {
        return g;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getH() {
        return h;
    }

    public void setH(BigInteger h) {
        this.h = h;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.p);
        hash = 67 * hash + Objects.hashCode(this.g);
        hash = 67 * hash + Objects.hashCode(this.q);
        hash = 67 * hash + Objects.hashCode(this.h);
        hash = 67 * hash + Objects.hashCode(this.x);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElGamalSystem other = (ElGamalSystem) obj;
        if (!Objects.equals(this.p, other.p)) {
            return false;
        }
        if (!Objects.equals(this.g, other.g)) {
            return false;
        }
        if (!Objects.equals(this.q, other.q)) {
            return false;
        }
        if (!Objects.equals(this.h, other.h)) {
            return false;
        }
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ElGamalSystem{" + "p=" + p + ", g=" + g + ", q=" + q + ", h=" + h + ", x=" + x + '}';
    }
    
}
