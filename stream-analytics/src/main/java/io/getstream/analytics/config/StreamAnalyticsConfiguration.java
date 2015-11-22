package io.getstream.analytics.config;

public class StreamAnalyticsConfiguration {

    /**
     * Socket timeout in ms.
     */
    private int timeout = 500;

    /**
     * Connection timeout in ms.
     */
    private int connectionTimeout = 500;

    /**
     * TimeToLive in ms.
     */
    private long timeToLive = 400;

    /**
     * Keep alive in ms.
     */
    private long keepAlive = 3000;

    /**
     * Max concurrent connection the pool should handle.
     */
    private int maxConnections = 20;

    /**
     * Max concurrent connection per route.
     */
    private int maxConnectionsPerRoute = 20;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public long getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(long keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }
}
