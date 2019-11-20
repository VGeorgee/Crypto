
import java.math.BigInteger;

public class ExtendedEuclidean {
    private static final int DEFAULTARRAYSIZE = 1000;
    BigInteger remainder;
    BigInteger quotient;
    BigInteger X;
    BigInteger Y;

    public ExtendedEuclidean(BigInteger remainder, BigInteger quotient, BigInteger X, BigInteger Y) {
        this.remainder = remainder;
        this.quotient = quotient;
        this.X = X;
        this.Y = Y;
    }
    
    public static ExtendedEuclidean compute(BigInteger a, BigInteger b){
        BigInteger[][] tp = init(a, b);
        BigInteger[] Rk = tp[0];
        BigInteger[] Qk = tp[1];
        BigInteger[] Xk = tp[2];
        BigInteger[] Yk = tp[3];
        
        int i;
        for(i = 1; !Rk[i].equals(BigInteger.ZERO); i++){
            Qk[i] = Rk[i - 1].divide(Rk[i]);
            Rk[i + 1] = Rk[i - 1].mod(Rk[i]);
            Xk[i + 1] = (Qk[i].multiply(Xk[i])).add(Xk[i - 1]);
            Yk[i + 1] = (Qk[i].multiply(Yk[i])).add(Yk[i - 1]);
        }
        i--;
        return new ExtendedEuclidean(Rk[i], Qk[i - 1], i % 2 == 0 ? Xk[i] : Xk[i].negate(),  i % 2 == 0 ? Yk[i].negate() : Yk[i]);
    }
    
    private static BigInteger[][] init(BigInteger a, BigInteger b){
        BigInteger[] Rk = new BigInteger[DEFAULTARRAYSIZE];
        BigInteger[] Qk = new BigInteger[DEFAULTARRAYSIZE];
        BigInteger[] Xk = new BigInteger[DEFAULTARRAYSIZE];
        BigInteger[] Yk = new BigInteger[DEFAULTARRAYSIZE];
        BigInteger[][] tp = {Rk, Qk, Xk, Yk}; 
        
        Rk[0] = a;
        Rk[1] = b;
        Qk[0] = BigInteger.ZERO;
        Xk[0] = BigInteger.ONE;
        Xk[1] = BigInteger.ZERO;
        Yk[1] = BigInteger.ONE;
        Yk[0] = BigInteger.ZERO;
        return tp;
    }

    @Override
    public String toString() {
        return "remainder=" + remainder + ", quotient=" + quotient + ", X=" + X + ", Y=" + Y;
    }

    
}
