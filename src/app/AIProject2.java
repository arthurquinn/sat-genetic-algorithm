package app;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import common.BarChart;

public class AIProject2 {
    
    private static final int POPULATION_SIZE = 10;

    public static void main(String[] args) {

        ClauseList cl = new ClauseList();
        
        List<AlgorithmResult[]> results20 = new ArrayList<AlgorithmResult[]>();
        List<AlgorithmResult[]> results50 = new ArrayList<AlgorithmResult[]>();
        List<AlgorithmResult[]> results75 = new ArrayList<AlgorithmResult[]>();
        List<AlgorithmResult[]> results100 = new ArrayList<AlgorithmResult[]>();
        
        try {

            long startTime = System.nanoTime();
            
            // Run uf100 files
            File[] files = (new File("uf100")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());
                
                AlgorithmResult[] arr = new AlgorithmResult[3];
                arr[0] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 250).runAlgorithm();
                arr[1] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 500).runAlgorithm();
                arr[2] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 750).runAlgorithm();
                
                results100.add(arr);
                System.out.println("uf100-" + i + " complete..");
            }
            
            // Run uf75 files
            files = (new File("uf75")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());

                AlgorithmResult[] arr = new AlgorithmResult[3];
                arr[0] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 250).runAlgorithm();
                arr[1] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 500).runAlgorithm();
                arr[2] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 750).runAlgorithm();
                
                results75.add(arr);
                System.out.println("uf75-" + i + " complete..");
            }
            
            // Run uf50 files
            files = (new File("uf50")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());

                AlgorithmResult[] arr = new AlgorithmResult[3];
                arr[0] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 250).runAlgorithm();
                arr[1] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 500).runAlgorithm();
                arr[2] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 750).runAlgorithm();
                
                results50.add(arr);
                System.out.println("uf50-" + i + " complete..");
            }

            // Run uf20 files
            files = (new File("uf20")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());

                AlgorithmResult[] arr = new AlgorithmResult[3];
                arr[0] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 250).runAlgorithm();
                arr[1] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 500).runAlgorithm();
                arr[2] = new SATGeneticAlgorithm(cl, POPULATION_SIZE, 750).runAlgorithm();
                
                results20.add(arr);
                System.out.println("uf20-" + i + " complete..");
            }

            List<AlgorithmResult[]>[] arr = new List[] { results100, results75, results50, results20 };
            
            String[] rowKeys = new String[] { "250ms", "500ms", "750ms" };
            String[] columnKeys = new String[] { "UF-100", "UF-75", "UF-50", "UF-20" };
            
            BarChart bc = new BarChart("SAT Genetic Algorithm - Results from 100 Benchmarks", arr);
            bc.createSuccessChart("SAT Genetic Algorithm - Average Success %", columnKeys, rowKeys);
            
            bc = new BarChart("SAT Genetic Algorithm - Results from 100 Benchmarks", arr);
            bc.createMedianTimeChart("SAT Genetic Algorithm - Median Elapsed Time", columnKeys, rowKeys);
            
            bc = new BarChart("SAT Genetic Algorithm - Results from 100 Benchmarks", arr);
            bc.createBitflipChart("SAT Genetic Algorithm - Average Number of Bit Flips", columnKeys, rowKeys);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
