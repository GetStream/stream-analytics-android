package io.getstream.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import io.getstream.analytics.beans.Engagement;
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
        StreamAnalytics.setUserId("tracking_user");
    }

    public void trackImpression(View view) {
        mStreamAnalytics.handleActionImpression(new Impression.EventBuilder()
                        .withActivityIds(new String[]{"id_1", "id_2"})
                        .withFeedId("feed_1")
                        .build()
        );
    }

    public void trackEngagement(View view) {
        mStreamAnalytics.handleActionEngagement(new Engagement.EventBuilder()
                        .withActivityId("id_1")
                        .withFeedId("feed_1")
                        .withLabel("Engagement click")
                        .withScore(1d)
                        .build()
        );
    }
}
