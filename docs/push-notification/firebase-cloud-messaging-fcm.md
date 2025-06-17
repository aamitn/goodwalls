# Firebase Cloud Messaging (FCM)

Firebase Cloud Messaging API has been deprecated, the Server API Key cannot be generated and cannot be used by 06/20/2024, so, migrate to the latest Firebase Cloud Messaging API (HTTP v1) is required.

Set up a Firebase service account to let the Firebase Admin SDK authorize calls to FCM APIs. Open **Project settings** in the Firebase console and select the **Service accounts** tab. Click **Generate new private key** to download the configuration snippet.

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FoPhVKI2DltyM9RqMJPFW%252Fimage.png%3Falt%3Dmedia%26token%3D4ba013ea-2d22-404d-9867-106bcef51d02&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=83c9c046&#x26;sv=2" alt=""><figcaption></figcaption></figure>

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F473084755-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252FTVrvrIJUO7RA0fbmCKdp%252Fuploads%252FkxPc2tHYNiZ586k0mZpM%252Fimage.png%3Falt%3Dmedia%26token%3Dc4acbca4-4752-4370-9109-5d1bd6174a6e&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=393a81ab&#x26;sv=1" alt=""><figcaption></figcaption></figure>

Rename the downloaded file to **service-account.json** and copy replace it to root of the folder path where you put the php admin panel code.

Another alternative, you can directly open the .json file that has been downloaded then select all and copy all the code, open the **service-account.json** file in your admin panel code and replace all the code with yours that you copied previously.

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FYEgg2nuZhHHPngeB6F8a%252Fimage.png%3Falt%3Dmedia%26token%3Dfaa25076-128b-427f-8d75-0e9ef0a38a8c&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=76c80610&#x26;sv=2" alt=""><figcaption></figcaption></figure>

**FCM Notification Topic**

* Notification topic name is determined by you, the notification topic must be written in lowercase without space (use underscore), e.g : **material\_wallpaper\_topic**
* Login to your Admin Panel, navigate to Settings menu and write your **FCM Notification Topic**

![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FuoT4CWKxT4x3LHCYHC5Z%252Fimage.png%3Falt%3Dmedia%26token%3Dcbfeb6fc-7b87-4172-89e1-c9be047a9fa9\&width=768\&dpr=4\&quality=100\&sign=84ecd097\&sv=2)
