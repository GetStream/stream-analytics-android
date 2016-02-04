package io.getstream.analytics.beans;

import com.google.gson.Gson;

import org.junit.Test;

public class ImpressionTest {

    private final Gson gson = new Gson();

    @Test
    public void shouldCreateImpression() {
        Impression impression = new Impression.EventBuilder()
                .withContentList(
                        new Content.ContentBuilder()
                                .withForeignId("message:34349698")
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("message:34349699")
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("message:34349610")
                                .build()
                )
                .withFeedId("1")
                .build();
        System.out.println(gson.toJson(impression));
    }

    @Test
    public void shouldCreateImpressionWithoutContent() {
        Impression impression = new Impression.EventBuilder()
                .withFeedId("1")
                .build();
        System.out.println(gson.toJson(impression));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailCreatingImpression() {
        Impression impression = new Impression.EventBuilder()
                .withContentList(
                        new Content.ContentBuilder()
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("message:34349699")
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("message:34349610")
                                .build()
                )
                .withFeedId("1")
                .build();
        System.out.println(gson.toJson(impression));
    }
}