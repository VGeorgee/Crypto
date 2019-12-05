
import java.math.BigInteger;
import java.util.Scanner;



public class Main {
    
    private static String[] options = new String[]{"1 - Miller-Rabin Prime Test", "2 - Extended Euclidean Algorithm", "3 - RSA encrypt", "4 - exit"};
    
    
    public static void printOptions(){
        System.out.println("---------------------------");
        for(String s : options){
            System.out.println(s);
        }
        System.out.println("---------------------------");
    }
    
    private static int getIndex(String s){
        for(int i = 0; i < options.length; i++)
            if(options[i].contains(s))
                return i;
        return -1;
    }
    
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String input;
        int index;
        do{
           printOptions();
           index = getIndex(sc.nextLine());
           System.out.println("you choose " + options[index]);
           
           switch(index){
               case 0: {
                   String[] in = sc.nextLine().split(" ");
                   System.out.println(MillerRabin.isPrime(new BigInteger(in[0])));
                   break;
               }
               case 1: {
                   String[] in = sc.nextLine().split(" ");
                   System.out.println(ExtendedEuclidean.compute(new BigInteger(in[0]), new BigInteger(in[1])));
                   break;
               }
               case 2: {
                   BigInteger b = new BigInteger(sc.nextLine());
                   RSA r = new RSA();
                   r.generateKeys();
                   System.out.println(r.decrypt(r.encrypt(b)));
                   break;
               }
           }
           
        }
        while(index != options.length - 1);
        
        /*
        
        RSA r = new RSA();
        //System.out.println(ExtendedEuclidean.compute(new BigInteger("47"), new BigInteger("252252")).X.mod(new BigInteger("252252")));
        // r.generateKeys(Prime.generatePrime(512), Prime.generatePrime(512));
        r.generateKeys();
        //r.generateKeys(Prime.generatePrime(256), Prime.generatePrime(256));
        System.out.println(r.decryptCRT(r.encrypt(3213421)));
        
        System.out.println(r.decrypt(r.encrypt(54)));
        
        String a = "8", b = "2365919607608589998389935433649144370716288138979924412629075017767928512326404609726635429343747062064351725055452778848776133483702891026114264414058108040800975322904866664358456402260050964629485122714679028004584583643446308171171499887281604645447180776564777124611321937190806659824977436023681002707497430074622146700058148610986115720695928142351898468916026607273505921543541889014564885943378256352415387544737444480635439137560625876648852417586982997895665537053700169164655378838969407666811476490382525934509305789751872982500630548019057360094946204098582494682605619842379452480441229857942866675243", c = "7097758822825769995169806300947433112148864416939773237887225053303785536979213829179906288031241186193055175166358336546328400451108673078342793242174324122402925968714599993075369206780152893888455368144037084013753750930338924513514499661844813936341542329694331373833965811572419979474932308071043008122665017903069475947877961975590922692531662492356116648956421278161405845480998898604892494566290442669097236960272540513277978363365744376919500845119552195002940368169719880662964233602542553576474895953015686604827351846142682692325340045352059730551108520909433402330154375676080601988480584055548630332455";
        
        System.out.println(Helper.FME(new BigInteger(a), new BigInteger(b), new BigInteger(c)));
        
        System.out.println((new BigInteger(a)).modPow(new BigInteger(b), new BigInteger(c)));
        
        */
        
        /*
        
        BigInteger definitelyNotPrime = new BigInteger("129");
        BigInteger probablyPrime = BigInteger.probablePrime(10, new Random());
        //System.out.println(MillerRabin.isPrime(probablyPrime));
        System.out.println(MillerRabin.isPrime( BigInteger.probablePrime(10, new Random())));
        System.out.println(Prime.generatePrime(20));
         // */
        
    }
}
