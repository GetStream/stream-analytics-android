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
import java.util.List;
import java.util.Map;

public class Engagement {

    @SerializedName("feed_id")
    private final String feedId;

    private final String label;
    private final Double score;

    @SerializedName("user_data")
    private UserData userData;

    private final Integer boost;
    private final String location;
    private final String position;

    @SerializedName("features")
    private final List<Feature> features;

    private final Map<String, Serializable> content;

    private Engagement(UserData userData,
                       String feedId,
                       String label,
                       Double score,
                       Integer boost,
                       String location,
                       String position,
                       List<Feature> features,
                       Map<String, Serializable> content) {
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

        public EventBuilder withFeatures(final Feature feature) {
            if (this.features == null) {
                this.features = new ArrayList<>();
            }
            this.features.add(feature);
            return this;
        }

        public EventBuilder withFeatures(final List<Feature> features) {
            if (this.features == null) {
                this.features = features;
            } else {
                this.features.addAll(features);
            }
            return this;
        }

        public Engagement build() {
            return new Engagement(userData, feedId, label, score, boost, location, position, features,
                    content == null ? null : content.getContent());
        }
    }
}
