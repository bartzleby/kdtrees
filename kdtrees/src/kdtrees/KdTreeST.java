package kdtrees;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeST<Value> {

	// unit testing of the methods (not graded)
	public static void main(String[] args) {
		KdTreeST<Integer> tkdt = new KdTreeST<>();
		
//		tkdt.put(new Point2D(0,0), 0);
//		tkdt.put(new Point2D(1,1), 1);
//		tkdt.put(new Point2D(1,2), 2);
//		tkdt.put(new Point2D(1,1), 3);
//		tkdt.put(new Point2D(0,1), 4);
//		tkdt.put(new Point2D(-1,1),5);
//		
//		System.out.println(tkdt.root.o);
//		System.out.println(tkdt.root.p);
//		System.out.println(tkdt.root.value);
//		
//		System.out.println(tkdt.root.rt.o);
//		System.out.println(tkdt.root.rt.p);
//		System.out.println(tkdt.root.rt.value);
//		
//		System.out.println(tkdt.root.rt.rt.o);
//		System.out.println(tkdt.root.rt.rt.p);
//		System.out.println(tkdt.root.rt.rt.value);
//		
//		System.out.println(tkdt.size());
//		
//		System.out.println(tkdt.contains(new Point2D(1,1)));
//		tkdt.put(new Point2D(2,3), 0);
//		tkdt.put(new Point2D(1,5), 1);
//		tkdt.put(new Point2D(4,2), 2);
//		tkdt.put(new Point2D(4,5), 3);
//		tkdt.put(new Point2D(3,3), 4);
//		tkdt.put(new Point2D(4,4), 5);
//		System.out.println(tkdt.points());
//		
////		for (Point2D p : tkdt.range(new RectHV(1.5,2.5,4.5,33.5)))
////			System.out.println(p);
//		
//		System.out.println(tkdt.nearest(new Point2D(-5,13)));
//		System.out.println(tkdt.root.o);
		
		KdTreeST<Integer> kdtree = rd(new In("inFile.txt"));
		//System.out.println(kdtree.points());
		
		Point2D[] testPoints = {new Point2D(0.5, 0.99), new Point2D(0.5, 0.95), 
				new Point2D(0.5, 0.93), new Point2D(0.5, 0.91), new Point2D(0.5, 0.89)};

		for (Point2D p : testPoints) {
			System.out.printf("%s nearest:%s\n", p, kdtree.nearest(p));
		}
	}
	
	private static KdTreeST<Integer> rd(In in) {
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
        return kdtree;
	}
	
	private int s; // size

	private Node root;
	private class Node {

		private Point2D p;      // the point
		private Value value;    // the symbol table maps the point to this value
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
		private boolean o;		// orientation: true for horizontal
		
		private Node(Point2D p, Value val, boolean o) {
			this.p = p;
			this.value = val;
			this.o = o;
		}
		
	}

	/*
	 * 
	 */
	public KdTreeST() {} // construct an empty symbol table of points

	public boolean isEmpty() {
		return size() == 0;
	} // is the symbol table empty?

	public int size() {
		return s;
	} // number of points

	public void put(Point2D p, Value val) {
		if (p == null || val == null) 
			throw new NullPointerException();
		
		// we will gradually squeeze the bounds as the potential new node
		// is pushed farther into tree.  This avoids an unwieldy set
		// of floor and ceiling calls.
		// bounds as [ [lowerLeftX, lowerLeftY], [upperRightX, upperRightY] ]
		double[][] bounds = {{Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY}, 
				{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY}};
		
	    root = put(root, p, val, true, bounds);
	    //assert check();
	} // associate the value val with point p
	
	private Node put(Node x, Point2D key, Value val, boolean po, double[][] bounds) {
		
	    if (x == null) {
	    	Node n  = new Node(key, val, !po);
	    	setRect(n, bounds);
	    	s++; return n;
	    }
	    // if the node to be inserted is oriented horizontally compare xs
	    double cmp = po ? key.x() - x.p.x() : key.y() - x.p.y();

	    if (cmp < 0) {
	    // if left of or below set bounds appropriately
	    // and put new node to left subtree
	    	if (po) bounds[1][0] = x.p.x();
	    	else bounds[1][1] = x.p.y();
	    	x.lb = put(x.lb, key, val, !po, bounds);
	    } else if (!key.equals(x.p)) {
	    // otherwise, if the node belongs in the upper or right subtree
	    //               and is not already in the tree
	    // 
	    // set bounds appropriately and put new node to right subtree
		    if (po) bounds[0][0] = x.p.x();
		    else bounds[0][1] = x.p.y();
		    x.rt = put(x.rt, key, val, !po, bounds);
    	} else {
    	// in this case the value is overwritten and updated node returned
    		x.value = val;
    	} return x;
	}
	
	/*
	 * maps our bounds[][] to RectHV object
	 * belonging to Node n
	 */
	private void setRect(Node n, double[][] bounds) {
		n.rect = new RectHV(bounds[0][0], bounds[0][1], bounds[1][0], bounds[1][1]);
	}

	

	public Value get(Point2D p) {
		if (p == null)
			throw new NullPointerException();
		
		return get(root, p);
	} // value associated with point p
	
	private Value get(Node x, Point2D p) {
		if (x == null) return null;
		Double cmp = x.o ? p.x() - x.p.x() : p.y() - x.p.y();
        if (cmp < 0) return get(x.lb, p);
        else {
        	if (!p.equals(x.p)) {
        		return get(x.rt, p);
        	} return x.value;
        }
    }

	public boolean contains(Point2D p) {
		if (p == null) throw new NullPointerException();
		if (this.get(p) == null)
			return false;
		else return true;
	} // does the symbol table contain point p?

	public Iterable<Point2D> points() {
		Queue<Point2D> loq = new Queue<>();		
		if (this.isEmpty()) return loq;

		Queue<Node> q = new Queue<>();
		q.enqueue(root);
		while (!q.isEmpty()) {
			Node n = q.dequeue();
			loq.enqueue(n.p);
			
			if (!(n.lb == null)) q.enqueue(n.lb);
			if (!(n.rt == null)) q.enqueue(n.rt);
		}
		
		return loq;
	} // all points in the symbol table

	/**
	 * all points within tree and RectHV rect
	 * @param rect
	 * @return
	 */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new NullPointerException();
		Bag<Point2D> bg = new Bag<>();
		range(rect, root, bg);
		return bg;
	} // all points that are inside the rectangle
	
	private void range(RectHV rect, Node x, Bag<Point2D> b) {
		if (x == null) return;
		if (x.rect.intersects(rect))
		{
			if (rect.contains(x.p))	b.add(x.p);
			range(rect, x.lb, b);
			range(rect, x.rt, b);
		} else return;
	}
	
	/*
	 * private inner class to represent a nearNode:
	 * i.e. a node with an associated distance
	 * 		which is set as distance from
	 * 		point passed to nearest()
	 */
	private class nearNode {
		Node node;
		double d;
		
		private nearNode(Node n, double d) {
			this.node = n;
			this.d = d;
		}
	}

	/**
	 * returns the point in the tree which is nearest 
	 * query point p
	 * @param p
	 * @return
	 */
	public Point2D nearest(Point2D p) {
		if (p == null) throw new NullPointerException();
		if (this.isEmpty()) return null;
		
		// 
		nearNode winner = new nearNode(null, Double.POSITIVE_INFINITY);
		winner = testNodeProximity(p, root, winner);
		return winner.node.p;
	} // a nearest neighbor to point p; null if the symbol table is empty
	
	private nearNode testNodeProximity(Point2D p, Node n, nearNode winner) {
		//System.out.printf("testing node %s\n", n.p);
		if (n == null) return winner;
		if (p.distanceSquaredTo(n.p) < winner.d)
			winner = new nearNode(n, p.distanceSquaredTo(n.p));
		
		// if the node partitions horizontally compare ys, else xs
		Double cmp = n.o ? p.y() - n.p.y() : p.x() - n.p.x();
		// if point to compare is left of or below leader,
		// test point in left subtree first
		if (cmp < 0) winner = testNodesInSequence(p, n.lb, n.rt, winner);
		else         winner = testNodesInSequence(p, n.rt, n.lb, winner);
		
		return winner;
	}
	
	private nearNode testNodesInSequence(Point2D p, Node n1, Node n2, nearNode winner) {
		winner = testNodeProximity(p, n1, winner);
		if (n2 == null) return winner;
		if (n2.rect.distanceSquaredTo(p) < winner.d)
			winner = testNodeProximity(p, n2, winner);
			
		return winner;
	}
	
}
