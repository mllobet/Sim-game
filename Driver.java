import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Driver {

	public static void main (String [ ] args) {
		Board myBoard = new Board ( );
		BufferedReader playerMoves = new BufferedReader (new InputStreamReader (System.in)); 
		Display myDisplay = new Display (myBoard);
		while (true) {
			// It's the player's turn to move, playing RED.
			// There has to be an uncolored connector, since it's the user's turn
			// and there are 15 connectors.
			System.out.print ("Your move? ");
			String s = "";
			try {
				s = playerMoves.readLine ( );
			} catch (Exception exc) {
				//This shouldn't happen.
				exc.printStackTrace ( );
				System.exit (2);
			}
			Connector cnctr;
			try {
				cnctr = Connector.toConnector (s);
			} catch (IllegalFormatException exc) {
				System.out.println (exc.getMessage ( ));
				continue;
			}
			if (myBoard.colorOf (cnctr) == Color.RED || myBoard.colorOf (cnctr) == Color.BLUE) {
				System.out.println ("ERROR: " + cnctr + " has already been colored.");
				continue;
			}
			if (myBoard.formsTriangle (cnctr, Color.RED)) {
				System.out.println ("You lose!");
				try {
					Thread.sleep (4000);
				} catch (InterruptedException e1) {
				}
				System.exit (1);
			}
			myBoard.add (cnctr, Color.RED);
			assert myBoard.isOK ( ) : "NOT OK";
			
			// Now it's the computer's turn.
			Connector computerMove = myBoard.choice ( );
			System.out.println ("Computer takes " + computerMove);
			if (myBoard.formsTriangle (computerMove, Color.BLUE)) {
				System.out.println ("You win!");
				try {
					Thread.sleep (4000);
				} catch (InterruptedException e1) {
				}
				System.exit (0);
			}
			myBoard.add (computerMove, Color.BLUE);
			assert myBoard.isOK ( );
			myDisplay.repaint ( );
			try {
				Thread.sleep (1000);
			} catch (InterruptedException e1) {
			}
		}
	}
}
