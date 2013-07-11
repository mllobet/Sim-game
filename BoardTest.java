import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import junit.framework.TestCase;

public class BoardTest extends TestCase {

	@Test
	// Check empty board.
	public void testEmptyBoard ( ) {
		Board b = new Board ( );
		assertTrue (b.isOK ( ));
		checkCollection (b, 0, 0); // applies more tests
		assertTrue (!b.formsTriangle (new Connector (1, 2), Color.RED));
	}

	@Test
	// Check one-connector board.
	public void test1Connector ( ) {
		Board b = new Board ( );
		b.add (new Connector (1, 2), Color.RED);
		assertTrue (b.isOK ( ));
		checkCollection (b, 1, 0);

		Iterator<Connector> iter = b.connectors (Color.RED);
		assertTrue (iter.hasNext ( ));
		Connector cnctr = iter.next ( );
		assertEquals (b.colorOf (cnctr), Color.RED);
		assertEquals (new Connector (1, 2), cnctr);
		assertTrue (!iter.hasNext ( ));

		assertTrue (!b.formsTriangle (new Connector(1,3), Color.RED));
		assertTrue (!b.formsTriangle (new Connector(5,6), Color.RED));
		assertTrue (!b.choice ( ).equals (new Connector (1, 2)));
		assertEquals (b.colorOf (b.choice ( )), Color.WHITE);
	}
	

	// More tests go here.

	// (a useful helper method)
	// Make the following checks on a board that should be legal:
	//	Check connector counts (# reds + # blues + # uncolored should be 16.
	//	Check red vs. blue counts.
	//	Check for duplicate connectors.
	//	Check for a blue triangle, which shouldn't exist.
	private void checkCollection (Board b, int redCount, int blueCount) {
		// Fill this in if you'd like to use this method.
	}
	

	public class Pair<T1, T2>
	{
		public T1 first;
		public T2 second;
		
		public Pair(T1 first, T2 second)
		{
			this.first = first;
			this.second = second;
		}
	}
	
	private char[][] _matrix =
		{
			{ '\0', 'w', 'r', 'b', 'w', 'r' },
			{ 'w', '\0', 'r', 'b', 'w', 'b' },
			{ 'r', 'r', '\0', 'b', 'w', 'w' },
			{ 'b', 'b', 'b', '\0', 'w', 'r' },
			{ 'w', 'w', 'w', 'w', '\0', 'b' },
			{ 'r', 'b', 'w', 'r', 'b', '\0' },
		};
	
	private Pair<Character, Integer>[] _counter;
	
	@Test
	public void testConstructor()
	{
		Board.Iterator it = new Board.Iterator(_matrix);
		Board.Iterator it2 = new Board.Iterator(_matrix, 'w');
		Board.Iterator it3 = new Board.Iterator(_matrix, Board.Iterator.NULL_COLOR);
	}
	
	@Test
	public void testWithoutColor()
	{
		int count = 0;
		Board.Iterator it = new Board.Iterator(_matrix);
		while (it.hasNext())
		{
			it.next();
			count++;
		}
		assertEquals(15, count);
	}
	
	@Test
	public void testWithColor()
	{
		_counter = new Pair[3];
		_counter[0] = new Pair<Color, Integer>(Color.RED, 4);
		_counter[1] = new Pair<Color, Integer>(Color.BLUE, 5);
		_counter[2] = new Pair<Color, Integer>(Color.WHITE, 6);
		for (int i = 0; i < _counter.length; i++)
		{
			Integer count = 0;
			Board.Iterator it = new Board.Iterator(_matrix, _counter[i].first);
			while (it.hasNext())
			{
				it.next();
				count++;
			}
			assertEquals(_counter[i].second, count);		 
		}
	}

}
