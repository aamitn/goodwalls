---
description: applicationId (Required)
---

# Application ID

#### applicationId (Required) <a href="#applicationid-required" id="applicationid-required"></a>

**applicationId** is the main domain of your application. The applicationId is not related to the package name, although some application developers name the applicationId and the package name the same.

Each application to be published must have a unique domain name, generally using 3 word arrangements (can be more than 3), for example: **com.app.goodwalls**, **com** is identified as the domain, **app** as the name of the developer and **goodwalls**as the name of the application.

1. Open **Gradle Scripts > build.gradle (Module: app)**
2. Change the application ID with your own id name
3. Click **Sync Now**.
4. Change the id as unique as possible, because application id is very important used if you want to publish the application to the google play.

{% hint style="info" %}
Important : your **applicationId** must be the **same** as the **Android package name** you created in the firebase console when you **created google-services.json**
{% endhint %}

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FdBVLYPvvChf7O4SNSQp2%252Fimage.png%3Falt%3Dmedia%26token%3Df103d157-b856-4338-9afb-f78b9c7faece&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=76429f16&#x26;sv=2" alt=""><figcaption></figcaption></figure>

#### Package name and namespace (optional) <a href="#package-name-and-namespace-optional" id="package-name-and-namespace-optional"></a>

Package name is the folder path to store class files. The package name is not required to be changed, so it uses the default source code. But we recommend changing it according to the applicationId structure, if you want to change it:

1. Click the settings icon in Android Studio
2. Select **Tree Appearance**, **remove the checklist** in the **Compact Middle Packages** section
3. Select one of the sub packages then **right click > Refactor > Rename...**
4. When finished, return the checklist to Compact Middle Packages so that the package name structure looks neater again

Important: the structure of the **package name** must be **the same** as the **namespace** in the **build.gradle** file, otherwise the project will be error

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FYx3Lt2xlBrzveSyZAnOB%252Fimage.png%3Falt%3Dmedia%26token%3Deedfd308-7901-490a-a7fd-31469b5f59b2&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=5dc9d516&#x26;sv=2" alt=""><figcaption></figcaption></figure>

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FfZ1tMQbzwfygEaxZqpJL%252Fimage.png%3Falt%3Dmedia%26token%3D7c5948f3-2dcb-42aa-a127-b72eb28112e3&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=73382a97&#x26;sv=2" alt=""><figcaption></figcaption></figure>

#### **Invalidate caches** <a href="#invalidate-caches" id="invalidate-caches"></a>

After the renaming of the package name is complete, you need to rebuild the project and invalidate the cache

1. Select **Build > Rebuild Project** and wait until the building project finished
2. Select **File > Invalidate Caches.. > Invalidate and Restart**
