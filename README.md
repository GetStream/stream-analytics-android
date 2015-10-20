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

Tokens can allow for write data for any user_id or for one specific user_id.

In case you want to generate the token yourself, the JWT Token payload is the following
```
{
    'action': 'write',
    'user_id': '*',
    'resource': 'analytics'
}
```

This token allows to send events for any value of user_id.

and the payload must be encoded with the api_secret related to the given api_key (http://jwt.io/)


### Testing authentication

Authentication can be tested on the /auth_test/

Here's a curl example:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/auth_test/?api_key=8r9brv6v4xj5' \
    --data '{}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsInVzZXJfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.rKwIdyV10DQyoFgC-KmAXBUljoFU7wbeqQV6uqNvSDg' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json' --verbose
```

#### Development Keys

For development purposes, you can use the following api_key, jwt_token combination

##### API_KEY

```
8r9brv6v4xj5
```

##### JWT_TOKEN

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsInVzZXJfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.rKwIdyV10DQyoFgC-KmAXBUljoFU7wbeqQV6uqNvSDg
```

API Endpoints
-------------

### user_id

Every resource created by the client is always related to a user_id, API clients should require `user_id` as one of the required initialization paramters (eg. have user_id in the constructor function signature) and send it together with the rest of fields.


### Engagement

**URI Endpoint:** `/engagement/`

##### Fields:

* **foreign_id**: str, required
* **feed_id**: str, [optional]
* **label**: str, required
* **boost**: int, [optional]
* **user_id**: int, required
* **location**: str, [optional]
* **position**: str, [optional]
* **features**: list of objects, [optional]


##### Examples:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/engagement/?api_key=8r9brv6v4xj5' \
    --data '{"foreign_id": "message:34349698","user_id":"tom","label":"click","feed_id": "user:ChartMill"}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsInVzZXJfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.rKwIdyV10DQyoFgC-KmAXBUljoFU7wbeqQV6uqNvSDg' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/engagement/?api_key=8r9brv6v4xj5' \
    --data '{"extra_data": {"onestr": "str", "oneint": 12}, "foreign_id": "message:34349698","user_id":"tom","boost":"12","label":"click","feed_id": "user:ChartMill"}'\
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsInVzZXJfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.rKwIdyV10DQyoFgC-KmAXBUljoFU7wbeqQV6uqNvSDg' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```

### Impressions

**URI Endpoint:** `/impression/`

##### Fields:

* **foreign_id**: list of str, required
* **feed_id**: str, [optional]
* **boost**: int, [optional]
* **user_id**: int, required
* **location**: str, [optional]
* **position**: str, [optional]
* **features**: list of objects, [optional]

##### Example:

```bash
curl 'https://analytics.getstream.io/analytics/v1.0/impression/?api_key=8r9brv6v4xj5' \
    --data '{"extra_data": {"onestr": "str", "oneint": 12}, "foreign_ids": ["message:34349698", "message:34349699"],"user_id":"tom","feed_id": "user:ChartMill"}' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY3Rpb24iOiJ3cml0ZSIsInVzZXJfaWQiOiIqIiwicmVzb3VyY2UiOiJhbmFseXRpY3MifQ.rKwIdyV10DQyoFgC-KmAXBUljoFU7wbeqQV6uqNvSDg' \
    --header 'STREAM-AUTH-TYPE: jwt' \
    --header 'Content-Type: application/json'
```

##### Features field

The features field is a list of feature objects. A feature object encapsulates the following data: 'group' and 'value' both must be strings. The client will send this as a JSON (eg. [{"group":"topic", "value":"cats"}])


General Client Guidelines
-------------------------

### Async

API clients should perform HTTP calls asyncronously, without blocking the UI.

### API retries

Failed requests should be dropped. Retries are out of scope.

### Limit thread count and queue capacity

API clients should use up a fixed amount of threads to perform async calls, task queues must have a reasonable finite capacity and drop old tasks when such capacity is reached.

### Naming conventions for public classes and methods across clients

* StreamAnalytics: the main class responsible for sending events to the APIs
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
StreamAnalytics analytics = StreamAnalytics.getInstance("user_id");

// track an engagement event
analytics.send(new Engagement.EventBuilder()
       .setActivityID("activity-xxx")
       .setFeedId("feed-xxx")
       .setLabel("click")
       .setScore(1)
       .build());

// track an engagement event
analytics.send(new Impression.EventBuilder()
       .setActivityIDs(Arrays.asList("id1", "id2", "id3"))
       .setFeedId("feed-xxx")
       .build());
```


iOS Client Specs
--------------------

Follow Android specs for now.
