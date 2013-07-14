import java.awt.*;
import java.util.Iterator;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
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

			Graphics2D g2d = (Graphics2D)g;
			Line2D lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);
			g2d.draw(lin);
			//g.drawLine (POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
			//	POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);		
		}

		// Draw the lines connecting the points.
		Iterator<Connector> freeConnectors = myBoard.connectors ( Color.WHITE);
		while (freeConnectors.hasNext ( )) {
			Connector cnctr = freeConnectors.next ( );
			g.setColor (Color.GRAY);
			int k1 = cnctr.endPt1 ( );
			int k2 = cnctr.endPt2 ( );

			Graphics2D g2d = (Graphics2D)g;
			Line2D lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);
			g2d.draw(lin);
			//g.drawLine (POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
			//	POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);		
		}

		Iterator<Connector> redDotted = myBoard.redDotted();
		while (redDotted.hasNext())
		{
			Connector c = redDotted.next();

			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1,2}, 0));
			g2d.setColor(Color.RED);

			int k1 = c.endPt1 ( );
			int k2 = c.endPt2 ( );

			Line2D lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);
			g2d.draw(lin);
		}

		Iterator<Connector> blueDotted = myBoard.blueDotted();
		while (blueDotted.hasNext())
		{
			Connector c = blueDotted.next();

			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1,2}, 0));
			g2d.setColor(Color.BLUE);

			int k1 = c.endPt1 ( );
			int k2 = c.endPt2 ( );

			Line2D lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);
			g2d.draw(lin);

		}

		Iterator<Connector> dualDotted = myBoard.dualDotted();
		while (dualDotted.hasNext())
		{
			Connector c = dualDotted.next();

			Graphics2D g2d = (Graphics2D)g;

			int k1 = c.endPt1 ( );
			int k2 = c.endPt2 ( );

			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 2, new float[] {1,2}, 0));
			g2d.setColor(Color.BLUE);

			Line2D lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);


			g2d.draw(lin);

			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1,2}, 2));
			g2d.setColor(Color.RED);

			lin = new Line2D.Float(POINT_POSITIONS[k1-1][0], POINT_POSITIONS[k1-1][1],
					POINT_POSITIONS[k2-1][0], POINT_POSITIONS[k2-1][1]);

			g2d.draw(lin);
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
