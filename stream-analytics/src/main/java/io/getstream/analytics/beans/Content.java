/**

 Copyright (c) 2016, Stream.io Inc.
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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of Stream Analytics is to track every event which you think is a good
 * indicator of a user being interested in a certain bit of content.
 * The {@link Content} object is designed to collect custom data related to the
 * content you want to track using Stream Analytics.<br/>
 * In order to create a new Content object you can use the {@link io.getstream.analytics.beans.Content.ContentBuilder}
 * which implements a fluid pattern to make your life easier.<br/>
 * Example:</br>
 * <pre>
 *   new Content.ContentBuilder()
 *     .withForeignId("message:34349698") // mandatory
 *     .withAttribute("verb", "share")
 *     .withAttribute("actor", new ContentAttribute("1", "user1"))
 *     .build()
 * </pre>
 * ForeignId is a mandatory field. If not set you will get a {@link RuntimeException}.
 */
public class Content {

    private final Map<String, Serializable> content;

    private Content(Map<String, Serializable> content) {
        this.content = content;
    }

    /**
     * Return a key/value representation of the data stored into the Content object.
     * @return Immutable K/V of the stored data.
     */
    public Map<String, Serializable> getContent() {
        return content;
    }

    /**
     * Builder for {@link Content} object.
     */
    public static class ContentBuilder {

        private String foreignId;
        private String label;
        private Map<String, Serializable> content = new HashMap<>();

        /**
         * This is a mandatory field. Add a "foreign_id" to the Content.
         * @param id Id of the content you are referring to.
         * @return The same builder
         */
        public ContentBuilder withForeignId(final String id) {
            this.foreignId = id;
            return this;
        }

        /**
         * Add a "label" field.
         * @param label Label
         * @return The same builder
         */
        public ContentBuilder withLabel(final String label) {
            this.label = label;
            return this;
        }

        /**
         * Add a custom attribute to the {@link Content}.
         * @param name Name of the attribute
         * @param value Value of the attribute
         * @return The same builder
         */
        public ContentBuilder withAttribute(final String name, final String value) {
            this.content.put(name, value);
            return this;
        }

        /**
         * Add a custom attribute to the {@link Content}.
         * @param name Name of the attribute
         * @param value Value of the attribute
         * @return The same builder
         */
        public ContentBuilder withAttribute(final String name, final int value) {
            this.content.put(name, value);
            return this;
        }

        /**
         * Add a custom attribute to the {@link Content}.
         * @param name Name of the attribute
         * @param value Value of the attribute
         * @return The same builder
         */
        public ContentBuilder withAttribute(final String name, final long value) {
            this.content.put(name, value);
            return this;
        }

        /**
         * Add a custom attribute to the {@link Content}.
         * @param name Name of the attribute
         * @param value Value of the attribute
         * @return The same builder
         */
        public ContentBuilder withAttribute(final String name, final boolean value) {
            this.content.put(name, value);
            return this;
        }

        /**
         * Add a custom {@link ContentAttribute} to the {@link Content}.
         * @param name Name of the attribute
         * @param attribute A custom {@link ContentAttribute}
         * @return The same builder
         */
        public ContentBuilder withAttribute(final String name, final ContentAttribute attribute) {
            this.content.put(name, attribute);
            return this;
        }

        /**
         * Create a {@link Content} object.
         * @return An instance of the Content object.
         */
        public Content build() {
            if (null == foreignId) {
                throw new RuntimeException("foreign_id cannot be null");
            }
            content.put("foreign_id", foreignId);
            if (null != label) {
                content.put("label", label);
            }
            return new Content(Collections.unmodifiableMap(content));
        }
    }
}
