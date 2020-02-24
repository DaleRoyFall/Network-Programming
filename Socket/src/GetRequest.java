
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Multithreading extends Thread {
	private Thread thread;
	private String threadName;
	private String imgUrl;
	private String imageName;
	
	Multithreading(String threadName, String imgUrl, String imageName) {
		this.threadName = threadName;
		this.imgUrl = imgUrl;
		this.imageName = imageName;
	}
	
	// Save images to computer
	private static void saveImage(String imageUrl, String imageName) throws IOException {
		URL url = new URL(imageUrl);
		
		String destinationFile = "C://Users/User/Desktop/imageSave/" + imageName;
		
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);
		
		byte[] b = new byte[2048];
		int length;
		
		while((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		
		is.close();
		os.close();
	}
	
	// Run thread
	public void run() {
		try {
			saveImage(imgUrl, imageName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	// Start thread
	public void start() {
		//System.out.println("Starting " +  threadName );
	      if (thread == null) {
	    	  thread = new Thread (this, threadName);
	    	  thread.start ();
	      }
	}
	
}

public class GetRequest {

    public static void main(String[] args) throws Exception {

        GetRequest getReq = new GetRequest();

        //Runs SendReq passing in the url and port from the command line
        getReq.SendReq("www.unite.md", 80);


    }
    
    public void SendReq(String url, int port) throws Exception {

        //Instantiate a new socket
        Socket s = new Socket(url, port);

        //Instantiates a new PrintWriter passing in the sockets output stream
        PrintWriter wtr = new PrintWriter(s.getOutputStream());

        //Prints the request string to the output stream
        wtr.println("GET / HTTP/1.1");
        wtr.println("Host: " + url);
        wtr.println("");
        wtr.flush();
        

        //Creates a BufferedReader that contains the server response
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String outStr;
        
        // Extract images from string
        //--------------------------------------------------------------------
		
        int numberOfImages;
        int imageFind = 0;
        
        // Read wanted number of images
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much images you want to download?");
        numberOfImages = scanner.nextInt();
        scanner.close();
        
        // Print match
		while((outStr = bufRead.readLine()) != null){
			imageFind = extractImage(outStr, imageFind);
			
			// End of loop
			if(numberOfImages + 1 <= imageFind)
				break;
		}
		
		System.out.println("End of images");
        //--------------------------------------------------------------------

        //Closes out buffer and writer
        bufRead.close();
        wtr.close();
        s.close();

    }
    
    public static int extractImage(String outStr, int imageFind) throws IOException {
    	// Regex pattern for images
        Pattern pattern = Pattern.compile("[^\\'\"=\\s]+\\.(jpe?g|png|gif)");
        
		// Matcher that contains found regex
        Matcher match;
        
        // Image url
        String imgUrl;
        
        String imageFullName;
        String strArr[];
        
        String imageName;
    	
    	match = pattern.matcher(outStr);
    	
    	while(match.find()) {
    		
    		// Get full name of the image
        	imageFullName = match.group();
        	    		
        	// Full url of the image
        	imgUrl = "http://www.unite.md" + imageFullName;
        	
        	strArr = imageFullName.split("/");
        	
        	// Get full name of image
        	imageName = strArr[strArr.length - 1];
        	
        	Multithreading thread = new Multithreading("Thread" + imageFind, imgUrl, imageName);
			thread.start();
        	
			imageFind++;
       }    	
    	
    	return imageFind;
    }
}
    