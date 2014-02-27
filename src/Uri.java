



public class Uri extends HttpExecutor {
	
	private static final String PATH = "key=";
	private String service = null;
	private String baseUrl = null;
	
	public Uri(String serviceEndpoint, String api, String key){
		if(api.equals("Search")){
			service = "Search?";                //sort={\"price\":\"desc\"}&";
		}
		else if(api.equals("Product")){
			service = "Product/";  				//prdid?includes=["styles"]&
		}
		baseUrl = serviceEndpoint + service + PATH + key;
	}
	
	public String get(){
		String response = null;
		try{
			
			endpoint = baseUrl;
			
			response = execute(HttpMethod.GET, null);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return response;
	}
	
}
