package io.getstream.analytics.service;

import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.config.StreamAnalyticsClient;
import io.getstream.analytics.repository.AnalyticsRepositoryImpl;

public final class StreamAnalytics {

	private static StreamAnalytics instance;

	private final AnalyticsRepositoryImpl repository;

	private StreamAnalytics(StreamAnalyticsAuth auth) {
		this.repository = new AnalyticsRepositoryImpl(new StreamAnalyticsClient().newClient(), auth);
	}

	public static StreamAnalytics getInstance(StreamAnalyticsAuth auth) {
		if (instance == null) {
			synchronized(StreamAnalytics.class) {
				if (instance == null) {
					instance = new StreamAnalytics(auth);
				}
			}
		}
		return instance;
	}

	public void send(Engagement engagement) {
		repository.sendEngagement(engagement);
	}

	public void send(Impression impression) {
		repository.sendImpression(impression);
	}

	public void handleActionEngagement(String jsonPayload) {
		repository.sendEngagement(jsonPayload);
	}

	public void handleActionEngagement(Engagement engagement) {
		repository.sendEngagement(engagement);
	}

	public void handleActionImpression(String jsonPayload) {
		repository.sendImpression(jsonPayload);
	}

	public void handleActionImpression(Impression impression) {
		repository.sendImpression(impression);
	}
}
