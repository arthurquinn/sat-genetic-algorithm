package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import app.AlgorithmResult;

public class BarChart extends ApplicationFrame{
    
    public BarChart(String title, List<AlgorithmResult>[] resultArray, String[] colKeys) {
        super(title);
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 0; i < resultArray.length; i++) {
            int succTotal = 0;
            double bitflipTotal = 0;
            
            List<Double> runtimes = new ArrayList<Double>();
            
            double count = (double)resultArray[i].size();
            
            // Calculate the average success/runtime/bitflips
            for (int j = 0; j < count; j++) {
                succTotal = resultArray[i].get(j).getSuccess() ? succTotal + 1 : succTotal;
                runtimes.add(resultArray[i].get(j).getRunningTimeMs());
                bitflipTotal += resultArray[i].get(j).getTotalBitflips();
            }
            
            Collections.sort(runtimes);
            double succAvg = (double)succTotal / count;
            double timeMedian = runtimes.get(runtimes.size() / 2);
            double bitflipAvg = (double)bitflipTotal / count;
            
            dataset.addValue(succAvg, colKeys[i], "% Success");
            // dataset.addValue(timeMedian, "Median Runtime(ms)", colKeys[i]);
            // dataset.addValue(bitflipAvg, "Average Bit Flips", colKeys[i]);
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
    
    private void createDataset(List<AlgorithmResult> results) {
        
        
        
        
        
    }

}
