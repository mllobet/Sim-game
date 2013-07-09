import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testConnectorIterator
{
	
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
		_counter[0] = new Pair<Character, Integer>('r', 4);
		_counter[1] = new Pair<Character, Integer>('b', 5);
		_counter[2] = new Pair<Character, Integer>('w', 6);
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
