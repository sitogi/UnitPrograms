package takano.sample;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Jira {

	private static final String BASE_URL = "http://localhost:8080";

	private static final String PATH_GET_ISSUE = "/rest/api/2/issue/DAT-1";
	private static final String PATH_GET_FIELDS = "/rest/api/2/field";
	private static final String PATH_GET_CREATE_ISSUE = "/rest/api/2/issue";
	private static final String PATH_GET_CREATE_ISSUE_META = "/rest/api/2/issue/createmeta";

	public static void main(String args[]) throws UnsupportedEncodingException {
		getCreateIssueMeta();
		createIssue();
	}

	private static void getCreateIssueMeta() {
		final HttpGet httpGet = new HttpGet(BASE_URL + PATH_GET_CREATE_ISSUE_META);
		httpGet.setHeader("Authorization", "Basic " + toBase64("takano", "password"));
		httpGet.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

		executeHttpRequest(httpGet);
	}

	private static void getFields() {
		final HttpGet httpGet = new HttpGet(BASE_URL + PATH_GET_FIELDS);
		httpGet.setHeader("Authorization", "Basic " + toBase64("takano", "password"));
		httpGet.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

		executeHttpRequest(httpGet);
	}

	private static void getReleaseList() {
		final HttpGet httpGet = new HttpGet(BASE_URL + PATH_GET_ISSUE + "?properties=summary");
		httpGet.setHeader("Authorization", "Basic " + toBase64("takano", "password"));
		httpGet.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

		executeHttpRequest(httpGet);
	}

	private static void executeHttpRequest(final HttpRequestBase httpRequest) {
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(httpRequest)) {
			System.out.println(response.getStatusLine());
			final HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createIssue() throws UnsupportedEncodingException {
		final HttpPost httpPost = new HttpPost(BASE_URL + PATH_GET_CREATE_ISSUE);
		httpPost.setHeader("Authorization", "Basic " + toBase64("takano", "password"));
		httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());

		httpPost.setEntity(new StringEntity(""));

		executeHttpRequest(httpPost);
	}

	private static String toBase64(final String user, final String password) {
		// TODO Charset
		final String authInfo = user + ":" + password;
		final byte[] encoded = Base64.getEncoder().encode(authInfo.getBytes());
		return new String(encoded);
	}

}