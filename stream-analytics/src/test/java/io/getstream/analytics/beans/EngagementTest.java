package io.getstream.analytics.beans;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.Serializable;

public class EngagementTest {

    private final Gson gson = new Gson();

    @Test
    public void shouldCreateImpression() {
        Engagement engagement = new Engagement.EventBuilder()
                .withUser("id", "alias")
                .withFeedId("1")
                .withContent(
                        new Content.ContentBuilder()
                                .withForeignId("message:34349698")
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build()
                )
                .build();
        System.out.println(gson.toJson(engagement));
    }

    public class Label implements Serializable {
        private String country;
        private String artist;

        public Label(String country, String artist) {
            this.country = country;
            this.artist = artist;
        }
    }
}