import java.awt.*;
import java.util.Iterator;

import javax.swing.*;

// This code handles the graphical display of the board.
// You may assume it works fine.

public class Display extends JPanel {

	private int myWindowWidth = 200;
	private int myWindowHeight = 220;
	private int POINT_RADIUS = 10;
	private int [ ] [ ]	POINT_POSITIONS 
		= {{100, 20}, {170, 70}, {170, 130}, {100, 180}, {30, 130}, {30, 70}};
	private Board myBoard;

	public Display (Board board) {
		JFrame myFrame = new JFrame ( );		
		myFrame.setSize (myWindowWidth, myWindowHeight);
		myFrame.add (this);
		myFrame.setVisible (true);
		myFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		myBoard = board;
	}

	public void paintComponent (Graphics g) {
		// Clear the screen.
		g.setColor (Color.WHITE);
		g.fillRect (0, 0, myWindowWidth, myWindowHeight);
		
		// Draw the lines connecting the points.
		Iterator<Connector> allConnectors = myBoard.connectors ( );
		while (allConnectors.hasNext ( )) {
			Connector cnctr = allConnectors.next ( );
			g.setColor (myBoard.colorOf (cnctr));
			int k1 = cnctr.endPt1 ( );
			int k2 = cnctr.endPt2 ( );
			g.drawLine (POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);
		}
		
		// Draw the points.
		g.setColor (Color.BLACK);
		for (int k=0; k<6; k++) {
			g.fillOval (POINT_POSITIONS[k][0]-POINT_RADIUS, POINT_POSITIONS[k][1]-POINT_RADIUS, 
					2*POINT_RADIUS, 2*POINT_RADIUS);
		}
		
		// Draw the labels.
		g.setColor (Color.WHITE);
		for (int k=0; k<6; k++) {
			g.drawString (""+(k+1), 
					POINT_POSITIONS[k][0]-POINT_RADIUS/2, POINT_POSITIONS[k][1]+POINT_RADIUS/2);
		}
	}
}
