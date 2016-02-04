package io.getstream.analytics.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Content {

    private final Map<String, Serializable> content;

    private Content(Map<String, Serializable> content) {
        this.content = content;
    }

    public Map<String, Serializable> getContent() {
        return content;
    }

    public static class ContentBuilder {

        private String foreignId;
        private String label;
        private Map<String, Serializable> content = new HashMap<>();

        public ContentBuilder withForeignId(final String id) {
            this.foreignId = id;
            return this;
        }

        public ContentBuilder withLabel(final String label) {
            this.label = label;
            return this;
        }

        public ContentBuilder withAttribute(final String name, final String value) {
            this.content.put(name, value);
            return this;
        }

        public ContentBuilder withAttribute(final String name, final int value) {
            this.content.put(name, value);
            return this;
        }

        public ContentBuilder withAttribute(final String name, final long value) {
            this.content.put(name, value);
            return this;
        }

        public ContentBuilder withAttribute(final String name, final boolean value) {
            this.content.put(name, value);
            return this;
        }

        public ContentBuilder withAttribute(final String name, final ContentAttribute attribute) {
            this.content.put(name, attribute);
            return this;
        }

        public Content build() {
            if (null == foreignId) {
                throw new RuntimeException("foreign_id cannot be null");
            }
            content.put("foreign_id", foreignId);
            if (null != label) {
                content.put("label", label);
            }
            return new Content(content);
        }
    }

}
