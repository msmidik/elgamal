package cz.muni.fi.pv079.elgamal;

/**
 *
 * @author Martin Schmidt
 */
public class Main {
    
    private static final String FILE = "Elgamal_public_keys_174735.txt";

    public static void main(String[] args) {
        DataLoader dl = new DataLoader();
                
        ElGamalManager man = new ElGamalManager(dl.loadData(FILE));
        //man.test();  
        System.out.println("findQ");
        man.findQ();
        System.out.println("findX");
        man.findX();

    }
    
    

}
