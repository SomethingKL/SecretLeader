package JUnitTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import framework.TCPClient;

public class InitPresTest {
	TCPClient client = new TCPClient();
	
	@Test
	public void test() {
		String[] initPres = client.readFile("data/Turn.txt");
		String[] firstPlayer = client.readFile("data/Players.txt");
		assertEquals(initPres[0],firstPlayer[0]);
	}

}
