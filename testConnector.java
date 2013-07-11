import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testConnector {

	@Test
	public void testValidToConnector ( ){
		Connector c = Connector.toConnector("12");
		assertEquals(c.toString(),"12");
	}

	@Test
	public void testWhitespacedToConnector ( ){
		Connector c = Connector.toConnector("  12  ");
		assertEquals(c.toString(),"12");
	}

	@Test
	public void testInvalidWhitespacedToConnector ( ){
		boolean caught = false;
		try{
			@SuppressWarnings("unused")
			Connector c = Connector.toConnector("  1 2  ");
		}
		catch (IllegalFormatException e){
			caught = true;
		}
		assertTrue(caught);
	}

	@Test
	public void testOutOfRangeToConnector ( ){
		boolean caught = false;
		try{
			@SuppressWarnings("unused")
			Connector c = Connector.toConnector("  9 2  ");
		}
		catch (IllegalFormatException e){
			caught = true;
		}
		assertTrue(caught);
	}

	@Test
	public void testTooManyToConnector ( ){
		boolean caught = false;
		try{
			@SuppressWarnings("unused")
			Connector c = Connector.toConnector("  234  ");
		}
		catch (IllegalFormatException e){
			caught = true;
		}
		assertTrue(caught);
	}

	@Test
	public void testEqualPointsToConnector ( ){
		boolean caught = false;
		try{
			@SuppressWarnings("unused")
			Connector c = Connector.toConnector("  33  ");
		}
		catch (IllegalFormatException e){
			caught = true;
		}
		assertTrue(caught);
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
		
		Board.IteratorOfIterators it = new Board.IteratorOfIterators();
		it.concat(l1.iterator());
		it.concat(l2.iterator());
		it.concat(l3.iterator());
		int count = 0;
		while (it.hasNext())
		{
			it.next();
			count++;
		}
		assertEquals(5, count);
	}
}
