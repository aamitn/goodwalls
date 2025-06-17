# Ad Networks

We provide several choices of ad network libraries that you can customize yourself based on monetization needs for your application.

The library implementation can be managed easily in the Android Studio project dependencies in the **build.gradle (Module: app)** file

![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FvwIUay9fzQYZugHfB0tE%252Fimage.png%3Falt%3Dmedia%26token%3D990d9fea-4c21-4080-808a-0e86db0fdf87\&width=768\&dpr=4\&quality=100\&sign=2f493084\&sv=2)

#### Ad Network Sdk options <a href="#a-d-network-sdk-options" id="a-d-network-sdk-options"></a>

{% code title="build.gradle (Module: app)" %}
```
implementation 'com.github.solodroid-dev.ads:[ad-sdk-name]:[version]'
```
{% endcode %}

**multi-ads-sdk**

`multi-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads, ironSource`

**multi-ads-sdk-no-is**

`multi-ads-sdk-no-is:1.+` with ad network support `AdMob, Ad Manager, Meta Audience Network, AppLovin Max, AppLovin Discovery, Start.io, Unity Ads`

**admob-ads-sdk**

`admob-ads-sdk:1.+` with ad network support `AdMob, Ad Manager`

**facebook-ads-sdk**

`facebook-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, Meta Audience Network`

**applovin-ads-sdk**

`applovin-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, AppLovin Max, AppLovin Discovery`

**startapp-ads-sdk**

`startapp-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, Start.io`

**unity-ads-sdk**

`unity-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, Unity Ads`

**ironsource-ads-sdk**

`ironsource-ads-sdk:1.+` with ad network support `AdMob, Ad Manager, ironSource`

**no-ads-sdk**

`no-ads-sdk:1.+` with ad network support `No Ads`

**Important** : All ad network id units are placed in admin panel except **AdMob App ID** and **AppLovin SDK Key**, both ids must be integrated programmatically in android code inside **res/value/ads.xml**

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FYd3BsnwHNzH7t2iCHM6I%252Fimage.png%3Falt%3Dmedia%26token%3D8fe8d093-6101-4477-8341-d7a64532acbd&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=2b946af1&#x26;sv=2" alt=""><figcaption></figcaption></figure>

#### **Remote ads from the admin Panel** <a href="#remote-a-ds-from-the-admin-panel" id="remote-a-ds-from-the-admin-panel"></a>

* **Switch Ads :** You can switch to use Ad Network according your needs
* **Backup Ads :** If your main ad fails to show, it will automatically load backup ads if enabled
* You deactivate a particular ad **format** in the available form by giving 0 value
* You can completely deactivate the ad by set OFF in the Ad Status form

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FpQgCFEBis6wssIWf8gPR%252Fimage.png%3Falt%3Dmedia%26token%3D6d061001-1c41-45c8-9c72-f39f46c40ad0&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=30550ae5&#x26;sv=2" alt=""><figcaption></figcaption></figure>

#### Working with offline ads <a href="#working-with-offline-a-ds" id="working-with-offline-a-ds"></a>

We provide another alternative if you don't want the application to be integrated with remote ads in the admin panel, you can disable it via **Config.java** by setting the **ENABLE\_OFFLINE\_ADS\_MODE** value to **true**, then manually input the settings and the ad unit id you want to use in **ads.xml**

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FmT2blmATvvL7rHjzC7qF%252Fimage.png%3Falt%3Dmedia%26token%3D87c45356-b8de-40f6-a14e-ab0081761f1f&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=9195f680&#x26;sv=2" alt=""><figcaption></figcaption></figure>

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252Fdgems5l5ebTtxgDI8NJp%252Fimage.png%3Falt%3Dmedia%26token%3D217ecc97-d150-494d-bf67-417649565958&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=a9fd19ea&#x26;sv=2" alt=""><figcaption></figcaption></figure>

#### Test Ad and Production <a href="#test-a-d-and-production" id="test-a-d-and-production"></a>

Test mode will automatically enabled in debug mode for some Ad Networks to prevent invalid traffic or activity when you test the application, the mode will automatically disabled when you release your app in production (generated signed apk / app bundle).
