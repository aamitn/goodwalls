# GoodWalls Android App

Beautiful wallpaper application for Android that allows users to browse, download and set wallpapers.


## Tech Stack

- Minimum SDK: API 21 (Android 5.0)
- Target SDK: API 35 (Android 15)
- Language: Java
- Architecture: MVVM
- Libraries:
  - AndroidX
  - Glide for image loading
  - Retrofit for API calls
  - Room for local storage
  - OneSignal for notifications
  - Material Components
  - ViewBinding

## Project Structure

```
android_app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/goodwalls1/
│   │   │   │   ├── activities/
│   │   │   │   ├── adapters/
│   │   │   │   ├── api/
│   │   │   │   ├── models/
│   │   │   │   ├── utils/
│   │   │   │   └── viewmodels/
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle
└── build.gradle
```

## Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/aamitn/goodwalls.git
```

2. Open project in Android Studio

3. Configure Backend Key (generated from Backend->app
->create):
```java
    public static final String SERVER_KEY = "YOURKEY";
```

4. Configure signing:
- Create keystore file
- Update `app/build.gradle` with signing config
- Or use the existing one `goodwalls.jks`

5. Build and run

6. For manual builds run
``` 
gradlew assembleRelease --scan # Create APK
gradlew bundleRelease --scan # Create AAB
```

7. APK/AAB Locations
- For Automated Android Studio builds : 
    - `android_app\app\release\app-release.apk`
    - `android_app\app\release\app-release.aab`
- For Manual Builds : 
    - `.\android_app\app\build\outputs\apk\release\app-release-unsigned.apk`
    - `.\android_app\app\build\outputs\bundle\release\app-release.aab`

8. Create Firebase Project and Download the `google-services.json`, replace with the one present at `./android_app/app/google-services.json`

## Testing

Run unit tests:
```bash
./gradlew testReleaseUnitTest
./gradlew testDebugUnitTest
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```
## License

Open-Source Free Software Maintained by  [Bitmutex Technologies](https://www.bitmutex.com)