
import java.math.BigInteger;
import java.util.Random;






public class Main {
    public static void main(String[] args) {
        /*
        ExtendedEuclidean e = ExtendedEuclidean.compute(new BigInteger("435"), new BigInteger("33"));
        System.out.println(e);
        System.out.println((new BigInteger("3")).compareTo(BigInteger.ONE));

        // */
        RSA r = new RSA();
        //System.out.println(ExtendedEuclidean.compute(new BigInteger("47"), new BigInteger("252252")).X.mod(new BigInteger("252252")));
        r.generateKeys(Prime.generatePrime(256), Prime.generatePrime(128));
        //r.generateKeys(Prime.generatePrime(256), Prime.generatePrime(256));
        System.out.println(r.decrypt(r.encrypt(2330)));
        /*
        
        BigInteger definitelyNotPrime = new BigInteger("129");
        BigInteger probablyPrime = BigInteger.probablePrime(10, new Random());
        //System.out.println(MillerRabin.isPrime(probablyPrime));
        System.out.println(MillerRabin.isPrime( BigInteger.probablePrime(10, new Random())));
        System.out.println(Prime.generatePrime(20));
         // */
    }
}
