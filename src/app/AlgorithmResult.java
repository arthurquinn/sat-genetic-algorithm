package app;

public class AlgorithmResult {
    
    
    private boolean success;
    private double runningTimeMs;
    private double totalBitflips;
    
    public AlgorithmResult(boolean success, double runningTimeMs, double avgBitFlips) {
        this.success = success;
        this.runningTimeMs = runningTimeMs;
        this.totalBitflips = avgBitFlips;
    }
    
    public boolean getSuccess() { return this.success; }
    
    public double getRunningTimeMs() { return this.runningTimeMs; }
    
    public double getTotalBitflips() { return this.totalBitflips; }

}
