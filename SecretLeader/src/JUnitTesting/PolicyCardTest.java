package JUnitTesting;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.IOException;

import org.junit.Test;

import entity.PolicyCard;

public class PolicyCardTest {
	PolicyCard card;
	String nextScreen = "false";
	
	
	@Test
	public void test() {
		System.out.println("reading policy card state");
		try {
			card = new PolicyCard(new Point(760, 620),"Blue");
		} catch (IOException e) {
			e.printStackTrace();
			assertEquals(0,1);
		}
		boolean isKept = card.getCardKept();
		assertEquals(isKept, false);
	}

}
