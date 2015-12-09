# GetStream Android Analytics Client

[![Build Status](https://travis-ci.org/GetStream/stream-analytics-android.svg?branch=master)](https://travis-ci.org/GetStream/stream-analytics-android)

## How to use it

### Download the client:

Releases are listed here: https://github.com/GetStream/stream-analytics-android/releases/

### Import the library in your gradle project:

```
    dependencies {
        compile files('/path_to_file/stream-analytics-release-1.0.aar')
    }
```

### Add the INTERNET permission in your application's AndroidManifest.xml

```xml
    <uses-permission android:name="android.permission.INTERNET" />
```

### Usage

Create an instance of the client with your credentials in an Activity or Application class

```java
        StreamAnalyticsAuth auth = new StreamAnalyticsAuth(API_KEY, API_TOKEN);
        
        StreamAnalytics client = StreamAnalytics.getInstance(auth);
        client.setUserId("486892");
```

You can set the global userId in the client and optionally override it per tracking event call.

### Example engagement tracking

```java
    client.send(new Engagement.EventBuilder()
            .withForeignId("message:34349698")
            .withFeedId("user:ChartMill")
            .withLabel("Engagement click")
            .withScore(1d)
            .withBoost(1)
            .withLocation("homepage")
            .withPosition("0")
            .withFeatures(features)
            .build()
    );
```

### Example impression tracking

Every time you display content to the user, you should track the impression. The impression event allows you to track what content is displayed to the user. There are 2 required fields that you need to provide:

* foreign_ids: the list of IDs for the content you are showing to the user.
* feed_id: the feed_id (eg. "flat:123", "newsfeed:42") from where the content origins, if the content does not come from a Stream's powered feed you should use a clear and unique identifier (eg. "user_search_page", "user42_profile_page", etc.)

Here's an example of how you would track the display of the "user:ChartMill" feed to the current user, the example implies that the user was presented with a list of contents: one message with ID 34349698 and one comment with ID 414416008. Using a combiantion of content_type and ID is a very simple way to avoid collision between objects of different types.

```java
    client.send(new Impression.EventBuilder()
            .withForeignIds(new String[]{"message:34349698", "comment:414416008"})
            .withFeedId("user:ChartMill")
            .build()
    );
```

You can find a comprehensive list of all optional parameters that you can send with the impression event in the API reference docs.

### Additional fields reference
    
You can get more information out of analytics by adding extra information regarding impressions and engagements. Below you can fine the list of additional fields that you can provide to both Engagement and Impression events.

#### boost

An integer that multiplies the score of the interaction (eg. 2 or -1)

#### location

A string representing the location of the content (eg. "homepage")

#### position

When tracking interactions with content, it might be useful to provide the ordinal position (eg. 2)

#### features

If necessary, you can send additional information about the data involved or the specific event. The features field must be a list of feature objects. The feature object will convert this into a group and value.

eg.

```java
    ArrayList<Feature> features = new ArrayList<>();
    features.add(new Feature("topic", "coffee"));
```

### Sample app

There is an example app available on GitHub.
https://github.com/GetStream/stream-analytics-android/tree/master/sample
