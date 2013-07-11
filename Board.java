import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;

	private final int NEGINF = Integer.MIN_VALUE;
	private final int INF = Integer.MIN_VALUE;

	private static final char RED = 'r';
	private static final char BLUE = 'b';
	private static final char WHITE = 'w';
	private static final char PLAYER = 'b';

	private Color[][] _state; 
	private int[][]  _zobrist;

	class Position
	{
		Color[][] state;
		Color player;
		int[] redDeg;
		int[] blueDeg;
		public Position(Color[][] s, Color p, int[] redDeg, int[] blueDeg){
			state = s;
			player = player;
			this.redDeg = redDeg;
			this.blueDeg = blueDeg;
		}
	}

	// Initialize an empty board with no colored edges.
	public Board ( ) {
		_state = new Color[6][6];
		_zobrist = new int[6][12];
		Random generator = new Random();
		for(int i = 0; i < 6; ++i)
			for(int j = 0; j < 6; ++j)
			{
				if(i != j) _state[i][j].equals(Color.WHITE);
				_zobrist[i][j] = generator.nextInt();
				_zobrist[i][12 - j - 1] = generator.nextInt();
			}
	}

	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {

		_state[cnctr.endPt1() - 1][cnctr.endPt2() - 1].equals(c);
		_state[cnctr.endPt2() - 1][cnctr.endPt1() - 1].equals(c);
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
	public Color colorOf (Connector e) {
		return _state[e.endPt1() - 1][e.endPt2() - 1];
	}

	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Color[][] state, Connector cnctr, Color c) 
	{		
		if (! state[cnctr.endPt1() - 1][cnctr.endPt2() - 1].equals(Color.WHITE))
		{
			throw new IllegalFormatException("Invalid connector");
		}

		return formsUncheckedTriangle(state,cnctr,c);
	}

	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	private boolean formsUncheckedTriangle (Color[][] state, Connector cnctr, Color c) 
	{			
		for(int i = 0; i < 6; ++i)
			if(state[cnctr.endPt1() - 1][i].equals(c) && i != cnctr.endPt2() - 1)
				if(state[cnctr.endPt2() - 1][i].equals(c)) 
					return true;

		return false;
	}

	public boolean formsTriangle(Connector cnctr, Color c){
		return formsTriangle(_state,cnctr,c);
	}
	
	private Color invertColor(Color c){
		return (c.equals(Color.RED)) ? Color.BLUE : Color.RED;
	}

	private int hashKey(Connector c)
	{
		int key = 0;
		for (int i = 0; i < 6; ++i)
		{
			if (! _state[c.endPt1() - 1][c.endPt2() - 1].equals(Color.WHITE))
				key ^= _zobrist[i][c.endPt1() + c.endPt2()];
		}
		return key;
	}

	/*
	 * Negascout stuff
	 */ 

	 // Does a move in a current position
	 private void doMove(Connector c, Position p) {
		p.state[c.endPt1() - 1][c.endPt2() - 1] = p.player;
		p.state[c.endPt2() - 1][c.endPt1() - 1] = p.player;

		if(p.player.equals(Color.RED))
			++p.redDeg[c.endPt1() - 1];
		else
			++p.redDeg[c.endPt2() - 1];
	 }

	 // Undoes a move in a current position
	 private void undoMove(Connector c, Position p) {
		 p.state[c.endPt1() - 1][c.endPt2() - 1] = Color.WHITE;
		 p.state[c.endPt2() - 1][c.endPt1() - 1] = Color.WHITE;

		 if(p.player.equals(Color.RED))
			 --p.redDeg[c.endPt1() - 1];
		 else
			 --p.redDeg[c.endPt2() - 1];
	 }

	 // Gets all the possible moves in a given position
	 public java.util.Iterator<Connector> moves (Position p)
	 {
		 return new Iterator(p.state, WHITE);
	 }

	 // Evaluates if the position is good for the PLAYER, priorizes speed over correctness.
	 // Returns positive or negative value, wether or not it is good for the player at the current position.
	 private void evaluate(Position p) {
		 //Check if we win or lose
		 //Iterator<Connector> it = new Iterator<Connector>(p.state, colorToChar(p.player));
	 }
	 
	 // whether or not a color loses in a given state
	 private boolean loses(char[][] state, Color color){
		 Iterator<Connector> iter = new Iterator<Connector>(state, color);
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
		 //State s = new State(_state, 'r');

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

		 private Color     _color;
		 private Color[][] _matrix;
		 private int      _x;
		 private int      _y;

		 public Iterator(Color[][] matrix, Color color)
		 {
			 _color = color;
			 _matrix = matrix;
			 _y = 0;
			 _x = 0;
		 }

		 public Iterator(Color[][] matrix)
		 {
			 _color = null;
			 _matrix = matrix;
			 _y = 0;
			 _x = 1;
		 }

		 @Override
		 public boolean hasNext()
		 {
			 int y = _y;
			 int x = _x;
			 while (y < 6)
			 {
				 while (x < 6)
				 {
					 if (_color == null || _color.equals(_matrix[y][x]))
						 return true;
					 x++;
				 }
				 ++y;
				 x = y + 1;
			 }
			 return false;
		 }

		 @Override
		 public Connector next()
		 {
			 advance();
			 return new Connector(++_x, _y + 1);
		 }



		 public void advance()
		 {
			 while (_y < 6)
			 {
				 while (_x < 6)
				 {
					 if (_color == Iterator.NULL_COLOR || _color == _matrix[_y][_x])
						 return;
					 _x++;
				 }
				 ++_y;
				 _x = _y + 1;
			 }
		 }

		 @Override
		 public void remove()
		 {
			 // throw new RuntimeException("Method remove is not implemented and should not be used");
		 }	
	 }
}