<a href="https://play.google.com/store/apps/details?id=com.app.goodwalls1" target="_blank" align="left">
  <img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png"  alt="Get it on Google Play"  height="auto" width=130" align="right">
</a>

# GoodWalls App

<img src="./android_app/assets/logo.png" alt="Goodwalls logo" height="auto" width="90" align="left" style="border-radius:12px; padding-right:5px;">

A wallpaper application with Android client and PHP/MySQL backend. The app allows users to browse, download and set wallpapers and live wallpapers.


## Project Location

| **Android App**                    | **Backend**                    |
|------------------------------------|--------------------------------|
| [`Location`](./android_app/)       | [`Location`](./backend/)       |
| [`Readme`](./android_app/README.md)  | [`Readme`](./backend/README.md)  |


### Documentation
- `docs.html` - Comprehensive documentation
- `changelog.html` - Version history and updates

## Requirements

### Android App
- Android Studio
- Minimum SDK version: Android 5.0 (API 21)
- Max Target SDK : Android 15 (API 35)
- Gradle build system

### Backend
- PHP 7.0+
- MySQL 5.6+
- Apache web server mod_rewrite enabled / nginx

## Setup

1. Import the database schema from [`/backend/material_wallpaper_db.sql`](./backend/material_wallpaper_db.sql)

2. Configure backend:
   - Update database credentials in `/backend/includes/config.php`
   - Configure server paths in Apache

3. Configure Android app:
   - Update API endpoint in `Config.java`
   - Update package name in `build.gradle`
   - Generate signing keystore

## License

Open-Source Free Software Maintained by  [Bitmutex Technologies](https://www.bitmutex.com)
