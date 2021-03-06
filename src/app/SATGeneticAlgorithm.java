package app;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class SATGeneticAlgorithm {

    private int alottedTime;
    
    private ClauseList clauseList;

    private Chromosome[] population;

    private Random random;
    
    private boolean timeExpired;

    public SATGeneticAlgorithm(ClauseList clauseList, int populationSize, int alottedTime) {
        this.alottedTime = alottedTime;
        this.timeExpired = false;
        this.clauseList = clauseList;
        this.random = new Random();
        this.population = new Chromosome[populationSize];
        for (int i = 0; i < populationSize; i++) {
            this.population[i] = new Chromosome(clauseList.getNumVars(), random);
        }
    }

    public AlgorithmResult runAlgorithm() {
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("time up");
                timeExpired = true;
                timer.cancel();
                timer.purge();
            }
          }, alottedTime);
        
        int totalFlipped = 0;
        long start = System.nanoTime();

        while (!timeExpired) {
            // Calculate eval values
            
            int evalSum = 0;
            int[] eval = new int[population.length];
            for (int i = 0; i < population.length; i++) {
                eval[i] = population[i].calcEvaluationValue(clauseList);
                
                if (eval[i] == clauseList.getClauses().size()) {
                    timer.cancel();
                    timer.purge();
                    // Create successful result and return
                    return new AlgorithmResult(true, (System.nanoTime() - start) / 1000000.0, totalFlipped);
                }
                
                evalSum += eval[i];
                //System.out.println("" + i + ": " + eval[i]);
            }

            // Calculate selection probs (upper bounds)
            double[] probUpper = new double[population.length];
            double acc = 0;
            for (int i = 0; i < population.length; i++) {
                acc += (double) eval[i] / (double) evalSum;
                probUpper[i] = acc;
                //System.out.println("" + i + ": " + probUpper[i]);
            }
            probUpper[population.length - 1] = 1.0f; // set to 1 to resolve precision errors

            // Determine elite
            int[] eliteIdx = new int[2];
            int max1 = 0;
            int max2 = 0;
            for (int i = 0; i < population.length; i++) {
                if (eval[i] > max1) {
                    
                    // swap max1 to max2
                    max2 = max1;
                    eliteIdx[1] = eliteIdx[0];

                    // set new max 1
                    max1 = eval[i];
                    eliteIdx[0] = i;
                } else if (eval[i] > max2) {
                    
                    // set new max 2
                    max2 = eval[i];
                    eliteIdx[1] = i;
                }
            }

            //System.out.println("elite1: " + eliteIdx[0]);
            //System.out.println("elite2: " + eliteIdx[1]);

            // Probabilistic selection
            Chromosome[] newPop = new Chromosome[population.length];
            for (int i = 0; i < population.length; i++) {
                if (i != eliteIdx[0] && i != eliteIdx[1]) {
                    double p = random.nextDouble();
                    for (int j = 0; j < population.length; j++) {
                        if (p < probUpper[j]) {
                            newPop[i] = new Chromosome(population[j]);
                            //System.out.println("selecting " + j + " to replace " + i);
                            break;
                        }
                    }
                } else {
                    //System.out.println("Preserving elite at " + i);
                    newPop[i] = population[i];
                }
            }
            population = newPop;

            // Uniform crossover
            int xIdx = 0;
            int yIdx = 0;
            for (int i = 0; i < (population.length / 2) - 1; i++) {
                while (xIdx == eliteIdx[0] || xIdx == eliteIdx[1]) {
                    xIdx++;
                }
                yIdx = xIdx + 1;
                while (yIdx == eliteIdx[0] || yIdx == eliteIdx[1]) {
                    yIdx++;
                }
                //System.out.println("performing crossover on " + xIdx + " and " + yIdx);
                population[xIdx].uniformCrossover(population[yIdx], random);
                xIdx = yIdx + 1;
            }

            // Disruptive mutation
            for (int i = 0; i < population.length; i++) {
                // mutate non-elite with prob 0.90
                if (i != eliteIdx[0] && i != eliteIdx[1] && random.nextDouble() < 0.90f) {
                    population[i].mutate(random);
                    //System.out.println("mutating " + i);
                }
            }

            // Flip heuristic
            for (int i = 0; i < population.length; i++) {
                if (i != eliteIdx[0] && i != eliteIdx[1]) {
                    totalFlipped += population[i].flipHeuristic(population[i].calcEvaluationValue(clauseList), clauseList, random);
                    //System.out.println("Flipping " + i);
                }
            }
        }
        
        // System.out.println("time: " + ((System.nanoTime() - start) / 1000000.0) + "ms");
        return new AlgorithmResult(false, (System.nanoTime() - start) / 1000000.0, totalFlipped);
    }
}
