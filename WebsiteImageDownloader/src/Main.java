import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
/*
 * Program to download all images from websites that use img tags.
 * Doesn't check image type
 * Run from command line/terminal with java Main <link> <save location> 
 * main will test both location and url before downloading anything 
 * idea from http://improvingsoftware.com/2009/04/10/the-programmers-guide-to-getting-hired-the-code-sample/
 * Code Sample Idea #4
 * by Madis-Karli Koppel
 */

public class Main {
	public static void main(String[] args) {
		// try/catch to check args from command line
		try {
			ImageURLs.testURL(args[0]);
			Set<String> data = ImageURLs.getImageLocationSet(args[0]);
			String downloadLocation = args[1];
			if (String.valueOf(downloadLocation.charAt(downloadLocation.length() - 1)) != "/") {
				downloadLocation += "/"; // Last char must be /!
			}
			
			try {// try location with test image so we wouldn't waste time trying to download all images
				WriteFileToDisk.testLocation(downloadLocation);
				WriteFileToDisk.writeFileToDisk(data, downloadLocation);
				System.out.println("Finished with task");
			} catch (FileNotFoundException e) {
				System.out.println("Location not available " + e);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("I need two arguments : first argument - url, second - save location");
		} catch (IOException e1) {
			System.out.println("Can't connect to URL " + e1);
		}

		// Test without cmd/terminal
		// Set<String> data = ImageURLs.download("http://www.delfi.ee/");
		// String downloadLocation = "C:/Users/Madis/Desktop/VPL_vahetest/pildid/";

	}
}
