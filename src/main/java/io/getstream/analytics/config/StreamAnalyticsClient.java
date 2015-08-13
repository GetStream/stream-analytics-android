package io.getstream.analytics.config;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class StreamAnalyticsClient {

    private OkHttpClient initClient(final StreamAnalyticsConfiguration config) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(config.getConnectionTimeout(), TimeUnit.MILLISECONDS);
        client.setReadTimeout(config.getTimeout(), TimeUnit.MILLISECONDS);
        client.setWriteTimeout(config.getTimeout(), TimeUnit.MILLISECONDS);
        client.setRetryOnConnectionFailure(false);
        client.setConnectionPool(new ConnectionPool(config.getMaxConnections(), config.getKeepAlive()));
        return client;
    }

    public static OkHttpClient newClient() {
        //TODO: configuration shall retrieve properties from manifest
        return new StreamAnalyticsClient().initClient(new StreamAnalyticsConfiguration());
    }
}
