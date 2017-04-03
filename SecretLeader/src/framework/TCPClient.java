/**This is our secret leader central file reader.
 * Basically this class just reads and writes from the central file(s).
 * once we have our code working we can try to implement this with networking
 */
package framework;

import java.io.*;
import java.util.ArrayList;

public class TCPClient {
	/**used to write to the file*/
	private static FileWriter writer;
	/**constant representing carriage return*/
	private final static char newline = '\n';
	
	public TCPClient(){
	}
	/**@param name of the file being read from
	 * @return a string array containing the information from a file
	 */
	public String[] readFile(String name){
		try (BufferedReader br = new BufferedReader(new FileReader(name))){
		    ArrayList<String> stream = new ArrayList<>();
		    br.lines().forEach(stream::add);
		    int size = stream.size();
		    String[] answer = new String[size];
		    for(int k=0;k<size;k++)
		    	answer[k]=stream.get(k);
			br.close();
			return answer;
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	/**@param name of the file to be opened
	 */
	public void openToWrite(String name){
		try{
			writer = new FileWriter(name);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	/**@param input is written to the open file
	 */
	public void writeToFile(String input){
		try{
			writer.write(input + newline);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	/**{@literal} closes the open file
	 */
	public void close(){
		try{
			writer.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}