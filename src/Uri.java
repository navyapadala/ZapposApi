



public class Uri extends HttpExecutor {
	
	private static final String PATH = "key=67d92579a32ecef2694b74abfc00e0f26b10d623";
	private String service = null;
	private String baseUrl = null;
	
	public Uri(String serviceEndpoint, String api){
		if(api.equals("Search")){
			service = "Search?";                //sort={\"price\":\"desc\"}&";
		}
		else if(api.equals("Product")){
			service = "Product/";  //prdid?includes=["styles"]&
		}
		baseUrl = serviceEndpoint + service + PATH;
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
	
/*	public String put(){
		String response = null;
		try{
			//input uri and categories from user and create endpoint and payload
		//	String uri = ConsoleUtil.readResponse("Enter uri: ");
		//	endpoint = baseUrl + "/" + uri;
			
			endpoint = baseUrl;
			ArrayList<String> categories = ConsoleUtil.readResponseArray("Enter comma separated list of categories: ");
			String payload = "<newcats><categories>";
			for(String category: categories){
				payload += "<cat>" + category + "</cat>";
			}
			payload += "</categories></newcats>";
			
			response = execute(HttpMethod.PUT, payload);
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}
		return response;
	} */
}
