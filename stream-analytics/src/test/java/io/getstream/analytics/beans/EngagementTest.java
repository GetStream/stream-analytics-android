package io.getstream.analytics.beans;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class EngagementTest {

    private final Gson gson = new Gson();

    @Test
    public void shouldCreateEngagement() {
        Engagement engagement = new Engagement.EventBuilder()
                .withUser("id", "alias")
                .withFeedId("user:thierry")
                .withContent(
                        new Content.ContentBuilder()
                                .withForeignId("message:34349698")
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build()
                )
                .withBoost(2)
                .withLocation("profile_page")
                .withPosition("3")
                .withFeatures(new Feature("profile", "rock"))
                .withFeatures(Arrays.asList(new Feature("theme", "black")))
                .build();
        String resultString = gson.toJson(engagement);
        System.out.println(resultString);
        assertTrue(resultString.contains("content"));
        assertTrue(resultString.contains("message:34349698"));
        assertTrue(resultString.contains("label"));
        assertTrue(resultString.contains("user1"));
        assertTrue(resultString.contains("rock"));
        assertTrue(resultString.contains("black"));
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailCreatingEngagement() {
        Engagement engagement = new Engagement.EventBuilder()
                .withUser("id", "alias")
                .withFeedId("1")
                .withContent(
                        new Content.ContentBuilder()
                                .withAttribute("verb", "share")
                                .withAttribute("actor", new ContentAttribute("1", "user1"))
                                .build()
                )
                .build();
    }
}