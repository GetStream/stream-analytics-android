Analytics API documentation
===========================

This document describes the current version of Stream Analytics APIs (v1.0). 

API base URL
------------

Every HTTP request has the implicit https://analytics.getstream.io/analytics/$VERSION/ URI base path

API version
-----------

API version is selected with the URI base path, current version is `v1.0` (eg. https://analytics.getstream.io/analytics/v1.0/)


Content Encoding
----------------

Request data must be JSON encoded, the Content-type HTTP header must be sent with 'application/json' value.

```
Content-type=application/json
```

Authentication
--------------

In order to authenticate with Stream Analytics APIs the following HTTP headers and parameters need to be provided with every request:

This header is required to tell the APIs which kind of authentication is being used

```
STREAM-AUTH-TYPE=jwt
```

The Authorization header contains a base64 encoded token

```
AUTHORIZATION=<jwt_token>
```

The api_key parameter 
```
api_key=<api_key>
```

> NOTE: api_key and jwt_token are provided as coupled pairs, it is not mix api_key and jwt_token coming from different tuples.


In case you want to generate the token yourself, the JWT Token payload is the following
```
{
    'action': 'write',
    'feed_id': '*',
    'resource': 'analytics'
}
```

and the payload must be encoded with the api_secret related to the given api_key (http://jwt.io/)


### Testing authentication

Authentication can be tested on the /auth_test/

Here's a curl example:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/auth_test/?api_key=nfq26m3qgfyp' \
    --data '{}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json' --verbose
```

#### Development Keys

For development purposes, you can use the following api_key, jwt_token combination

##### API_KEY

```
nfq26m3qgfyp
```

##### JWT_TOKEN

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE
```

API Endpoints
-------------

### user_id

Every resource created by the client is always related to a user_id, API clients should require `user_id` as one of the required initialization paramters (eg. have user_id in the constructor function signature)


### Engagement

**URI Endpoint:** `/engagement/`

##### Fields:

* **activity_id**: int, required
* **feed_id**: str, required
* **label**: str, required
* **score**: int, [optional]
* **user_id**: int, required
* **extra_data**: object, [optional]

##### Examples:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/engagement/?api_key=nfq26m3qgfyp' \
    --data '{"activity_id": "message:34349698","user_id":"tom","label":"click","feed_id": "user:ChartMill"}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/engagement/?api_key=nfq26m3qgfyp' \
    --data '{"extra_data": {"onestr": "str", "oneint": 12}, "activity_id": "message:34349698","user_id":"tom","score":"12","label":"click","feed_id": "user:ChartMill"}'\
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```

### Impressions

**URI Endpoint:** `/impression/`

##### Fields:

* **activity_ids**: list of int, required
* **feed_id**: str, required
* **user_id**: int, required
* **extra_data**: object, [optional]

##### Example:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/impression/?api_key=nfq26m3qgfyp' \
    --data '{"extra_data": {"onestr": "str", "oneint": 12}, "activity_ids": ["message:34349698", "message:34349699"],"user_id":"tom","feed_id": "user:ChartMill"}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsImZlZWRfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.pFU9mTsGtBuhdU-_NjKmd6dLRwvAcNskeMZ97BRRMnE' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```


General Client Guidelines
-------------------------

### Async

API clients should perform HTTP calls asyncronously, without blocking the UI.

### Sampling

API clients should support event sampling, that is dropping a percentage of tracked data. Sampling should be turned off by default.

### API retries

Failed requests should be dropped. Retries are out of scope.

### Limit thread count and queue capacity

API clients should use up a fixed amount of threads to perform async calls, task queues must have a reasonable finite capacity and drop old tasks when such capacity is reached.

### Naming conventions for public classes and methods across clients

* StreamAnalytics: the main class responsible for creating Tracker instances
* Tracker: the class that exposes the API event methods
    * send: send an event to the APIs
* Engagement: the engagement class holding engagement data
* Impression: the impression class holding impression data


Android Client Specs
--------------------

### Application namespace:

io.getstream.analytics

### manifest.xml configuration

```xml
<meta-data android:name="io.getstream.analytics.v1.API_KEY" android:value="@string/api_key" />
<meta-data android:name="io.getstream.analytics.v1.AUTH_TOKEN" android:value="@string/jwt_token" />
```

### Code example:

```java
// gets the instance of StreamAnalytics (Singleton)
StreamAnalytics analytics = StreamAnalytics.getInstance(context);

// gets a new instance of Tracker for specific user_id
Tracker tracker analytics.newTracker("user_id");

// track an engagement event
tracker.send(new Engagement.EventBuilder()
       .setActivityID("activity-xxx")
       .setFeedId("feed-xxx")
       .setLabel("click")
       .setScore(1)
       .build());

// track an engagement event
tracker.send(new Impression.EventBuilder()
       .setActivityIDs(Arrays.asList("id1", "id2, "id3"))
       .setFeedId("feed-xxx")
       .build());
```
