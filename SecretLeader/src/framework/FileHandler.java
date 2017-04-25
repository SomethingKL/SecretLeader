package framework;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileHandler{
	/**used to write to the file*/
	private static FileWriter writer;
	
	public FileHandler(){
	}
	public String[] readFile(String name){
		String sentence;
		ArrayList<String> stream = new ArrayList<>();
		Socket clientSocket;
		try{
			clientSocket = new Socket("symfony.local", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "read "+name;
			outToServer.writeBytes(sentence + '\n');
			while((sentence = inFromServer.readLine())!=null){
				if(sentence.compareTo("end")==0)
					break;
				stream.add(sentence);
			}
			int size = stream.size();
		    String[] answer = new String[size];
		    stream.toArray(answer);
			clientSocket.close();
			return answer;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**@param name of the file to be opened
	 */
	public void openToWrite(String name){
		String sentence;
		Socket clientSocket;
		try{
			clientSocket = new Socket("symfony.local", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			sentence = "open " +name;
			outToServer.writeBytes(sentence + '\n');
			clientSocket.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/**@param input is written to the open file
	 */
	public void writeToFile(String input){
		String sentence;
		Socket clientSocket;
		try{
			clientSocket = new Socket("symfony.local", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			sentence = input;
			outToServer.writeBytes(sentence + '\n');
			clientSocket.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	/**{@literal} closes the open file
	 */
	public void close(){
		String sentence;
		Socket clientSocket;
		try{
			clientSocket = new Socket("symfony.local", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			sentence = "close";
			outToServer.writeBytes(sentence + '\n');
			clientSocket.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**Gets the length, in terms of how many lines, are in the file.
	 * @param file, the file wanting to be used
	 */
	/*public int getLength(String file){
		String[] str = readFile(file);
		return str.length;
	}*/
}