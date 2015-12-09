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

Sometimes can be handy to import a complete version of the library containing all the
required dependencies:

```
    dependencies {
        compile files('/path_to_file/stream-analytics-release-1.0-with-dependencies.aar')
    }
```

### Bind the service in your application's manifest.xml

```xml
 <service
    android:name="io.getstream.analytics.service.StreamAnalyticsService"
    android:exported="true"
    android:enabled="true">
    <intent-filter>
        <action android:name="io.getstream.analytics.service.ENGAGEMENT" />
        <action android:name="io.getstream.analytics.service.IMPRESSION" />
    </intent-filter>
    <meta-data android:name="io.getstream.analytics.v1.API_KEY" android:value="@string/api_key" />
    <meta-data android:name="io.getstream.analytics.v1.AUTH_TOKEN" android:value="@string/jwt_token" />
 </service>
```
