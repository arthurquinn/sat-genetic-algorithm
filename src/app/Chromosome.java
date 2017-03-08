package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome {
		
	private boolean[] values;

	public Chromosome(int numVars, Random random) {
		this.values = new boolean[numVars];
		for (int i = 0; i < numVars; i++) { 
			if (random.nextFloat() >= 0.50)
				this.values[i] = true;
		}
	}
	
	public Chromosome(Chromosome c) {
		values = new boolean[c.getValues().length];
		for (int i = 0; i < c.getValues().length; i++) {
			this.values[i] = c.getValues()[i];
		}
	}
	
	public boolean[] getValues() { return this.values; }
	
    public int calcEvaluationValue(ClauseList clauseList) {
        int f = 0;
        List<Integer[]> cl = clauseList.getClauses();
        for (int i = 0; i < cl.size(); i++) {
            for (int j = 0; j < 3; j++) { 
                boolean val = values[Math.abs(cl.get(i)[j]) - 1];
                if (cl.get(i)[j] < 0)
                    val = !val;
                if (val) {
                    f += 1;
                    break;
                }
            }
        }
        return f;
    }

    public void uniformCrossover(Chromosome c, Random random) {
        for (int i = 0; i < values.length; i++) {
            // They get our value with 0.50 prob; we get theirs with 0.50 prob
            if (random.nextDouble() < 0.50f)
                c.values[i] = values[i];
            else
                values[i] = c.values[i];
        }
    }

    public void mutate(Random random) {
        for (int i = 0; i < values.length; i++)
            if (random.nextDouble() < 0.50)
                values[i] = !values[i];
    }

    public void flipHeuristic(int initEval, ClauseList clauseList, Random random) {
        int gain = 1;
        int startEval = initEval;
        int currEval = initEval;

        while (gain > 0) {
            startEval = currEval;
            List<Integer> remainIdx = new ArrayList<Integer>();
            for (int i = 0; i < values.length; i++) {
                remainIdx.add(i);
            }
            
            for (int i = 0; i < values.length; i++) {
                // Get random index
                int idx = remainIdx.remove(random.nextInt(remainIdx.size()));

                // Flip value at idx
                values[idx] = !values[idx];
                int eval = calcEvaluationValue(clauseList);

                // If flip netted us a gain, update current eval and accept flip; else reject flip
                if (eval >= currEval)
                    currEval = eval;
                else
                    values[idx] = !values[idx];
            }

            // Check if all flips netted us a gain
            gain = currEval - startEval; 
        }
    }
}
