package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClauseList {

    private int numVars;

    private List<Integer[]> clauses;

    public void loadCNFFile(String filepath) throws IOException {
        this.clauses = new ArrayList<Integer[]>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            String line = br.readLine();
            int lineNo = 0;
            while (line != null) {
                if (lineNo == 7) {
                    String[] temp = line.split(" ");
                    this.numVars = Integer.parseInt(temp[2]);
                } else if (lineNo > 7) {
                    if (line.contains("%"))
                        break;
                    Integer[] clause = new Integer[3];
                    String[] temp = line.split(" ");
                    int i = 0;
                    if (temp[i].equals(""))
                        i++;
                    clause[0] = Integer.parseInt(temp[i++]);
                    clause[1] = Integer.parseInt(temp[i++]);
                    clause[2] = Integer.parseInt(temp[i]);
                    this.clauses.add(clause);
                }
                line = br.readLine();
                lineNo++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
        }
    }

    public int getNumVars() {
        return this.numVars;
    }

    public List<Integer[]> getClauses() {
        return this.clauses;
    }
}
