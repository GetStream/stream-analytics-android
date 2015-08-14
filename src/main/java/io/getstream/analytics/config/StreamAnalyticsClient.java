package io.getstream.analytics.config;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class StreamAnalyticsClient {

	private final OkHttpClient client;

	public StreamAnalyticsClient() {
		this.client = initClient(new StreamAnalyticsConfiguration());
	}

    private OkHttpClient initClient(final StreamAnalyticsConfiguration config) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        client.setReadTimeout(config.getTimeout(), TimeUnit.MILLISECONDS);
        client.setWriteTimeout(config.getTimeout(), TimeUnit.MILLISECONDS);
        client.setRetryOnConnectionFailure(false);
        client.setConnectionPool(new ConnectionPool(config.getMaxConnections(), config.getKeepAlive()));
        return client;
    }

    public OkHttpClient newClient() {
        //TODO: configuration shall retrieve properties from manifest
        return this.client;
    }
}
