<?php include_once('includes/header.php'); ?>

<?php

	error_reporting(0);

	$now = new DateTime();
	$lastUpdate = $now->format('Y-m-d H:i:s');

    // delete selected records
    if(isset($_POST['submit'])) {

        $arr = $_POST['chk_id'];
        $count = count($arr);
        if ($count > 0) {
            foreach ($arr as $nid) {

                $sql_image = "SELECT image FROM tbl_gallery WHERE id = $nid";
                $img_results = mysqli_query($connect, $sql_image);

                $sql_delete = "DELETE FROM tbl_gallery WHERE id = $nid";

                if (mysqli_query($connect, $sql_delete)) {
                    while ($row = mysqli_fetch_assoc($img_results)) {
                        unlink('upload/' . $row['image']);
                        unlink('upload/thumbs/' . $row['image']);
                    }
                    $_SESSION['msg'] = "$count Selected wallpapers deleted";
                } else {
                    $_SESSION['msg'] = "Error deleting record";
                }

            }
        } else {
            $_SESSION['msg'] = "Whoops! no wallpapers selected to delete";
        }
        header("Location:live.php");
        exit;
    }

	if (isset($_REQUEST['keyword']) && $_REQUEST['keyword']<>"") {
		$keyword = $_REQUEST['keyword'];
		$reload = "live.php";
		$sql =  "SELECT w.*, c.category_name FROM tbl_gallery w, tbl_category c WHERE w.cat_id = c.cid AND c.category_status = '1' AND (w.image_extension = 'image/gif' OR w.image_extension = 'application/octet-stream') AND w.image_name LIKE '%$keyword%'";
		$result = $connect->query($sql);
	} else {
		$reload = "live.php";
		$sql =  "SELECT w.*, c.category_name FROM tbl_gallery w, tbl_category c WHERE w.cat_id = c.cid AND c.category_status = '1' AND (w.image_extension = 'image/gif' OR w.image_extension = 'application/octet-stream') ORDER BY w.id DESC";
		$result = $connect->query($sql);
	}

	$rpp = $postPerPage;
	$page = intval($_GET["page"]);
	if($page <= 0) $page = 1;  
	$tcount = mysqli_num_rows($result);
	$tpages = ($tcount) ? ceil($tcount / $rpp) : 1;
	$count = 0;
	$i = ($page-1) * $rpp;
	$no_urut = ($page-1) * $rpp;

	//add to featured
    if (isset($_GET['page']) && isset($_GET['add'])) {
		$data = array(
			'featured' => 'yes',
			'last_update' => $lastUpdate
		);	
		$hasil = update('tbl_gallery', $data, "WHERE id = '".$_GET['add']."'");
		if ($hasil > 0) {
	        $_SESSION['msg'] = "Success added to featured wallpaper";
	        header('Location:live.php?page='.$_GET['page']);
			exit;
		}
    }

    //remove from featured
    if (isset($_GET['page']) && isset($_GET['remove'])) {
		$data = array('featured' => 'no');	
		$hasil = update('tbl_gallery', $data, "WHERE id = '".$_GET['remove']."'");
		if ($hasil > 0) {
	        $_SESSION['msg'] = "Success removed from featured wallpaper";
	        header('Location:live.php?page='.$_GET['page']);
			exit;
		}
    }

    //disable wallpaper
    if (isset($_GET['page']) && isset($_GET['disable'])) {
		$data = array('image_status' => '0');	
		$update = update('tbl_gallery', $data, "WHERE id = '".$_GET['disable']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Wallpaper successfully disabled";
	        header('Location:live.php?page='.$_GET['page']);
			exit;
		}
    }

    //enable wallpaper
    if (isset($_GET['page']) && isset($_GET['enable'])) {
		$data = array('image_status' => '1');	
		$update = update('tbl_gallery', $data, "WHERE id = '".$_GET['enable']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Wallpaper successfully enabled";
	        header("Location:live.php?page=".$_GET['page']);
			exit;
		}
    }	

    //unlock wallpaper
    if (isset($_GET['page']) && isset($_GET['unlock'])) {
		$data = array('rewarded' => '0');	
		$update = update('tbl_gallery', $data, "WHERE id = '".$_GET['unlock']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Wallpaper successfully unlocked";
	        header("Location:live.php?page=".$_GET['page']);
			exit;
		}
    }

    //lock wallpaper
    if (isset($_GET['page']) && isset($_GET['lock'])) {
		$data = array('rewarded' => '1');	
		$update = update('tbl_gallery', $data, "WHERE id = '".$_GET['lock']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Wallpaper successfully locked";
	        header("Location:live.php?page=".$_GET['page']);
			exit;
		}
    }

    //jump page
	if (isset($_POST['jump_to_page'])) {
    	$pageNumber = clean($_POST['page_number']); 
		header('Location:live.php?page='.$pageNumber);
		exit;
    }

?>

<section class="content">

	<ol class="breadcrumb">
		<li><a href="dashboard.php">Dashboard</a></li>
		<li class="active">Manage live Wallpaper</a></li>
	</ol>

	<div class="container-fluid">

		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="card corner-radius">
					<div class="header">
						<h2>MANAGE LIVE WALLPAPER</h2>
						<div class="header-dropdown m-r--5">
							<a href="live-add.php"><button type="button" class="button button-rounded btn-offset waves-effect waves-float">ADD LIVE WALLPAPER</button></a>
						</div>
					</div>

					<div style="margin-top: -10px;" class="body table-responsive">

						<?php if(isset($_SESSION['msg'])) { ?>
						<div class='alert alert-info alert-dismissible corner-radius bottom-offset' role='alert'>
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>&nbsp;&nbsp;</button>
							<?php echo $_SESSION['msg']; ?>
						</div>
						<?php unset($_SESSION['msg']); } ?>

						<form method="get" id="form_validation">
							<table class='table'>
								<tr>
									<td>
										<div class="form-group form-float">
											<div class="form-line">
												<input type="text" class="form-control" name="keyword" placeholder="Search..." required>
											</div>
										</div>
									</td>
									<td width="1%"><a href="live.php"><button type="button" class="button button-rounded waves-effect waves-float">RESET</button></a></td>
									<td width="1%"><button type="submit" class="btn bg-blue btn-circle waves-effect waves-circle waves-float"><i class="material-icons">search</i></button></td>
								</tr>
							</table>
						</form>

						<?php if ($tcount == 0) { ?>
							<p align="center" style="font-size: 110%;">There are no live wallpapers.</p>
						<?php } else { ?>

						<form method="post" action="">

							<div style="margin-left: 8px; margin-top: -36px; margin-bottom: 10px;">
								<button type="submit" name="submit" id="submit" class="button button-rounded waves-effect waves-float" onclick="return confirm('Are you sure want to delete all selected wallpapers?')">Delete selected items(s)</button>&nbsp;&nbsp;
								<a href="" data-toggle="modal" data-target="#modal-jump-to-page"><button type="button" class="button button-rounded waves-effect waves-float">Jump to Page</button></a>
							</div>				

							<table class='table table-hover table-striped'>
								<thead>
									<tr>
										<th width="1%">
											<div class="demo-checkbox" style="margin-bottom: -15px">
												<input id="chk_all" name="chk_all" type="checkbox" class="filled-in chk-col-blue" />
												<label for="chk_all"></label>
											</div>
										</th>
										<th>Image</th>
										<th>Name</th>
										<th>Category</th>
										<th><div align="center">Type</div></th>
										<th><div align="center">Featured</div></th>
										<th><div align="center">Views</div></th>
										<th><div align="center">Downloads</div></th>
										<th><center>Action</center></th>
									</tr>
								</thead>
								<?php
								while(($count < $rpp) && ($i < $tcount)) {
									mysqli_data_seek($result, $i);
									$data = mysqli_fetch_array($result);
									?>
									<tr>
										<td width="1%" style="vertical-align: middle;">
											<div class="demo-checkbox" style="margin-top: 10px;">
												<input type="checkbox" name="chk_id[]" id="<?php echo $data['id'];?>" class="chkbox filled-in chk-col-blue" value="<?php echo $data['id'];?>"/>
												<label for="<?php echo $data['id'];?>"></label>
											</div>
										</td>

										<td style="vertical-align: middle;">
											<?php if ($data['type'] == 'url') { ?>
												<?php if ($data['image_extension'] == 'application/octet-stream') { ?>
													<a href="" data-toggle="modal" data-target="#modal_preview<?php echo $data['id']; ?>"><img style="object-fit:cover;" class="<?php if ($data['image_status'] == '1') { echo 'img-rounded';} else {echo 'img-rounded img-grayscale';} ?>" src="upload/thumbs/<?php echo $data['image_thumb'];?>" height="64px" width="40px"/></a>
												<?php } else { ?>
													<img style="object-fit:cover;" class="<?php if ($data['image_status'] == '1') { echo 'img-rounded';} else {echo 'img-rounded img-grayscale';} ?>" src="<?php echo $data['image_url'];?>" height="64px" width="40px"/>
												<?php } ?>
											<?php } else { ?>
												<?php if ($data['image_extension'] == 'application/octet-stream') { ?>
													<a href="" data-toggle="modal" data-target="#modal_preview<?php echo $data['id']; ?>"><img style="object-fit:cover;" class="<?php if ($data['image_status'] == '1') { echo 'img-rounded';} else {echo 'img-rounded img-grayscale';} ?>" src="upload/thumbs/<?php echo $data['image_thumb'];?>" height="64px" width="40px"/></a>
												<?php } else { ?>
													<img style="object-fit:cover;" class="<?php if ($data['image_status'] == '1') { echo 'img-rounded';} else {echo 'img-rounded img-grayscale';} ?>" src="upload/<?php echo $data['image'];?>" height="64px" width="40px"/>
												<?php } ?>
											<?php } ?>
										</td>

										<td style="vertical-align: middle;"><?php if ($data['image_status'] == '1') { echo $data['image_name']; } else { echo '<strike>'.$data['image_name'].'</strike>'; } ?></td>
										<td style="vertical-align: middle;"><?php if ($data['image_status'] == '1') { echo $data['category_name']; } else { echo '<strike>'.$data['category_name'].'</strike>'; } ?></td>

										<td align="center" style="vertical-align: middle;">
											<?php if ($data['type'] == 'url') { ?>
											<span class="label label-rounded <?php if ($data['image_status'] == '1') { echo 'bg-orange'; } else { echo 'bg-grey'; } ?>">&nbsp;URL&nbsp;</span>
											<?php } else { ?>
											<span class="label label-rounded <?php if ($data['image_status'] == '1') { echo 'bg-green'; } else { echo 'bg-grey'; } ?>">UPLOAD</span>
											<?php } ?>
										</td>

										<td align="center" style="vertical-align: middle;">
											<?php if ($data['featured'] == 'no') { ?>
											<a href="live.php?page=<?php echo $page; ?>&add=<?php echo $data['id'];?>" onclick="return confirm('Add to featured wallpaper?')" ><i class="material-icons" style="color:grey">lens</i></a>
											<?php } else { ?>
											<a href="live.php?page=<?php echo $page; ?>&remove=<?php echo $data['id'];?>" onclick="return confirm('Remove from featured wallpaper?')" ><i class="material-icons" style="color:#4bae4f">lens</i></a>
											<?php } ?>
										</td>

										<td align="center" style="vertical-align: middle;"><?php if ($data['image_status'] == '1') { echo $data['view_count']; } else { echo '<strike>'.$data['view_count'].'</strike>'; } ?></td>
										<td align="center" style="vertical-align: middle;"><?php if ($data['image_status'] == '1') { echo $data['download_count']; } else { echo '<strike>'.$data['download_count'].'</strike>'; } ?></td>

										<td style="vertical-align: middle;"><center>

											<?php if ($data['rewarded'] == '1') { ?>
											<a href="live.php?page=<?php echo $page; ?>&unlock=<?php echo $data['id'];?>" onclick="return confirm('Do you want to unlock this wallpaper from Rewarded Ad?')">
												<i class="material-icons">lock</i>
											</a>
											<?php } else { ?>
											<a href="live.php?page=<?php echo $page; ?>&lock=<?php echo $data['id'];?>" onclick="return confirm('Do you want to lock this wallpaper with Rewarded Ad?')">
												<i class="material-icons">lock_open</i>
											</a>
											<?php } ?>

											<?php if ($data['image_status'] == '1') { ?>
											<a href="live.php?page=<?php echo $page; ?>&disable=<?php echo $data['id'];?>" onclick="return confirm('Are you sure want to disable this wallpaper?')">
												<i class="material-icons">visibility</i>
											</a>
											<?php } else { ?>
											<a href="live.php?page=<?php echo $page; ?>&enable=<?php echo $data['id'];?>" onclick="return confirm('Are you sure want to enable this wallpaper?')">
												<i class="material-icons">visibility_off</i>
											</a>
											<?php } ?>											

											<a href="wallpaper-send.php?id=<?php echo $data['id'];?>">
												<i class="material-icons">notifications_active</i>
											</a>

											<a href="live-edit.php?id=<?php echo $data['id'];?>">
												<i class="material-icons">mode_edit</i>
											</a>

											<a href="live-delete.php?id=<?php echo $data['id'];?>" onclick="return confirm('Are you sure want to delete this wallpaper?')" >
												<i class="material-icons">delete</i>
											</a></center>
										</td>

										<div class="modal fade" id="modal_preview<?php echo $data['id']; ?>" tabindex="-1" role="dialog">
										    <div class="modal-dialog modal-md" role="document">
										            <div class="modal-content">
										                <div class="modal-header">
										                    <h4 class="modal-title" id="largeModalLabel">Live Wallpaper Preview</h4>
										                </div>
										                <div class="modal-body">
										                	<?php 
										                		$ID = $data['id'];
										                		$sql = "SELECT * FROM tbl_gallery WHERE id = '$ID'";
										                		$imageResult = $connect->query($sql);
															    $data2 = $imageResult->fetch_assoc();
															    $type = $data2['type'];
															    if ($type == 'upload') {
															    	$imageUrl = 'upload/'.$data2['image'];
															    } else {
															    	$imageUrl = $data2['image_url'];
															    }
										                	?>
										                    <video width="100%" height="500px" style="background-color: #000;" controls loop>
															  	<source src="<?php echo $imageUrl; ?>" type="video/mp4">
																Your browser does not support the video tag.
															</video>
										                </div>
										                <div class="modal-footer">
										                    <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CLOSE</button>
										                </div>
										            </div>
										    </div>
										</div>	

									</tr>
									<?php
									$i++; 
									$count++;
								}
								?>
							</table>						

						</form>

						<?php } ?>

						<?php if ($tcount > $postPerPage) { echo pagination($reload, $page, $keyword, $tpages); } ?>
					</div>

				</div>
			</div>
		</div>
	</div>
</section>

<div style="margin-top: 20%;" class="modal fade" id="modal-jump-to-page" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <form method="post" id="form_validation">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="largeModalLabel">Jump to Page</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="form-line">
                            <div class="font-12"><b>Input Page Number</b></div>
                            <input type="number" class="form-control" name="page_number" id="page_number" min="1" max="<?php echo $tpages; ?>" required>
                        </div>
                        <div class="help-info pull-left">Page number between ( 1 - <?php echo $tpages; ?> )</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">CANCEL</button>
                    <button type="submit" name="jump_to_page" class="btn btn-link waves-effect">GO</button>
                </div>
            </div>
        </form>
    </div>
</div>

<?php include_once('includes/footer.php'); ?>