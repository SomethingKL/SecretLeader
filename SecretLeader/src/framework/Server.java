package framework;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server{
	/**used to write to the file*/
	private static FileWriter writer;
	private static boolean open = false;
	
	public static void main(String[] args){
		String clientSentence;
		ServerSocket welcomeSocket;
		try{
			welcomeSocket = new ServerSocket(80);
			while(true)
			{
				Socket connectionSocket = welcomeSocket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				clientSentence = inFromClient.readLine();
				String[] input = clientSentence.split(" ");
				if(input[0].compareTo("open")==0){
					writer = new FileWriter(input[1]);
					open = true;
				}else if(open && input[0].compareTo("close")==0){
					writer.close();
					open = false;
				}else if(open){
					writer.append(clientSentence + '\n');
				}else if(input[0].compareTo("read")==0){
					sendToClient(returnFile(input[1]),outToClient);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void sendToClient(String[] info, DataOutputStream stream) throws IOException{
		for(String line: info){
			stream.writeBytes(line+'\n');
		}
		stream.writeBytes("end\n");
	}
	/**@param name of the file being read from
	 * @return a string array containing the information from a file
	 */
	public static String[] returnFile(String name){
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
	}
}