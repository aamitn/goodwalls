-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 30, 2023 at 01:49 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `material_wallpaper_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin`
--

CREATE TABLE `tbl_admin` (
  `id` int(11) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `user_role` enum('100','101','102') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_admin`
--

INSERT INTO `tbl_admin` (`id`, `username`, `password`, `email`, `full_name`, `user_role`) VALUES
(1, 'admin', 'd82494f05d6917ba02f7aaa29689ccb444bb73f20380876cb05d1f37537b7892', 'youremail@gmail.com', 'Administrator', '100');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ads`
--

CREATE TABLE `tbl_ads` (
  `id` int(11) NOT NULL,
  `ad_status` varchar(5) NOT NULL DEFAULT 'on',
  `ad_type` varchar(45) NOT NULL DEFAULT 'admob',
  `backup_ads` varchar(45) NOT NULL DEFAULT 'none',
  `admob_publisher_id` varchar(45) NOT NULL DEFAULT '0',
  `admob_app_id` varchar(255) NOT NULL DEFAULT '0',
  `admob_banner_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `admob_interstitial_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `admob_rewarded_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `admob_native_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `admob_app_open_ad_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `fan_banner_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `fan_interstitial_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `fan_rewarded_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `fan_native_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `startapp_app_id` varchar(255) NOT NULL DEFAULT '0',
  `unity_game_id` varchar(45) NOT NULL DEFAULT '0',
  `unity_banner_placement_id` varchar(45) NOT NULL DEFAULT '0',
  `unity_interstitial_placement_id` varchar(45) NOT NULL DEFAULT '0',
  `unity_rewarded_placement_id` varchar(255) NOT NULL DEFAULT '0',
  `applovin_banner_ad_unit_id` varchar(45) NOT NULL DEFAULT '0',
  `applovin_interstitial_ad_unit_id` varchar(45) NOT NULL DEFAULT '0',
  `applovin_rewarded_ad_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `applovin_native_ad_manual_unit_id` varchar(45) NOT NULL DEFAULT '0',
  `applovin_app_open_ad_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `applovin_banner_zone_id` varchar(45) NOT NULL DEFAULT '0',
  `applovin_banner_mrec_zone_id` varchar(255) NOT NULL DEFAULT '0',
  `applovin_interstitial_zone_id` varchar(45) NOT NULL DEFAULT '0',
  `applovin_rewarded_zone_id` varchar(255) NOT NULL DEFAULT '0',
  `ad_manager_banner_unit_id` varchar(255) NOT NULL DEFAULT '/6499/example/banner',
  `ad_manager_interstitial_unit_id` varchar(255) NOT NULL DEFAULT '/6499/example/interstitial',
  `ad_manager_rewarded_unit_id` varchar(255) NOT NULL DEFAULT '0',
  `ad_manager_native_unit_id` varchar(255) NOT NULL DEFAULT '/6499/example/native',
  `ad_manager_app_open_ad_unit_id` varchar(255) NOT NULL DEFAULT '/6499/example/app-open',
  `ironsource_app_key` varchar(255) NOT NULL DEFAULT '85460dcd',
  `ironsource_banner_placement_name` varchar(255) NOT NULL DEFAULT 'DefaultBanner',
  `ironsource_interstitial_placement_name` varchar(255) NOT NULL DEFAULT 'DefaultInterstitial',
  `ironsource_rewarded_placement_name` varchar(255) NOT NULL DEFAULT '0',
  `wortise_app_id` varchar(255) NOT NULL DEFAULT 'test-app-id',
  `wortise_banner_unit_id` varchar(255) NOT NULL DEFAULT 'test-banner',
  `wortise_interstitial_unit_id` varchar(255) NOT NULL DEFAULT 'test-interstitial',
  `wortise_rewarded_unit_id` varchar(255) NOT NULL DEFAULT 'test-rewarded',
  `wortise_native_unit_id` varchar(255) NOT NULL DEFAULT 'test-native',
  `wortise_app_open_unit_id` varchar(255) NOT NULL DEFAULT 'test-app-open',
  `mopub_banner_ad_unit_id` varchar(45) NOT NULL DEFAULT '0',
  `mopub_interstitial_ad_unit_id` varchar(45) NOT NULL DEFAULT '0',
  `interstitial_ad_interval` int(11) NOT NULL DEFAULT 3,
  `native_ad_interval` int(11) NOT NULL DEFAULT 20,
  `native_ad_index` int(11) NOT NULL DEFAULT 4,
  `native_ad_index_2` int(11) NOT NULL DEFAULT 4,
  `native_ad_index_3` int(11) NOT NULL DEFAULT 6,
  `native_ad_style_post_list` varchar(45) NOT NULL DEFAULT 'large',
  `native_ad_style_post_details` varchar(45) NOT NULL DEFAULT 'large',
  `native_ad_style_exit_dialog` varchar(45) NOT NULL DEFAULT 'medium',
  `last_update_ads` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ads`
--

INSERT INTO `tbl_ads` (`id`, `ad_status`, `ad_type`, `backup_ads`, `admob_publisher_id`, `admob_app_id`, `admob_banner_unit_id`, `admob_interstitial_unit_id`, `admob_rewarded_unit_id`, `admob_native_unit_id`, `admob_app_open_ad_unit_id`, `fan_banner_unit_id`, `fan_interstitial_unit_id`, `fan_rewarded_unit_id`, `fan_native_unit_id`, `startapp_app_id`, `unity_game_id`, `unity_banner_placement_id`, `unity_interstitial_placement_id`, `unity_rewarded_placement_id`, `applovin_banner_ad_unit_id`, `applovin_interstitial_ad_unit_id`, `applovin_rewarded_ad_unit_id`, `applovin_native_ad_manual_unit_id`, `applovin_app_open_ad_unit_id`, `applovin_banner_zone_id`, `applovin_banner_mrec_zone_id`, `applovin_interstitial_zone_id`, `applovin_rewarded_zone_id`, `ad_manager_banner_unit_id`, `ad_manager_interstitial_unit_id`, `ad_manager_rewarded_unit_id`, `ad_manager_native_unit_id`, `ad_manager_app_open_ad_unit_id`, `ironsource_app_key`, `ironsource_banner_placement_name`, `ironsource_interstitial_placement_name`, `ironsource_rewarded_placement_name`, `wortise_app_id`, `wortise_banner_unit_id`, `wortise_interstitial_unit_id`, `wortise_rewarded_unit_id`, `wortise_native_unit_id`, `wortise_app_open_unit_id`, `mopub_banner_ad_unit_id`, `mopub_interstitial_ad_unit_id`, `interstitial_ad_interval`, `native_ad_interval`, `native_ad_index`, `native_ad_index_2`, `native_ad_index_3`, `native_ad_style_post_list`, `native_ad_style_post_details`, `native_ad_style_exit_dialog`, `last_update_ads`) VALUES
(1, 'on', 'admob', 'none', 'pub-3940256099942544', 'ca-app-pub-3940256099942544~3347511713', 'ca-app-pub-3940256099942544/6300978111', 'ca-app-pub-3940256099942544/1033173712', 'ca-app-pub-3940256099942544/5224354917', 'ca-app-pub-3940256099942544/2247696110', 'ca-app-pub-3940256099942544/3419835294', 'YOUR_PLACEMENT_ID', 'YOUR_PLACEMENT_ID', 'YOUR_PLACEMENT_ID', 'YOUR_PLACEMENT_ID', '0', '4089993', 'banner', 'video', 'rewardedVideo', '678839d3c2b41709', 'ffdebc78207cf165', '0', '18ad16a60584d766', 'df523068d02cf0d7', '0', '0', '0', '0', '/6499/example/banner', '/6499/example/interstitial', '/6499/example/rewarded', '/6499/example/native', '/6499/example/app-open', '85460dcd', 'DefaultBanner', 'DefaultInterstitial', 'DefaultRewardedVideo', 'test-app-id', 'test-banner', 'test-interstitial', 'test-rewarded', 'test-native', 'test-app-open', 'b195f8dd8ded45fe847ad89ed1d016da', '24534e1901884e398f1253216226017e', 3, 13, 6, 4, 6, 'large', 'large', 'medium', '2023-07-29 23:15:38');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ads_placement`
--

CREATE TABLE `tbl_ads_placement` (
  `ads_placement_id` int(11) NOT NULL DEFAULT 1,
  `banner_home` int(11) NOT NULL DEFAULT 1,
  `banner_post_details` int(11) NOT NULL DEFAULT 1,
  `banner_category_details` int(11) NOT NULL DEFAULT 1,
  `banner_search` int(11) NOT NULL DEFAULT 1,
  `interstitial_post_list` int(11) NOT NULL DEFAULT 1,
  `rewarded_post_details` int(11) NOT NULL DEFAULT 1,
  `native_ad_post_list` int(11) NOT NULL DEFAULT 1,
  `native_ad_exit_dialog` int(11) NOT NULL DEFAULT 1,
  `app_open_ad_on_start` int(11) NOT NULL DEFAULT 1,
  `app_open_ad_on_resume` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ads_placement`
--

INSERT INTO `tbl_ads_placement` (`ads_placement_id`, `banner_home`, `banner_post_details`, `banner_category_details`, `banner_search`, `interstitial_post_list`, `rewarded_post_details`, `native_ad_post_list`, `native_ad_exit_dialog`, `app_open_ad_on_start`, `app_open_ad_on_resume`) VALUES
(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ads_status`
--

CREATE TABLE `tbl_ads_status` (
  `ads_status_id` int(11) NOT NULL,
  `banner_ad_on_home_page` int(11) NOT NULL,
  `banner_ad_on_search_page` int(11) NOT NULL,
  `banner_ad_on_wallpaper_detail` int(11) NOT NULL,
  `banner_ad_on_wallpaper_by_category` int(11) NOT NULL,
  `interstitial_ad_on_click_wallpaper` int(11) NOT NULL,
  `interstitial_ad_on_wallpaper_detail` int(11) NOT NULL,
  `native_ad_on_wallpaper_list` int(11) NOT NULL,
  `native_ad_on_exit_dialog` int(11) NOT NULL,
  `app_open_ad` int(11) NOT NULL,
  `last_update_ads_status` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ads_status`
--

INSERT INTO `tbl_ads_status` (`ads_status_id`, `banner_ad_on_home_page`, `banner_ad_on_search_page`, `banner_ad_on_wallpaper_detail`, `banner_ad_on_wallpaper_by_category`, `interstitial_ad_on_click_wallpaper`, `interstitial_ad_on_wallpaper_detail`, `native_ad_on_wallpaper_list`, `native_ad_on_exit_dialog`, `app_open_ad`, `last_update_ads_status`) VALUES
(1, 1, 1, 1, 1, 1, 1, 1, 1, 0, '2023-07-19 09:20:14');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_app_config`
--

CREATE TABLE `tbl_app_config` (
  `id` int(11) NOT NULL,
  `package_name` varchar(255) NOT NULL,
  `status` varchar(5) NOT NULL,
  `redirect_url` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `cid` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL,
  `category_status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`cid`, `category_name`, `category_image`, `category_status`) VALUES
(1, 'Google', '4261-2021-04-03.jpg', 1),
(2, 'Android', '8694-2021-04-03.jpg', 1),
(3, 'Apple', '7971-2021-04-03.jpg', 1),
(4, 'Samsung', '2853-2021-04-03.jpg', 1),
(5, 'Nokia', '2448-2021-04-03.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_fcm_template`
--

CREATE TABLE `tbl_fcm_template` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT 'Notification',
  `message` text NOT NULL,
  `image` varchar(255) NOT NULL,
  `link` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_fcm_template`
--

INSERT INTO `tbl_fcm_template` (`id`, `title`, `message`, `image`, `link`) VALUES
(1, 'Material Wallpaper', 'Hello world, this is Material Wallpaper, you can purchase this item on Codecanyon', '1690670089_img_featured.jpg', 'https://codecanyon.net/item/material-wallpaper/11637956'),
(2, 'Material Wallpaper', 'New update version is available now', '', 'https://play.google.com/store/apps/details?id=com.solodroid.solowallpaper');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_gallery`
--

CREATE TABLE `tbl_gallery` (
  `id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `image` varchar(500) NOT NULL,
  `view_count` int(11) NOT NULL DEFAULT 0,
  `download_count` int(11) NOT NULL DEFAULT 0,
  `image_url` text NOT NULL,
  `type` varchar(45) NOT NULL DEFAULT 'upload',
  `featured` varchar(10) NOT NULL DEFAULT 'no',
  `tags` text NOT NULL,
  `image_name` varchar(255) NOT NULL DEFAULT 'none',
  `image_resolution` varchar(255) NOT NULL DEFAULT '0',
  `image_size` varchar(255) NOT NULL DEFAULT '0',
  `image_extension` varchar(45) NOT NULL,
  `image_status` int(11) NOT NULL DEFAULT 1,
  `image_thumb` varchar(500) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT current_timestamp(),
  `rewarded` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_gallery`
--

INSERT INTO `tbl_gallery` (`id`, `cat_id`, `image`, `view_count`, `download_count`, `image_url`, `type`, `featured`, `tags`, `image_name`, `image_resolution`, `image_size`, `image_extension`, `image_status`, `image_thumb`, `last_update`, `rewarded`) VALUES
(1, 1, '1617458543_6897_Google Pixel 5 Wallpaper 10.jpg', 0, 0, '', 'upload', 'yes', 'google,pixel 5,stock wallpaper', 'Pixel 5', '1080 x 2280', '228.72 KB', 'image/jpeg', 1, '', '2023-01-04 03:34:41', 1),
(2, 1, '1617458544_22575_Google Pixel 5 Wallpaper 12.jpg', 0, 0, '', 'upload', 'no', 'google,pixel 5,stock wallpaper', 'Pixel 5', '1080 x 2280', '212.47 KB', 'image/jpeg', 1, '', '2021-04-12 03:43:02', 0),
(3, 1, '1617458544_36093_Google Pixel 5 Wallpaper 11.jpg', 0, 0, '', 'upload', 'no', 'google,pixel 5,stock wallpaper', 'Pixel 5', '1080 x 2280', '243.52 KB', 'image/jpeg', 1, '', '2021-04-03 14:06:33', 0),
(4, 1, '1617458544_88897_Google Pixel 5 Wallpaper 1.jpg', 0, 0, '', 'upload', 'no', 'google,pixel 5,stock wallpaper', 'Pixel 5', '1080 x 2280', '350.04 KB', 'image/jpeg', 1, '', '2021-04-03 14:06:55', 0),
(5, 1, '1617458544_97959_Google Pixel 5 Wallpaper 3.jpg', 0, 0, '', 'upload', 'no', 'google,pixel 5,stock wallpaper', 'Pixel 5', '1080 x 2280', '184.18 KB', 'image/jpeg', 1, '', '2023-01-04 03:32:56', 0),
(6, 2, '1690645580_default_image.gif', 0, 0, '', 'upload', 'no', 'Android,live wallpaper', 'Falling star', '423 x 757', '76.72 KB', 'image/gif', 1, '', '2023-07-28 11:53:38', 1),
(7, 2, '1617458708_20186_Android 11 Wallpaper 5.jpg', 0, 0, '', 'upload', 'no', 'android,android 11,android wallpaper,stock wallpaper', 'Android 11', '1048 x 1344', '42.24 KB', 'image/jpeg', 1, '', '2021-04-03 14:05:08', 0),
(9, 2, '1617458708_49073_Android 11 Wallpaper 4.jpg', 0, 0, '', 'upload', 'yes', 'android,android 11,android wallpaper,stock wallpaper', 'Android 11', '1048 x 1344', '33.98 KB', 'image/jpeg', 1, '', '2021-04-05 04:45:52', 1),
(10, 2, '1617458708_85493_Android 11 Wallpaper 2.jpg', 0, 0, '', 'upload', 'no', 'android,android 11,android wallpaper,stock wallpaper', 'Android 11', '1048 x 1344', '32.53 KB', 'image/jpeg', 1, '', '2021-04-12 03:31:27', 0),
(11, 3, '1617458945_5580_Apple iPhone 12 Wallpaper 6.jpg', 0, 0, '', 'upload', 'no', 'apple,ios,iphone 12,stock wallpaper', 'iPhone 12', '1356 x 2934', '172.79 KB', 'image/jpeg', 1, '', '2021-09-22 14:24:07', 0),
(12, 3, '1617458945_12990_Apple iPhone 12 Wallpaper 4.jpg', 0, 0, '', 'upload', 'yes', 'apple,ios,iphone 12,stock wallpaper', 'iPhone 12', '1356 x 2934', '159.57 KB', 'image/jpeg', 1, '', '2021-09-25 10:11:28', 1),
(13, 3, '1617458946_87715_Apple iPhone 12 Wallpaper 9.jpg', 0, 0, '', 'upload', 'no', 'apple,ios,iphone 12,stock wallpaper', 'iPhone 12', '1356 x 2934', '145.49 KB', 'image/jpeg', 1, '', '2021-09-25 10:47:46', 0),
(14, 3, '1617458946_95507_Apple iPhone 12 Wallpaper 2.jpg', 0, 0, '', 'upload', 'yes', 'apple,ios,iphone 12,stock wallpaper', 'iPhone 12', '1356 x 2934', '161.54 KB', 'image/jpeg', 1, '', '2021-12-02 21:55:49', 1),
(15, 3, '1617458946_95865_Apple iPhone 12 Wallpaper 10.jpg', 0, 0, '', 'upload', 'yes', 'apple,ios,iphone 12,stock wallpaper', 'iPhone 12', '1356 x 2934', '147.53 KB', 'image/jpeg', 1, '', '2023-01-04 03:06:46', 1),
(16, 4, '1617459033_26758_Samsung Galaxy S20 FE Wallpaper (Dex) 1.jpg', 0, 0, '', 'upload', 'yes', 'samsung,galaxy s20,stock wallpaper', 'Galaxy S20', '1080 x 1920', '249.35 KB', 'image/jpeg', 1, '', '2023-07-29 17:49:00', 1),
(17, 4, '1617459034_34186_Samsung Galaxy S20 FE Wallpaper 4.jpg', 0, 0, '', 'upload', 'yes', 'samsung,galaxy s20,stock wallpaper', 'Galaxy S20', '1350 x 2400', '265.23 KB', 'image/jpeg', 1, '', '2023-07-29 17:48:57', 1),
(18, 4, '', 0, 0, 'https://raw.githubusercontent.com/solodroid-id/sample-data/master/galaxy-s20-live-purple.mp4', 'url', 'yes', 'android,samsung,galaxy s20,live wallpaper,stock wallpaper', 'Galaxy S20+', '1440 x 3200', '11.98 MB', 'application/octet-stream', 1, '1690669626_galaxy s20 live purple.jpg', '2023-07-29 17:42:47', 1),
(19, 4, '1617459076_37465_Samsung Galaxy Note 20 Dex Wallpaper 3 Exclusive.jpg', 0, 0, '', 'upload', 'no', 'samsung,galaxy note 20,stock wallpaper', 'Galaxy Note 20 Ultra', '1080 x 1920', '330.36 KB', 'image/jpeg', 1, '', '2021-09-25 10:47:41', 0),
(20, 4, '1617459076_87504_Samsung Galaxy Note 20 Dex Wallpaper 2 Exclusive.jpg', 0, 0, '', 'upload', 'yes', 'samsung,galaxy note 20,stock wallpaper', 'Galaxy Note 20 Ultra', '1080 x 1920', '300.55 KB', 'image/jpeg', 1, '', '2022-02-13 08:58:55', 1),
(21, 5, '1617459160_40168_Nokia 1.3 Wallpaper 3.jpg', 0, 0, '', 'upload', 'yes', 'nokia,nokia 1.3,stock wallpaper', 'Nokia 1.3', '856 x 1520', '185.12 KB', 'image/jpeg', 1, '', '2021-09-25 10:47:39', 1),
(22, 5, '1617459161_60387_Nokia 1.3 Wallpaper 5.jpg', 0, 0, '', 'upload', 'no', 'nokia,nokia 1.3,stock wallpaper', 'Nokia 1.3', '856 x 1520', '118.78 KB', 'image/jpeg', 1, '', '2021-11-29 13:40:48', 0),
(23, 5, '1617459211_52569_Nokia 5.3 Wallpaper 2.jpg', 0, 0, '', 'upload', 'yes', 'nokia,nokia 2.4,stock wallpaper', 'Nokia 2.4', '900 x 1600', '111.94 KB', 'image/jpeg', 1, '', '2022-02-14 02:16:13', 1),
(24, 4, '', 0, 0, 'https://raw.githubusercontent.com/solodroid-id/sample-data/master/galaxy-s21-live-violet.mp4', 'url', 'yes', 'android,samsung,galaxy s21,live wallpaper,stock wallpaper', 'Galaxy S21', '1440 x 3200', '11.68 MB', 'application/octet-stream', 1, '1690669969_galaxy s21 live violet.jpg', '2023-07-29 17:42:49', 1),
(25, 5, '1617459211_46414_Nokia 8.3 5G Wallpaper.jpg', 0, 0, '', 'upload', 'no', 'nokia,nokia 2.4,stock wallpaper', 'Nokia 2.4', '938 x 1666', '350.02 KB', 'image/jpeg', 1, '', '2021-11-29 13:40:40', 0),
(26, 5, '1617598117_39183_Nokia 2.4 Wallpaper 1.jpg', 0, 0, '', 'upload', 'no', 'nokia,nokia 2.4,stock wallpaper,nature', 'Nokia 2.4', '900 x 1600', '256.45 KB', 'image/jpeg', 1, '', '2022-02-17 22:26:01', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_license`
--

CREATE TABLE `tbl_license` (
  `id` int(11) NOT NULL,
  `purchase_code` varchar(255) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `buyer` varchar(255) NOT NULL,
  `license_type` varchar(45) NOT NULL,
  `purchase_date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_menu`
--

CREATE TABLE `tbl_menu` (
  `menu_id` int(11) NOT NULL,
  `menu_title` varchar(255) NOT NULL,
  `menu_order` varchar(255) NOT NULL,
  `menu_filter` varchar(255) NOT NULL,
  `menu_category` varchar(255) NOT NULL DEFAULT '0',
  `menu_status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_menu`
--

INSERT INTO `tbl_menu` (`menu_id`, `menu_title`, `menu_order`, `menu_filter`, `menu_category`, `menu_status`) VALUES
(1, 'Recent', 'recent', 'wallpaper', '0', 1),
(2, 'Featured', 'featured', 'both', '0', 1),
(3, 'Popular', 'popular', 'wallpaper', '0', 1),
(4, 'Random', 'random', 'wallpaper', '0', 1),
(5, 'Live Wallpaper', 'recent', 'live', '0', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_settings`
--

CREATE TABLE `tbl_settings` (
  `id` int(11) NOT NULL,
  `limit_recent_wallpaper` int(11) NOT NULL DEFAULT 0,
  `category_sort` varchar(45) NOT NULL,
  `category_order` varchar(45) NOT NULL,
  `onesignal_app_id` varchar(500) NOT NULL DEFAULT '0',
  `onesignal_rest_api_key` varchar(500) NOT NULL DEFAULT '0',
  `providers` varchar(45) NOT NULL DEFAULT 'onesignal',
  `protocol_type` varchar(10) NOT NULL DEFAULT 'http',
  `privacy_policy` text NOT NULL,
  `package_name` varchar(255) NOT NULL DEFAULT 'com.app.materialwallpaper',
  `fcm_server_key` varchar(500) NOT NULL,
  `fcm_notification_topic` varchar(255) NOT NULL DEFAULT 'material_wallpaper_topic',
  `more_apps_url` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_settings`
--

INSERT INTO `tbl_settings` (`id`, `limit_recent_wallpaper`, `category_sort`, `category_order`, `onesignal_app_id`, `onesignal_rest_api_key`, `providers`, `protocol_type`, `privacy_policy`, `package_name`, `fcm_server_key`, `fcm_notification_topic`, `more_apps_url`) VALUES
(1, 30, 'cid', 'DESC', '00000000-0000-0000-0000-000000000000', '0', 'onesignal', '', '<p>Solodroid built the Material Wallpaper app as a Free app. This SERVICE is provided by Solodroid at no cost and is intended for use as is.</p>\r\n\r\n<p>This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.</p>\r\n\r\n<p>If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.</p>\r\n\r\n<p>The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Material Wallpaper unless otherwise defined in this Privacy Policy.</p>\r\n\r\n<p><strong>Information Collection and Use</strong></p>\r\n\r\n<p>For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request will be retained by us and used as described in this privacy policy.</p>\r\n\r\n<p>The app does use third party services that may collect information used to identify you.</p>\r\n\r\n<p>Link to privacy policy of third party service providers used by the app</p>\r\n\r\n<ul>\r\n	<li><a href=\"https://www.google.com/policies/privacy/\" target=\"_blank\">Google Play Services</a></li>\r\n	<li><a href=\"https://support.google.com/admob/answer/6128543?hl=en\" target=\"_blank\">AdMob</a></li>\r\n	<li><a href=\"https://firebase.google.com/policies/analytics\" target=\"_blank\">Google Analytics for Firebase</a></li>\r\n	<li><a href=\"https://www.facebook.com/about/privacy/update/printable\" target=\"_blank\">Facebook</a></li>\r\n	<li><a href=\"https://unity3d.com/legal/privacy-policy\" target=\"_blank\">Unity</a></li>\r\n	<li><a href=\"https://onesignal.com/privacy_policy\" target=\"_blank\">One Signal</a></li>\r\n	<li><a href=\"https://www.applovin.com/privacy/\" target=\"_blank\">AppLovin</a></li>\r\n	<li><a href=\"https://www.startapp.com/privacy/\" target=\"_blank\">StartApp</a></li>\r\n</ul>\r\n\r\n<p><strong>Log Data</strong></p>\r\n\r\n<p>We want to inform you that whenever you use our Service, in a case of an error in the app we collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (&ldquo;IP&rdquo;) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</p>\r\n\r\n<p><strong>Cookies</strong></p>\r\n\r\n<p>Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device&#39;s internal memory.</p>\r\n\r\n<p>This Service does not use these &ldquo;cookies&rdquo; explicitly. However, the app may use third party code and libraries that use &ldquo;cookies&rdquo; to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</p>\r\n\r\n<p><strong>Service Providers</strong></p>\r\n\r\n<p>We may employ third-party companies and individuals due to the following reasons:</p>\r\n\r\n<ul>\r\n	<li>To facilitate our Service;</li>\r\n	<li>To provide the Service on our behalf;</li>\r\n	<li>To perform Service-related services; or</li>\r\n	<li>To assist us in analyzing how our Service is used.</li>\r\n</ul>\r\n\r\n<p>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.</p>\r\n\r\n<p><strong>Security</strong></p>\r\n\r\n<p>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>\r\n\r\n<p><strong>Links to Other Sites</strong></p>\r\n\r\n<p>This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.</p>\r\n\r\n<p><strong>Children&rsquo;s Privacy</strong></p>\r\n\r\n<p>These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13 years of age. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>\r\n\r\n<p><strong>Changes to This Privacy Policy</strong></p>\r\n\r\n<p>We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page.</p>\r\n\r\n<p>This policy is effective as of 2021-09-29</p>\r\n\r\n<p><strong>Contact Us</strong></p>\r\n\r\n<p>If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at youremail@gmail.com.</p>\r\n', 'com.app.materialwallpaper', '0', 'material_wallpaper_topic', 'https://codecanyon.net/category/mobile/android?sort=date&term=solodroid#content');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_ads`
--
ALTER TABLE `tbl_ads`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_ads_placement`
--
ALTER TABLE `tbl_ads_placement`
  ADD PRIMARY KEY (`ads_placement_id`);

--
-- Indexes for table `tbl_ads_status`
--
ALTER TABLE `tbl_ads_status`
  ADD PRIMARY KEY (`ads_status_id`);

--
-- Indexes for table `tbl_app_config`
--
ALTER TABLE `tbl_app_config`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`cid`);

--
-- Indexes for table `tbl_fcm_template`
--
ALTER TABLE `tbl_fcm_template`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_gallery`
--
ALTER TABLE `tbl_gallery`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_license`
--
ALTER TABLE `tbl_license`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_menu`
--
ALTER TABLE `tbl_menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- Indexes for table `tbl_settings`
--
ALTER TABLE `tbl_settings`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_ads`
--
ALTER TABLE `tbl_ads`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_ads_status`
--
ALTER TABLE `tbl_ads_status`
  MODIFY `ads_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_app_config`
--
ALTER TABLE `tbl_app_config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_fcm_template`
--
ALTER TABLE `tbl_fcm_template`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_gallery`
--
ALTER TABLE `tbl_gallery`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `tbl_license`
--
ALTER TABLE `tbl_license`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_menu`
--
ALTER TABLE `tbl_menu`
  MODIFY `menu_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tbl_settings`
--
ALTER TABLE `tbl_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
