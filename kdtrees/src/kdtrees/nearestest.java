package kdtrees;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdRandom;

public class nearestest {

	public static void main(String[] args) {
		
		String file = "kdtree/input10K.txt";
		In in = new In(file);
		
		int n = 10000;
		
		// initialize the two data structures with point from standard input
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }
        
        Point2D[] points = genTestPoints(n);
        
        long runTimeNano = timeST(kdtree, points);
        double runTimeSec = runTimeNano * Math.pow(10, -9);
        double callsperSecond = Math.pow(runTimeSec, -1) *n;
        System.out.println(callsperSecond);
        
        runTimeNano = timeST(brute, points);
        runTimeSec = runTimeNano * Math.pow(10, -9);
        callsperSecond = Math.pow(runTimeSec, -1) *n;
        System.out.println(callsperSecond);
	}

	private static long timeST(KdTreeST<Integer> kdtree, Point2D[] points) {
		long startTime = System.nanoTime();

        for (Point2D p : points)
        	kdtree.nearest(p);
        
        long endTime = System.nanoTime();
        
        long runTime = endTime - startTime;
		return runTime;
	}
	
	private static long timeST(PointST<Integer> pst, Point2D[] points) {
		long startTime = System.nanoTime();

        for (Point2D p : points)
        	pst.nearest(p);
        
        long endTime = System.nanoTime();
        
        long runTime = endTime - startTime;
		return runTime;
	}
	
	/*
	 * generates n random points within unit square
	 */
	private static Point2D[] genTestPoints(int n) {
		
		Point2D[] points = new Point2D[n];
		
		for (int i = 0; i < n; i++) {
			double x = StdRandom.uniform();
			double y = StdRandom.uniform();
			Point2D p = new Point2D(x,y);
			
			points[i] = p;
		}
	
		return points;
	}
}
