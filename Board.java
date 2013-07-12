import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	private boolean starting;

	private HashSet<Connector> R; 
	private HashSet<Connector> B;
	private HashSet<Connector> LR;
	private HashSet<Connector> LB;
	private HashSet<Connector> LRB;
	private HashSet<Connector> F;

	// Initialize an empty board with no colored edges.
	public Board ( ) {
		starting = true;

		R = new HashSet<Connector>(15);
		B = new HashSet<Connector>(15);
		LR = new HashSet<Connector>(15);
		LB = new HashSet<Connector>(15);
		LRB = new HashSet<Connector>(15);
		F = new HashSet<Connector>(15);

		for(int i = 1; i <= 5; ++i)
		{
			for(int j = 1; j <= 6 - i; ++j)
			{
				if(i  <= j) F.add(new Connector(i,j));
			}
		}
	}

	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) 
	{
		if (R.contains(cnctr) || B.contains(cnctr))
			throw new IllegalArgumentException("conector is already in the board");

		//UPDATE R or B SETS
		if (c.equals(Color.RED))
			R.add(cnctr);
		else
			B.add(cnctr);

		//UPDATE LR, LB and LRB

		LinkedList<Connector> removeList = new LinkedList<Connector>();

		Iterator<Connector> iter = F.iterator();
		while (iter.hasNext())
		{
			boolean redT, blueT;
			Connector check = iter.next();
			redT = formsTriangle(check,Color.RED);
			blueT = formsTriangle(check,Color.BLUE);

			if (redT && blueT)
				LRB.add(check);
			else if (redT)
				LR.add(check);
			else if (blueT)
				LB.add(check);

			if (redT || blueT)
				removeList.add(check);
		}

		for(Connector cn : removeList)
		{
			F.remove(cn);
		}

	}

	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public java.util.Iterator<Connector> connectors (Color c)
	{
		if (c.equals(Color.RED))
			return R.iterator();
		else if (c.equals(Color.BLUE))
			return B.iterator();
		else
			return F.iterator();		
	}

	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public java.util.Iterator<Connector> connectors ( )
	{
		return new IteratorOfIterators(R.iterator(), B.iterator(), F.iterator());
	}

	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		if (R.contains(e))
			return Color.RED;
		else if (B.contains(e))
			return Color.BLUE;
		else
			return Color.WHITE;
	}

	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle(Connector cnctr, Color c){
		if(cnctr == null)
			throw new IllegalArgumentException("null Connector");

		boolean triangle = false;
		if(c.equals(Color.RED))
		{
			int i = 1;
			while(i <= 6 && !triangle)
			{
				//triangle = R.
			}
		}
		else 
		{

		}
	}
	
	// Choices & Rules part:

	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	public Connector choice ( ) {
		if (starting)
		{	//Rule 1:
			if(!R.contains(new Connector(1, 2)))
				return new Connector(1,2);	
			else
				return new Connector(2,3);
		}
		else
		{
			Iterator<Connector> iter = F.iterator();

			//Rule 2:
			if(iter != null && iter.hasNext())
			{
				return applyRules(iter);
			}
			//Rule 3:
			else
			{
				iter = LR.iterator();
				if(iter != null && iter.hasNext())
				{
					return applyRules(iter);
				}
				else
				{
					throw new IllegalArgumentException("WE ARE DOOMED");
				}
			}
		}
	}
	
	// Apply all the rules on a given set of iterators
	
	private Connector applyRules(Iterator<Connector> iter)
	{
		return null;
	}

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	//	Each connector in the board is properly initialized so that 
	// 	1 <= myPoint1 < myPoint2 <= 6.
	public boolean isOK ( ) {
		// You fill this in.
		return true;
	}

	public static class IteratorOfIterators implements Iterator<Connector>
	{
		private List<Iterator<Connector>> _iterators;
		private int _idx;

		public IteratorOfIterators(Iterator<Connector>... iterators)
		{
			_iterators = Arrays.asList(iterators);
			_idx = 0;
		}

		@Override
		public boolean hasNext()
		{
			int idx = _idx;
			while (idx < _iterators.size() && _iterators.get(idx).hasNext() == false)
				idx++;
			return idx < _iterators.size() && _iterators.get(idx).hasNext();
		}

		@Override
		public Connector next()
		{
			while (_iterators.get(_idx).hasNext() == false)
				_idx++;
			return _iterators.get(_idx).next();
		}

		@Override
		public void remove()
		{
			// throw new RuntimeException("Method remove is not implemented and should not be used");
		}

		public void concat(Iterator<Connector> it)
		{
			_iterators.add(it);
		}
	}
}