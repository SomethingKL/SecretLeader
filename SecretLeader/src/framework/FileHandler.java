package framework;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileHandler{
	public FileHandler(){
		mead();
	}
	public void mead(){
		String sentence;
		//String modifiedSentence;
		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		Socket clientSocket;
		try{
			clientSocket = new Socket("symfony.local", 80);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sentence = "FIRE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";//inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			//modifiedSentence = inFromServer.readLine();
			//System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
/**@param name of the file being read from
 * @return a string array containing the information from a file
 */
/*public String[] readFile(String name){
	try(BufferedReader br = new BufferedReader(new FileReader(name))){
	    ArrayList<String> nonComments = new ArrayList<>();
	    String line;
	    while((line = br.readLine()) != null)
	    	if(line.charAt(0) != '#')
	    		nonComments.add(line);
	    int length = nonComments.size();
	    String[] answer = new String[length];
	    for(int k=0;k<length;k++)
	    	answer[k] = nonComments.get(k);
		br.close();
		return answer;
	}catch(Exception e){
		System.out.println(e);
	}
	return null;
}*/