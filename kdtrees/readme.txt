/******************************************************************************
 *  Name:     D. Bartz
 *  NetID:    
 *  Precept:  
 *
 *  Partner Name:       
 *  Partner NetID:      
 *  Partner Precept:    
 *
 *  Hours to complete assignment (optional):
 		Jul16:  30min
 		Jul17: 180min
 		Jul18:  75min
 		Jul18:  60min
 		Jul18:  60min
 		Jul18:  60min
 		Jul18:  30min DOC
 		Jul21:  60min java cmd / cp / scripts
 		Jul21:  60min DEBUG
 		Jul21:  30min nearestest
 *
 ******************************************************************************/

Programming Assignment 5: Kd-Trees


/******************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 *****************************************************************************/

Node contains fiels for point in plane, value, axis-aligned rect, 
pointers to left and right sub trees, and a boolean value 
to describe how it partitions the plane.


/******************************************************************************
 *  Describe your method for range search in a kd-tree.
 *****************************************************************************/

It is implemented with a recursive call to a private range method that searches 
all trees with axis-aligned rects which intersect the query rectangle.  
Adds point to bag if it's contained.


/******************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 *****************************************************************************/

I use a private class nearNode to represent a search Node and its distance 
from the query point.  Winning (nearest) node is initialized to null with a
a distance infinity away from query point, and tree is searched recursively
in any trees that could contain a nearer point.


/******************************************************************************
 *  Using the 64-bit memory cost model from the textbook and lecture,
 *  give the total memory usage in bytes of your 2d-tree data structure
 *  as a function of the number of points N. Use tilde notation to
 *  simplify your answer (i.e., keep the leading coefficient and discard
 *  lower-order terms).
 *
 *  Include the memory for all referenced objects (including
 *  Node, Point2D, and RectHV objects) except for Value objects
 *  (because the type is unknown). Also, include the memory for
 *  all referenced objects.
 *
 *  Justify your answer below.
 *
 *****************************************************************************/

bytes per Point2D: 

Point2D Object Overhead:	16	bytes

Comparator XOrder():			bytes
Comparator YOrder():			bytes
Comparator ROrder(): 			bytes

double x:					8	bytes
double y: 					8	bytes

padding:					0	bytes
----------------------------------------------------
							32		bytes

bytes per RectHV:

RectHV Object Overhead		16	bytes

double xmin					8	bytes
double ymin					8	bytes
double xmax					8	bytes
double ymax					8	bytes

padding						0	bytes
----------------------------------------------------
							48	bytes
							
Node
{ OH						16	bytes
P2D ref						8	bytes
Point2D						32	bytes
Vref						8	bytes
Value						x	bytes
RHV ref						8	bytes
RectHV						48	bytes
Reference to Node			8	bytes
Reference to Node			8	bytes
boolean						1	bytes
} padding					7	bytes
----------------------------------------------------
						x+	144	bytes
							
bytes per KdTree of N points:   ~144N bytes

KdTree Object Overhead:		16	bytes

int s						4	bytes
Node root (ref)				8	bytes

N*Nodes						n	bytes

padding						4	bytes
----------------------------------------------------
								bytes


/******************************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Show the math how you used to determine
 *  the operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 *****************************************************************************/

[SEE nearestest.java for MATH]

                       calls to nearest() per second
                     brute force               2d-tree
                     ---------------------------------
input10K.txt				470					166930
input100K.txt		too long (>20 min)			137248

input1M.txt			run time too long			



/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/


/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/






/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on  how helpful the class meeting was and on how much you learned 
 * from doing the assignment, and whether you enjoyed doing it.
 *****************************************************************************/
