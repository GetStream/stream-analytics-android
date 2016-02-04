package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.getstream.analytics.service.StreamAnalytics;

public class Impression {

    @SerializedName("content_list")
    private final List<Map<String, Serializable>> contentList;

    @SerializedName("feed_id")
    private final String feedId;

    @SerializedName("user_id")
    private final String userId;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private Impression(List<Map<String, Serializable>> contentList,
                       String feedId,
                       String userId,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features) {
        this.contentList = contentList;
        this.feedId = feedId;
        this.userId = userId;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.features = features;
    }

    public static class EventBuilder {

        private List<Map<String, Serializable>> contentList = new ArrayList<>();
        private String feedId;
        private String userId;
        private Integer boost;
        private String location;
        private String position;
        private List<Feature> features;

        public EventBuilder withContentList(final Content... contents) {
            for (Content contentElement : contents) {
                contentList.add(contentElement.getContent());
            }
            return this;
        }

        public EventBuilder withContentList(final List<Content> contents) {
            for (Content contentElement : contents) {
                contentList.add(contentElement.getContent());
            }
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

        public EventBuilder withDefaultUserId() {
            this.userId = StreamAnalytics.getInstance().getUserId();
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

        public Impression build() {
            return new Impression(contentList, feedId, userId, boost, location, position, features);
        }
    }
}
