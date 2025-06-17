# Disable Push Notification

If you don't want to use push notifications and want to disable the SDK, you can disable it in the Android Studio project dependencies in the **build.gradle (Module: app)** file by updating the push notification sdk from push-notification-sdk to no-push-notification-sdk



{% code title="build.gradle (Module: app)" %}
```
implementation 'com.github.solodroid-dev:no-push-notification-sdk:1.+'
```
{% endcode %}

![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F473084755-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252FTVrvrIJUO7RA0fbmCKdp%252Fuploads%252FwUI1bHrecVAyKJCQP5CH%252Fimage.png%3Falt%3Dmedia%26token%3Dcc479a9e-fe08-46cf-9b59-bc2acbcd49ca\&width=768\&dpr=4\&quality=100\&sign=87f3123f\&sv=1)
