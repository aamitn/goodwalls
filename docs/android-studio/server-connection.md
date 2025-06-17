# Server Connection

When your admin panel already configured properly, you need to do a following steps to be able to connect with the android application

1. Login to your admin panel, navigate to **Apps** menu
2. Click **AD NEW APP** button, then enter your **applicationId (Package Name)**, your applicationId (Package Name) must be the same as what you entered in **Firebase** configuration and select **Active** for your app status.
3. After the app is created, you will get a **Server Key** which is used to connect your server with the android application.
4. **Server Key** cannot be changed, it's auto generated based on your admin panel url and applicationId
5. Copy your **Server Key** and put the key to **java/\<your\_package\_name>/Config.java** in the **SERVER KEY** string tags for server connection.

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FoUiA6FQgvpK7biCoBE7E%252Fimage.png%3Falt%3Dmedia%26token%3D67e1c913-2311-48ef-9945-fc02dfcf58ef&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=b9f40219&#x26;sv=2" alt=""><figcaption></figcaption></figure>

{% code title="Config.java" %}
```
//server key obtained from the admin panel
public static final String SERVER_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
```
{% endcode %}

<figure><img src="https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FDnXdcYGBZ0SiWahOGxVL%252Fimage.png%3Falt%3Dmedia%26token%3D0490be38-9232-491e-95ef-2026b14137f6&#x26;width=768&#x26;dpr=4&#x26;quality=100&#x26;sign=3a2ed6a1&#x26;sv=2" alt=""><figcaption></figcaption></figure>
