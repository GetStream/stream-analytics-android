package io.getstream.analytics.repository;

import com.squareup.okhttp.OkHttpClient;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;

/**
 * REST interface to Stream server API.
 */
public interface AnalyticsRepository {

    void handleActionEngagement(String jsonPayload);

    void handleActionEngagement(Engagement engagement);

    void handleActionImpression(String jsonPayload);

    void handleActionImpression(Impression impression);

    OkHttpClient getClient();
}
