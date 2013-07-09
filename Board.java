import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	
	private ArrayList<Connector> connectors;
	
	private boolean[] whiteConnectors;
	private boolean[] redConnectors;
	private boolean[] blueConnectors;
	
	private Color[][] _adjencyMatrix;
	
	// Initialize an empty board with no colored edges.
	public Board ( ) {
		_adjencyMatrix = new Color[6][6];
		for (int i = 0; i < 6; i++)
		{
			Arrays.fill(_adjencyMatrix[i], Color.WHITE);
		}
		
		whiteConnectors = new boolean[15];
		redConnectors = new boolean[15];
		blueConnectors = new boolean[15];
		
		Arrays.fill(whiteConnectors, true);
		Arrays.fill(redConnectors, false);
		Arrays.fill(blueConnectors, false);
		
		for(int i = 1; i <= 5; ++i)
		{
			for(int j = 1; j <= 6 - i; ++j)
			{
				if(i  <= j) connectors.add(new Connector(i,j));
			}
		}
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public java.util.Iterator<Connector> connectors (Color c)
	{
		return new Iterator(_state, c);
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
	public Color colorOf (Connector e)
	{
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Connector cnctr, Color c) {
		// You fill this in.
		return false;
	}
	
	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	public Connector choice ( ) {
		// You fill this in.
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
