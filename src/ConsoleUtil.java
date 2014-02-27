

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsoleUtil {
	public static String readResponse(String msg) throws IOException{
		System.out.print(msg);
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		return consoleReader.readLine();
	}

	public static ArrayList<String> readResponseArray(String msg) throws IOException{
		System.out.print(msg);
		ArrayList<String> responses = new ArrayList<String>();
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		String responseString = consoleReader.readLine();
		if(responseString.indexOf(',') >= 0){
			String [] responseArray = responseString.split(",");
			for(String response : responseArray){
				response = response.trim();
				if(response.length() > 0){
					responses.add(response);
				}
			}
		}else{
			responses.add(responseString);
		}
		return responses;
	}
}
