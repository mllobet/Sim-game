import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	private boolean starting;

	private Color _state[][];		//Adjacency matrix representing the board

	private HashSet<Connector> R;	//Solid red edges 
	private HashSet<Connector> B;	//Solid blue edges
	private HashSet<Connector> LR;	//Losing red mvoes
	private HashSet<Connector> LB;	//Losing blue moves
	private HashSet<Connector> LRB; //Losing red and blue moves
	private HashSet<Connector> F;	//Free moves

	private final Functor[] _rules = {
			new Rule1(),new Rule2(), new Rule3(), new Rule4(), new Rule5(), new Rule6()
	};


	// Initialize an empty board with no colored edges.
	public Board ( ) {
		starting = true;

		R = new HashSet<Connector>(15);
		B = new HashSet<Connector>(15);
		LR = new HashSet<Connector>(15);
		LB = new HashSet<Connector>(15);
		LRB = new HashSet<Connector>(15);
		F = new HashSet<Connector>(15);

		_state = new Color[6][6];

		for(int i = 1; i <= 5; ++i)
		{
			Arrays.fill(_state[i - 1], Color.WHITE);
			for(int j = i + 1; j <= 6; ++j)
				F.add(new Connector(i,j));
		}
		Arrays.fill(_state[5], Color.WHITE);
	}

	// Add the given connector with the given color to the board. And update all the sets.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) 
	{
		if (R.contains(cnctr) || B.contains(cnctr))
			throw new IllegalArgumentException("connector is already in the board");

		//UPDATE R or B SET
		if (c.equals(Color.RED))
			R.add(cnctr);
		else
			B.add(cnctr);

		if(iAmDebugging)
			System.out.println("Size before: " + F.size());

		//Remove from previous set
		if (F.contains(cnctr))
			F.remove(cnctr);
		else if (LR.contains(cnctr))
			LR.remove(cnctr);
		else if (LB.contains(cnctr))
			LB.remove(cnctr);
		else
			LRB.remove(cnctr);

		_state[cnctr.endPt1() - 1][cnctr.endPt2() - 1] = c;
		_state[cnctr.endPt2() - 1][cnctr.endPt1() - 1] = c;

		update();
		
		if(iAmDebugging)
			System.out.println("(add) Size after: " + F.size());
	}
	
	public void remove(Connector c)
	{
		if (F.contains(c))
			throw new IllegalArgumentException("Connector is not in the board");
		
		if(iAmDebugging)
			System.out.println("(remove) Size before: " + F.size());
		F.add(c);
		
		if (R.contains(c))
			R.remove(c);
		else if (LR.contains(c))
			LR.remove(c);
		else if (LB.contains(c))
			LB.remove(c);
		else
			LRB.remove(c);
		
		_state[c.endPt1() - 1][c.endPt2() - 1] = Color.WHITE;
		_state[c.endPt2() - 1][c.endPt1() - 1] = Color.WHITE;
		
		update();
		if(iAmDebugging)
			System.out.println("(remove) Size after: " + F.size());
	}

	private void update()
	{
		//UPDATE LR, LB and LRB
		LinkedList<Connector> l = makeList(F.iterator(), LR.iterator(), LB.iterator(), LRB.iterator());

		while (!l.isEmpty())
		{
			boolean redT, blueT;

			Connector check = l.remove();

			redT = formsTriangle(check,Color.RED);
			blueT = formsTriangle(check,Color.BLUE);

			if (redT && blueT)
			{
				LRB.add(check);

				if (LR.contains(check))
					LR.remove(check);
				else if (LB.contains(check))
					LB.remove(check);

			}
			else if (redT)
			{
				if (!LR.contains(check))
				{
					if (LB.contains(check))
					{
						LRB.add(check);
						LB.remove(check);
					}
					else
					{
						LR.add(check);
					}

				}
			}
			else if (blueT)
			{
				if (!LB.contains(check))
				{
					if (LR.contains(check))
					{
						LRB.add(check);
						LR.remove(check);
					}
					else
					{
						LB.add(check);
					}
				}
			}

			if ((redT || blueT) && F.contains(check))
			{
				F.remove(check);
			}
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
		return new IteratorOfIterators(R.iterator(), B.iterator(), LR.iterator(), LB.iterator(), LRB.iterator(), F.iterator());
	}

	public Iterator<Connector> redDotted()
	{
		return LR.iterator();
	}

	public Iterator<Connector> blueDotted()
	{
		return LB.iterator();
	}

	public Iterator<Connector> dualDotted()
	{
		return LRB.iterator();
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
	public boolean formsTriangle(Connector cnctr, Color c)
	{
		if(cnctr == null)
			throw new IllegalArgumentException("null Connector");
		for (int i = 0; i < 6; ++i)
			if (_state[cnctr.endPt1() - 1][i] == c && _state[cnctr.endPt2() - 1][i] == c)
				return true;
		return false;
	}

	// Choices & Rules part:

	//	Makes a move for the blue player
	public Connector choice ( ) {
		if(iAmDebugging)
			System.out.println("Choice");

		// Rule 1:
		if (starting)
		{	
			starting = false;

			Iterator<Connector> iter = R.iterator();
			Connector firstRed = new Connector(1,2);
			if(iter.hasNext())
			{
				firstRed = iter.next();
			}

			iter = F.iterator();
			Connector out = new Connector(1,2);
			while(iter.hasNext())
			{
				Connector c = iter.next();

				if(c.endPt1() != firstRed.endPt1() && c.endPt1() != firstRed.endPt2() && c.endPt2() != firstRed.endPt1()
						&& c.endPt2() != firstRed.endPt2())
				{
					out = c;
					break;
				}
			}
			return out;
		}
		else
		{
			Iterator<Connector> iter = F.iterator();

			// Rule 2:
			if(iter != null && iter.hasNext())
			{
				return applyRules(iter);
			}
			// Rule 3:
			else
			{
				iter = LR.iterator();
				if(iter != null && iter.hasNext())
				{
					return applyRules(iter);
				}
				else
				{
					//we lose:
					iter = LB.iterator();
					if(iter.hasNext())
						return iter.next();
					else
					{
						iter = LRB.iterator();
						if(iter.hasNext())
							return iter.next();
						else
							throw new IllegalArgumentException("THIS SHOULD NEVER HAPPEN");

					}				
				}
			}
		}
	}

	// Apply all the rules on a given set of iterators
	private Connector applyRules(Iterator<Connector> iter)
	{
		List<Connector> result = null;
		for (Functor rule : _rules)
		{
			result = rule.Execute(iter, this);
			if (result.size() == 1)
				return result.get(0);
			iter = result.iterator();
		}
		return result.get(0);
		//throw new IllegalStateException("No move found !");
	}

	// Returns whether or not a connector is red (painted red or dotted red)
	private boolean isRed(Connector c)
	{
		return R.contains(c) || LR.contains(c);
	}

	private boolean isTrueRed(Connector c)
	{
		return R.contains(c);
	}

	// Returns whether or not a connector is dotted red
	private boolean isDottedRed(Connector c)
	{
		return LR.contains(c);
	}

	// Returns whether or not a connector is BLue (painted blue or dotted blue or dotted red-blue
	private boolean isBlue(Connector c)
	{
		return B.contains(c) || LB.contains(c) || LRB.contains(c);
	}

	private boolean isNeutral(Connector c)
	{
		return F.contains(c);
	}

	// Returns how many triangles it creates
	private int countTriangles(Connector cnctr, Color c)
	{
		int count = 0;
		if(cnctr == null)
			throw new IllegalArgumentException("null Connector");
		for (int i = 0; i < 6; ++i)
			if (_state[cnctr.endPt1() - 1][i] == c && _state[cnctr.endPt2() - 1][i] == c)
				++count;
		return count;
	}

	private int countHypSafe(Connector c)
	{
		int count = 0;
		for(int i = 1; i <= 6; ++i)
		{
			if(c.endPt1() != i && c.endPt2() != i)
			{
				Connector c1 = new Connector(c.endPt1(), i);
				Connector c2 = new Connector(c.endPt2(), i);
				if ((isRed(c1)^isRed(c2) && (isDottedRed(c1)^isDottedRed(c2)))) 
				{
					++count;
				}
			}
		}

		return count;
	}

	// Returns how many loser triangles it creates
	private int countLosers(Connector c)
	{
		int count = 0;
		for(int i = 1; i <= 6; ++i)
		{
			if(c.endPt1() != i && c.endPt2() != i)
			{
				Connector c1 = new Connector(c.endPt1(), i);
				Connector c2 = new Connector(c.endPt2(), i);
				if ((isRed(c1)^isRed(c2) && (isNeutral(c1)^isNeutral(c2)))) 
				{
					++count;
				}
			}
		}

		return count;
	}

	// Returns how many loser triangles it creates
	private int countValidLosers(Connector c)
	{
		int count = 0;
		for(int i = 1; i <= 6; ++i)
		{
			if(c.endPt1() != i && c.endPt2() != i)
			{
				Connector c1 = new Connector(c.endPt1(), i);
				Connector c2 = new Connector(c.endPt2(), i);
				if ((isTrueRed(c1)^isTrueRed(c2) && (isNeutral(c1)^isNeutral(c2)))) 
				{
					++count;
				}
			}
		}

		return count;
	}

	// Returns how many loser triangles it creates
	private int countMixedT(Connector c)
	{
		int count = 0;
		for(int i = 1; i <= 6; ++i)
		{
			if(c.endPt1() != i && c.endPt2() != i)
			{
				Connector c1 = new Connector(c.endPt1(), i);
				Connector c2 = new Connector(c.endPt2(), i);
				if ( (isRed(c1)^isRed(c2) && (isBlue(c1)^isBlue(c2))) || isBlue(c1) && isBlue(c2)) 
				{
					++count;
				}
			}
		}

		return count;
	}

	// Returns how many loser triangles it creates
	private int countPartialMixedT(Connector c)
	{
		int count = 0;
		for(int i = 1; i <= 6; ++i)
		{
			if(c.endPt1() != i && c.endPt2() != i)
			{
				Connector c1 = new Connector(c.endPt1(), i);
				Connector c2 = new Connector(c.endPt2(), i);
				if ( (isRed(c1)^isRed(c2) && isNeutral(c1)^isNeutral(c2)))
				{
					++count;
				}
			}
		}

		return count;
	}

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	//	Each connector in the board is properly initialized so that 
	// 	1 <= myPoint1 < myPoint2 <= 6.
	public boolean isOK ( ) {

		//		Each connector in the board is properly initialized so that 
		// 	1 <= myPoint1 < myPoint2 <= 6.
		Iterator<Connector> iters = connectors();

		while (iters.hasNext())
		{
			if (!iters.next().isOK())
				return false;
		}

		// Count sizes of sets:
		int connectorCount = R.size() + B.size() + LR.size() + LB.size() + LRB.size() + F.size();
		if(connectorCount != 15)
			return false;

		int adjMatConnectorcount = 0;
		//Count edges in adj matrix
		for(int i = 0; i < 6; ++i)
			for(int j = 0; j < 6; ++j)
				adjMatConnectorcount += !_state[i][j].equals(Color.WHITE) ? 1 : 0;
		adjMatConnectorcount /= 2;
		if(adjMatConnectorcount > 15)
			return false;

		//check for count difference 
		int blueCount = B.size();
		int redCount = R.size();
		//blue can never have more connectors than red
		if(blueCount - redCount > 0)
			return false;

		//red can never have more than 1 connector of difference
		if(redCount - blueCount > 1)
			return false;


		if (blueCount == redCount)
		{
			Iterator<Connector> iter = R.iterator();

			while (iter.hasNext())
			{
				if (formsTriangle(iter.next(), Color.RED))
					return false;
			}
		}

		if (redCount - blueCount == 1)
		{
			Iterator<Connector> iter = B.iterator();

			while (iter.hasNext())
			{
				if (formsTriangle(iter.next(), Color.BLUE))
					return false;
			}
		}

		// You fill this in.
		return true;
	}

	// Get lists from iterators
	private static <T> LinkedList<T> makeList(Iterator<T>... iter) {
		LinkedList<T> copy = new LinkedList<T>();
		for(Iterator<T> it : iter)
		{
			while (it.hasNext())
				copy.add(it.next());
		}

		return copy;
	}

	private interface Functor

	{
		public List<Connector> Execute(Iterator<Connector> iter, Board board);
	}

	private class Rule1 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int min = Integer.MAX_VALUE;
			List<Connector> result = new ArrayList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countTriangles(check, Color.BLUE);
				if (count == min)
				{
					result.add(check);
				}
				if (count < min)
				{
					min = count;
					result.clear();
					result.add(check);
				}


			}
			return result;
		}

	}

	public class Rule2 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int min = Integer.MAX_VALUE;
			LinkedList<Connector> result = new LinkedList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countLosers(check);
				if (count == min)
				{
					result.add(check);
				}
				if (count < min)
				{
					min = count;
					result.clear();
					result.add(check);
				}

			}

			return result;
		}
	}

	private class Rule3 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int min = Integer.MAX_VALUE;
			LinkedList<Connector> result = new LinkedList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countHypSafe(check);
				if (count == min)
				{
					result.add(check);
				}
				if (count < min)
				{
					min = count;
					result.clear();
					result.add(check);
				}

			}

			return result;
		}
	}

	public class Rule4 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int max = Integer.MIN_VALUE;
			LinkedList<Connector> result = new LinkedList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countMixedT(check);
				if (count == max)
				{
					result.add(check);
				}
				if (count > max)
				{
					max = count;
					result.clear();
					result.add(check);
				}

			}

			return result;
		}
	}

	public class Rule5 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int max = Integer.MIN_VALUE;
			LinkedList<Connector> result = new LinkedList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countPartialMixedT(check);
				if (count == max)
				{
					result.add(check);
				}
				if (count > max)
				{
					max = count;
					result.clear();
					result.add(check);
				}

			}

			return result;
		}
	}

	public class Rule6 implements Functor
	{
		@Override
		public List<Connector> Execute(Iterator<Connector> iter, Board board)
		{
			int min = Integer.MAX_VALUE;
			LinkedList<Connector> result = new LinkedList<Connector>();
			while (iter.hasNext())
			{
				Connector check = iter.next();
				int count = countValidLosers(check);
				if (count == min)
				{
					result.add(check);
				}
				if (count < min)
				{
					min = count;
					result.clear();
					result.add(check);
				}

			}

			return result;
		}
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