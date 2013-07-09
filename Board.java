import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	
	private char[][] _state;
	
	private static final char RED = 'r';
	private static final char BLUE = 'b';
	private static final char WHITE = 'w';
	
	
	class State
	{
		char[][] state = new char[6][6];
		char color = Board.RED;
		public State(char[][] s, char col){
			state = s;
			color = col;
		}
	}
	
	// Initialize an empty board with no colored edges.
	public Board ( ) {
		_state = new char[6][6];
		for(int i = 0; i < 6; ++i)
			for(int j = 0; j < 6; ++j)
				if(i != j) _state[i][j] = Board.WHITE;
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
		char paint = this.ColorToChar(c);
		
		_state[cnctr.endPt1() - 1][cnctr.endPt2() - 1] = paint;
		_state[cnctr.endPt2() - 1][cnctr.endPt1() - 1] = paint;
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public java.util.Iterator<Connector> connectors (Color c)
	{
		return new Iterator(_state, this.ColorToChar(c));
	}
	
	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public java.util.Iterator<Connector> connectors ( )
	{
		return new Iterator(_state);
	}
	
	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		if (_state[e.endPt1() - 1][e.endPt2() - 1] == 'r')
			return Color.RED;
		else if (_state[e.endPt1() - 1][e.endPt2() - 1] == 'b')
			return Color.BLUE;
		else
			return Color.WHITE;
	}
	
	private boolean testSegments(List<Integer> segmentsList, Connector cnctr)
	{
		int p1 = cnctr.endPt1();
		int p2 = cnctr.endPt2();
		
		if (segmentsList.contains(p1) && segmentsList.contains(p2))
			return true;
		
		return false;
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Connector cnctr, Color c) 
	{		
		if (_state[cnctr.endPt1()][cnctr.endPt2()] != 'w')
		{
			throw new IllegalFormatException("Invalid connector");
		}
		
		int nbSegments = 0;
		List<Integer> segmentsList = new ArrayList<Integer>();
		
		char color = (Color.red.equals(c) ? 'r' : 'b');
		
		for (int i = 0; i < 6; ++i)
		{
			for (int j = 0; j < 6; ++i)
			{
				if (_state[i][j] == color)
				{
					segmentsList.add(j);
					++nbSegments;
				}
			}
		
			if (nbSegments >= 2)
			{
				if (testSegments(segmentsList, cnctr))
					return true;
			}
			segmentsList.clear();
		}
		return false;
	}

	private char ColorToChar(Color c)
	{
		if (c.equals(Color.RED))
			return Board.RED;
		else if (c.equals(Color.BLUE))
			return Board.BLUE;
		return Board.WHITE;
	}
	
	
	/*
	 * Negascout stuff
	 */ 
	
	
	
	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	public Connector choice ( ) {
		State s = new State(_state, 'r');
		
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
	
	public static class Iterator implements java.util.Iterator<Connector>
	{
		public static final char NULL_COLOR = '\0';
		
		private char     _color;
		private char[][] _matrix;
		private int      _x;
		private int      _y;
		
		public Iterator(char[][] matrix, char color)
		{
			_color = color;
			_matrix = matrix;
			_y = 0;
			_x = 0;
		}
		
		public Iterator(char[][] matrix)
		{
			_color = Iterator.NULL_COLOR;
			_matrix = matrix;
			_y = 0;
			_x = 1;
		}
		
		@Override
		public boolean hasNext()
		{
			while (_y < 6)
			{
				while (_x < 6)
				{
					if (_color == Iterator.NULL_COLOR || _color == _matrix[_y][_x])
						return true;
					_x++;
				}
				++_y;
				_x = _y + 1;
			}
			return false;
		}

		@Override
		public Connector next()
		{
			return new Connector(++_x, _y + 1);
		}

		@Override
		public void remove()
		{
			// throw new RuntimeException("Method remove is not implemented and should not be used");
		}	
	}
}
