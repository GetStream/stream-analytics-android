package io.getstream.analytics.beans;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Impression {

    @SerializedName("foreign_ids") private final String[] foreignIds;
    @SerializedName("feed_id") private final String feedId;
    @SerializedName("user_id") private final String userId;
    private final Integer boost;
    private final String location;
    private final String position;
    @SerializedName("extra_data") private final Map<String, Object> extraData;
    @SerializedName("features") private final List<Pair<String, String>> features;

    private Impression(String[] foreignIds,
                       String feedId,
                       String userId,
                       Integer boost,
                       String location,
                       String position,
                       Map<String, Object> extraData,
                       List<Pair<String, String>> features) {
        this.foreignIds = foreignIds;
        this.feedId = feedId;
        this.userId = userId;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.extraData = extraData;
        this.features = features;
    }

    public static class EventBuilder {

        private String[] foreignIds;
        private String feedId;
        private String userId;
        private Integer boost;
        private String location;
        private String position;
        private Map<String, Object> extraData;
        private List<Pair<String, String>> features;

        public EventBuilder withForeignIds(final String... foreignIds) {
            this.foreignIds = foreignIds;
            return this;
        }

        public EventBuilder withFeedId(final String feedId) {
            this.feedId = feedId;
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

        public Impression build() {
            return new Impression(foreignIds, feedId, userId, boost, location, position, extraData, features);
        }
    }
}
