<?php ob_start(); ?>
<?php include_once('includes/header.php'); ?>

<?php
	
	if (isset($_GET['id'])) {
		$ID = clean($_GET['id']);
	} else {
		$ID = clean("");
	}

	// get image file from table
	$sql = "SELECT type, image, image_thumb, image_extension FROM tbl_gallery WHERE id = '$ID'";
	$result = $connect->query($sql);
	$row = $result->fetch_assoc();

	$type = $row['type'];
	$image = $row['image'];
	$imageThumb = $row['image_thumb'];
	$format = $row['image_extension'];

	// delete data from menu table
	$sql_delete = "DELETE FROM tbl_gallery WHERE id = '$ID'";
	$delete = $connect->query($sql_delete);

	// if delete data success
	if ($delete) {

		if ($type == 'upload') {
			$delete_image = unlink('upload/'.$image);
			$delete_image = unlink('upload/thumbs/'.$image);
			$delete_image = unlink('upload/thumbs/'.$imageThumb);
		} else {
			if ($format == 'application/octet-stream') {
				$delete_image = unlink('upload/thumbs/'.$imageThumb);
			}
		}

		$_SESSION['msg'] = "Live wallpaper deleted successfully...";
	    header( "Location: live.php");
	     exit;
	}

?>

<?php include_once('includes/footer.php'); ?>