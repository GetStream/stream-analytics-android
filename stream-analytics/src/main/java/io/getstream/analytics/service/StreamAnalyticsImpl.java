package io.getstream.analytics.service;

import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.beans.UserData;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.config.StreamAnalyticsClient;
import io.getstream.analytics.repository.AnalyticsRepositoryImpl;

public final class StreamAnalyticsImpl implements StreamAnalytics {

    private static StreamAnalytics instance;

    private final AnalyticsRepositoryImpl repository;
    private String userId;
    private String alias;

    private StreamAnalyticsImpl(StreamAnalyticsAuth auth) {
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
                    instance = new StreamAnalyticsImpl(auth);
                }
            }
        }
        return instance;
    }

    @Override
    public void setUser(String userId, String alias) {
        this.userId = userId;
        this.alias = alias;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setDebug(boolean debug) {
        this.repository.setDebug(debug);
    }

    @Override
    public void send(Engagement engagement) {
        if (null == engagement.getUser()) {
            engagement.setUser(new UserData(userId, alias));
        }
        repository.sendEngagement(engagement);
    }

    @Override
    public void send(Impression impression) {
        if (null == impression.getUser()) {
            impression.setUser(new UserData(userId, alias));
        }
        repository.sendImpression(impression);
    }

    public void handleActionEngagement(String jsonPayload) {
        repository.sendEngagement(jsonPayload);
    }

    public void handleActionImpression(String jsonPayload) {
        repository.sendImpression(jsonPayload);
    }
}
