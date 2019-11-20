
import java.math.BigInteger;



public class RSA {
    private static final int initialBitSize = 128;
    
    PublicKey publicKey;
    PrivateKey privateKey;
    BigInteger n;
    
    
    public void generateKeys(){
        generateKeys(Prime.getPrimeFromBigInteger(initialBitSize), Prime.getPrimeFromBigInteger(initialBitSize));
    }
    
    public void generateKeys(BigInteger a, BigInteger b){
        BigInteger phiN = a.subtract(BigInteger.ONE).multiply(b.subtract(BigInteger.ONE));
        generateKeys(a, b, Helper.getE(phiN));
    }
    
    public void generateKeys(BigInteger a, BigInteger b, BigInteger e){
        BigInteger N = a.multiply(b);
        this.n = N;
        BigInteger phiN = a.subtract(BigInteger.ONE).multiply(b.subtract(BigInteger.ONE));
        
        BigInteger d = ExtendedEuclidean.compute(phiN, e).Y.mod(phiN);
        
        publicKey = new PublicKey(e, N);
        privateKey = new PrivateKey(d);
        
    }
    
    public BigInteger encrypt(int numberToEncrypt){
        BigInteger number = new BigInteger(numberToEncrypt + "");
        return encrypt(number);
    }
    
    public BigInteger encrypt(BigInteger numberToEncrypt){
        return numberToEncrypt.modPow(publicKey.e, publicKey.n);
    }
        
    public BigInteger decrypt(BigInteger numberToDecrypt){
        return numberToDecrypt.modPow(privateKey.d, n);
    }
    
    public BigInteger decryptWithCRT(BigInteger numberToDecrypt){
        return null;
    }
    
}

class PublicKey{
    BigInteger e;
    BigInteger n;

    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    @Override
    public String toString() {
        return "PublicKey{ n=" + n + ", e=" + e + " }";
    }

}

class PrivateKey{
    BigInteger d;

    public PrivateKey(BigInteger d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "PrivateKey{ " + "d=" + d + " }";
    }
    
}



class Helper{        
    public static BigInteger getE(BigInteger phiN){
        BigInteger two = new BigInteger("2");
        BigInteger bi = new BigInteger("7");
        
        while(!ExtendedEuclidean.compute(phiN, bi).remainder.equals(BigInteger.ONE)){
            bi = bi.add(two);
        }
        
        return bi;
    }
    
}