package io.getstream.analytics.beans;

public class Engagement {

    private final String activityId;
    private final String feedId;
    private final String label;
    private final Double score;

    private Engagement(final String activityId,
                       final String feedId,
                       final String label,
                       final Double score) {
        this.activityId = activityId;
        this.feedId = feedId;
        this.label = label;
        this.score = score;
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
