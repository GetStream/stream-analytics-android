package io.getstream.analytics.beans;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Engagement {

    @SerializedName("foreign_id") private final String foreignId;
    @SerializedName("feed_id") private final String feedId;
    private final String label;
    private final Double score;
    @SerializedName("user_id") private final String userId;
    private final Integer boost;
    private final String location;
    private final String position;
    @SerializedName("extra_data") private final Map<String, Object> extraData;
    @SerializedName("features") private final List<Pair<String, String>> features;


    private Engagement(final String foreignId,
                       final String feedId,
                       final String label,
                       final Double score,
                       final String userId,
                       Integer boost,
                       String location,
                       String position,
                       Map<String, Object> extraData,
                       List<Pair<String, String>> features) {
        this.foreignId = foreignId;
        this.feedId = feedId;
        this.label = label;
        this.score = score;
        this.userId = userId;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.extraData = extraData;
        this.features = features;
    }

    public static class EventBuilder {

        private String foreignId;
        private String feedId;
        private String label;
        private Double score;
        private String userId;
        private Integer boost;
        private String location;
        private String position;
        private Map<String, Object> extraData;
        private List<Pair<String, String>> features;

        public EventBuilder withForeignId(final String foreignId) {
            this.foreignId = foreignId;
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

        public EventBuilder withExtraData(final Map<String, Object> extraData) {
            this.extraData = extraData;
            return this;
        }

        public EventBuilder withFeatures(final List<Pair<String, String>> features) {
            this.features = features;
            return this;
        }

        public Engagement build() {
            return new Engagement(foreignId, feedId, label, score, userId, boost, location, position, extraData, features);
        }
    }
}
