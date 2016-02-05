package io.getstream.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import io.getstream.analytics.beans.Content;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Feature;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.service.StreamAnalytics;
import io.getstream.analytics.service.StreamAnalyticsImpl;

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
        mStreamAnalytics = StreamAnalyticsImpl.getInstance(auth);
        mStreamAnalytics.setUserId("486892");
        mStreamAnalytics.setDebug(BuildConfig.DEBUG);
    }

    public void trackImpression(View view) {
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature("topic", "coffee"));

        mStreamAnalytics.send(new Impression.EventBuilder()
                        .withContentList(
                                new Content.ContentBuilder()
                                        .withForeignId("message:34349698")
                                        .build(),
                                new Content.ContentBuilder()
                                        .withForeignId("message:34349699")
                                        .build()
                        )
                        .withFeedId("user:ChartMill")
                        .withBoost(1)
                        .withLocation("homepage")
                        .withPosition("1")
                        .withFeatures(features)
                        .build()
        );
    }

    public void trackEngagement(View view) {
        ArrayList<Feature> features = new ArrayList<>();
        features.add(new Feature("topic", "tea"));

        mStreamAnalytics.send(new Engagement.EventBuilder()
                        .withContent(new Content.ContentBuilder().withForeignId("message:34349698").build())
                        .withFeedId("user:ChartMill")
                        .withLabel("Engagement click")
                        .withScore(1d)
                        .withBoost(1)
                        .withLocation("homepage")
                        .withPosition("0")
                        .withFeatures(features)
                        .build()
        );
    }
}
