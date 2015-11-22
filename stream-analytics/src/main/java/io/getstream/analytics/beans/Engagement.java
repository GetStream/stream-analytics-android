package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

public class Engagement {

    @SerializedName("activity_id") private final String activityId;
    @SerializedName("feed_id") private final String feedId;
    private final String label;
    private final Double score;
    @SerializedName("user_id") private String userId;

    private Engagement(final String activityId,
                       final String feedId,
                       final String label,
                       final Double score) {
        this.activityId = activityId;
        this.feedId = feedId;
        this.label = label;
        this.score = score;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public static class EventBuilder {

        private String activityId;
        private String feedId;
        private String label;
        private Double score;

        public EventBuilder withActivityId(final String activityId) {
            this.activityId = activityId;
            return this;
        }

        public EventBuilder withFeedId(final String feedId) {
            this.feedId = feedId;
            return this;
        }

        public EventBuilder withLabel(final String label) {
            this.label = label;
            return this;
        }

        public EventBuilder withScore(final Double score) {
            this.score = score;
            return this;
        }

        public Engagement build() {
            return new Engagement(activityId, feedId, label, score);
        }
    }
}
