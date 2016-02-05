/**

 Copyright (c) 2015, Stream.io Inc.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those
 of the authors and should not be interpreted as representing official policies,
 either expressed or implied, of the FreeBSD Project.

 */
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

    private StreamAnalyticsImpl analytics;
	private StreamAnalyticsAuth streamAnalyticsAuth;

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
		if (null == analytics) {
            StreamAnalyticsAuth streamAnalyticsAuth = getAnalyticsAuth();
            if (null == streamAnalyticsAuth) {
                throw new RuntimeException("Cannot retrieve Stream.io analytics settings");
            }
			analytics = (StreamAnalyticsImpl) StreamAnalyticsImpl.getInstance(streamAnalyticsAuth);
        }
	}

	@Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ENGAGEMENT.equals(action)) {
				analytics.handleActionEngagement(intent.getStringExtra(EXTRA_PAYLOAD));
            } else if (ACTION_IMPRESSION.equals(action)) {
				analytics.handleActionImpression(intent.getStringExtra(EXTRA_PAYLOAD));
            }
        }
    }

    private StreamAnalyticsAuth getAnalyticsAuth() {
		if (null != this.streamAnalyticsAuth) {
			return this.streamAnalyticsAuth;
		}

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

	public void setStreamAnalyticsAuth(StreamAnalyticsAuth streamAnalyticsAuth) {
		this.streamAnalyticsAuth = streamAnalyticsAuth;
	}
}