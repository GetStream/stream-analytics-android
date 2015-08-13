package io.getstream.analytics.service;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import io.getstream.analytics.beans.Engagement;
import io.getstream.analytics.beans.Impression;
import io.getstream.analytics.config.StreamAnalyticsAuth;
import io.getstream.analytics.config.StreamAnalyticsClient;
import io.getstream.analytics.repository.AnalyticsRepository;
import io.getstream.analytics.repository.AnalyticsRepositoryImpl;

/**
 * Send out analytic events to the Stream service.
 */
public class StreamAnalyticsService extends IntentService {

    public static final String TAG = StreamAnalyticsService.class.getSimpleName();

    /**
     * Actions.
     */
    public static final String ACTION_ENGAGEMENT = "io.getstream.analytics.service.ENGAGEMENT";
	public static final String ACTION_IMPRESSION = "io.getstream.analytics.service.IMPRESSION";
    public static final String EXTRA_PAYLOAD = "io.getstream.analytics.service.extra.PAYLOAD";

    private static final Gson GSON = new Gson();

    private AnalyticsRepository analyticsRepository;

    public StreamAnalyticsService() {
        super("StreamAnalyticsService");
	}

    /**
     * Utility method to send a new Engagement action using an {@link android.app.IntentService}.
     * @param context Context of the call.
     * @param engagement Engagement object to send out
     */
    public static void sendEngagement(Context context, Engagement engagement) {
        Intent intent = new Intent(context, StreamAnalyticsService.class);
        intent.setAction(ACTION_ENGAGEMENT);
        intent.putExtra(EXTRA_PAYLOAD, GSON.toJson(engagement));
        context.startService(intent);
    }

    /**
     * Utility method to send a new Impression action using an {@link android.app.IntentService}.
     * @param context Context of the call.
     * @param impression Impression object to send out
     */
    public static void sendImpression(Context context, Impression impression) {
        Intent intent = new Intent(context, StreamAnalyticsService.class);
        intent.setAction(ACTION_ENGAGEMENT);
        intent.putExtra(EXTRA_PAYLOAD, GSON.toJson(impression));
        context.startService(intent);
    }

	@Override
	public void onCreate() {
		super.onCreate();
		if (null == analyticsRepository) {
            StreamAnalyticsAuth streamAnalyticsAuth = getStreamAnalyticsAuth();
            if (null == streamAnalyticsAuth) {
                throw new RuntimeException("Cannot retrieve Stream.io analytics settings");
            }
            analyticsRepository = new AnalyticsRepositoryImpl(StreamAnalyticsClient.newClient(),
                    streamAnalyticsAuth);
        }
	}

	@Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ENGAGEMENT.equals(action)) {
                analyticsRepository.handleActionEngagement(intent.getStringExtra(EXTRA_PAYLOAD));
            } else if (ACTION_IMPRESSION.equals(action)) {
                analyticsRepository.handleActionImpression(intent.getStringExtra(EXTRA_PAYLOAD));
            }
        }
    }

    private StreamAnalyticsAuth getStreamAnalyticsAuth() {
		try {
			final Bundle data = getPackageManager().getServiceInfo(new ComponentName(this,
							StreamAnalyticsService.class), PackageManager.GET_META_DATA).metaData;
			return new StreamAnalyticsAuth(data.getString(StreamAnalyticsAuth.PARAM_API_KEY),
					data.getString(StreamAnalyticsAuth.PARAM_AUTH_TOKEN));
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
    }

    public void setAnalyticsRepository(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }
}