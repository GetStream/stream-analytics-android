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

	public static StreamAnalytics getInstance() throws IllegalStateException {
		if (instance == null) {
			throw new IllegalStateException("No instance created yet - Please initialize with StreamAnalytics.getInstance(StreamAnalyticsAuth auth) first");
		}
		return instance;
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

	public void setUser(String userId, String alias) {
		this.setUserId(userId);
		this.setAlias(alias);
	}

	public void setUserId(String userId) {
		this.repository.setUserId(userId);
	}

	public void setAlias(String alias) {
		this.repository.setAlias(alias);
	}

	public String getUserId() {
		return this.repository.getUserId();
	}

	public void setDebug(boolean debug) {
		this.repository.setDebug(debug);
	}

	public void send(Engagement engagement) {
		repository.sendEngagement(engagement);
	}

	public void send(Impression impression) {
		repository.sendImpression(impression);
	}

	// Used by service
	public void handleActionEngagement(String jsonPayload) {
		repository.sendEngagement(jsonPayload);
	}

	// Used by service
	public void handleActionImpression(String jsonPayload) {
		repository.sendImpression(jsonPayload);
	}
}
