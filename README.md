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

Every resource created by the client is always related to a user_id. 
API clients should require `user_id` as one of the required initialization parameters (eg. have user_id in the constructor function signature) and send it together with the rest of fields.


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

* **foreign_ids**: list of str, required
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

The features field is a list of feature objects. A feature object encapsulates the following data: 'group' and 'value' both must be strings. (eg. [{"group":"topic", "value":"cats"}, {"group":"language", "value":"en"}])


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
