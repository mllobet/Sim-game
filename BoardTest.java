import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
	
	@Test
	public void testIteratorsConcat()
	{
		List<Connector> l1 = new ArrayList<Connector>();
		List<Connector> l2 = new ArrayList<Connector>();
		List<Connector> l3 = new ArrayList<Connector>();
		l1.add(new Connector(1, 2));
		l1.add(new Connector(2, 3));
		l1.add(new Connector(3, 4));
		l1.add(new Connector(4, 5));
		l3.add(new Connector(5, 6));
		
		Board.IteratorOfIterators it = new Board.IteratorOfIterators(l1.iterator(), l2.iterator(), l3.iterator());
		int count = 0;
		while (it.hasNext())
		{
			it.next();
			count++;
		}
		assertEquals(5, count);
	}
	
	@Test
	public void testIsOK()
	{
		// Basic empty board test
		Board b = new Board();
		assertTrue(b.isOK());
		
		// Having more blue than red is impossible
		b = new Board();
		b.add(new Connector(1, 4), Color.BLUE);
		b.add(new Connector(2, 4), Color.BLUE);
		b.add(new Connector(3, 4), Color.BLUE);
		b.add(new Connector(4, 5), Color.RED);
		assertFalse(b.isOK());

		// You can only have 1 more red than blue
		b = new Board();
		b.add(new Connector(1, 4), Color.RED);
		b.add(new Connector(2, 4), Color.RED);
		b.add(new Connector(3, 4), Color.RED);
		b.add(new Connector(4, 5), Color.BLUE);
		assertFalse(b.isOK());
		
		// Should work
		b = new Board();
		b.add(new Connector(1, 2), Color.RED);
		b.add(new Connector(2, 3), Color.RED);
		b.add(new Connector(3, 1), Color.RED);
		b.add(new Connector(3, 5), Color.BLUE);
		b.add(new Connector(3, 4), Color.BLUE);
		assertTrue(b.isOK());
		
		// Test a blue triangle
		b = new Board();
		b.add(new Connector(1, 2), Color.BLUE);
		b.add(new Connector(2, 3), Color.BLUE);
		b.add(new Connector(3, 1), Color.BLUE);
		b.add(new Connector(3, 5), Color.RED);
		b.add(new Connector(3, 4), Color.RED);
		assertFalse(b.isOK());
		
		// Test a normal board
		b = new Board();
		b.add(new Connector(1, 2), Color.BLUE);
		b.add(new Connector(2, 3), Color.BLUE);
		b.add(new Connector(3, 5), Color.BLUE);
		b.add(new Connector(3, 6), Color.RED);
		b.add(new Connector(3, 4), Color.RED);
		b.add(new Connector(5, 6), Color.RED);
		b.add(new Connector(5, 1), Color.RED);
		assertTrue(b.isOK());
	}
	

	// More tests go here.

	// (a useful helper method)
	// Make the following checks on a board that should be legal:
	//	Check connector counts (# reds + # blues + # uncolored should be 15.
	//	Check red vs. blue counts.
	//	Check for duplicate connectors.
	//	Check for a blue triangle, which shouldn't exist.
	private void checkCollection (Board b, int redCount, int blueCount)  {
		assertEquals(true, b.isOK());
		
		int redBoardCount = iteratorLength(b.connectors(Color.RED));
		int blueBoardCount = iteratorLength(b.connectors(Color.BLUE));
		int whiteBoardCount = iteratorLength(b.connectors(Color.WHITE));
		assertEquals(15, redBoardCount + blueBoardCount + whiteBoardCount);
		
		HashSet<Connector> set = new HashSet<Connector>();
		for (Iterator<Connector> it = b.connectors(); it.hasNext();)
		{
			Connector c = it.next();
			assertEquals(false, set.contains(c));
			set.add(c);
		}
	}
	
	private int iteratorLength(Iterator<Connector> it) {
		int count = 0;
		while (it.hasNext())
		{
			it.next();
			count++;
		}
		return count;
	}
}
