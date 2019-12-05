
import java.math.BigInteger;



public class RSA {
    private static final int INITIALBITSIZE = 128;
    
    PublicKey publicKey;
    PrivateKey privateKey;
    BigInteger n;
    
    BigInteger p;
    BigInteger q;
    
    
    public void generateKeys(){
        generateKeys(Prime.generatePrime(INITIALBITSIZE), Prime.generatePrime(INITIALBITSIZE));
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
        p = a;
        q = b;
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
    
    public BigInteger decryptCRT(BigInteger numberToDecrypt){
        
        BigInteger mp = numberToDecrypt.modPow((privateKey.d.mod(p.subtract(BigInteger.ONE))), p);
        BigInteger mq = numberToDecrypt.modPow((privateKey.d.mod(q.subtract(BigInteger.ONE))), q);
        
        ExtendedEuclidean ee = ExtendedEuclidean.compute(p, q);
        
        BigInteger yp = ee.X;
        BigInteger yq = ee.Y;
        
        return (mp.multiply(yq).multiply(q).add(mq.multiply(yp).multiply(p))).mod(q.multiply(p));
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
    private static BigInteger two = new BigInteger("2");
    
    public static BigInteger getE(BigInteger phiN){
        BigInteger bi = new BigInteger("7");
        
        while(!ExtendedEuclidean.compute(phiN, bi).remainder.equals(BigInteger.ONE)){
            bi = bi.add(two);
        }
        
        return bi;
    }
    
    
    public static BigInteger FME(BigInteger a, BigInteger b, BigInteger modulo){
        boolean[] in = new boolean[32768];
        BigInteger[] ints = new BigInteger[32768];
        BigInteger modularExponent = BigInteger.ONE;
        
        
        ints[0] = BigInteger.ONE;
        
        int i;
        for(i = 1; ints[i - 1].compareTo(b) < 0; i++){
            ints[i] = ints[i - 1].multiply(two);
        }
        
        int vegsohatvany = i;
        
        BigInteger base = new BigInteger(b.toString());
        for(i--; i >= 0; i--){
            if(base.compareTo(ints[i]) >= 0){
                in[i] = true;
                base = base.subtract(ints[i]);
            }
        }
        
        BigInteger[] hatvanyok = new BigInteger[32768];
        hatvanyok[0] = a;

        for(i = 1; i < vegsohatvany; i++){
            if(hatvanyok[i - 1].equals(BigInteger.ONE)){
                hatvanyok[i] = BigInteger.ONE;
            }else hatvanyok[i] = hatvanyok[i - 1].multiply(hatvanyok[i - 1]).mod(modulo);
        }
        
        for(i = 0; i < vegsohatvany; i++){
            if(in[i]){
                modularExponent = modularExponent.multiply(hatvanyok[i]).mod(modulo);
            }
        }
        
        return modularExponent;
    }
    
}