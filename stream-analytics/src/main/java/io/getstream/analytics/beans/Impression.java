package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

public class Impression {

    @SerializedName("foreign_ids") private String[] activityIds;
    @SerializedName("feed_id") private String feedId;
    @SerializedName("user_id") private String userId;

    private Impression(String[] activityIds, String feedId, String userId) {
        this.activityIds = activityIds;
        this.feedId = feedId;
        this.userId = userId;
    }

    public static class EventBuilder {

        private String[] activityIds;
        private String feedId;

        public EventBuilder withActivityIds(final String... activityIds) {
            this.activityIds = activityIds;
            return this;
        }

        public EventBuilder withFeedId(final String feedId) {
            this.feedId = feedId;
            return this;
        }

        public Impression build() {
            return new Impression(activityIds, feedId, "8r9brv6v4xj5");
        }
    }
}
