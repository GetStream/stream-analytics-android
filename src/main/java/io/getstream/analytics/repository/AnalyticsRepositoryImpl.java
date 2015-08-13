package io.getstream.analytics.repository;

import android.util.Log;
import com.google.gson.Gson;
import com.squareup.okhttp.*;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;

import java.io.IOException;

/**
 * Implementation of the REST client for Stream Analytics service.
 */
public class AnalyticsRepositoryImpl implements AnalyticsRepository {

    public static final String TAG = AnalyticsRepositoryImpl.class.getSimpleName();

	public static final String BASE_ENDPOINT = "https://analytics.getstream.io/analytics/v1.0/";

    public static final String IMPRESSION_PATH_PARAM = "impression";
	public static final String ENGAGEMENT_PATH_PARAM = "engagement";

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String PARAM_API_KEY = "apikey";
    public static final String HEADER_STREAM_AUTH_TYPE = "STREAM-AUTH-TYPE";
    public static final String HEADER_AUTHORIZATION = "AUTHORIZATION";
    public static final String AUTH_TYPE_JWT = "jwt";

    private final OkHttpClient client;
    private final String authToken;

    private final Gson gson = new Gson();
	private final HttpUrl engagementEndpoint;
	private final HttpUrl impressionEndpoint;

	public AnalyticsRepositoryImpl(final OkHttpClient client, StreamAnalyticsAuth analyticsAuth) {
        this.client = client;
        this.authToken = analyticsAuth.getAuthToken();
        this.engagementEndpoint = HttpUrl.parse(BASE_ENDPOINT).
                newBuilder().addPathSegment(ENGAGEMENT_PATH_PARAM).addQueryParameter(PARAM_API_KEY,
                analyticsAuth.getApiKey()).build();
		this.impressionEndpoint = HttpUrl.parse(BASE_ENDPOINT).
				newBuilder().addPathSegment(IMPRESSION_PATH_PARAM).addQueryParameter(PARAM_API_KEY,
                analyticsAuth.getApiKey()).build();
	}

    @Override
    public void handleActionEngagement(String jsonPayload) {
        performVoidCall(new Request.Builder()
                .url(engagementEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, jsonPayload))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void handleActionEngagement(Engagement engagement) {
        performVoidCall(new Request.Builder()
                .url(engagementEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(engagement)))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void handleActionImpression(String jsonPayload) {
        performVoidCall(new Request.Builder()
                .url(impressionEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, jsonPayload))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void handleActionImpression(Impression impression) {
        performVoidCall(new Request.Builder()
                .url(impressionEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(impression)))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public OkHttpClient getClient() {
        return client;
    }

    public void performVoidCall(Request request) {
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            Log.e(TAG, "Error sending analytics events to Stream.io.", e);
        }
    }
}
