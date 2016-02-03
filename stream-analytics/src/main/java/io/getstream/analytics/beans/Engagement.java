package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.getstream.analytics.service.StreamAnalytics;

public class Engagement {

    private final ContentElement content;

    @SerializedName("feed_id")
    private final String feedId;

    private final String label;
    private final Double score;

    @SerializedName("user_id")
    private final String userId;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private Engagement(final ContentElement content,
                       final String feedId,
                       final String label,
                       final Double score,
                       final String userId,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features) {
        this.content = content;
        this.feedId = feedId;
        this.label = label;
        this.score = score;
        this.userId = userId;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.features = features;
    }

    public static class EventBuilder {

        private String content;
        private String feedId;
        private String label;
        private Double score;
        private String userId;
        private Integer boost;
        private String location;
        private String position;
        private List<Feature> features;

        public EventBuilder withForeignId(final String foreignId) {
            this.content = foreignId;
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

        public EventBuilder withUserId(final String userId) {
            this.userId = userId;
            return this;
        }

        public EventBuilder withBoost(final Integer boost) {
            this.boost = boost;
            return this;
        }

        public EventBuilder withLocation(final String location) {
            this.location = location;
            return this;
        }

        public EventBuilder withPosition(final String position) {
            this.position = position;
            return this;
        }

        public EventBuilder withFeatures(final List<Feature> features) {
            this.features = features;
            return this;
        }

        public Engagement build() {
            return new Engagement(new ContentElement(content), feedId, label, score, userId != null ? userId : StreamAnalytics.getInstance().getUserId(),
                    boost, location, position, features);
        }
    }

    protected static class ContentElement {
        @SerializedName("foreign_id")
        private final String foreignId;

        public ContentElement(String foreignId) {
            this.foreignId = foreignId;
        }
    }
}
