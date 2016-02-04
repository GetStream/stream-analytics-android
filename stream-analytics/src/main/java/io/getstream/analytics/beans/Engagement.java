package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Engagement {

    @SerializedName("feed_id")
    private final String feedId;

    private final String label;
    private final Double score;

    @SerializedName("user_data")
    private final UserData userData;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private final Map<String, Serializable> content;

    private Engagement(final UserData userData,
                       final String feedId,
                       final String label,
                       final Double score,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features,
                       final Map<String, Serializable> content) {
        this.userData = userData;
        this.feedId = feedId;
        this.label = label;
        this.score = score;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.features = features;
        this.content = content;
    }

    public static class EventBuilder {

        private UserData userData;
        private String feedId;
        private String label;
        private Double score;
        private Integer boost;
        private String location;
        private String position;
        private List<Feature> features;
        private Content content;

        public EventBuilder withUserId(final String id) {
            this.userData = new UserData(id);
            return this;
        }

        public EventBuilder withUser(final String id, final String alias) {
            this.userData = new UserData(id, alias);
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

        public EventBuilder withBoost(final Integer boost) {
            this.boost = boost;
            return this;
        }

        public EventBuilder withContent(final Content content) {
            this.content = content;
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
            return new Engagement(userData, feedId, label, score, boost, location, position, features,
                    content == null ? null : content.getContent());
        }
    }
}
