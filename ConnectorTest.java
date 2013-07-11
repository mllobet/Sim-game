import static org.junit.Assert.*;

import org.junit.Test;


public class ConnectorTest {

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

}
