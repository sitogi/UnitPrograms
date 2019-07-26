package takano.sample;

//import org.apache.*

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamSample {

	public static void main(String[] args) throws Exception {
//		simpleInputStream();
		useBuffer();
		simpleBufferedInputStream();
		useBufferAndBufferedInputStream();
//		apacheCommonsIo();
	}

	private static void simpleInputStream() throws IOException {
		final long start = System.currentTimeMillis();
		try (InputStream in = new FileInputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf");
				OutputStream out = new FileOutputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf")) {
			int read;
			while ((read = in.read()) != -1) {
				out.write(read);
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - start));
	}

	private static void useBuffer() throws IOException {
		final long start = System.currentTimeMillis();
		byte[] buf = new byte[8196];
		try (InputStream in = new FileInputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf");
				OutputStream out = new FileOutputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf")) {
			int read;
			while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - start));
	}

	private static void simpleBufferedInputStream() throws IOException {
		final long start = System.currentTimeMillis();
		try (InputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf"));
//		try (InputStream in = new FileInputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf");
				OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf"))) {
//				OutputStream out = new FileOutputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf")) {
			int read;
			while ((read = in.read()) != -1) {
				out.write(read);
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - start));
	}

	private static void useBufferAndBufferedInputStream() throws IOException {
		final long start = System.currentTimeMillis();
		byte[] buf = new byte[8196];
		try (InputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf"));
				OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf"))) {
			int read;
			while ((read = in.read(buf)) != -1) {
				out.write(buf, 0, read);
			}
		}
		System.out.println("time: " + (System.currentTimeMillis() - start));
	}

//	private static void apacheCommonsIo() throws IOException {
//		final long start = System.currentTimeMillis();
//		final File srcFile = new File("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.pdf");
//		final File destFile = new File("C:\\Users\\ap141011\\Desktop\\SDKGettingStarted.out.pdf");
//		FileUtils.copyFile(srcFile, destFile);
//		System.out.println("time: " + (System.currentTimeMillis() - start));
//	}

}
