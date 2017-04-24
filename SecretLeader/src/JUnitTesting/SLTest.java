package JUnitTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import framework.TCPClient;

public class SLTest {
	TCPClient client = new TCPClient();
	String nextScreen = "false";
	
	
	@Test
	public void test() {
		System.out.println("reading leaveStarting.txt");
		String[] next = client.readFile("data/leaveStarting.txt");
		assertEquals(next[0], nextScreen);
		
		System.out.println("reading Board.txt");
		String[] board = client.readFile("data/Board.txt");
		assertEquals(board[0], "0");
		assertEquals(board[1], "0");
		
		System.out.println("reading displayInfo.txt");
		String[] info = client.readFile("data/displayInfo.txt");
		assertEquals(info[0], "0");
		
		System.out.println("reading ProposedChancellor.txt");
		String[] chance = client.readFile("data/ProposedChancellor.txt");
		assertEquals(chance[0], "None");
	}

}
