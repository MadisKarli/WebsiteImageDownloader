import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
/*
 * This Class will write files to location on disk
 * coded and tested for images
 */
public class WriteFileToDisk {
	/*
	 * Summary:		Takes a set of file urls and writes them to given location
	 * Parameters:	Set of file urls, String where to write these files
	 * Return:		Void, files on disk
	 */
	public static void writeFileToDisk(Set<String> data, String location) {
		for (String	url : data) {
			//System.out.println(url);
			String saveLocation = location;
			String name = fileName(url); // get file name from url
			saveLocation += name;
			try {
				saveFile(url, saveLocation);
			} catch (FileNotFoundException e) {
				System.out.println("Location not available" + e);
			}
		}
	}
	
	/*
	 * Summary:		Write file to disk
	 * Parameters:	String url to file, String where to write file
	 */
	public static void saveFile(String link, String location)
			throws FileNotFoundException {
		try {
			
			URL url;
			InputStream is;
			try{								//it failed to download some images from delfi.ee
				url = new URL(link);	
				is = url.openStream();
			}catch (MalformedURLException e) {	//so try to add http: before url
				System.out.println("MalformedURL @saveFile, trying to add http: " + e);
				//System.out.println("http:" + link);
				url = new URL("http:" + link);
				is = url.openStream();
			}
			// code from
			// http://www.avajava.com/tutorials/lessons/how-do-i-save-an-image-from-a-url-to-a-file.html
			OutputStream os = new FileOutputStream(location);
			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
			//end of code from link
			
		} catch (MalformedURLException e) {
			System.out.println("MalformedURL @saveFile " + e);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Summary:		Get file name and type from given url
	 * 				http:/www.xxx.com/yyy/.../filename.type
	 * 				or http:/www.xxx.com/yyy/.../filename.type?itok=aaaa
	 * Parameters:	String url of file
	 * Return: 		String name of file
	 */
	public static String fileName(String url) {
		int index = url.lastIndexOf("/") + 1;	// file name from url after last /
		
		String name = url.substring(index);
		try {									// some files have ? and info after .filetype, we don't need that
			int indexOfQuestionmark = url.indexOf("?");
			name = url.substring(index, indexOfQuestionmark);
		} catch (StringIndexOutOfBoundsException e) {
			// Guess there weren't any question marks
		}
		return name;
	}
	
	/*
	 * Summary:		Download test file and throw FileNotFoundException if fail
	 * 				Delete test file
	 * Parameters:	String of location
	 */
	public static void testLocation(String location)
			throws FileNotFoundException {
		saveFile("http://i.imgur.com/Ff1t8GV.png", location
				+ "WriteToDiskTest.png");
		Path path = Paths.get(location + "WriteToDiskTest.png");
		try {					//try to delete test file
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
