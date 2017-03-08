package app;

import java.util.List;

public class SATGeneticAlgorithm {
	
	private ClauseList clauseList;
	
	private Chromosome[] population;
	
	public SATGeneticAlgorithm(ClauseList clauseList, int populationSize) {
		this.clauseList = clauseList;
		this.population = new Chromosome[populationSize];
		for (int i = 0; i < populationSize; i++) {
			this.population[i] = new Chromosome(clauseList.getNumVars());
		}
	}
	
	public void runAlgorithm() {
		List<Integer[]> clauses = clauseList.getClauses();
		
		// Step 0: Perform the evaluation function for each chromosome in the population
		int[] evalValues = new int[population.length];
		for (int i = 0; i < population.length; i++) {
			evalValues[i] = population[i].performEvaluationFunction(clauseList);
			
			System.out.println(evalValues[i]);
		}
		
		// Step 1: Get two elite chromosomes from the population
		int eliteIdxs[] = evaluateElite(evalValues);
		
		System.out.println(eliteIdxs[0]);
		System.out.println(eliteIdxs[1]);
		
		
		
		
		// Step 2: Probabalistically select the most fit chromosomes in the population for reproduction
		
		
		
		
	}
	
	
	// Determine the two most elite evaluation values
	private int[] evaluateElite(int[] evalVals) {
		int[] idxs = new int[2];
		
		int max1 = 0;
		int max2 = 0;
		
		
		for (int i = 0; i < evalVals.length; i++) {
			if (evalVals[i] > max1) {
				
				// swap max1 to max2
				max2 = max1;
				idxs[1] = idxs[0];
				
				// set new max 1
				max1 = evalVals[i];
				idxs[0] = i;
				
			} else if (evalVals[i] > max2) { 
				
				// set new max 2
				max2 = evalVals[i];
				idxs[1] = i;
			}
		}
		
		return idxs;
	}
}
