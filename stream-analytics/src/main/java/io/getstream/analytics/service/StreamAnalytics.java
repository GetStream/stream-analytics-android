package io.getstream.analytics.service;

import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.config.StreamAnalyticsClient;
import io.getstream.analytics.repository.AnalyticsRepositoryImpl;

public final class StreamAnalytics {

	private static StreamAnalytics instance;
	private static String userId = null;

	private final AnalyticsRepositoryImpl repository;

	private StreamAnalytics(StreamAnalyticsAuth auth) {
		this.repository = new AnalyticsRepositoryImpl(new StreamAnalyticsClient().newClient(), auth);
	}

	public static StreamAnalytics getInstance(StreamAnalyticsAuth auth) {
		if (instance == null) {
			synchronized(StreamAnalytics.class) {
				if (instance == null) {
					instance = new StreamAnalytics(auth);
					userId = auth.getApiKey();
				}
			}
		}
		return instance;
	}

	public static void setUserId(String userId) {
		StreamAnalytics.userId = userId;
	}

	public void handleActionEngagement(String jsonPayload) {
		repository.sendEngagement(jsonPayload);
	}

	public void handleActionEngagement(Engagement engagement) {
		engagement.setUserId(userId);
		repository.sendEngagement(engagement);
	}

	public void handleActionImpression(String jsonPayload) {
		repository.sendImpression(jsonPayload);
	}

	public void handleActionImpression(Impression impression) {
		repository.sendImpression(impression);
	}
}
