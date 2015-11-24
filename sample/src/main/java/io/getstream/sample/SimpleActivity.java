package io.getstream.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Feature;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.service.StreamAnalytics;

public class SimpleActivity extends Activity {
    StreamAnalytics mStreamAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        StreamAnalyticsAuth auth = new StreamAnalyticsAuth(
                getString(R.string.auth_api_key),
                getString(R.string.auth_api_token)
        );
        mStreamAnalytics = StreamAnalytics.getInstance(auth);
        mStreamAnalytics.setDebug(BuildConfig.DEBUG);
    }

    public void trackImpression(View view) {
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature("hello", "world"));
        features.add(new Feature("more", "streams"));

        mStreamAnalytics.send(new Impression.EventBuilder()
                        .withForeignIds(new String[]{"message:34349698", "message:34349699"})
                        .withFeedId("user:ChartMill")
                        .withUserId("tom")
                        .withBoost(1)
                        .withLocation("Amsterdam")
                        .withPosition(SimpleActivity.class.getSimpleName())
                        .withFeatures(features)
                        .build()
        );
    }

    public void trackEngagement(View view) {
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature("hello", "world"));
        features.add(new Feature("more", "streams"));

        mStreamAnalytics.send(new Engagement.EventBuilder()
                        .withForeignId("message:34349698")
                        .withFeedId("user:ChartMill")
                        .withLabel("Engagement click")
                        .withUserId("tom")
                        .withScore(1d)
                        .withBoost(1)
                        .withLocation("Amsterdam")
                        .withPosition(SimpleActivity.class.getSimpleName())
                        .withFeatures(features)
                        .build()
        );
    }
}
