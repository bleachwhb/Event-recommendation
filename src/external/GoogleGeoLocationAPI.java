package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;



public class GoogleGeoLocationAPI {
	private static final String API_HOST = "maps.googleapis.com";
	private static final String SEARCH_PATH = "/maps/api/directions/json";
	private static final String API_KEY = "AIzaSyAl5ANN4vTo-PNcvuWYVCWLO3DY_MyVb4o";
	
	public JSONObject search(double lat, double lon, String mode, String destination) throws Exception {
		String url = "https://" + API_HOST + SEARCH_PATH;
		String origin = lat + "," + lon;
		String query = null;
		if(mode == null) {
			query = String.format("language=en-za&origin=%s&destination=%s&key=%s", origin, destination, API_KEY);
		} else {
			query = String.format("mode=%s&language=en-za&origin=%s&destination=%s&key=%s", mode, origin, destination, API_KEY);
		}
		try {
			String test = url + "?" + query;
			test = test.replaceAll(" ", "+");
			HttpURLConnection connection = (HttpURLConnection) new URL(test).openConnection();
			connection.setRequestMethod("GET");
 
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url + "?" + query);
			System.out.println("Response Code : " + responseCode);
 
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Extract events array only.
			JSONObject responseJson = new JSONObject(response.toString());
			JSONArray res = (JSONArray) responseJson.get("routes");
			JSONObject temp = (JSONObject) res.getJSONObject(0);
			JSONArray legs = (JSONArray) temp.get("legs");
			JSONObject useful = (JSONObject) legs.getJSONObject(0);
			JSONObject distance = (JSONObject) useful.get("distance");
			String distance_text = (String) distance.get("text");
			distance_text = distance_text.replaceAll(" ", "");
			JSONObject duration = (JSONObject) useful.get("duration");
			String duration_text = (String) duration.get("text");
			duration_text = duration_text.replaceAll(" ", "");
			JSONObject traffic_info = new JSONObject();
			traffic_info.put("distance", distance_text);
			traffic_info.put("duration", duration_text);
			System.out.println(distance_text);
			return traffic_info;
		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}
	}
	public static void main(String[] args) {
		GoogleGeoLocationAPI tmApi = new GoogleGeoLocationAPI();
		try {
			JSONObject res = tmApi.search(37.38, -122.08, null, "Los Angel");
			System.out.println(res);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}

}
