import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiClient {

	public static final String PROPERTIES_FILE = "client.properties";
	public static final String PROPERTY_NAME_SERVICE_ENDPOINT = "ServiceEndpoint";
	public static final String PROPERTY_NAME_CONSUMER_KEY = "ConsumerKey";

	//public static float prc = 0;
	//public static int cnt = 0;
	
	public static void main(String[] args) {

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Failed to open properties file "+ PROPERTIES_FILE);
			System.exit(1);
		}
		String serviceEndpoint = properties.getProperty(PROPERTY_NAME_SERVICE_ENDPOINT);
		if (serviceEndpoint == null || serviceEndpoint.length() == 0) {
			System.out.println("Couldn't read ServiceEndpoint property from file "+ PROPERTIES_FILE);
			System.exit(1);
		}

		String key = properties.getProperty(PROPERTY_NAME_CONSUMER_KEY);
		if (key == null || key.length() == 0) {
			System.out.println("Couldn't read ConsumerKey property from file "+ PROPERTIES_FILE);
			System.exit(1);
		}
		 String price = "Enter price: ";

		try {
		do{	
			float prc = 0;
			do {
				try {
					prc = Float.parseFloat(ConsoleUtil.readResponse(price)); 
					break;
				} catch (NumberFormatException ex) {
					System.out.println("Incorrect format...");
				}
			} while (true);  

			String count = "Enter number of products: ";

			int cnt = 0;
			do {
				try {
					cnt = Integer.parseInt(ConsoleUtil.readResponse(count));
					if(cnt>10){
						System.out.println("Enter value less than or equal to 10.");
						continue;
					}
					break;
				} catch (NumberFormatException ex) {
					System.out.println("Incorrect format...");
				}
			} while (true);    

			String response = getUriInfoEx(serviceEndpoint, "Search", key);
			JSONObject jObject = new JSONObject(response);
			JSONArray menu = jObject.getJSONArray("results");
			//System.out.println(menu);

			double prices[] = new double[menu.length()];

			for(int i=0; i<menu.length(); i++){
				prices[i] = Double.parseDouble((menu.getJSONObject(i).getString("price").substring(1)));
			}

			Arrays.sort(prices);
			/*   for(int i=0; i<prices.length; i++){
			        System.out.println(prices[i]);
			    }  */

			double sum = 0;
			double subset[] = new double[cnt];
			for(int i=0; i<cnt; i++){
				subset[i]=prices[i];
				sum = sum+prices[i];
			}
			double minSum = sum;
			//System.out.println(sum);
			String prodName[] = new String[subset.length];

			if(sum==prc){
				
				printProducts(menu,subset,prodName);
				break;
			}
			else if(sum>prc){
			    	System.out.println("A combination of "+cnt+" products don't exists for a price of "+prc);
			    	System.out.println("The minimum cost for the number of products you have entered is "+minSum);
			    	continue;
			    }
			else if(sum<prc){	
	//			System.out.println("Sum is less than or equal to "+prc);
					
				for(int j=0;j<prices.length-1;j++){
					if(cnt+j<prices.length){
						sum = sum + prices[cnt+j] - prices[0+j];
					if(sum<prc){
						for(int i=0;i<cnt;i++){
							subset[i] = prices[1+j+i];
						}
					}
					else if(sum==prc){
						for(int i=0;i<cnt;i++){
							subset[i] = prices[1+j+i];
						}
						break;
					}
					else if(sum>prc)
						break;
					}	
				}
				printProducts(menu,subset,prodName);
				break;
			}
		}while(true);	
	} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static String getUriInfoEx(String serviceEndpoint, String api, String key) {
		Uri resource = new Uri(serviceEndpoint, api, key);
		return resource.get();
	}
	
	private static void printProducts(JSONArray menu, double[] subset, String[] prodName){
		
		try{
		for(int i=0; i<menu.length(); i++){
			for(int k=0;k<subset.length;k++){
				if(subset[k] == Double.parseDouble(menu.getJSONObject(i).getString("price").substring(1)) && prodName[k]==null){
					String temp = menu.getJSONObject(i).getString("productName");
					//	if(!Arrays.asList(prodName).contains(temp)){
					prodName[k] = temp;
					System.out.println(prodName[k]+" --- $"+subset[k]);  // }
				}
			}
		}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
