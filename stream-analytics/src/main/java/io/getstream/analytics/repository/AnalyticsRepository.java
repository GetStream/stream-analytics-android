package io.getstream.analytics.repository;

import com.squareup.okhttp.OkHttpClient;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;

/**
 * REST interface to Stream server API.
 */
public interface AnalyticsRepository {

	void setDebug(boolean debug);

	void sendEngagement(String jsonPayload);
	void sendEngagement(Engagement engagement);
	void sendImpression(String jsonPayload);
	void sendImpression(Impression impression);

	OkHttpClient getClient();
}
