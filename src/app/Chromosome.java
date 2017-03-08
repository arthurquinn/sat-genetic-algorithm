package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {
		
	private boolean[] varAssignments;

	public Chromosome(int numVars) {
		this.varAssignments = new boolean[numVars];
		
		Random random = new Random();
		for (int i = 0; i < numVars; i++) { 
			if (random.nextFloat() >= 0.50)
				this.varAssignments[i] = true;
		}
	}
	
	public boolean[] getVarAssignments() { return this.varAssignments; }
	
	public boolean getAssignment(int idx) {
		return varAssignments[idx];
	}
	
	public void setAssignment(boolean val, int idx) {
		varAssignments[idx] = val;
	}
	
	
	// Performs the flip heuristic operation
	public void flipHeuristic(int evalVal, ClauseList clauseList) {
		Random random = new Random();
		List<Integer> idxToFlip = new ArrayList<Integer>();
		int gain = 1;
		
		int flipIters = 0;
		
		// Continue the flip heuristic as long as the flips yielded a net gain in the evaluation function values
		while (gain > 0) {
			int tempVal = -1;
			int startVal = evalVal;
			
			System.out.println("Performing " + flipIters++ + "th flip iteration");
			
			// Maintain a list of assignment indexes we have yet to flip
			for (int i = 0; i < varAssignments.length; i++) {
				idxToFlip.add(i);
			}
			for (int i = 0; i < varAssignments.length; i++) {
				
				// Get a random assignment index and remove it from the list
				int idxIdx = random.nextInt(idxToFlip.size());
				int flipIdx = idxToFlip.remove(idxIdx);
				
				// Flip the assignment and calculate the evaluation value after the flip
				boolean origVal = getAssignment(flipIdx);
				setAssignment(!origVal, flipIdx);
				tempVal = performEvaluationFunction(clauseList);
				
				// If the flip results in an improvement, set the new evaluation value and keep the flip; else discard the flip
				if (tempVal >= evalVal)
					evalVal = tempVal;
				else
					setAssignment(origVal, flipIdx);
			}
			gain = evalVal - startVal;
		}
	}
	
	
	// Mutates the chromosome; Here mutation is defined as flipping each var assignment with probability 0.50
	public void mutate() {
		Random random = new Random();
		for (int i = 0; i < varAssignments.length; i++) {
			if (random.nextDouble() < 0.50) {
				varAssignments[i] = !varAssignments[i];
			}
		}
	}
	
	// Performs a uniform crossover with another chromosome; numVars must be consistent for each chromosome
	public void uniformCrossover(Chromosome c) {
		Random random = new Random();
		for (int i = 0; i < varAssignments.length; i++) {
			// Assign theirs to ours with 0.50 prob; Assign ours to theirs with 0.50 prob
			if (random.nextDouble() < 0.50)
				setAssignment(c.getAssignment(i), i);
			else
				c.setAssignment(getAssignment(i), i);
		}
	}
	
	
	// Performs the evaluation function; here the evaluation function is the number of clauses satisfied by this chromosome
	public int performEvaluationFunction(ClauseList clauseList) {
		
		int n = 0;
		List<Integer[]> cl = clauseList.getClauses();
		
		// Apply each chromosome to the clause list and determine the number of clauses satisfied
		for (int i = 0; i < cl.size(); i++) {
			n = applyChromosomeToClause(cl.get(i)) ? n + 1 : n;
		}
		
		return n;
	}
	
	
	// Determines if this chromosome satisfies a clause
	private boolean applyChromosomeToClause(Integer[] clause) {
		
		// Determine if the assignments of this chromosome satisfy the clause
		for (int i = 0; i < clause.length; i++) {
			
			// See if the assignment is true and not it if necessary; if the assignment is true the entire clause is true
			boolean a = varAssignments[Math.abs(clause[i]) - 1];
			if (clause[i] < 0)
				a = !a;
			if (a)
				return a;
		}
		return false;
	}
}
