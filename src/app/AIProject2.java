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
        
        List<AlgorithmResult> results20 = new ArrayList<AlgorithmResult>();
        List<AlgorithmResult> results50 = new ArrayList<AlgorithmResult>();
        List<AlgorithmResult> results75 = new ArrayList<AlgorithmResult>();
        List<AlgorithmResult> results100 = new ArrayList<AlgorithmResult>();
        
        try {

            long startTime = System.nanoTime();
            
            // Run uf100 files
            File[] files = (new File("uf100")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());
                results100.add(new SATGeneticAlgorithm(cl, POPULATION_SIZE).runAlgorithm());
                
                System.out.println("uf100-" + i + " complete..");
            }
            
            // Run uf75 files
            files = (new File("uf75")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());
                results75.add(new SATGeneticAlgorithm(cl, POPULATION_SIZE).runAlgorithm());
                
                System.out.println("uf75-" + i + " complete..");
            }
            
            // Run uf50 files
            files = (new File("uf50")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());
                results50.add(new SATGeneticAlgorithm(cl, POPULATION_SIZE).runAlgorithm());
                
                System.out.println("uf50-" + i + " complete..");
            }

            // Run uf20 files
            files = (new File("uf20")).listFiles();
            for (int i = 0; i < 100; i++) {
                cl.loadCNFFile(files[i].getPath());
                results20.add(new SATGeneticAlgorithm(cl, POPULATION_SIZE).runAlgorithm());
                
                System.out.println("uf20-" + i + " complete..");
            }

            List<AlgorithmResult>[] arr = new List[] { results100, results75, results50, results20 };
            String[] columnKeys = new String[] { "UF-100", "UF-75", "UF-50", "UF-20" };
            
            BarChart bc = new BarChart("SAT Genetic Algorithm - Results from 100 Benchmarks", arr, columnKeys);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
