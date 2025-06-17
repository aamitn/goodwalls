# GoodWalls Backend

Backend administration panel and REST API for the GoodWalls wallpaper application. Built with PHP and MySQL.


## Requirements

- PHP 7.0 or higher
- MySQL 5.6 or higher 
- Apache with mod_rewrite enabled or Nginx
- PHP Extensions:
  - GD Library
  - JSON
  - MySQLi
  - cURL

## Installation

1. Create a new MySQL database and import the schema:
```sql
mysql -u username -p database_name < material_wallpaper_db.sql
```
2. Configure database connection in `includes/config.php`:
```php
<?php
$host = "localhost";
$user = "database_username"; 
$pass = "database_password";
$database = "database_name";
```
3. Configure application settings:
- Set app name in `strings.php`
- Configure OneSignal keys in admin dashboard
- Set up admin account credentials

4. Set proper permissions:
```bash
chmod 755 upload/
chmod 644 includes/config.php
```

5. Edit the file `service-account.json` with contents from:
- Create  a (firebase)[https://console.firebase.google.com] project with app package name matching to that in `android_app` project folder 
- Navigate to the cereated project
- Go to Project Overview -> PROJECT SETTINGS-> SERVICE ACCOUNT,JAVA-> CREATE PRIVATE KEY


## API Documentation
API endpoints are available at /api/v1/. Key endpoints:

- `GET /api/v1/get_wallpapers` - Get wallpaper list
- `GET /api/v1/get_categories` - Get categories
- `GET /api/v1/get_featured` - Get featured wallpapers
- `POST /api/v1/update_view` - Update view count
- `POST /api/v1/update_download` - Update download count

## Directory Structure
```
/backend
├── api/                # REST API endpoints
├── assets/            # CSS, JS, images
├── includes/          # Configuration files
├── upload/            # Uploaded wallpapers
├── index.php          # Admin login
├── dashboard.php      # Admin dashboard
└── *.php             # Admin panel views
```

## License

Open-Source Free Software Maintained by  [Bitmutex Technologies](https://www.bitmutex.com)