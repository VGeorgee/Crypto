
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Prime {
    private static List<BigInteger> bis = new ArrayList<>();
    
    public static BigInteger generatePrime(int bitSize){
        if(bitSize < 2) {
            bitSize = 2;
        }
        BigInteger b = BigInteger.ONE;
        BigInteger two = new BigInteger("2");
        
        for(int i = 0; i < bitSize; i++){
            b = b.multiply(two);
        }
         b = b.subtract(BigInteger.ONE);
         
        while(!MillerRabin.isPrime(b, 1) || bis.contains(b)){
            b = b.subtract(two);
        }
        bis.add(b);
        
        return b;
    }
    
    public static BigInteger getPrimeFromBigInteger(int bitSize){
        return BigInteger.probablePrime(bitSize, new Random());
    }
    
}


class MillerRabin{
    private static BigInteger two = new BigInteger("2");
    private static final int initialTries = 2;
      
    
    public static boolean isPrime(BigInteger n){
        return isPrime(n, initialTries);
    }
    
    
    public static boolean isPrime(BigInteger n, int tries){
        if(n.compareTo(BigInteger.ONE) <= 0 ) return false;
        
        if(n.equals(two) || n.equals(new BigInteger("3")) || n.equals(new BigInteger("5")) || n.equals(new BigInteger("7"))) return true;
        if((n.mod(two)).equals(BigInteger.ZERO)) return false;
        BigInteger d = getD(n);
        
        for(int i = 0; i < tries; i++){
            if(test(n, d) == false)
                return false;
        }
        
        return true;
    }
    
    
    private static boolean test(BigInteger n, BigInteger d){
        BigInteger a;
        if(n.compareTo(new BigInteger(Integer.MAX_VALUE + "")) < 0){
            a = (new BigInteger("" + ((new Random()).nextInt(n.intValue() - 2)) + 2)).abs();
        } else{
            a = (new BigInteger("" + ((new Random()).nextInt(Integer.MAX_VALUE - 2)) + 2)).abs();
        }
        
        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        
        BigInteger x = a.modPow(d, n);
        if(x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) return true;
        
        while(!d.equals(nMinusOne)){
            d = d.multiply(two);
            x = x.multiply(two).mod(n);
            if(x.equals(BigInteger.ONE)) return false;
            if(x.equals(nMinusOne)) return true;
        }
        
        return false;
    }
    
    
    private static BigInteger getD(BigInteger n){
        n = n.subtract(BigInteger.ONE);
        while((n.mod(two)).equals(BigInteger.ZERO)){
            n = n.divide(two);
        }
        return n;
    }
    
}
