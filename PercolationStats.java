/* *****************************************************************************
 *  Name: Yirou Ge
 *  Date: 2019/04/08
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CON = 1.96;
    private final int time;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trails) {
        if (n <= 0 || trails <= 0)
            throw new IllegalArgumentException("index out of range");
        time = trails;
        double[] fractions = new double[trails];
        for (int i = 0; i < trails; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            int x = p.numberOfOpenSites();
            fractions[i] = (double) x / (n * n);
        }
        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        double low = mean - ((CON * stddev) / Math.sqrt(time));
        return low;
    }

    public double confidenceHi() {
        double high = mean + ((CON * stddev) / Math.sqrt(time));
        return high;
    }

    public static void main(String[] args) {
        PercolationStats exp = new PercolationStats(Integer.parseInt(args[0]),
                                                    Integer.parseInt(args[1]));
        System.out.println("mean\t" + "= " + exp.mean());
        System.out.println("stddev\t" + "= " + exp.stddev());
        System.out.println(
                "95% confidence interval\t" + "= [" + exp.confidenceLo() + ", " + exp.confidenceHi()
                        + "]");
    }
}
