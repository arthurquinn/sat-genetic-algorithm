package app;

public class AlgorithmResult {
    
    
    private boolean success;
    private double runningTimeMs;
    private int totalBitflips;
    
    public AlgorithmResult(boolean success, double runningTimeMs, int totalBitflips) {
        this.success = success;
        this.runningTimeMs = runningTimeMs;
        this.totalBitflips = totalBitflips;
    }
    
    public boolean getSuccess() { return this.success; }
    
    public double getRunningTimeMs() { return this.runningTimeMs; }
    
    public int getTotalBitflips() { return this.totalBitflips; }

}
