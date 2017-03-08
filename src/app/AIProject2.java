package app;

import java.io.IOException;

public class AIProject2 {

	public static void main(String[] args) {
		
		ClauseList cl = new ClauseList();
		try {
			
			cl.loadCNFFile("uf20\\uf20-01.cnf");
			
			
			SATGeneticAlgorithm satAlg = new SATGeneticAlgorithm(cl, 10);
			
			satAlg.runAlgorithm();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
