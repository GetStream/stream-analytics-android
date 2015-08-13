package io.getstream.analytics.beans;

public class Impression {

    private String[] activityIds;
    private String feedId;

    private Impression(String[] activityIds, String feedId) {
        this.activityIds = activityIds;
        this.feedId = feedId;
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
            return new Impression(activityIds, feedId);
        }
    }
}
