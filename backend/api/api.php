<?php 

    include 'functions.php';

    if (isset($_GET['action']) && $_GET['action'] == "get_category") {

        getCategory();

    } else if (isset($_GET['action']) && $_GET['action'] == "get_category_detail") {

        $id = $_GET['id'];
        $offset = $_GET['offset'];
        getCategoryDetail($id, $offset);

    } else if (isset($_GET['action']) && $_GET['action'] == "get_recent") {

        $offset = $_GET['offset'];
        getRecent($offset);

    } else if (isset($_GET['action']) && $_GET['action'] == "get_popular") {

        $offset = $_GET['offset'];
        getPopular($offset);
        
    } else if (isset($_GET['action']) && $_GET['action'] == "get_random") {

        $offset = $_GET['offset'];
        getRandom($offset);
        
    } else if (isset($_GET['action']) && $_GET['action'] == "get_featured") {

        $offset = $_GET['offset'];
        getFeatured($offset);
        
    } else if (isset($_GET['action']) && $_GET['action'] == "get_search") {

        $search = $_GET['search'];
        $offset = $_GET['offset'];
        getSearch($search, $offset);
        
    } else if (isset($_GET['action']) && $_GET['action'] == "view_count") {

        $id = $_GET['id'];
        viewCount($id);

    } else if (isset($_GET['action']) && $_GET['action'] == "download_count") {

        $id = $_GET['id'];
        downloadCount($id);

    } else if (isset($_GET['action']) && $_GET['action'] == "get_privacy_policy") {

        getPrivacyPolicy();

    } else {

        echo "no method found";

    }

?>