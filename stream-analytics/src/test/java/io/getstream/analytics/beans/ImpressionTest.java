package io.getstream.analytics.beans;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImpressionTest {

    private final Gson gson = new Gson();

    @Test
    public void shouldCreateImpression() {
        Impression impression = new Impression.EventBuilder()
                .withContentList(
                        new Content.ContentBuilder()
                                .withForeignId("tweet:34349698")
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("tweet:34349699")
                                .build(),
                        new Content.ContentBuilder()
                                .withForeignId("tweet:34349610")
                                .build()
                )
                .withFeedId("flat:tommaso")
                .withLocation("android-app")
                .build();
        String resultString = gson.toJson(impression);
        assertTrue(resultString.contains("flat:tommaso"));
        assertTrue(resultString.contains("content_list"));
        assertTrue(resultString.contains("tweet:34349699"));
        assertTrue(resultString.contains("tweet:34349610"));
    }

    @Test
    public void shouldCreateImpressionWithoutContent() {
        Impression impression = new Impression.EventBuilder()
                .withFeedId("flat:tommaso")
                .withUser("id1", "alias")
                .build();
        String resultString = gson.toJson(impression);
        assertTrue(resultString.contains("user_data"));
        assertTrue(resultString.contains("id1"));
        assertTrue(resultString.contains("alias"));
        assertFalse(resultString.contains("content"));
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
    }
}