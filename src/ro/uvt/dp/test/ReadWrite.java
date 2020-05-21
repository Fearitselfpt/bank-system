package ro.uvt.dp.test;

import java.io.*;

public class ReadWrite {
	public static void writeToFile(String data) {
	    OutputStream os = null;
	    try {
	        os = new FileOutputStream("loggs.txt", true);
	        os.write(data.getBytes(), 0, data.length());
	        byte[] strToBytes = data.getBytes();
	        os.write(strToBytes);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }finally{
	        try {
	            os.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
