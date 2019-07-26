package takano.sample;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

public class MyRetriableHttpClient {

    private final String url;

    private int connTimeout = 3000;
    private int socketTimeout = 3000;
    private int retryCount = 3;
    private int waitTime = 300;

    public static void main(String[] args) throws IOException {
        final MyRetriableHttpClient client = new MyRetriableHttpClient("");
        client.execute();
    }

    public MyRetriableHttpClient(String url) {
        super();
        this.url = url;
    }

    public MyRetriableHttpClient setTimeout(int connTimeout, int socketTimeout) {
        this.connTimeout = connTimeout;
        this.socketTimeout = socketTimeout;
        return this;
    }

    public MyRetriableHttpClient setRetry(int retryCount, int waitTime) {
        this.retryCount = retryCount;
        this.waitTime = waitTime;
        return this;
    }

    public Optional<String> execute() throws IOException {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(this.connTimeout)
                .setSocketTimeout(this.socketTimeout).build();
        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultRequestConfig(config)
                .setRetryHandler(new MyHttpRetryHandler());

        CloseableHttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        final CloseableHttpResponse ret = client.execute(request);
        ret.close();
        client.close();

        String response = EntityUtils.toString(ret.getEntity(), Charset.forName("UTF-8"));
        return Optional.ofNullable(response);
    }

    private class MyHttpRetryHandler implements HttpRequestRetryHandler {

        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            final HttpClientContext clientContext = HttpClientContext.adapt(context);
            final HttpResponse response = clientContext.getResponse();
            final StatusLine statusLine = response.getStatusLine();
            if (executionCount > retryCount) {
                return false;
            }

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ignored) {
            }

            return true;
        }

    }

}
