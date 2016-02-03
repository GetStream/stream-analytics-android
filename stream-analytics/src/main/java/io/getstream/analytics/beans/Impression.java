package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.getstream.analytics.service.StreamAnalytics;

public class Impression {

    @SerializedName("content_list")
    private final List<ContentElement> contentList;

    @SerializedName("feed_id")
    private final String feedId;

    @SerializedName("user_id")
    private final String userId;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private Impression(List<ContentElement> contentList,
                       String feedId,
                       String userId,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features) {
        this.contentList = contentList;
        this.feedId = feedId;
        this.userId = userId != null ? userId : StreamAnalytics.getInstance().getUserId();
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.features = features;
    }

    public static class EventBuilder {

        private List<String> contentList;
        private String feedId;
        private String userId;
        private Integer boost;
        private String location;
        private String position;
        private List<Feature> features;

        public EventBuilder withContentList(final String... foreignIds) {
            this.contentList = Arrays.asList(foreignIds);
            return this;
        }

        public EventBuilder withContentList(final List<String> foreignIds) {
            this.contentList = foreignIds;
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

        public EventBuilder withFeatures(final List<Feature> features) {
            this.features = features;
            return this;
        }

        public Impression build() {
            List<ContentElement> elements = new ArrayList<>();
            for (String foreignId : contentList) {
                elements.add(new ContentElement(foreignId));
            }
            return new Impression(elements, feedId, userId, boost, location, position, features);
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
