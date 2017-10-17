/**

 Copyright (c) 2015, Stream.io Inc.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those
 of the authors and should not be interpreted as representing official policies,
 either expressed or implied, of the FreeBSD Project.

 */
package io.getstream.analytics.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Arrays;

import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;

/**
 * Implementation of the REST client for Stream Analytics service.
 */
public class AnalyticsRepositoryImpl implements AnalyticsRepository {

    public static final String TAG = AnalyticsRepositoryImpl.class.getSimpleName();

	public static final String BASE_ENDPOINT = "https://analytics.stream-io-api.com/analytics/v1.0/";

    public static final String IMPRESSION_PATH_PARAM = "impression/";
	public static final String ENGAGEMENT_PATH_PARAM = "engagement/";

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String PARAM_API_KEY = "api_key";
    public static final String HEADER_STREAM_AUTH_TYPE = "STREAM-AUTH-TYPE";
    public static final String HEADER_AUTHORIZATION = "AUTHORIZATION";
    public static final String AUTH_TYPE_JWT = "jwt";

    private final OkHttpClient client;
    private final String authToken;

    private final Gson gson = new Gson();
	private final HttpUrl engagementEndpoint;
	private final HttpUrl impressionEndpoint;

    private boolean debug = false;

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
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void sendEngagement(String jsonPayload) {
        performVoidCall(new Request.Builder()
                .url(engagementEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, jsonPayload))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void sendEngagement(Engagement engagement) {
        if (debug) {
            Log.d(TAG, "-------");
            Log.d(TAG, "Generated json");
            Log.d(TAG, gson.toJson(engagement));
            Log.d(TAG, "-------");
        }

        performVoidCall(new Request.Builder()
                .url(engagementEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(engagement)))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void sendImpression(String jsonPayload) {
        performVoidCall(new Request.Builder()
                .url(impressionEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, jsonPayload))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    @Override
    public void sendImpression(Impression impression) {
        if (debug) {
            Log.d(TAG, "-------");
            Log.d(TAG, "Generated json");
            Log.d(TAG, gson.toJson(impression));
            Log.d(TAG, "-------");
        }

        performVoidCall(new Request.Builder()
                .url(impressionEndpoint)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(impression)))
                .addHeader(HEADER_STREAM_AUTH_TYPE, AUTH_TYPE_JWT)
                .addHeader(HEADER_AUTHORIZATION, authToken)
                .build());
    }

    public void performVoidCall(Request request) {
        if (debug) {
            Log.d(TAG, "Request: " + request);
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (debug) {
                    Log.d(TAG, "-------");
                    Log.d(TAG, "Failure: " + request);
                    Log.d(TAG, "IOException: " + Arrays.toString(e.getStackTrace()));
                    Log.d(TAG, "-------");
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (debug) {
                    Log.d(TAG, "-------");
                    Log.d(TAG, "Response: " + response);
                    Log.d(TAG, "Body: " + response.body().string());
                    Log.d(TAG, "-------");
                }
            }
        });
    }
}
