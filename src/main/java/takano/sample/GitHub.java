package takano.sample;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GitHub {

	private static final String BASE_URL = "https://api.github.com/";
	private static final String TOKEN = "";

	private static final String PATH_GET_RELEASES = "";
	private static final String PATH_INSTALLER = "";

	// Only one auth mechanism allowed; only the X-Amz-Algorithm query parameter,
	// Signature query string parameter or the Authorization header should be specified.
	private static final String AUTH_PARAMETER = "?access_token=" + TOKEN;

	// only query
	public static void main(String args[]) throws IOException {
		System.out.println(new File("..").getCanonicalPath());
		getReleaseList();
		getReleaseFile();
	}

	private static void getReleaseList() {
		final CloseableHttpClient client = HttpClientBuilder.create().build();
		final HttpGet httpGet = new HttpGet(BASE_URL + PATH_GET_RELEASES);
		httpGet.setHeader("Authorization", "token " + TOKEN);

		try {
			final CloseableHttpResponse response = client.execute(httpGet);
			final HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getReleaseFile() {
		final CloseableHttpClient client = HttpClientBuilder.create().build();
		final HttpGet httpGet = new HttpGet(BASE_URL + PATH_INSTALLER + AUTH_PARAMETER);
//        httpGet.setHeader("Authorization", "token " + TOKEN);
		httpGet.setHeader("Accept", "application/octet-stream");

		try {
			final CloseableHttpResponse response = client.execute(httpGet);
			final HttpEntity entity = response.getEntity();
			try (InputStream in = new BufferedInputStream(entity.getContent());
					OutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get("")))) {
				int read;
				while ((read = in.read()) != -1) {
					out.write(read);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}