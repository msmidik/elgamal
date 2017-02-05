package cz.muni.fi.pv079.elgamal;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * manage elGamal
 * @author Martin
 */
public class ElGamalManager {

    private static final BigInteger TRIAL_LIMIT = new BigInteger("10000000").pow(2);
    private List<ElGamalSystem> elGamals;
    private static final BigInteger FIND_X_LIMIT = new BigInteger("100000000");

    public ElGamalManager(List<ElGamalSystem> elGamals) {
        this.elGamals = elGamals;
    }

    public void test() {
        //BigInteger p = elGamals.get(0).getP();
        BigInteger fact = pollandRho(new BigInteger("10403"));
        System.out.println(fact);
        BigInteger fact2 = trialDivision(new BigInteger("10403"));
        System.out.println(fact2);
        BigInteger p = elGamals.get(0).getP();

        
         for (ElGamalSystem e : elGamals) {
         BigInteger f = trialDivision(e.getP(), TRIAL_LIMIT);
         System.out.println(e.getP()+" has small factor: "+f);
         }
         
         
        BigInteger p3 = elGamals.get(3).getP();
        System.out.println(findFactors(p3));

        BigInteger test = BigInteger.valueOf(121894);
        System.out.println("eulerPhi: " + orderGroup(test));

        BigInteger g3 = elGamals.get(3).getG();
        System.out.println("q3 = " + orderSubgroup(g3, p3));

    }
    
    /**
     * find all q for elGamal list
     */
    public void findQ() {
        for (ElGamalSystem egs : elGamals) {
           BigInteger g = egs.getG();
           BigInteger p = egs.getP();
           System.out.println("q: " + orderSubgroup(g, p));     
        }
    }

    /**
     * find all x for elGamal list (limited version, better would be CRT)
     */
    public void findX() {
        for (ElGamalSystem egs : elGamals) {
            BigInteger x = BigInteger.ONE;
            BigInteger h = egs.getH();
            BigInteger g = egs.getG();
            BigInteger p = egs.getP();
            boolean success = false;
            while (x.compareTo(FIND_X_LIMIT) <= 0) {
                if (h.equals(g.modPow(x, p))) {
                    System.out.println("x: " + x);
                    success = true;
                    break;
                }
                x = x.add(BigInteger.ONE);
            }
            if (!success) {
                System.out.println("x: not found");
            }
        }
    }

    /**
     * compute order of g (using Lagrange theorem)
     * @param g generator of subgroup
     * @param p modulo
     * @return order of g
     */
    private BigInteger orderSubgroup(BigInteger g, BigInteger p) {
        BigInteger orderG = orderGroup(p);
        List<BigInteger> orderCandidates = combineFactors(findFactors(orderG));

        for (BigInteger qCand : orderCandidates) {
            if (g.modPow(qCand, p).equals(BigInteger.ONE)) {
                return qCand;
            }
        }
        return null;
    }

    /**
     * create list of all combinations of factors
     *
     * @param factors factors
     * @return combinations of factors
     */
    private List<BigInteger> combineFactors(List<BigInteger> factors) {
        int size = factors.size();
        List<BigInteger> combinations = new LinkedList<>();

        for (int i = 1; i <= Math.pow(2, size); i++) {
            BigInteger combination = BigInteger.ONE;

            String binStr = Integer.toBinaryString(i);
            if (binStr.length() < size) {
                char[] chars = new char[size - binStr.length()];
                Arrays.fill(chars, '0');
                String zeroes = new String(chars);
                binStr = zeroes + binStr;
            }
            char[] bin = binStr.toCharArray();
            for (int j = 0; j < bin.length; j++) {
                if (bin[j] == '1') {
                    combination = combination.multiply(factors.get(j));
                }
            }
            combinations.add(combination);
        }

        List<BigInteger> unique = new LinkedList<>(new HashSet<>(combinations));
        Collections.sort(unique);
        return unique;
    }

    /**
     * order of group mod p (euler func.)
     *
     * @param gr mod p
     * @return order
     */
    private BigInteger orderGroup(BigInteger gr) {
        return eulerPhi(findFactors(gr));
    }

    /**
     * factorize integer (first trial division then polland rho)
     * @param n integer
     * @return list of factors
     */
    private List<BigInteger> findFactors(BigInteger n) {
        List<BigInteger> factors = new LinkedList<>();
        BigInteger f;

        while ((f = trialDivision(n, TRIAL_LIMIT)) != null) {
            factors.add(f);
            n = n.divide(f);
        }

        if (n.compareTo(BigInteger.valueOf(2)) > 0) {
            while (!(f = pollandRho(n)).equals(n)) {
                factors.add(f);
                n = n.divide(f);
            }
        }

        if (n.compareTo(BigInteger.valueOf(2)) > 0) {
            factors.add(n);
        }
        //System.out.println("factors: "+factors );
        return factors;
    }

    /**
     * polland rho algo
     * @param n integer
     * @return factor
     */
    private BigInteger pollandRho(BigInteger n) {
        BigInteger one = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);
        BigInteger factor = one;
        BigInteger cycleSize = two;
        BigInteger x = two;
        BigInteger xFixed = two;
        while (factor.equals(one)) {
            for (BigInteger count = one; count.compareTo(cycleSize) <= 0 && factor.compareTo(one) <= 0; count = count.add(one)) {
                x = ((x.multiply(x)).add(one)).mod(n);
                factor = n.gcd(x.subtract(xFixed));
            }
            cycleSize = cycleSize.multiply(two);
            xFixed = x;
        }
        return factor;
    }

    /**
     * get factor using trial division
     * @param n integer
     * @return factor
     */
    private BigInteger trialDivision(BigInteger n) {
        return trialDivision(n, n);
    }

    /**
     * get factor using trial division (limited no. of trials)
     * @param n integer
     * @param limitSquared number of trials (squared)
     * @return factor
     */
    private BigInteger trialDivision(BigInteger n, BigInteger limitSquared) {
        BigInteger two = BigInteger.valueOf(2);

        if (n.compareTo(two) < 0) {
            return null;
        }

        while (n.mod(two).equals(BigInteger.ZERO)) {
            return two;
        }

        BigInteger factor = BigInteger.valueOf(3);
        while (factor.multiply(factor).compareTo(limitSquared) <= 0) {
            if (n.mod(factor).equals(BigInteger.ZERO)) {
                return factor;
            } else {
                factor = factor.add(two);
            }
        }
        return null;
    }

    /**
     * calculate euler phi from factors
     * @param factors factors
     * @return euler phi
     */
    private BigInteger eulerPhi(List<BigInteger> factors) {
        int size = factors.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return factors.get(0).subtract(BigInteger.ONE);
        }

        BigInteger phi = BigInteger.ONE;
        Set<BigInteger> unique = new HashSet<>(factors);
        for (BigInteger f : unique) {
            int count = Collections.frequency(factors, f);
            phi = phi.multiply(f.pow(count).subtract(f.pow(count - 1)));
        }
        return phi;
    }

}
