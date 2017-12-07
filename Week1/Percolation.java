/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation 
 *  Dependencies: WeightedQuickUnionUF.java
 *  
 *  A Percolation model.
 * 
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private int n;
    private int size;
    private int number = 0; // number of open sites
    private WeightedQuickUnionUF mUF;
    private WeightedQuickUnionUF mFullUF;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must larger than zero");
        }
        
        this.n = n;
        size = n*n;
        sites = new boolean[size];
        mUF = new WeightedQuickUnionUF(size + 2); // Extra 2 for virtual top and bottom site
        mFullUF = new WeightedQuickUnionUF(size + 1);
        for (int i = 0; i < size; i++) {
            sites[i] = false; // false for blocked, true for open, default all blocked
            if (i < n) { 
                // union top sites with virtual top site
                mUF.union(i, size); // size for virtual top site
                mFullUF.union(i, size);
            }
            if (i > size - n - 1) {
                // union bottom sites with virtual bottom site
                mUF.union(i, size + 1); // size + 1 for virtual bottom site
            }
        }
    }
    
    public void open(int row, int col) {
        checkSite(row, col);
        
        if (!isOpen(row, col)) {
            int site = index(row, col);
            sites[site] = true; // open the site
            number++;
            
            if (row > 1) { // have top site
                int top = index(row - 1, col);
                if (isOpen(top)) {
                    union(site, top);
                }
            }
            
            if (row < n) { // have bottom site
                int bottom = index(row + 1, col);
                if (isOpen(bottom)) {
                    union(site, bottom);
                }
            }
            
            if (col > 1) { // have left site
                int left = index(row, col - 1);
                if (isOpen(left)) {
                    union(site, left);
                }
            }
            
            if (col < n) { // have right site
                int right = index(row, col + 1);
                if (isOpen(right)) {
                    union(site, right);
                }
            }
        }
    }
    
    public boolean isOpen(int row, int col) {
        checkSite(row, col);
        
        return isOpen(index(row, col));
    }
    
    private boolean isOpen(int index) {
        if (index < 0 || index > sites.length - 1) {
            throw new IllegalArgumentException("index must between zero and sites.length - 1");
        }
        return sites[index];
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col)  {
        checkSite(row, col);
        
        int site = index(row, col);
        
        if(row == 1) {
            return isOpen(site);
        }
        return mFullUF.connected(site, size);
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        return number;
    }
        
    // does the system percolate?    
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        return mUF.connected(size, size + 1);
    }          
    
    private int index(int row, int col) {
        return (row - 1) * n + col - 1;
    }
    
    private void union(int p, int q) {
        if (!mUF.connected(p, q)) {
            mUF.union(p, q);
        }
        if (!mFullUF.connected(p, q)) {
            mFullUF.union(p, q);
        }
    }
    
    private void checkSite(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row must between zero and n");
        }
        
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("column must between zero and n");
        }
    }
        
    public static void main(String[] args) {
    }
}