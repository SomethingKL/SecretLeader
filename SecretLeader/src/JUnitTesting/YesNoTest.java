package JUnitTesting;

import static org.junit.Assert.*;
import org.junit.Test;

import entity.Votecard;
import framework.TCPClient;

public class YesNoTest {
	TCPClient client = new TCPClient();
	Votecard votes = new Votecard();
	int players = client.getLength("data/Players.txt");
	String[] play = client.readFile("data/Players.txt");
	
	@Test
	public void test() {
		if(votes.getNoCount() <= players/2){
			System.out.println("vote passes");
			assert(true);
		}
		else{
			System.out.println("vote fails");
			assert(false);
		}
		
	}

}
