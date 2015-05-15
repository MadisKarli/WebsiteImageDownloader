import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
/*
 * This Class will connect to url and find src= part of <img tag.
 * Methods to work with each line of HTML or with whole HTML in one go
 * Currently in use methods to deal with HTML in one go
 */
public class ImageURLs {
	/*
	 * Summary:		Gets HTML from site and then finds all links for pictures from <img src=picture 
	 * Parameters:	String url of site
	 * Return:		Set of locations
	 */
	public static Set<String> getImageLocationSet(String site) {
		//Set<String> data = getImageLocationsFromSite(site);
		String HtmlCode = getHtmlString(site);
		Set<String> data1 = getImageLinksFromHtmlString(HtmlCode);
		return data1;
	}
	/*
	 * Summary:		Connects to url and returns HTML
	 * Parameters:	String url of site
	 * Return:		String of site HTML
	 */
	private static String getHtmlString(String site) {
		Set<String> locations = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		try {
			URL adress = new URL(site);
			URLConnection connect = adress.openConnection();
			connect.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				String link = getImageLocationFromLine(inputLine);
				sb.append(inputLine);
				if (link != null) {
					locations.add(link);
				}
			}
			in.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			System.out.println("Connection failed, malformedURL " + e);
		} catch (IOException e) {
			System.out.println("IOEXCeption " + e);
		}
		return sb.toString();
	}
	/*
	 * Summary:		Gets  HTML code and finds url from <img src="link"
	 * Parameters:	String HTML
	 * Return:		Set of image locations
	 */
	private static Set<String> getImageLinksFromHtmlString(String html){
		Set<String> links = new HashSet<String>();
		html = html.replaceAll("\\s+","");
		while(true){
			if (html.toLowerCase().contains("<img")) {			// find img tag
				int fromImg = html.indexOf("<img");				// split line from img tag to avoid false src-s
				html = html.substring(fromImg);
				if (html.toLowerCase().contains("src=")) {		// get String containing location
					int firstIndex = html.indexOf("src=") + 5;	// for that find string between first src= and last "
					String tmp = html.substring(firstIndex);
					int lastIndex = tmp.indexOf("\"");
					String ImageUrl = tmp.substring(0, lastIndex);
					html = html.substring(lastIndex);			// remove used code from HTML
					links.add(ImageUrl);
				}
			}else{
			break;												// end when no more <img tags
			}
		}
		return links;
	}
	
	/*
	 * Summary:		Try to establish connection to url, throw IOException when not successful
	 * 				Delete test file
	 * Parameters:	String of url
	 */
	public static void testURL(String url) throws IOException{
		URL adress = new URL(url);
		URLConnection connect = adress.openConnection();
		connect.connect();
	}
	
	
	
	//FROM NOW ON CODE TO DEAL WITH HTML ONE LINE AT A TIME
	//NOT IN USE
	/*
	 * Summary:		Connects to HTML site and calls getImageLocationFromLine with each line
	 * Parameters:	String url of site
	 * Return:		Set of locations
	 */
	private static Set<String> getImageLocationsFromSite(String site) {
		Set<String> locations = new HashSet<String>();
		try {
			URL adress = new URL(site);
			URLConnection connect = adress.openConnection();
			connect.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connect.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				String link = getImageLocationFromLine(inputLine);
				if (link != null) {
					locations.add(link);
				}
			}
			in.close();
			return locations;
		} catch (MalformedURLException e) {
			System.out.println("Connection failed, malformedURL " + e);
		} catch (IOException e) {
			System.out.println("IOEXCeption " + e);
		}
		return locations;
	}
	
	/*
	 * Summary:		Gets one line from HTML code and finds url from src="link"
	 * Parameters:	String line from HTML
	 * Return:		String url of image
	 */
	private static String getImageLocationFromLine(String line) {
		line = line.replaceAll("\\s+","");
		// find img tag
		if (line.toLowerCase().contains("<img")) {
			// split line from img tag to avoid false src-s
			int fromImg = line.indexOf("<img");
			line = line.substring(fromImg);
			if (line.toLowerCase().contains("src=")) {
				// get String containing location
				// for that find string between first src= and last "
				int firstIndex = line.indexOf("src=") + 5;
				String tmp = line.substring(firstIndex);
				int lastIndex = tmp.indexOf("\"");
				String ImageUrl = tmp.substring(0, lastIndex);
				return ImageUrl;
			}
		}
		return null;
	}


	
}
