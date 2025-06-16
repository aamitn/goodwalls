<?php include_once('includes/header.php'); ?>

<?php

    $ID =  clean("1");

    $sql = "SELECT * FROM tbl_ads WHERE id = '$ID'";
    $result = $connect->query($sql);
    $settings_row = $result->fetch_assoc();

    if (isset($_POST['submit'])) {

        $primaryAd = $_POST['ad_type'];
        $backupAd = $_POST['backup_ads'];

        //primary ads
        if ($primaryAd == 'admob') {
            $adMobPublisherId = clean($_POST['admob_publisher_id']);
            $adMobAppId = clean($_POST['admob_app_id']);
            $AdMobBannerId = clean($_POST['admob_banner_unit_id']);
            $AdMobInterstitialId = clean($_POST['admob_interstitial_unit_id']);
            $AdMobRewardedId = clean($_POST['admob_rewarded_unit_id']);
            $AdMobNativeId = clean($_POST['admob_native_unit_id']);
            $AdMobAppOpenId = clean($_POST['admob_app_open_ad_unit_id']);

            $dataPrimaryAds = array(
                'admob_publisher_id' => $adMobPublisherId,
                'admob_app_id' => $adMobAppId,
                'admob_banner_unit_id' => $AdMobBannerId,
                'admob_interstitial_unit_id' => $AdMobInterstitialId,
                'admob_rewarded_unit_id' => $AdMobRewardedId,
                'admob_native_unit_id' => $AdMobNativeId,
                'admob_app_open_ad_unit_id' => $AdMobAppOpenId
            );
        }

        if ($primaryAd == 'google_ad_manager') {
            $AdManagerBannerId = clean($_POST['ad_manager_banner_unit_id']);
            $AdManagerInterstitialId = clean($_POST['ad_manager_interstitial_unit_id']);
            $AdManagerRewardedId = clean($_POST['ad_manager_rewarded_unit_id']);
            $AdManagerNativeId = clean($_POST['ad_manager_native_unit_id']);
            $AdManagerAppOpenId = clean($_POST['ad_manager_app_open_ad_unit_id']);

            $dataPrimaryAds = array(
                'ad_manager_banner_unit_id' => $AdManagerBannerId,
                'ad_manager_interstitial_unit_id' => $AdManagerInterstitialId,
                'ad_manager_rewarded_unit_id' => $AdManagerRewardedId,
                'ad_manager_native_unit_id' => $AdManagerNativeId,
                'ad_manager_app_open_ad_unit_id' => $AdManagerAppOpenId
            );
        }

        if ($primaryAd == 'fan') {
            $fanBannerId = clean($_POST['fan_banner_unit_id']);
            $fanInterstitialId = clean($_POST['fan_interstitial_unit_id']);
            $fanRewardedId = clean($_POST['fan_rewarded_unit_id']);
            $fanNativeId = clean($_POST['fan_native_unit_id']);

            $dataPrimaryAds = array(
                'fan_banner_unit_id' => $fanBannerId,
                'fan_interstitial_unit_id' => $fanInterstitialId,
                'fan_rewarded_unit_id' => $fanRewardedId,
                'fan_native_unit_id' => $fanNativeId
            );
        }        

        if ($primaryAd == 'startapp') {
            $startAppId = clean($_POST['startapp_app_id']);

            $dataPrimaryAds = array(
                'startapp_app_id' => $startAppId
            );
        }

        if ($primaryAd == 'unity') {
            $unityGameId = clean($_POST['unity_game_id']);
            $unityBannerId = clean($_POST['unity_banner_placement_id']);
            $unityInterstitialId = clean($_POST['unity_interstitial_placement_id']);
            $unityRewardedId = clean($_POST['unity_rewarded_placement_id']);

            $dataPrimaryAds = array(
                'unity_game_id' => $unityGameId,
                'unity_banner_placement_id' => $unityBannerId,
                'unity_interstitial_placement_id' => $unityInterstitialId,
                'unity_rewarded_placement_id' => $unityRewardedId
            );
        }

        if ($primaryAd == 'applovin') {
            $appLovinBannerId = clean($_POST['applovin_banner_ad_unit_id']);
            $appLovinInterstitialId = clean($_POST['applovin_interstitial_ad_unit_id']);
            $appLovinRewardedId = clean($_POST['applovin_rewarded_ad_unit_id']);
            $appLovinNativeId = clean($_POST['applovin_native_ad_manual_unit_id']);
            $appLovinOpenAdId = clean($_POST['applovin_app_open_ad_unit_id']);

            $dataPrimaryAds = array(
                'applovin_banner_ad_unit_id'       => $appLovinBannerId,
                'applovin_interstitial_ad_unit_id' => $appLovinInterstitialId,
                'applovin_rewarded_ad_unit_id' => $appLovinRewardedId,
                'applovin_native_ad_manual_unit_id' => $appLovinNativeId,
                'applovin_app_open_ad_unit_id' => $appLovinOpenAdId
            );
        }

        if ($primaryAd == 'applovin_discovery') {
            $appLovinBannerZoneId = clean($_POST['applovin_banner_zone_id']);
            $appLovinBannerMrecZoneId = clean($_POST['applovin_banner_mrec_zone_id']);
            $appLovinInterstitialZoneId = clean($_POST['applovin_interstitial_zone_id']);
            $appLovinRewardedZoneId = clean($_POST['applovin_rewarded_zone_id']);

            $dataPrimaryAds = array(
                'applovin_banner_zone_id' => $appLovinBannerZoneId,
                'applovin_banner_mrec_zone_id' => $appLovinBannerMrecZoneId,
                'applovin_interstitial_zone_id' => $appLovinInterstitialZoneId,
                'applovin_rewarded_zone_id' => $appLovinRewardedZoneId
            );
        }

        if ($primaryAd == 'ironsource') {
            $ironSourceAppKey = clean($_POST['ironsource_app_key']);
            $ironSourceBannerPlacementName = clean($_POST['ironsource_banner_placement_name']);
            $ironSourceInterstitialPlacementName = clean($_POST['ironsource_interstitial_placement_name']);
            $ironSourceRewardedPlacementName = clean($_POST['ironsource_rewarded_placement_name']);

            $dataPrimaryAds = array(
                'ironsource_app_key' => $ironSourceAppKey,
                'ironsource_banner_placement_name' => $ironSourceBannerPlacementName,
                'ironsource_interstitial_placement_name' => $ironSourceInterstitialPlacementName,
                'ironsource_rewarded_placement_name' => $ironSourceRewardedPlacementName
            );
        }

        if ($primaryAd == 'wortise') {
            $wortiseAppId = clean($_POST['wortise_app_id']);
            $wortiseBannerId = clean($_POST['wortise_banner_unit_id']);
            $wortiseInterstitialId = clean($_POST['wortise_interstitial_unit_id']);
            $wortiseRewardedId = clean($_POST['wortise_rewarded_unit_id']);
            $wortiseNativeId = clean($_POST['wortise_native_unit_id']);
            $wortiseAppOpenId = clean($_POST['wortise_app_open_unit_id']);

            $dataPrimaryAds = array(
                'wortise_app_id' => $wortiseAppId,
                'wortise_banner_unit_id' => $wortiseBannerId,
                'wortise_interstitial_unit_id' => $wortiseInterstitialId,
                'wortise_rewarded_unit_id' => $wortiseRewardedId,
                'wortise_native_unit_id' => $wortiseNativeId,
                'wortise_app_open_unit_id' => $wortiseAppOpenId
            );
        }


        //backup ads
        if ($backupAd == 'admob') {
            $adMobPublisherId = clean($_POST['admob_publisher_id_backup']);
            $adMobAppId = clean($_POST['admob_app_id_backup']);
            $AdMobBannerId = clean($_POST['admob_banner_unit_id_backup']);
            $AdMobInterstitialId = clean($_POST['admob_interstitial_unit_id_backup']);
            $AdMobRewardedId = clean($_POST['admob_rewarded_unit_id_backup']);
            $AdMobNativeId = clean($_POST['admob_native_unit_id_backup']);
            $AdMobAppOpenId = clean($_POST['admob_app_open_ad_unit_id_backup']);

            $dataBackupAds = array(
                'admob_publisher_id' => $adMobPublisherId,
                'admob_app_id' => $adMobAppId,
                'admob_banner_unit_id' => $AdMobBannerId,
                'admob_interstitial_unit_id' => $AdMobInterstitialId,
                'admob_rewarded_unit_id' => $AdMobRewardedId,
                'admob_native_unit_id' => $AdMobNativeId,
                'admob_app_open_ad_unit_id' => $AdMobAppOpenId
            );
        }

        if ($backupAd == 'google_ad_manager') {
            $AdManagerBannerId = clean($_POST['ad_manager_banner_unit_id_backup']);
            $AdManagerInterstitialId = clean($_POST['ad_manager_interstitial_unit_id_backup']);
            $AdManagerRewardedId = clean($_POST['ad_manager_rewarded_unit_id_backup']);
            $AdManagerNativeId = clean($_POST['ad_manager_native_unit_id_backup']);
            $AdManagerAppOpenId = clean($_POST['ad_manager_app_open_ad_unit_id_backup']);

            $dataBackupAds = array(
                'ad_manager_banner_unit_id' => $AdManagerBannerId,
                'ad_manager_interstitial_unit_id' => $AdManagerInterstitialId,
                'ad_manager_rewarded_unit_id' => $AdManagerRewardedId,
                'ad_manager_native_unit_id' => $AdManagerNativeId,
                'ad_manager_app_open_ad_unit_id' => $AdManagerAppOpenId
            );
        }

        if ($backupAd == 'fan') {
            $fanBannerId = clean($_POST['fan_banner_unit_id_backup']);
            $fanInterstitialId = clean($_POST['fan_interstitial_unit_id_backup']);
            $fanRewardedId = clean($_POST['fan_rewarded_unit_id_backup']);
            $fanNativeId = clean($_POST['fan_native_unit_id_backup']);

            $dataBackupAds = array(
                'fan_banner_unit_id' => $fanBannerId,
                'fan_interstitial_unit_id' => $fanInterstitialId,
                'fan_rewarded_unit_id' => $fanRewardedId,
                'fan_native_unit_id' => $fanNativeId
            );
        }        

        if ($backupAd == 'startapp') {
            $startAppId = clean($_POST['startapp_app_id_backup']);

            $dataBackupAds = array(
                'startapp_app_id' => $startAppId
            );
        }

        if ($backupAd == 'unity') {
            $unityGameId = clean($_POST['unity_game_id_backup']);
            $unityBannerId = clean($_POST['unity_banner_placement_id_backup']);
            $unityInterstitialId = clean($_POST['unity_interstitial_placement_id_backup']);
            $unityRewardedId = clean($_POST['unity_rewarded_placement_id_backup']);

            $dataBackupAds = array(
                'unity_game_id' => $unityGameId,
                'unity_banner_placement_id' => $unityBannerId,
                'unity_interstitial_placement_id' => $unityInterstitialId,
                'unity_rewarded_placement_id' => $unityRewardedId
            );
        }

        if ($backupAd == 'applovin') {
            $appLovinBannerId = clean($_POST['applovin_banner_ad_unit_id_backup']);
            $appLovinInterstitialId = clean($_POST['applovin_interstitial_ad_unit_id_backup']);
            $appLovinRewardedId = clean($_POST['applovin_rewarded_ad_unit_id_backup']);
            $appLovinNativeId = clean($_POST['applovin_native_ad_manual_unit_id_backup']);
            $appLovinOpenAdId = clean($_POST['applovin_app_open_ad_unit_id_backup']);

            $dataBackupAds = array(
                'applovin_banner_ad_unit_id'       => $appLovinBannerId,
                'applovin_interstitial_ad_unit_id' => $appLovinInterstitialId,
                'applovin_rewarded_ad_unit_id' => $appLovinRewardedId,
                'applovin_native_ad_manual_unit_id' => $appLovinNativeId,
                'applovin_app_open_ad_unit_id' => $appLovinOpenAdId
            );
        }

        if ($backupAd == 'applovin_discovery') {
            $appLovinBannerZoneId = clean($_POST['applovin_banner_zone_id_backup']);
            $appLovinBannerMrecZoneId = clean($_POST['applovin_banner_mrec_zone_id_backup']);
            $appLovinInterstitialZoneId = clean($_POST['applovin_interstitial_zone_id_backup']);
            $appLovinRewardedZoneId = clean($_POST['applovin_rewarded_zone_id_backup']);

            $dataBackupAds = array(
                'applovin_banner_zone_id' => $appLovinBannerZoneId,
                'applovin_banner_mrec_zone_id' => $appLovinBannerMrecZoneId,
                'applovin_interstitial_zone_id' => $appLovinInterstitialZoneId,
                'applovin_rewarded_zone_id' => $appLovinRewardedZoneId
            );
        }

        if ($backupAd == 'ironsource') {
            $ironSourceAppKey = clean($_POST['ironsource_app_key_backup']);
            $ironSourceBannerPlacementName = clean($_POST['ironsource_banner_placement_name_backup']);
            $ironSourceInterstitialPlacementName = clean($_POST['ironsource_interstitial_placement_name_backup']);
            $ironSourceRewardedPlacementName = clean($_POST['ironsource_rewarded_placement_name_backup']);

            $dataBackupAds = array(
                'ironsource_app_key' => $ironSourceAppKey,
                'ironsource_banner_placement_name' => $ironSourceBannerPlacementName,
                'ironsource_interstitial_placement_name' => $ironSourceInterstitialPlacementName,
                'ironsource_rewarded_placement_name' => $ironSourceRewardedPlacementName
            );
        }

        if ($backupAd == 'wortise') {
            $wortiseAppId = clean($_POST['wortise_app_id_backup']);
            $wortiseBannerId = clean($_POST['wortise_banner_unit_id_backup']);
            $wortiseInterstitialId = clean($_POST['wortise_interstitial_unit_id_backup']);
            $wortiseRewardedId = clean($_POST['wortise_rewarded_unit_id_backup']);
            $wortiseNativeId = clean($_POST['wortise_native_unit_id_backup']);
            $wortiseAppOpenId = clean($_POST['wortise_app_open_unit_id_backup']);

            $dataBackupAds = array(
                'wortise_app_id' => $wortiseAppId,
                'wortise_banner_unit_id' => $wortiseBannerId,
                'wortise_interstitial_unit_id' => $wortiseInterstitialId,
                'wortise_rewarded_unit_id' => $wortiseRewardedId,
                'wortise_native_unit_id' => $wortiseNativeId,
                'wortise_app_open_unit_id' => $wortiseAppOpenId
            );
        }

        $dataGlobal = array(
            'ad_status' => clean($_POST['ad_status']),
            'ad_type' => $primaryAd,
            'backup_ads' => $backupAd,
            'interstitial_ad_interval' => clean($_POST['interstitial_ad_interval']),
            'native_ad_interval' => clean($_POST['native_ad_interval']),
            'native_ad_index_2' => clean($_POST['native_ad_index_2']),
            'native_ad_index_3' => clean($_POST['native_ad_index_3']),
            'native_ad_style_post_list' => clean($_POST['native_ad_style_post_list']),
            'native_ad_style_exit_dialog' => clean($_POST['native_ad_style_exit_dialog'])
        );

        $dataPlacementOn = array(
            'banner_home' => '1',
            'banner_post_details' => '1',
            'banner_category_details' => '1',
            'banner_search' => '1',
            'interstitial_post_list' => '1',
            'rewarded_post_details' => '1',
            'native_ad_post_list' => '1',
            'native_ad_exit_dialog' => '1',
            'app_open_ad_on_start' => '1',
            'app_open_ad_on_resume' => '1'
        );

        $dataPlacementOff = array(
            'banner_home' => '0',
            'banner_post_details' => '0',
            'banner_category_details' => '0',
            'banner_search' => '0',
            'interstitial_post_list' => '0',
            'rewarded_post_details' => '0',
            'native_ad_post_list' => '0',
            'native_ad_exit_dialog' => '0',
            'app_open_ad_on_start' => '0',
            'app_open_ad_on_resume' => '0'
        );

        if ($backupAd == $primaryAd) {
            $_SESSION['msg'] = "Backup Ad cannot be the same as Primary Ad and vice versa!";
            header( "Location:ads.php");
            exit;
        } else {
            $adStatus = clean($_POST['ad_status']);
            if ($settings_row['ad_status'] != $adStatus) {
                if ($adStatus == 'on') {
                    $updatePlacements = update('tbl_ads_placement', $dataPlacementOn, "WHERE ads_placement_id = '1'");
                } else if ($adStatus == 'off') {
                    $updatePlacements = update('tbl_ads_placement', $dataPlacementOff, "WHERE ads_placement_id = '1'");
                }
            }

            if ($backupAd == 'none') {
                $updateGlobal = update('tbl_ads', $dataGlobal, "WHERE id = '1'");
                $updatePrimaryAds = update('tbl_ads', $dataPrimaryAds, "WHERE id = '1'");

                if ($updateGlobal > 0 && $updatePrimaryAds > 0) {
                    $_SESSION['msg'] = "Changes Saved...";
                    header( "Location:ads.php");
                    exit;
                }
            } else {
                $updateGlobal = update('tbl_ads', $dataGlobal, "WHERE id = '1'");
                $updatePrimaryAds = update('tbl_ads', $dataPrimaryAds, "WHERE id = '1'");
                $updateBackupAds = update('tbl_ads', $dataBackupAds, "WHERE id = '1'");

                if ($updateGlobal > 0 && $updatePrimaryAds > 0 && $updateBackupAds > 0) {
                    $_SESSION['msg'] = "Changes Saved...";
                    header( "Location:ads.php");
                    exit;
                }
            }
        }

    }

?>

<?php 

    $sql_status = mysqli_query($connect, "SELECT * FROM tbl_ads_placement WHERE ads_placement_id = 1");
    $row_status = mysqli_fetch_assoc($sql_status);
    if (isset($_GET['placement']) && isset($_GET['status'])) {
        $placement = clean($_GET['placement']);
        $status = clean($_GET['status']);
        if ($status == 1) {
            $update = "UPDATE tbl_ads_placement SET $placement = 0";
            if (mysqli_query($connect, $update)) {
                header( "Location:ads.php");
                exit;
            }
        } else {
            $update = "UPDATE tbl_ads_placement SET $placement = 1";
            if (mysqli_query($connect, $update)) {
                header( "Location:ads.php");
                exit;
            }
        }
    }

?>

<script src="assets/js/ads-6.0.0.js"></script>

<section class="content">

    <ol class="breadcrumb">
        <li><a href="dashboard.php">Dashboard</a></li>
        <li class="active">Manage Ads</a></li>
    </ol>

    <div class="container-fluid">

        <div class="row clearfix">

            <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">

                <form method="post" enctype="multipart/form-data">

                    <div class="card corner-radius">
                        <div class="header">
                            <h2>MANAGE ADS</h2>
                            <div class="header-dropdown m-r--5">
                                <button type="submit" name="submit" class="button button-rounded btn-offset waves-effect waves-float">UPDATE</button>
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

                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="font-12">Ad Status</div>
                                        <select class="form-control show-tick" name="ad_status" id="ad_status">
                                            <?php if ($settings_row['ad_status'] == 'on') { ?>
                                            <option value="on" selected="selected">ON</option>
                                            <option value="off">OFF</option>
                                            <?php } else { ?>
                                            <option value="on">ON</option>
                                            <option value="off" selected="selected">OFF</option>
                                            <?php } ?>
                                        </select>
                                    </div>
                                </div>

                                <div id="ad_status_on">

                                    <!-- primary ads -->
                                    <div class="col-sm-12" style="font-size: 16px;"><b>PRIMARY ADS</b></div>

                                    <div id="primary_ads" class="col-md-12">
                                        <div class="form-group">
                                            <div class="">
                                                <div class="font-12">Primary Ad Network</div>
                                                <select class="form-control show-tick" name="ad_type" id="ad_type">
                                                    <?php if ($settings_row['ad_type'] == 'admob') { ?>
                                                        <option value="admob" selected="selected">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'startapp') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp" selected="selected">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'unity') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity" selected="selected">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'applovin') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin" selected="selected">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'applovin_discovery') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery" selected="selected">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'ironsource') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource" selected="selected">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'google_ad_manager') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager" selected="selected">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'fan') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan" selected="selected">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['ad_type'] == 'wortise') { ?>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise" selected="selected">Wortise</option>
                                                    <?php } ?>
                                                </select>
                                                <div class="help-info pull-left"><font color="#337ab7">The main ads you want to use and display in the application.</font></div>
                                            </div>
                                        </div>
                                        <br>

                                        <div id="admob_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Publisher ID</div>
                                                        <input type="text" class="form-control" name="admob_publisher_id" id="admob_publisher_id" value="<?php echo $settings_row['admob_publisher_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob App ID</div>
                                                        <div class="ex2" style="margin-top: 0px;">Important : Your <b>AdMob App ID</b> must be added programmatically inside Android Studio Project in the <b>AndroidManifest.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-admob-app-id"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                    </div>
                                                    <input type="hidden" class="form-control" name="admob_app_id" id="admob_app_id" value="<?php echo $settings_row['admob_app_id'];?>" required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_banner_unit_id" id="admob_banner_unit_id" value="<?php echo $settings_row['admob_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_interstitial_unit_id" id="admob_interstitial_unit_id" value="<?php echo $settings_row['admob_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_rewarded_unit_id" id="admob_rewarded_unit_id" value="<?php echo $settings_row['admob_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_native_unit_id" id="admob_native_unit_id" value="<?php echo $settings_row['admob_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_app_open_ad_unit_id" id="admob_app_open_ad_unit_id" value="<?php echo $settings_row['admob_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="ad_manager_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager App ID</div>
                                                        <div class="ex2" style="margin-top: 0px;">Important : Your <b>Ad Manager App ID</b> must be added programmatically inside Android Studio Project in the <b>AndroidManifest.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-admob-app-id"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_banner_unit_id" id="ad_manager_banner_unit_id" value="<?php echo $settings_row['ad_manager_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_interstitial_unit_id" id="ad_manager_interstitial_unit_id" value="<?php echo $settings_row['ad_manager_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_rewarded_unit_id" id="ad_manager_rewarded_unit_id" value="<?php echo $settings_row['ad_manager_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_native_unit_id" id="ad_manager_native_unit_id" value="<?php echo $settings_row['ad_manager_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_app_open_ad_unit_id" id="ad_manager_app_open_ad_unit_id" value="<?php echo $settings_row['ad_manager_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="fan_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Banner Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_banner_unit_id" id="fan_banner_unit_id" value="<?php echo $settings_row['fan_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Interstitial Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_interstitial_unit_id" id="fan_interstitial_unit_id" value="<?php echo $settings_row['fan_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Rewarded Video Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_rewarded_unit_id" id="fan_rewarded_unit_id" value="<?php echo $settings_row['fan_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Native Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_native_unit_id" id="fan_native_unit_id" value="<?php echo $settings_row['fan_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="startapp_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">StartApp App ID</div>
                                                        <input type="text" class="form-control" name="startapp_app_id" id="startapp_app_id" value="<?php echo $settings_row['startapp_app_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="unity_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Game ID</div>
                                                        <input type="text" class="form-control" name="unity_game_id" id="unity_game_id" value="<?php echo $settings_row['unity_game_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Banner Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_banner_placement_id" id="unity_banner_placement_id" value="<?php echo $settings_row['unity_banner_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Interstitial Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_interstitial_placement_id" id="unity_interstitial_placement_id" value="<?php echo $settings_row['unity_interstitial_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Rewarded Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_rewarded_placement_id" id="unity_rewarded_placement_id" value="<?php echo $settings_row['unity_rewarded_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="applovin_max_ads">

                                            <div class="form-group">
                                                <div class="form-line">
                                                    <div class="font-12">AppLovin SDK Key</div>
                                                    <div class="ex2">Important : Your <b>AppLovin SDK Key</b> must be added programmatically inside Android Studio Project in the <b>res/value/ads.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-applovin-sdk-key"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_ad_unit_id" id="applovin_banner_ad_unit_id" value="<?php echo $settings_row['applovin_banner_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Interstitial Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_interstitial_ad_unit_id" id="applovin_interstitial_ad_unit_id" value="<?php echo $settings_row['applovin_interstitial_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Rewarded Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_rewarded_ad_unit_id" id="applovin_rewarded_ad_unit_id" value="<?php echo $settings_row['applovin_rewarded_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Native Ad (Manual) ID</div>
                                                        <input type="text" class="form-control" name="applovin_native_ad_manual_unit_id" id="applovin_native_ad_manual_unit_id" value="<?php echo $settings_row['applovin_native_ad_manual_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin App Open Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_app_open_ad_unit_id" id="applovin_app_open_ad_unit_id" value="<?php echo $settings_row['applovin_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="applovin_discovery_ads">

                                            <div class="form-group">
                                                <div class="form-line">
                                                    <div class="font-12">AppLovin SDK Key</div>
                                                    <div class="ex2">Important : Your <b>AppLovin SDK Key</b> must be added programmatically inside Android Studio Project in the <b>res/value/ads.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-applovin-sdk-key"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_zone_id" id="applovin_banner_zone_id" value="<?php echo $settings_row['applovin_banner_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner (MREC) Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_mrec_zone_id" id="applovin_banner_mrec_zone_id" value="<?php echo $settings_row['applovin_banner_mrec_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Interstitial Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_interstitial_zone_id" id="applovin_interstitial_zone_id" value="<?php echo $settings_row['applovin_interstitial_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Rewarded Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_rewarded_zone_id" id="applovin_rewarded_zone_id" value="<?php echo $settings_row['applovin_rewarded_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="ironsource_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource App Key</div>
                                                        <input type="text" class="form-control" name="ironsource_app_key" id="ironsource_app_key" value="<?php echo $settings_row['ironsource_app_key'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Banner Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_banner_placement_name" id="ironsource_banner_placement_name" value="<?php echo $settings_row['ironsource_banner_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Interstitial Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_interstitial_placement_name" id="ironsource_interstitial_placement_name" value="<?php echo $settings_row['ironsource_interstitial_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Rewarded Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_rewarded_placement_name" id="ironsource_rewarded_placement_name" value="<?php echo $settings_row['ironsource_rewarded_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="wortise_ads">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise App ID</div>
                                                        <input type="text" class="form-control" name="wortise_app_id" id="wortise_app_id" value="<?php echo $settings_row['wortise_app_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_banner_unit_id" id="wortise_banner_unit_id" value="<?php echo $settings_row['wortise_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_interstitial_unit_id" id="wortise_interstitial_unit_id" value="<?php echo $settings_row['wortise_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_rewarded_unit_id" id="wortise_rewarded_unit_id" value="<?php echo $settings_row['wortise_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_native_unit_id" id="wortise_native_unit_id" value="<?php echo $settings_row['wortise_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_app_open_unit_id" id="wortise_app_open_unit_id" value="<?php echo $settings_row['wortise_app_open_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                    </div>


                                    <!-- secondary ads -->
                                    <div class="col-sm-12" style="font-size: 16px;"><b>BACKUP ADS</b></div>
                                    <div id="secondary_ads" class="col-md-12">
                                        <div class="form-group">
                                            <div class="">
                                                <div class="font-12">Backup Ads</div>
                                                <select class="form-control show-tick" name="backup_ads" id="backup_ads">
                                                    <?php if ($settings_row['backup_ads'] == 'admob') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob" selected="selected">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'startapp') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp" selected="selected">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'unity') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity" selected="selected">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'applovin') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin" selected="selected">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'applovin_discovery') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery" selected="selected">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'ironsource') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource" selected="selected">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'google_ad_manager') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager" selected="selected">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'fan') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan" selected="selected">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } else if ($settings_row['backup_ads'] == 'wortise') { ?>
                                                        <option value="none">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise" selected="selected">Wortise</option>
                                                    <?php } else { ?>
                                                        <option value="none" selected="selected">None</option>
                                                        <option value="admob">AdMob</option>
                                                        <option value="google_ad_manager">Google Ad Manager</option>
                                                        <option value="startapp">StartApp</option>
                                                        <option value="unity">Unity Ads</option>
                                                        <option value="applovin_discovery">AppLovin Discovery</option>
                                                        <option value="applovin">AppLovin MAX</option>
                                                        <option value="ironsource">ironSource</option>
                                                        <option value="fan">Meta Audience Network</option>
                                                        <option value="wortise">Wortise</option>
                                                    <?php } ?>
                                                </select>
                                            </div>
                                            <div class="help-info pull-left"><font color="#337ab7">The backup ads you want to show if your main ad fails to show due to limits, banned, etc.</font></div>
                                        </div>
                                        <br>

                                        <div id="none_ads_backup">

                                        </div>

                                        <div id="admob_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Publisher ID</div>
                                                        <input type="text" class="form-control" name="admob_publisher_id_backup" id="admob_publisher_id_backup" value="<?php echo $settings_row['admob_publisher_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob App ID</div>
                                                        <div class="ex2" style="margin-top: 0px;">Important : Your <b>AdMob App ID</b> must be added programmatically inside Android Studio Project in the <b>AndroidManifest.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-admob-app-id"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                    </div>
                                                    <input type="hidden" class="form-control" name="admob_app_id_backup" id="admob_app_id_backup" value="<?php echo $settings_row['admob_app_id'];?>" required>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_banner_unit_id_backup" id="admob_banner_unit_id_backup" value="<?php echo $settings_row['admob_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_interstitial_unit_id_backup" id="admob_interstitial_unit_id_backup" value="<?php echo $settings_row['admob_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_rewarded_unit_id_backup" id="admob_rewarded_unit_id_backup" value="<?php echo $settings_row['admob_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_native_unit_id_backup" id="admob_native_unit_id_backup" value="<?php echo $settings_row['admob_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AdMob App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="admob_app_open_ad_unit_id_backup" id="admob_app_open_ad_unit_id_backup" value="<?php echo $settings_row['admob_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="ad_manager_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager App ID</div>
                                                        <div class="ex2" style="margin-top: 0px;">Important : Your <b>Ad Manager App ID</b> must be added programmatically inside Android Studio Project in the <b>AndroidManifest.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-admob-app-id"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_banner_unit_id_backup" id="ad_manager_banner_unit_id_backup" value="<?php echo $settings_row['ad_manager_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_interstitial_unit_id_backup" id="ad_manager_interstitial_unit_id_backup" value="<?php echo $settings_row['ad_manager_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_rewarded_unit_id_backup" id="ad_manager_rewarded_unit_id_backup" value="<?php echo $settings_row['ad_manager_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_native_unit_id_backup" id="ad_manager_native_unit_id_backup" value="<?php echo $settings_row['ad_manager_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Ad Manager App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="ad_manager_app_open_ad_unit_id_backup" id="ad_manager_app_open_ad_unit_id_backup" value="<?php echo $settings_row['ad_manager_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="fan_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Banner Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_banner_unit_id_backup" id="fan_banner_unit_id_backup" value="<?php echo $settings_row['fan_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Interstitial Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_interstitial_unit_id_backup" id="fan_interstitial_unit_id_backup" value="<?php echo $settings_row['fan_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Rewarded Video Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_rewarded_unit_id_backup" id="fan_rewarded_unit_id_backup" value="<?php echo $settings_row['fan_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Audience Network Native Placement Name</div>
                                                        <input type="text" class="form-control" name="fan_native_unit_id_backup" id="fan_native_unit_id_backup" value="<?php echo $settings_row['fan_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="startapp_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">StartApp App ID</div>
                                                        <input type="text" class="form-control" name="startapp_app_id_backup" id="startapp_app_id_backup" value="<?php echo $settings_row['startapp_app_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="unity_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Game ID</div>
                                                        <input type="text" class="form-control" name="unity_game_id_backup" id="unity_game_id_backup" value="<?php echo $settings_row['unity_game_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Banner Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_banner_placement_id_backup" id="unity_banner_placement_id_backup" value="<?php echo $settings_row['unity_banner_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Interstitial Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_interstitial_placement_id_backup" id="unity_interstitial_placement_id_backup" value="<?php echo $settings_row['unity_interstitial_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Unity Rewarded Ad Placement ID</div>
                                                        <input type="text" class="form-control" name="unity_rewarded_placement_id_backup" id="unity_rewarded_placement_id_backup" value="<?php echo $settings_row['unity_rewarded_placement_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="applovin_max_ads_backup">

                                            <div class="form-group">
                                                <div class="form-line">
                                                    <div class="font-12">AppLovin SDK Key</div>
                                                    <div class="ex2">Important : Your <b>AppLovin SDK Key</b> must be added programmatically inside Android Studio Project in the <b>res/value/ads.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-applovin-sdk-key"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_ad_unit_id_backup" id="applovin_banner_ad_unit_id_backup" value="<?php echo $settings_row['applovin_banner_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Interstitial Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_interstitial_ad_unit_id_backup" id="applovin_interstitial_ad_unit_id_backup" value="<?php echo $settings_row['applovin_interstitial_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Rewarded Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_rewarded_ad_unit_id_backup" id="applovin_rewarded_ad_unit_id_backup" value="<?php echo $settings_row['applovin_rewarded_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Native Ad (Manual) ID</div>
                                                        <input type="text" class="form-control" name="applovin_native_ad_manual_unit_id_backup" id="applovin_native_ad_manual_unit_id_backup" value="<?php echo $settings_row['applovin_native_ad_manual_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin App Open Ad ID</div>
                                                        <input type="text" class="form-control" name="applovin_app_open_ad_unit_id_backup" id="applovin_app_open_ad_unit_id_backup" value="<?php echo $settings_row['applovin_app_open_ad_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="applovin_discovery_ads_backup">

                                            <div class="form-group">
                                                <div class="form-line">
                                                    <div class="font-12">AppLovin SDK Key</div>
                                                    <div class="ex2">Important : Your <b>AppLovin SDK Key</b> must be added programmatically inside Android Studio Project in the <b>res/value/ads.xml</b>&nbsp;&nbsp;<a href="" data-toggle="modal" data-target="#modal-applovin-sdk-key"><i class="material-icons" style="vertical-align: -6px;">launch</i></a></div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_zone_id_backup" id="applovin_banner_zone_id_backup" value="<?php echo $settings_row['applovin_banner_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Banner (MREC) Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_banner_mrec_zone_id_backup" id="applovin_banner_mrec_zone_id_backup" value="<?php echo $settings_row['applovin_banner_mrec_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Interstitial Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_interstitial_zone_id_backup" id="applovin_interstitial_zone_id_backup" value="<?php echo $settings_row['applovin_interstitial_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">AppLovin Rewarded Zone ID</div>
                                                        <input type="text" class="form-control" name="applovin_rewarded_zone_id_backup" id="applovin_rewarded_zone_id_backup" value="<?php echo $settings_row['applovin_rewarded_zone_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="ironsource_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource App Key</div>
                                                        <input type="text" class="form-control" name="ironsource_app_key_backup" id="ironsource_app_key_backup" value="<?php echo $settings_row['ironsource_app_key'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Banner Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_banner_placement_name_backup" id="ironsource_banner_placement_name_backup" value="<?php echo $settings_row['ironsource_banner_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Interstitial Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_interstitial_placement_name_backup" id="ironsource_interstitial_placement_name_backup" value="<?php echo $settings_row['ironsource_interstitial_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">ironSource Rewarded Placement Name</div>
                                                        <input type="text" class="form-control" name="ironsource_rewarded_placement_name_backup" id="ironsource_rewarded_placement_name_backup" value="<?php echo $settings_row['ironsource_rewarded_placement_name'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                        <div id="wortise_ads_backup">

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise App ID</div>
                                                        <input type="text" class="form-control" name="wortise_app_id_backup" id="wortise_app_id_backup" value="<?php echo $settings_row['wortise_app_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Banner Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_banner_unit_id_backup" id="wortise_banner_unit_id_backup" value="<?php echo $settings_row['wortise_banner_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Interstitial Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_interstitial_unit_id_backup" id="wortise_interstitial_unit_id_backup" value="<?php echo $settings_row['wortise_interstitial_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Rewarded Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_rewarded_unit_id_backup" id="wortise_rewarded_unit_id_backup" value="<?php echo $settings_row['wortise_rewarded_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise Native Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_native_unit_id_backup" id="wortise_native_unit_id_backup" value="<?php echo $settings_row['wortise_native_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="">
                                                    <div class="form-line">
                                                        <div class="font-12">Wortise App Open Ad Unit ID</div>
                                                        <input type="text" class="form-control" name="wortise_app_open_unit_id_backup" id="wortise_app_open_unit_id_backup" value="<?php echo $settings_row['wortise_app_open_unit_id'];?>" required>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>

                                    </div>

                                    <div class="col-sm-12" style="font-size: 16px;"><b>GLOBAL CONFIGURATION</b></div>

                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="form-line">
                                                <div class="font-12">Interstitial Ad Interval</div>
                                                <input type="number" class="form-control" name="interstitial_ad_interval" id="interstitial_ad_interval" value="<?php echo $settings_row['interstitial_ad_interval'];?>" required>
                                            </div>
                                            <div class="help-info pull-left"><font color="#337ab7">Displays Interstitial Ad every X clicks in the post list.</font></div>
                                        </div>    
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="form-line">
                                                <div class="font-12">Native Ad Index 2 Columns</div>
                                                <input type="number" class="form-control" name="native_ad_index_2" id="native_ad_index_2" value="<?php echo $settings_row['native_ad_index_2'];?>" required>
                                            </div>
                                            <div class="help-info pull-left"><font color="#337ab7">The position of the first Native Ad displayed on the wallpaper grid 2 columns, the index starts from 0</font></div> 
                                        </div>    
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="form-line">
                                                <div class="font-12">Native Ad Index 3 Columns</div>
                                                <input type="number" class="form-control" name="native_ad_index_3" id="native_ad_index_3" value="<?php echo $settings_row['native_ad_index_3'];?>" required>
                                            </div>
                                            <div class="help-info pull-left"><font color="#337ab7">The position of the first Native Ad displayed on the wallpaper grid 3 columns, the index starts from 0</font></div> 
                                        </div>    
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="font-12">Native Ad Style Post List</div>
                                            <select class="form-control show-tick" name="native_ad_style_post_list" id="native_ad_style_post_list">
                                                <?php if ($settings_row['native_ad_style_post_list'] == 'small') { ?>
                                                <option value="small" selected="selected">Small</option>
                                                <option value="medium">Medium</option>
                                                <option value="large">Large</option>
                                                <?php } else if ($settings_row['native_ad_style_post_list'] == 'medium') { ?>
                                                <option value="small">Small</option>
                                                <option value="medium" selected="selected">Medium</option>
                                                <option value="large">Large</option>
                                                <?php } else { ?>
                                                <option value="small">Small</option>
                                                <option value="medium">Medium</option>
                                                <option value="large" selected="selected">Large</option>
                                                <?php } ?>
                                            </select>
                                        </div>    
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="font-12">Native Ad Style Exit Dialog</div>
                                            <select class="form-control show-tick" name="native_ad_style_exit_dialog" id="native_ad_style_exit_dialog">
                                                <?php if ($settings_row['native_ad_style_exit_dialog'] == 'small') { ?>
                                                <option value="small" selected="selected">Small</option>
                                                <option value="medium">Medium</option>
                                                <option value="large">Large</option>
                                                <?php } else if ($settings_row['native_ad_style_exit_dialog'] == 'medium') { ?>
                                                <option value="small">Small</option>
                                                <option value="medium" selected="selected">Medium</option>
                                                <option value="large">Large</option>
                                                <?php } else { ?>
                                                <option value="small">Small</option>
                                                <option value="medium">Medium</option>
                                                <option value="large" selected="selected">Large</option>
                                                <?php } ?>
                                            </select>
                                        </div>    
                                    </div>

                                    <input type="hidden" class="form-control" name="native_ad_interval" id="native_ad_interval" value="<?php echo $settings_row['native_ad_interval'];?>" required>

                                    <div class="col-sm-12" style="font-size: 16px; margin-top: 20px;"><b>INFORMATION</b></div>

                                    <div class="row clearfix" style="padding-right: 50px;">

                                        <ol>
                                            <li><b>Facebook Audience Network</b> will only use bidding for Android apps starting September 30, 2021. Placement ID from Waterfall are <b>deprecated</b> now. So, put Audience Network placement ID from admin panel is no longer used, you need to setup for Bidding with Partner Mediation.</li>
                                            <br>

                                            <li>If you choose to use <b>Facebook Audience Network</b> Open Bidding, please select <b>Ad Network Type</b> to be <b>AdMob</b>, <b>Google Ad Manager</b>, <b>AppLovin MAX</b> or <b>IronSource</b>, These Ad Networks support being a Mediation Partner although it's still a Beta version.</li>
                                            <br>

                                            <li><b>AdMob</b> as a Bidding Mediation Partner for Audience Network :
                                                <br>* The official documentation can be seen <a href="https://developers.facebook.com/docs/audience-network/bidding/partner-mediation/admob" target="_blank"><b>Here</b></a>
                                                <br>* See AdMob's guidance on how to set up for <a href="https://developers.google.com/admob/android/mediation/meta" target="_blank"><b>Android</b></a> (Step 1 & 2)
                                            </li>
                                            <br>

                                            <li><b>Google Ad Manager</b> as a Bidding Mediation Partner for Audience Network :
                                                <br>* The official documentation can be seen <a href="https://developers.facebook.com/docs/audience-network/bidding/partner-mediation/ad-manager" target="_blank"><b>Here</b></a>
                                                <br>* See Google Ad Manager's guidance on how to set up for <a href="https://developers.google.com/ad-manager/mobile-ads-sdk/android/mediation/meta" target="_blank"><b>Android</b></a> (Step 1 & 2)
                                            </li>
                                            <br>

                                            <li><b>AppLovin MAX</b> as a Bidding Mediation Partner for Audience Network :
                                                <br>* The official documentation can be seen <a href="https://developers.facebook.com/docs/audience-network/bidding/partner-mediation/max" target="_blank"><b>Here</b></a>
                                                <br>* See AppLovin MAX guidance on how to set up for <a href="https://dash.applovin.com/documentation/mediation/android/mediation-setup/facebook" target="_blank"><b>Android</b></a>
                                            </li>
                                            <br>

                                            <li><b>ironSource</b> as a Bidding Mediation Partner for Audience Network :
                                                <br>* The official documentation can be seen <a href="https://developers.facebook.com/docs/audience-network/bidding/partner-mediation/ironsource" target="_blank"><b>Here</b></a>
                                                <br>* See ironSource's guidance on how to set up for <a href="https://developers.is.com/ironsource-mobile/android/facebook-mediation-guide/" target="_blank"><b>Android</b></a>
                                            </li>
                                            <br>

                                            <li>Supported Ad Formats :
                                                <br>* <b>AdMob</b> : Banner, Interstitial, Rewarded Video, Native, App Open Ad
                                                <br>* <b>Google Ad Manager</b> : Banner, Interstitial, Rewarded Video, Native, App Open Ad
                                                <br>* <b>StartApp</b> : Banner, Interstitial, Rewarded Video, Native Ad
                                                <br>* <b>Unity Ads</b> : Banner, Interstitial Ad, Rewarded Video
                                                <br>* <b>AppLovin Discovery</b> : Banner, MREC, Interstitial, Rewarded Video Ad
                                                <br>* <b>AppLovin MAX</b> : Banner, Interstitial, Rewarded Video, Native, App Open Ad
                                                <br>* <b>ironSource</b> : Banner, Rewarded Video, Interstitial
                                                <br>* <b>Audience Network</b> : Waterfall & Open Bidding (AdMob, Google Ad Manager, AppLovin MAX or ironSource as mediation partner)
                                                <br>* <b>Wortise</b> : Banner, Interstitial, Rewarded Video, Native, App Open Ad
                                            </li>

                                        </ol>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </form>

            </div>

            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                <div class="card corner-radius">
                    <div class="header">
                        <h2>ADS PLACEMENT</h2>
                    </div>
                    <div class="body">
                        <div class="row clearfix">
                            <div class="col-sm-12">
                                <div class="font-12 ex1">&nbsp;&nbsp;Enable or Disable Certain Ads Format Separately</div>
                                <table class="table-condensed">

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=banner_home&status=<?php echo $row_status['banner_home']; ?>">
                                        <td>
                                            <?php if ($row_status['banner_home'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Banner Ad on Home Page</td>
                                    </tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=banner_post_details&status=<?php echo $row_status['banner_post_details']; ?>">
                                        <td>
                                            <?php if ($row_status['banner_post_details'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Banner Ad on Post Details</td>
                                    </tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=banner_category_details&status=<?php echo $row_status['banner_category_details']; ?>">
                                        <td>
                                            <?php if ($row_status['banner_category_details'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Banner Ad on Category Details</td>
                                    </tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=banner_search&status=<?php echo $row_status['banner_search']; ?>">
                                        <td>
                                            <?php if ($row_status['banner_search'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Banner Ad on Search Page</td>
                                    </tr>

                                    <tr><td></td></tr>
                                    <tr><td></td></tr>


                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=interstitial_post_list&status=<?php echo $row_status['interstitial_post_list']; ?>">
                                        <td>
                                            <?php if ($row_status['interstitial_post_list'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Interstitial Ad on Post List</td>
                                    </tr>

                                    <tr><td></td></tr>
                                    <tr><td></td></tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=rewarded_post_details&status=<?php echo $row_status['rewarded_post_details']; ?>">
                                        <td>
                                            <?php if ($row_status['rewarded_post_details'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Rewarded on Post Details</td>
                                    </tr>

                                    <tr><td></td></tr>
                                    <tr><td></td></tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=native_ad_post_list&status=<?php echo $row_status['native_ad_post_list']; ?>">
                                        <td>
                                            <?php if ($row_status['native_ad_post_list'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Native Ad on Post List & Details</td>
                                    </tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=native_ad_exit_dialog&status=<?php echo $row_status['native_ad_exit_dialog']; ?>">
                                        <td>
                                            <?php if ($row_status['native_ad_exit_dialog'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>Native Ad on Exit Dialog</td>
                                    </tr>

                                    <tr><td></td></tr>
                                    <tr><td></td></tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=app_open_ad_on_start&status=<?php echo $row_status['app_open_ad_on_start']; ?>">
                                        <td>
                                            <?php if ($row_status['app_open_ad_on_start'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>App Open Ad on Start</td>
                                    </tr>

                                    <tr class="clickable-row pointer-view" data-href="ads.php?placement=app_open_ad_on_resume&status=<?php echo $row_status['app_open_ad_on_resume']; ?>">
                                        <td>
                                            <?php if ($row_status['app_open_ad_on_resume'] == 1) { ?>
                                            <i class="material-icons ext2" style="color:#4bae4f">check_box</i>
                                            <?php } else { ?>
                                            <i class="material-icons ext2">check_box_outline_blank</i>
                                            <?php } ?>
                                        </td>
                                        <td>App Open Ad on Resume</td>
                                    </tr>

                                </table>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>

    </div>

</section>

<?php include_once('includes/footer.php'); ?>