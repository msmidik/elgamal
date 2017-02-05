package cz.muni.fi.pv079.elgamal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * load data from given text file
 * @author Martin
 */
public class DataLoader {
    /**
     * load data
     * @param file txt file
     * @return list of elgamal systems
     */
    public List<ElGamalSystem> loadData(String file) {
        List<ElGamalSystem> elGamals = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                elGamals.add(parse(line));
            }
            return elGamals;

        } catch (Exception ex) {
            System.err.println("data loading error");
            return null;
        }
    }
    
    /**
     * parse file line
     * @param line line
     * @return elgamasystem object
     */
    private ElGamalSystem parse(String line) {
        ElGamalSystem egs = new ElGamalSystem();
        String[] parsed = line.split("\\( ")[1].split(" \\)")[0].split(" , ");
        egs.setP(new BigInteger(parsed[0]));
        egs.setG(new BigInteger(parsed[1]));
        egs.setQ(new BigInteger(parsed[2]));
        egs.setH(new BigInteger(parsed[3]));
        egs.setX(new BigInteger(parsed[4]));
        return egs;
    }

}
