# Database Configuration

#### **Create Database** <a href="#create-database" id="create-database"></a>

* Login to your cpanel hosting and open **MySQL Databases**
* Create database and give database name like you want, as example : ‘**solodroid\_myapp\_db**’
* Create user for your database
* Add user to your database and check **ALL PRIVILEGES**

![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252FVHuhmykZZEYafVZA0Ysh%252Fimage.png%3Falt%3Dmedia%26token%3Dcfdbbb84-35d0-4378-94e9-864b8ac66aed\&width=768\&dpr=4\&quality=100\&sign=3031f643\&sv=2)![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252Felu0g0nhMvk2Q4XokHzr%252Fimage.png%3Falt%3Dmedia%26token%3D99a21dfa-db51-43a7-84b1-893fb80eb45e\&width=768\&dpr=4\&quality=100\&sign=e163a83f\&sv=2)![](https://solodroid.gitbook.io/~gitbook/image?url=https%3A%2F%2F2891416036-files.gitbook.io%2F%7E%2Ffiles%2Fv0%2Fb%2Fgitbook-x-prod.appspot.com%2Fo%2Fspaces%252Fi86x2ggRySum6oB03xV8%252Fuploads%252F0ShARTfLbrEY8F5i3x3U%252Fimage.png%3Falt%3Dmedia%26token%3D33da7ffb-6c4e-4fdd-b3a3-de6d9724b559\&width=768\&dpr=4\&quality=100\&sign=d7b247f9\&sv=2)

#### Import Database <a href="#import-database" id="import-database"></a>

* Open **phpMyAdmin** and select your database
* Import database file “ **material\_wallpaper\_db.sql**” from download package inside ‘**admin\_panel**‘ folder

#### Upload PHP Scripts <a href="#upload-php-scripts" id="upload-php-scripts"></a>

* Open **File Manager** via cpanel or ftp server
* Upload **“material\_wallpaper.zip**” inside “**admin\_panel**” folder to your folder destination on your hosting, then extract it

#### Database Connection <a href="#database-connection" id="database-connection"></a>

* to change configuration please see **includes/config.php** file
* you must edit this data with your own data.

```
//database configuration
$host       = 'localhost';
$user       = 'root';
$pass       = '';
$database   = 'material_wallpaper_db';

$connect = new mysqli($host, $user, $pass, $database);

if (!$connect) {
   die ("connection failed: " . mysqli_connect_error());
} else {
   $connect->set_charset('utf8mb4');
}
```

#### Default Admin Panel Login detail <a href="#default-admin-panel-login-detail" id="default-admin-panel-login-detail"></a>

Username : admin Password : admin
