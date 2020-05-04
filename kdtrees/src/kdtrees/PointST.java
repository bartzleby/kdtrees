package kdtrees;

//import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointST<Value> {

	// unit testing of the methods (not graded)
	public static void main(String[] args) {
		
		PointST<Integer> tpst = new PointST<>();
				
		tpst.put(new Point2D(3,4), 5);
		tpst.put(new Point2D(6,12), 7);
		tpst.put(new Point2D(6,3), 8);
		
		RectHV trect = new RectHV(2,0,5,12);

		for (Point2D p : tpst.points()) 
			System.out.println(p);
		
		System.out.println();
		System.out.println(tpst.nearest(new Point2D(5,10)));

	}

	private RedBlackBST<Point2D, Value> st;

	/*
	 * 
	 */
	public PointST() {
		this.st = new RedBlackBST<>();
	} // construct an empty symbol table of points

	public boolean isEmpty() {
		return this.st.isEmpty();
	} // is the symbol table empty?

	public int size() {
		return this.st.size();
	} // number of points

	public void put(Point2D p, Value val) {
		this.st.put(p, val);
	} // associate the value val with point p

	public Value get(Point2D p) {
		return this.st.get(p);
	} // value associated with point p

	public boolean contains(Point2D p) {
		return this.st.contains(p);
	} // does the symbol table contain point p?

	public Iterable<Point2D> points() {
		return this.st.keys();
	} // all points in the symbol table

	public Iterable<Point2D> range(RectHV rect) {
		
		Queue<Point2D> bg = new Queue<>();
		for (Point2D tp : this.points())
			if (rect.contains(tp))
				bg.enqueue(tp);
		
		return bg;
	} // all points that are inside the rectangle

	public Point2D nearest(Point2D p) {
		
		if (this.isEmpty()) return null;
		Point2D winner = null;
		Double windisq = Double.POSITIVE_INFINITY;
		
		for (Point2D tp : this.points())
			if (p.distanceSquaredTo(tp) < windisq)
			{
				winner = tp;
				windisq = p.distanceSquaredTo(tp);
			}
		
		return winner;
	} // a nearest neighbor to point p; null if the symbol table is empty

}
