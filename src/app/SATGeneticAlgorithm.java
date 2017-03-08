package app;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
		
		
		int evalSum = 0;
		int[] evalValues = new int[population.length];
		for (int i = 0; i < population.length; i++) {
			evalValues[i] = population[i].performEvaluationFunction(clauseList);
			evalSum += evalValues[i];
			System.out.println(evalValues[i]);
		}
		
		// int[] evalValues = Arrays.stream(population).mapToInt(c -> c.performEvaluationFunction(clauseList)).toArray();
		
		
		// Step 1: Get two elite chromosomes from the population
		int eliteIdxs[] = evaluateElite(evalValues);
		
		System.out.println(eliteIdxs[0]);
		System.out.println(eliteIdxs[1]);
		
		
		
		
		// Step 2: Probabalistically select the most fit chromosomes in the population for reproduction (elite automatically pass to step 3)
		
		// double[] evalProb = Arrays.stream(evalValues).mapToDouble(i -> (double)i / (double)evalSum).toArray();
		
		// evaluate their probabilities
		double[] evalProbUpperBounds = new double[population.length];
		evalProbUpperBounds[0] = (double)evalValues[0] / (double)evalSum;
		System.out.println(evalProbUpperBounds[0]);
		for (int i = 1; i < population.length; i++) {
			evalProbUpperBounds[i] = evalProbUpperBounds[i - 1] + ((double)evalValues[i] / (double)evalSum);
			System.out.println(evalProbUpperBounds[i]);
		}
		evalProbUpperBounds[population.length - 1] = 1.0; // set the last upper bound to be 1 to resolve precision issues

		
		// select based on their fitness (probability of selection based on evaluation function value) (elite are guaranteed selection)
		population = performProbabilisticSelection(evalProbUpperBounds, eliteIdxs);
		
		
		// Step 3: Uniformly crossover two adjacent chromosomes (ignore elite - they continue intact)
		performUniformCrossover(eliteIdxs);
		
		// Step 4: Perform disruptive mutation (ignore elite - they continue intact)
		performDisruptiveMutation(eliteIdxs);
		
		// Step 5: Flip heuristic
		performFlipHeuristic(eliteIdxs, evalValues);
		
		
		
		
		
	}
	
	private void performFlipHeuristic(int[] eliteIdxs, int[] startEvalVals) {
		for (int i = 0; i < population.length; i++) {
			if (i != eliteIdxs[0] && i != eliteIdxs[1]) {
				System.out.println("Beginning flip heuristic for " + i);
				population[i].flipHeuristic(startEvalVals[i], clauseList);
			}
		}
	}
	
	private void performDisruptiveMutation(int[] eliteIdxs) {
		Random random = new Random();
		for (int i = 0; i < population.length; i++) {
			// A chromosome is mutated with probability 0.90
			if (i != eliteIdxs[0] && i != eliteIdxs[1] && random.nextDouble() < 0.90) {
				// Mutation here is defined as flipping each var assignment with probability 0.50
				System.out.println("mutating " + i);
				population[i].mutate();
			}
		}
	}
	
	private void performUniformCrossover(int[] eliteIdxs) {
		int xIdx = 0;
		int yIdx = 0;
		for (int i = 0; i < (population.length / 2) - 1; i++) {
			while (xIdx == eliteIdxs[0] || xIdx == eliteIdxs[1]) {
				xIdx++;
			}
			yIdx = xIdx + 1;
			while (yIdx == eliteIdxs[0] || yIdx == eliteIdxs[1]) {
				yIdx++;
			}
			System.out.println("performing crossover on " + xIdx + " and " + yIdx);
			population[xIdx].uniformCrossover(population[yIdx]);
			xIdx = yIdx + 1;
		}
	}
	
	
	private Chromosome[] performProbabilisticSelection(double[] evalProbUpperBounds, int[] eliteIdxs) {
		Random random = new Random();
		Chromosome[] newPop = new Chromosome[population.length];
		for (int i = 0; i < population.length; i++) {
			if (i != eliteIdxs[0] && i != eliteIdxs[1]) {
				double p = random.nextDouble();
				for (int j = 0; j < population.length; j++) {
					if (p < evalProbUpperBounds[j]) {
						newPop[i] = population[j];
						System.out.println("selecting " + j + " to replace " + i);
						break;
					}
				}	
			} else {
				System.out.println("Preserving elite at " + i);
			}
		}
		return newPop;
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
