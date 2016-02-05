/**

 Copyright (c) 2015, Stream.io Inc.
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those
 of the authors and should not be interpreted as representing official policies,
 either expressed or implied, of the FreeBSD Project.

 */
package io.getstream.analytics.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Stream Analytics can track two types of events:
 * <ul>
 *     <li>{@link Impression}</li>
 *     <li>{@link Engagement}</li>
 * </ul>
 * By using {@link Impression} you can track which activities are shown to the user.
 * <p>
 * In order to create a new Impression you can use the {@link io.getstream.analytics.beans.Impression.EventBuilder} as shown below:
 * <pre>
 * Impression impression = new Impression.EventBuilder()
 *    .withFeedId("flat:tommaso")
 *    .build();
 * </pre>
 * </p>
 */
public class Impression {

    @SerializedName("content_list")
    private final List<Map<String, Serializable>> contentList;

    @SerializedName("feed_id")
    private final String feedId;

    @SerializedName("user_data")
    private UserData userData;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private Impression(UserData userData,
                       List<Map<String, Serializable>> contentList,
                       String feedId,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features) {
        this.userData = userData;
        this.contentList = contentList;
        this.feedId = feedId;
        this.boost = boost;
        this.location = location;
        this.position = position;
        this.features = features;
    }

    /**
     * Set user id and alias
     * @param userData User data
     */
    public void setUser(UserData userData) {
        this.userData = userData;
    }

    /**
     * Get the user data
     * @return User data
     */
    public UserData getUser() {
        return userData;
    }

    /**
     * Build a new {@link Impression}.
     */
    public static class EventBuilder {

        private List<Map<String, Serializable>> contentList = new ArrayList<>();
        private String feedId;
        private Integer boost;
        private String location;
        private String position;
        private List<Feature> features;
        private UserData userData;

        /**
         * Add new {@link Content}s to the event.
         * @param contents Contents to add to the event
         * @return The same builder
         */
        public EventBuilder withContentList(final Content... contents) {
            for (Content contentElement : contents) {
                contentList.add(contentElement.getContent());
            }
            return this;
        }

        /**
         * Add a whole list of {@link Content}s to the event.
         * The existing entry will not be overridden by this operation, elements contained in the list will be
         * added right after the existing ones.
         * @param contents A list of {@link Content} to add to the event
         * @return The same builder
         */
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

        public EventBuilder withUserId(final String id) {
            this.userData = new UserData(id);
            return this;
        }

        public EventBuilder withUser(final String id, final String alias) {
            this.userData = new UserData(id, alias);
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

        /**
         * Add as many {@link Feature}s as you want to the events.
         * @param features Feature(s) to add.
         * @return The same builder
         */
        public EventBuilder withFeature(final Feature... features) {
            this.withFeatures(Arrays.asList(features));
            return this;
        }

        /**
         * Add a list of {@link Feature}s to the event.
         * @param features
         * @return The same builder
         */
        public EventBuilder withFeatures(final List<Feature> features) {
            if (this.features == null) {
                this.features = features;
            } else {
                this.features.addAll(features);
            }
            return this;
        }

        /**
         * Build the {@link Impression}.
         * @return An immutable {@link Impression}
         */
        public Impression build() {
            return new Impression(userData,
                    contentList == null || contentList.size() == 0 ? null : Collections.unmodifiableList(contentList),
                    feedId,
                    boost,
                    location,
                    position,
                    features);
        }
    }
}
