/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation 
 *  Dependencies: WeightedQuickUnionUF.java
 *  
 *  A Percolation model.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private double[] result;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must larger than zero");
        }
        // perform trials independent experiments on an n-by-n grid
        result = new double[trials];
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            double count = percolation.numberOfOpenSites();
            result[i] = count / (n * n);
        }
        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);
    }
    
    // sample mean of percolation threshold
    public double mean()  {
        return mean;
    }  
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }          
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }     
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }
    
    // test client (described below)
    public static void main(String[] args) {
        if (args.length == 2) {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);
            PercolationStats percolationStats = new PercolationStats(n, t);
            StdOut.println("mean = " + percolationStats.mean());
            StdOut.println("stddev = " + percolationStats.stddev());
            StdOut.println("confidenceLo = " + percolationStats.confidenceLo());
            StdOut.println("confidenceHi = " + percolationStats.confidenceHi());
        }
    }   
}