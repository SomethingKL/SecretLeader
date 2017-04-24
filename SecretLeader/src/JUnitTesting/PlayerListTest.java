package JUnitTesting;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.IOException;

import org.junit.Test;

import entity.PlayerList;

public class PlayerListTest {
	PlayerList lst;
	
	
	
	@Test
	public void test() {
		try {
			lst = new PlayerList(new Point(5,305));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int players = lst.getPlayerslength();
		System.out.println("num players: " + Integer.toString(players));
		assertEquals(players, 4);
	}

}
