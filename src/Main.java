
import java.math.BigInteger;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        MillerRabin.isPrime(BigInteger.ONE);
        Scanner sc = new Scanner(System.in);
        String input;
        int index;
        do{
           printOptions(OPTIONS);
           index = getIndex(sc.nextLine(), OPTIONS);
           
           switch(index){
                case 0: {
                    System.out.print("a: ");
                    String[] in = sc.nextLine().split(" ");
                    System.out.println(MillerRabin.isPrime(new BigInteger(in[0])));
                    break;
                }
                case 1: {
                    System.out.print("a b:");
                    String[] in = sc.nextLine().split(" ");
                    System.out.println(ExtendedEuclidean.compute(new BigInteger(in[0]), new BigInteger(in[1])));
                    break;
                }
                case 2: {
                    handleRSARequest(sc);
                    break;
                 }
            }
        }
        while(index != OPTIONS.length - 1);
        
    }
    
    
    private static String[] OPTIONS = new String[]{"1 - Miller-Rabin Prime Test", "2 - Extended Euclidean Algorithm", "3 - RSA", "4 - exit"};
    private static String[] RSAOPTIONS = new String[] {"1 - Encrypt data", "2 - Decrypt data", "3 - exit"};
    private static String[] ENCRYPTMESSAGES = new String[] {
        "Set p (no input means generating random prime):",
        "Set q (no input means generating random prime):",
        "set e (no input means generating random number)",
        "Set m (only digits):"
    };
    
    private static String[] DECRYPTMESSAGES = new String[] {
        "Set p:", 
        "Set q:", 
        "Set d:",
        "set c"
    };
    
    public static void printOptions(String[] options){
        System.out.println("---------------------------");
        for(String s : options){
            System.out.println(s);
        }
        System.out.println("---------------------------");
    }
    
    
    private static int getIndex(String s, String[] options){
        for(int i = 0; i < options.length; i++)
            if(options[i].contains(s))
                return i;
        return -1;
    }
    
    
    private static void handleRSARequest(Scanner sc){
        System.out.println("\nselect required functionality:");
        int index;
        
        do{
            printOptions(RSAOPTIONS);
            index = getIndex(sc.nextLine(), RSAOPTIONS);
            
            if(index == 0){
                RSAEncrypt(sc);
            }
            else if( index == 1){
                RSADecrypt(sc);
            }
            
        }while(index != RSAOPTIONS.length - 1);
    }
    
    private static BigInteger tryParse(String s){
        BigInteger b = null;
         try {
            if(s.isEmpty()){
                b = Prime.generatePrime(128);
                System.out.println(b);
            }
            else{
                b = new BigInteger(s);
                if(!MillerRabin.isPrime(b)) {
                    System.out.println("number is not prime!");
                    return null;
                }
            }
        } catch (Exception ex) {
            System.out.println("wrong format!");
        }
        return b;
    }
    
    private static void RSAEncrypt(Scanner sc){
        RSA r = new RSA();
        BigInteger p = null, q = null, e = null, m, phiN = null, message = null;
        
        for(int i = 0; i < ENCRYPTMESSAGES.length; i++){
            System.out.println(ENCRYPTMESSAGES[i]);
            if(i == 0){
               p = tryParse(sc.nextLine());
               if(p == null) i--;
            }
            else if(i == 1){
               q = tryParse(sc.nextLine());
               if(q == null) i--;
            }
            else if(i == 2){
                phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
               String s = sc.nextLine();
               if(s.isEmpty()){
                   System.out.println("generating e:");
                   e = Helper.getE(phiN);
               }
               else{
                   e = new BigInteger(s);
                   if(!ExtendedEuclidean.compute(phiN, e).remainder.equals(BigInteger.ONE)){
                       System.out.println("not valid e");
                       i--;
                   }
               }
            }
            else if(i == 3){
                try {
                    message = new BigInteger(sc.nextLine());
                } catch (Exception ex) {
                    System.out.println("wrong input! only digits are allowed");
                    i--;
                }
            }
        }
        
        r.generateKeys(p, q, e);
        System.out.println(r);
        System.out.println("___________________________________________");
        System.out.println("\nENCRYPTED DATA = " + r.encrypt(message));
        System.out.println("___________________________________________");
    }
    
    
    private static void RSADecrypt(Scanner sc){
        RSA r = new RSA();
        BigInteger p = null, q = null, d = null, phiN = null, c = null;
        
        for(int i = 0; i < DECRYPTMESSAGES.length; i++){
            System.out.println(DECRYPTMESSAGES[i]);
            if(i == 0){
               p = tryParse(sc.nextLine());
               if(p == null) i--;
            }
            else if(i == 1){
               q = tryParse(sc.nextLine());
               if(q == null) i--;
            }
            else if(i == 2){
                phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
                d = new BigInteger(sc.nextLine());
            }
            else if(i == 3){
                try {
                    c = new BigInteger(sc.nextLine());
                } catch (Exception ex) {
                    System.out.println("wrong input! only digits are allowed");
                    i--;
                }
            }
        }
        
        r.generateKeysForDecrypt(p, q, d);
        System.out.println(r);
        System.out.println("___________________________________________");
        System.out.println("DECRYPTED DATA = " + r.decrypt(c));
        System.out.println("___________________________________________");
        
    }
}
