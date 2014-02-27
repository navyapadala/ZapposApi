

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

public class HttpExecutor {

	protected String endpoint = null;

	public String execute(HttpMethod method, String payload){

		Properties properties = new Properties();
	    try {
	    	properties.load(new FileInputStream(ApiClient.PROPERTIES_FILE));
	    } catch (IOException e) {
	    	System.out.println("Failed to open properties file " + ApiClient.PROPERTIES_FILE);
	    	System.exit(1);
	    }
	    String consumerKey = properties.getProperty(ApiClient.PROPERTY_NAME_CONSUMER_KEY);
	    if(consumerKey == null || consumerKey.length() == 0){
	    	System.out.println("Couldn't read ConsumerKey property from file " + ApiClient.PROPERTIES_FILE);
	    	System.exit(1);
	    }
	    
	    HttpURLConnection request = null;
		BufferedReader rd = null;
		OutputStreamWriter wr = null;
		StringBuilder response = null;
		try{
			URL endpointUrl = new URL(endpoint);
			request = (HttpURLConnection)endpointUrl.openConnection();
			request.setRequestMethod(method.toString());
			
			if((method == HttpMethod.POST || method == HttpMethod.PUT) && payload != null){
				request.addRequestProperty("Content-type", "text/xml");
				request.setDoOutput(true);
			}
			
			request.connect();
			
			if((method == HttpMethod.POST || method == HttpMethod.PUT) && payload != null){
				wr = new OutputStreamWriter(request.getOutputStream());
				wr.write(payload);
				wr.flush();
			}
			
			rd  = new BufferedReader(new InputStreamReader(request.getInputStream()));
			response = new StringBuilder();
			String line = null;
			while ((line = rd.readLine()) != null){
				response.append(line + '\n');
			}
		} catch (MalformedURLException e) {
			System.out.println("Exception: " + e.getMessage());
			//e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println("Exception: " + e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			//e.printStackTrace();
		} finally {
			try{
				request.disconnect();
			} catch(Exception e){}

			if(wr != null){
				try{
					wr.close();
				} catch(IOException ex){}
				wr = null;
			}
			
			if(rd != null){
				try{
					rd.close();
				} catch(IOException ex){}
				rd = null;
			}
		}

		return (response != null) ? response.toString() : null;
	}
}
