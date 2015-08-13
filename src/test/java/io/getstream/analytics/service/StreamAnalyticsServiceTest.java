package io.getstream.analytics.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.google.gson.Gson;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.config.StreamAnalyticsClient;
import io.getstream.analytics.repository.AnalyticsRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class StreamAnalyticsServiceTest {

	private final StreamAnalyticsService streamAnalyticsService;

	private static final Gson GSON = new Gson();

	@Mock
	private Context context;

	@Before
	public void setUp() {
		initMocks(this);
	}

	public StreamAnalyticsServiceTest() throws PackageManager.NameNotFoundException {
		StreamAnalyticsAuth auth = new StreamAnalyticsAuth("nfq26m3qgfyp",
				"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE");
		streamAnalyticsService = new StreamAnalyticsService();
		streamAnalyticsService.setAnalyticsRepository(new AnalyticsRepositoryImpl(StreamAnalyticsClient.newClient(), auth));
		streamAnalyticsService.onCreate();
	}

	@Test
	public void sendImpression() {
		Intent intent = new Intent(context, StreamAnalyticsService.class);
		intent.setAction(StreamAnalyticsService.ACTION_IMPRESSION);
		intent.putExtra(StreamAnalyticsService.EXTRA_PAYLOAD, GSON.toJson(
				new Impression.EventBuilder().withActivityIds("1", "2").withFeedId("1").build()
		));

		streamAnalyticsService.onHandleIntent(intent);
	}

	@Test
	public void sendEngagement() {
		Intent intent = new Intent(context, StreamAnalyticsService.class);
		intent.setAction(StreamAnalyticsService.ACTION_ENGAGEMENT);
		intent.putExtra(StreamAnalyticsService.EXTRA_PAYLOAD, GSON.toJson(
				new Engagement.EventBuilder().withActivityId("1").withFeedId("1").build()
		));

		streamAnalyticsService.onHandleIntent(intent);
	}
}