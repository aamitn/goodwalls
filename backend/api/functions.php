<?php

	function getCategory() {

		include_once "../includes/config.php";

		$setting_qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $setting_qry);
		$row    = mysqli_fetch_assoc($result);
		$sort   = $row['category_sort'];
		$order  = $row['category_order'];

		$json_object = array();
		
		$query = "SELECT cid, category_name, category_image FROM tbl_category ORDER BY $sort $order";
		$sql = mysqli_query($connect, $query);

		while ($data = mysqli_fetch_assoc($sql)) {
						
			$query = "SELECT COUNT(*) as num FROM tbl_gallery WHERE cat_id = '".$data['cid']."'";
			$total = mysqli_fetch_array(mysqli_query($connect, $query));
			$total = $total['num'];	

			$object['category_id'] = $data['cid'];
			$object['category_name'] = $data['category_name'];
			$object['category_image'] = $data['category_image'];
			$object['total_wallpaper'] = $total;
						 
			array_push($json_object, $object);
					
		}

		$set = $json_object;
					
		header( 'Content-Type: application/json; charset=utf-8' );
		echo $val = str_replace('\\/', '/', json_encode($set, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();

	}

	function getCategoryDetail($id, $offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$id = $_GET['id'];
		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;


		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND c.cid = $id ORDER BY w.id DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND c.cid = $id ORDER BY w.id DESC LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND c.cid = $id ORDER BY w.id DESC LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}	

	function getRecent($offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;
		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.id DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.id DESC LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.id DESC LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}


	function getPopular($offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;
		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.view_count DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.view_count DESC LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY w.view_count DESC LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}

	function getRandom($offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;
		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY RAND() DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY RAND() DESC LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id ORDER BY RAND() w.view_count DESC LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}

	function getFeatured($offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;
		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND w.featured = 'yes' ORDER BY w.id DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND w.featured = 'yes' ORDER BY w.id LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND w.featured = 'yes' ORDER BY w.id LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}

	function getSearch($search, $offset) {

		include_once "../includes/config.php";

		$qry = "SELECT * FROM tbl_settings where id = '1'";
		$result = mysqli_query($connect, $qry);
		$settings_row = mysqli_fetch_assoc($result);
		$load_more = $settings_row['limit_recent_wallpaper'];

		$search = $_GET['search'];
		$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;


		$all = mysqli_query($connect, "SELECT * FROM tbl_gallery ORDER BY id DESC");
		$count_all = mysqli_num_rows($all);
		$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND (c.category_name LIKE '%$search%' OR w.tags LIKE '%$search%') ORDER BY w.id DESC LIMIT $offset, $load_more");
		$count = mysqli_num_rows($query);
		$json_empty = 0;
		if ($count < $load_more) {
			if ($count == 0) {
				$json_empty = 1;
			} else {
				$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND (c.category_name LIKE '%$search%' OR w.tags LIKE '%$search%') ORDER BY w.id DESC LIMIT $offset, $count");
				$count = mysqli_num_rows($query);
				if (empty($count)) {
					$query = mysqli_query($connect, "SELECT w.id, w.image, w.image_url, w.type, w.view_count, w.download_count, w.featured, w.tags, c.cid AS 'category_id', c.category_name FROM tbl_category c, tbl_gallery w WHERE c.cid = w.cat_id AND (c.category_name LIKE '%$search%' OR w.tags LIKE '%$search%') ORDER BY w.id DESC LIMIT 0, $load_more");
					$num = 0;
				} else {
					$num = $offset;
				}
			}
		} else {
			$num = $offset;
		}
		$json = '[';
		while ($row = mysqli_fetch_array($query)) {
			$num++;
			$char ='"';
			$json .= '{
				"no": '.$num.',
				"image_id": "'.$row['id'].'", 
				"image_upload": "'.$row['image'].'",
				"image_url": "'.$row['image_url'].'",
				"type": "'.$row['type'].'",
				"view_count": "'.$row['view_count'].'",
				"download_count": "'.$row['download_count'].'",
				"featured": "'.$row['featured'].'",
				"tags": "'.$row['tags'].'",
				"category_id": "'.$row['category_id'].'",
				"category_name": "'.$row['category_name'].'"
			},';
		}

		$json = substr($json,0, strlen($json)-1);

		if ($json_empty == 1) {
			$json = '[]';
		} else {
			$json .= ']';
		}

		header('Content-Type: application/json; charset=utf-8');
		echo $json;

		mysqli_close($connect);

	}

	function viewCount($id) {

		$id = $_GET['id'];

		include_once "../includes/config.php";

		$jsonObj = array();	

		$query = "SELECT * FROM tbl_gallery WHERE id = $id";
		$sql = mysqli_query($connect, $query) or die(mysqli_error());

		while ($data = mysqli_fetch_assoc($sql)) {
						 
			$row['id'] = $data['id'];
			$row['view_count'] = $data['view_count'];
			 
			array_push($jsonObj, $row);
					
		}

		$view_qry = mysqli_query($connect, " UPDATE tbl_gallery SET view_count = view_count + 1 WHERE id = $id ");

		$set['result'] = $jsonObj;
					
		header( 'Content-Type: application/json; charset=utf-8' );
		echo $val = str_replace('\\/', '/', json_encode($set, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();			

	}

	function downloadCount($id) {

		$id = $_GET['id'];

		include_once "../includes/config.php";

		$jsonObj = array();	

		$query = "SELECT * FROM tbl_gallery WHERE id = $id";
		$sql = mysqli_query($connect, $query) or die(mysqli_error());

		while ($data = mysqli_fetch_assoc($sql)) {
						 
			$row['id'] = $data['id'];
			$row['download_count'] = $data['download_count'];
			 
			array_push($jsonObj, $row);
					
		}

		$view_qry = mysqli_query($connect, " UPDATE tbl_gallery SET download_count = download_count + 1 WHERE id = $id ");

		$set['result'] = $jsonObj;
					
		header( 'Content-Type: application/json; charset=utf-8' );
		echo $val = str_replace('\\/', '/', json_encode($set, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));
		die();			

	}

	function getPrivacyPolicy() {

		include_once "../includes/config.php";

		$query = "SELECT privacy_policy FROM tbl_settings LIMIT 1";			
		$resouter = mysqli_query($connect, $query);

		$set = array();
	    $total_records = mysqli_num_rows($resouter);
	    if($total_records >= 1) {
	      	while ($link = mysqli_fetch_array($resouter, MYSQLI_ASSOC)){
	        $set = $link;
	      }
	    }

	    header('Content-Type: application/json; charset=utf-8');
	    echo $val = str_replace('\\/', '/', json_encode($set));	

	}	

?>