<?php
 
 	//legacy API and no longer used in the latest upated version
 	include_once ('includes/config.php');
 	$connect->set_charset('utf8');
	
	if (isset($_GET['cat_id'])) {
			
		$query="SELECT image AS 'images', category_name AS 'cat_name', cid, category_image FROM tbl_category c, tbl_gallery n WHERE c.cid = n.cat_id and c.cid = '".$_GET['cat_id']."' ORDER BY n.id DESC";			
		$resouter = mysqli_query($connect, $query);
			
	} else if (isset($_GET['latest'])) {

		$setting_qry    = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $setting_qry);
		$row   = mysqli_fetch_assoc($result);

		$limit    = $row['limit_recent_wallpaper'];

		$query = "SELECT * FROM tbl_category c,tbl_gallery n WHERE c.cid = n.cat_id ORDER BY n.id DESC LIMIT $limit";	
		$resouter = mysqli_query($connect, $query);

	} else {

		$setting_qry    = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $setting_qry);
		$row   = mysqli_fetch_assoc($result);

		$sort    = $row['category_sort'];
		$order    = $row['category_order'];

		$query="SELECT * FROM tbl_category ORDER BY $sort $order";			
		$resouter = mysqli_query($connect, $query);
	}
     
    $set = array();
     
    $total_records = mysqli_num_rows($resouter);
    if ($total_records >= 1) { 
      while ($link = mysqli_fetch_array($resouter, MYSQLI_ASSOC)) {
        $set['MaterialWallpaper'][] = $link;
      }
    }
     
	header( 'Content-Type: application/json; charset=utf-8' );
    echo $val= str_replace('\\/', '/', json_encode($set));
	 	 
?>