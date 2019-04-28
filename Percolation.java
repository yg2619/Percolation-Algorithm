/* *****************************************************************************
 *  Name: Yirou Ge
 *  Date: 2019/04/04
 *  Description: assignment week 1 percolation
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridSize;
    private final WeightedQuickUnionUF wqf;
    // fullness is similar to wqf but without virtual bottom site, tracking fullness without backwash
    private final WeightedQuickUnionUF fullness;
    private boolean[][] grid;
    private final int virtualtop;
    private final int virtualbottom;
    private int opensites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("grid size needs to be larger than 0");
        gridSize = n;
        virtualtop = 0;
        virtualbottom = n * n + 1;
        grid = new boolean[n][n];
        wqf = new WeightedQuickUnionUF((n * n + 2)); // create a virtual top and bottom
        fullness = new WeightedQuickUnionUF((n * n + 1));
        opensites = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        for (int j = 1; j <= n; j++) {
            int i = 1;
            wqf.union(virtualtop, xyto1D(i, j));
            fullness.union(virtualtop, xyto1D(i, j));
        }
        for (int j = 1; j <= n; j++) {
            wqf.union(virtualbottom, xyto1D(n, j));
        }
    }

    public void open(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize)
            throw new IllegalArgumentException("index out of range");
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            opensites++;
        }
        if (row > 1 && isOpen(row - 1, col)) {
            wqf.union(xyto1D(row - 1, col), xyto1D(row, col));
            fullness.union(xyto1D(row - 1, col), xyto1D(row, col));
        }
        if (row < gridSize && isOpen(row + 1, col)) {
            wqf.union(xyto1D(row + 1, col), xyto1D(row, col));
            fullness.union(xyto1D(row + 1, col), xyto1D(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            wqf.union(xyto1D(row, col - 1), xyto1D(row, col));
            fullness.union(xyto1D(row, col - 1), xyto1D(row, col));
        }
        if (col < gridSize && isOpen(row, col + 1)) {
            wqf.union(xyto1D(row, col + 1), xyto1D(row, col));
            fullness.union(xyto1D(row, col + 1), xyto1D(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize)
            throw new IllegalArgumentException("index out of range");
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) { // backwash?
        if (row <= 0 || row > gridSize || col <= 0 || col > gridSize)
            throw new IllegalArgumentException("index out of range");
        return (isOpen(row, col) && fullness
                .connected(virtualtop, xyto1D(row, col))); // return可以退出该method并返回某个值
    }

    public int numberOfOpenSites() {
        return opensites;
    }

    public boolean percolates() {
        if (gridSize == 1) {
            return (isOpen(1, 1));
        }
        else {
            return (wqf.connected(virtualtop, virtualbottom));
        }
    }

    private int xyto1D(int row, int col) {
        return (row - 1) * gridSize + col;
    }

    public static void main(String[] args) {
        // main method left empty on purpose
    }


}
