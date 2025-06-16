<?php include_once('includes/header.php'); ?>
<script src="assets/plugins/ckeditor/ckeditor.js"></script>

<?php

    $ID =  clean("1");

	$sql = "SELECT * FROM tbl_settings WHERE id = '$ID'";
	$result = $connect->query($sql);
    $settings_row = $result->fetch_assoc();

    $server_url = (isset($_SERVER['HTTPS']) ? 'https' : 'http').'://'.$_SERVER['SERVER_NAME'].dirname($_SERVER['REQUEST_URI']);
    $applicationId = $settings_row['package_name'];
    $plain_text = $server_url.'_applicationId_'.$applicationId;
    $encode = encrypt($plain_text);

	if(isset($_POST['submit'])) {

	    $data = array(
            'category_sort' => clean($_POST['category_sort']),
            'category_order' => clean($_POST['category_order']),
            'onesignal_app_id' => clean($_POST['onesignal_app_id']),
            'onesignal_rest_api_key' => clean($_POST['onesignal_rest_api_key']),
            'privacy_policy' => clean($_POST['privacy_policy']),
            'providers' => clean($_POST['providers']),
            'package_name' => clean($_POST['package_name']),
            'fcm_server_key' => clean($_POST['fcm_server_key']),
            'fcm_notification_topic' => clean($_POST['fcm_notification_topic']),
            'more_apps_url' => clean($_POST['more_apps_url'])
	    );

	    $update_setting = update('tbl_settings', $data, "WHERE id = '$ID'");

	    if ($update_setting > 0) {
	        $_SESSION['msg'] = "Changes Saved...";
	        header( "Location:settings.php");
	        exit;
	    }
	}

?>

    <section class="content">

        <ol class="breadcrumb">
            <li><a href="dashboard.php">Dashboard</a></li>
            <li class="active">Settings</a></li>
        </ol>

       <div class="container-fluid">

            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

                	<form method="post" enctype="multipart/form-data" id="form_validation">
                    <div class="card corner-radius">
                        <div class="header">
                            <h2>SETTINGS</h2>
                            <div class="header-dropdown m-r--5">
                                <button type="submit" name="submit" class="button button-rounded btn-offset waves-effect waves-float">SAVE SETTINGS</button>
                            </div>
                        </div>
                        <div class="body">

                            <?php if(isset($_SESSION['msg'])) { ?>
                                <div class='alert alert-info alert-dismissible corner-radius' role='alert'>
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>&nbsp;&nbsp;</button>
                                    <?php echo $_SESSION['msg']; ?>
                                </div>
                            <?php unset($_SESSION['msg']); } ?>

                        	<div class="row clearfix">

                                <input type="hidden" class="form-control" name="package_name" id="package_name" value="<?php echo $settings_row['package_name'];?>" required>
                                
                                <div class="font-12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Category Order</b></div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                            <select name="category_sort" id="category_sort" class="form-control show-tick">
                                                <option value="cid" <?php if($settings_row['category_sort']=='cid'){?> selected <?php } ?> >ID</option>
                                                <option value="category_name" <?php if($settings_row['category_sort']=='category_name'){?> selected <?php }?> >Name</option>
                                            </select>
                                            <div class="help-info pull-left"><font color="#337ab7">Order by ID or Category Name</font></div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                            <select name="category_order" id="category_order" class="form-control show-tick">
                                                <option value="ASC" <?php if($settings_row['category_order']=='ASC'){ ?> selected <?php } ?> >ASC</option>
                                                <option value="DESC" <?php if($settings_row['category_order']=='DESC'){?> selected <?php }?> >DESC</option>
                                            </select>
                                            <div class="help-info pull-left"><font color="#337ab7">Ascending or Descending</font></div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="form-group">
                                            <div class="font-12"><b>Push Notification Provider</b></div>
                                                <select class="form-control show-tick" name="providers" id="providers">
                                                        <?php if ($settings_row['providers'] == 'onesignal') { ?>
                                                            <option value="onesignal" selected="selected">OneSignal</option>
                                                            <option value="firebase">Firebase Cloud Messaging (FCM)</option>
                                                        <?php } else { ?>
                                                            <option value="onesignal">OneSignal</option>
                                                            <option value="firebase" selected="selected">Firebase Cloud Messaging (FCM)</option>
                                                        <?php } ?>
                                                </select>
                                        <div class="help-info pull-left"><font color="#337ab7">Choose your provider for sending push notification</font></div>
                                    </div>
                                </div>

                                <input type="hidden" class="form-control" name="fcm_server_key" id="fcm_server_key" value="<?php echo $settings_row['fcm_server_key'];?>" required>

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <div class="font-12"><b>FCM Notification Topic</b></div>
                                            <input type="text" class="form-control" name="fcm_notification_topic" id="fcm_notification_topic" value="<?php echo $settings_row['fcm_notification_topic'];?>" required>
                                        </div>
                                        <div class="help-info pull-left"><a href="" data-toggle="modal" data-target="#modal-notifications">FCM notification topic must be written in lowercase without space (use underscore)</a></div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <div class="font-12"><b>OneSignal APP ID</b></div>
                                            <input type="text" class="form-control" name="onesignal_app_id" id="onesignal_app_id" value="<?php echo $settings_row['onesignal_app_id'];?>" required>
                                        </div>
                                        <div class="help-info pull-left"><a href="" data-toggle="modal" data-target="#modal-onesignal">Where do I get my OneSignal app id?</a></div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <div class="font-12"><b>OneSignal Rest API Key</b></div>
                                            <input type="text" class="form-control" name="onesignal_rest_api_key" id="onesignal_rest_api_key" value="<?php echo $settings_row['onesignal_rest_api_key'];?>" required>
                                        </div>
                                        <div class="help-info pull-left"><a href="" data-toggle="modal" data-target="#modal-onesignal">Where do I get my OneSignal Rest API Key?</a></div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <div class="font-12"><b>More Apps Url</b></div>
                                            <input type="text" class="form-control" name="more_apps_url" id="more_apps_url" value="<?php echo $settings_row['more_apps_url'];?>" required>
                                        </div>
                                        <div class="help-info pull-left"><font color="#337ab7">More apps url for other apps</font></div>
                                    </div>
                                </div> 
                            
                                <div class="col-sm-12">
                                    
                                    <div class="form-group">
                                        <div class="form-line">
                                            <div class="font-12 ex1"><b>Privacy Policy</b></div>
                                            <textarea class="form-control" name="privacy_policy" id="privacy_policy" class="form-control" cols="60" rows="10" required><?php echo $settings_row['privacy_policy'];?></textarea>

                                            <?php if ($ENABLE_RTL_MODE == 'true') { ?>
                                            <script>                             
                                                CKEDITOR.replace( 'privacy_policy' );
                                                CKEDITOR.config.contentsLangDirection = 'rtl';
                                            </script>
                                            <?php } else { ?>
                                            <script>                             
                                                CKEDITOR.replace( 'privacy_policy' );
                                                CKEDITOR.config.height = 400; 
                                            </script>
                                            <?php } ?>

                                        </div>
                                    </div>
                                </div>                                                            

                            </div>
                        </div>
                    </div>
                    </form>

                </div>
            </div>
            
        </div>

    </section>

<?php include_once('includes/footer.php'); ?>