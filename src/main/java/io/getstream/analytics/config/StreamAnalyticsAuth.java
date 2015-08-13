package io.getstream.analytics.config;

/**
 * Contains authentication credentials.
 */
public class StreamAnalyticsAuth {

    public static final String PARAM_API_KEY = "io.getstream.analytics.v1.API_KEY";
    public static final String PARAM_AUTH_TOKEN = "io.getstream.analytics.v1.AUTH_TOKEN";

    private String apiKey;
    private String authToken;

    public StreamAnalyticsAuth(String apiKey, String authToken) {
        this.apiKey = apiKey;
        this.authToken = authToken;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
