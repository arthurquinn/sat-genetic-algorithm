package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import app.AlgorithmResult;

public class BarChart extends ApplicationFrame{
    
    private String title;
    
    private List<AlgorithmResult[]>[] data;
    
    public BarChart(String title, List<AlgorithmResult[]>[] resultArray) {
        super(title);
       
        this.title = title;
        this.data = resultArray;
    }
    
    public void createSuccessChart(String title, String[] colKeys, String[] rowKeys) {
        setTitle(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        
        // For each different file
        for (int i = 0; i < data.length; i++) {
            
            int[] succCount = new int[3];
            
            // For each set of algorithm results
            for (int j = 0; j < data[i].size(); j++) {
                
                // For each different time data in algorithm result array
                for (int k = 0; k < data[i].get(j).length; k++) {
                    succCount[k] = data[i].get(j)[k].getSuccess() ? succCount[k] + 1 : succCount[k];
                }
            }
            
            double[] succAvg = new double[3];
            
            // Calculate average for each count
            for (int j = 0; j < succCount.length; j++) {
                succAvg[j] = (double)succCount[j] / (double)data[i].size();
                
                dataset.addValue(succAvg[j], rowKeys[j], colKeys[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                title, 
                "Variable Number", 
                "Percentage Correct", 
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
    
    public void createMedianTimeChart(String title, String[] colKeys, String[] rowKeys) {
        setTitle(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        
        // For each different file
        for (int i = 0; i < data.length; i++) {
            
            List<Double>[] elapsedTimes = new List[3];
            elapsedTimes[0] = new ArrayList<Double>();
            elapsedTimes[1] = new ArrayList<Double>();
            elapsedTimes[2] = new ArrayList<Double>();
            
            // For each set of algorithm results
            for (int j = 0; j < data[i].size(); j++) {
                
                // For each different time data in algorithm result array
                for (int k = 0; k < data[i].get(j).length; k++) {
                    elapsedTimes[k].add(data[i].get(j)[k].getRunningTimeMs());
                }
            }
            
            // Calculate average for each count
            for (int j = 0; j < elapsedTimes.length; j++) {

                Collections.sort(elapsedTimes[j]);
                double medianTime = elapsedTimes[j].get(elapsedTimes[j].size() / 2);
                dataset.addValue(medianTime, rowKeys[j], colKeys[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                title, 
                "Variable Number", 
                "Median Elapsed Time(ms)", 
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
    
    public void createBitflipChart(String title, String[] colKeys, String[] rowKeys) {
        setTitle(title);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        
        // For each different file
        for (int i = 0; i < data.length; i++) {
            
            int[] totalBitflip = new int[3];
            
            // For each set of algorithm results
            for (int j = 0; j < data[i].size(); j++) {
                
                // For each different time data in algorithm result array
                for (int k = 0; k < data[i].get(j).length; k++) {
                    totalBitflip[k] += data[i].get(j)[k].getTotalBitflips();
                }
            }
            
            // Calculate average for each count
            for (int j = 0; j < totalBitflip.length; j++) {

                double avgBitflip = (double)totalBitflip[j] / (double)data[i].size();
                dataset.addValue(avgBitflip, rowKeys[j], colKeys[i]);
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
                title, 
                "Variable Number", 
                "Average Number of Bit Flips", 
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
        
    }
}
