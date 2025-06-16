<?php include_once ('includes/header.php'); ?>

<?php

    $menus = "SELECT COUNT(*) as num FROM tbl_menu";
    $totalMenus = $connect->query($menus);
    $totalMenus = $totalMenus->fetch_array();
    $totalMenus = $totalMenus['num'];

    $categories = "SELECT COUNT(*) as num FROM tbl_category";
    $totalCategories = $connect->query($categories);
    $totalCategories = $totalCategories->fetch_array();
    $totalCategories = $totalCategories['num'];

    $featured = "SELECT COUNT(*) as num FROM tbl_gallery WHERE featured = 'yes'";
    $totalFeatured = $connect->query($featured);
    $totalFeatured = $totalFeatured->fetch_array();
    $totalFeatured = $totalFeatured['num'];

    $wallpapers = "SELECT COUNT(*) as num FROM tbl_gallery WHERE image_extension != 'image/gif' AND image_extension != 'application/octet-stream'";
    $totalWallpapers = $connect->query($wallpapers);
    $totalWallpapers = $totalWallpapers->fetch_array();
    $totalWallpapers = $totalWallpapers['num'];

    $liveWallpapers = "SELECT COUNT(*) as num FROM tbl_gallery WHERE image_extension = 'image/gif' OR image_extension = 'application/octet-stream'";
    $totalLiveWallpapers = $connect->query($liveWallpapers);
    $totalLiveWallpapers = $totalLiveWallpapers->fetch_array();
    $totalLiveWallpapers = $totalLiveWallpapers['num'];

?>

    <section class="content">

    <ol class="breadcrumb">
        <li><a href="dashboard.php">Dashboard</a></li>
        <li class="active">Home</a></li>
    </ol>

        <div class="container-fluid">
             
             <div class="row">

                <a href="menu.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_tab; ?></div>
                            <div class="color-name"><i class="material-icons">burst_mode</i></div>
                            <div class="color-class-name">Total ( <?php echo $totalMenus; ?> ) Menus</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="category.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_category; ?></div>
                            <div class="color-name"><i class="material-icons">view_agenda</i></div>
                            <div class="color-class-name">Total ( <?php echo $totalCategories; ?> ) Categories</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="wallpaper.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_wallpaper; ?></div>
                            <div class="color-name"><i class="material-icons">landscape</i></div>
                            <div class="color-class-name">Total ( <?php echo $totalWallpapers; ?> ) Wallpapers</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="live.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_live_wallpaper; ?></div>
                            <div class="color-name"><i class="material-icons">play_circle_filled</i></div>
                            <div class="color-class-name">Total ( <?php echo $totalLiveWallpapers; ?> ) Live wallpapers</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="featured.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_featured; ?></div>
                            <div class="color-name"><i class="material-icons">lens</i></div>
                            <div class="color-class-name">Total ( <?php echo $totalFeatured; ?> ) Wallpapers</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="ads.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_ads; ?></div>
                            <div class="color-name"><i class="material-icons">monetization_on</i></div>
                            <div class="color-class-name">App monetize</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="notification.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_notification; ?></div>
                            <div class="color-name"><i class="material-icons">notifications</i></div>
                            <div class="color-class-name">Notify your users</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="admin.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_administrator; ?></div>
                            <div class="color-name"><i class="material-icons">people</i></div>
                            <div class="color-class-name">Admin panel privileges</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="settings.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_setting; ?></div>
                            <div class="color-name"><i class="material-icons">settings</i></div>
                            <div class="color-class-name">Keys and privacy settings</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="apps.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_app; ?></div>
                            <div class="color-name"><i class="material-icons">adb</i></div>
                            <div class="color-class-name">Manage apps & redirect</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="license.php">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_license; ?></div>
                            <div class="color-name"><i class="material-icons">vpn_key</i></div>
                            <div class="color-class-name">Envato purchase code</div>
                            <br>
                        </div>
                    </div>
                </a>

                <a href="logout.php" onclick="return confirm('Are you sure want to logout?')">
                    <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
                        <div class="card demo-color-box bg-blue waves-effect corner-radius col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <br>
                            <div class="color-name uppercase"><?php echo $menu_logout; ?></div>
                            <div class="color-name"><i class="material-icons">power_settings_new</i></div>
                            <div class="color-class-name">Logout from admin panel</div>
                            <br>
                        </div>
                    </div>
                </a>

            </div>
            
        </div>

    </section>


<?php include_once('includes/footer.php'); ?>