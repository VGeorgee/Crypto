
import java.math.BigInteger;






public class Main {
    public static void main(String[] args) {
        ExtendedEuclidean e = ExtendedEuclidean.compute(new BigInteger("435"), new BigInteger("33"));
        System.out.println(e);
    }
}
