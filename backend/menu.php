<?php include_once('includes/header.php'); ?>

<?php

	error_reporting(0);

	if (isset($_REQUEST['keyword']) && $_REQUEST['keyword']<>"") {
		$keyword = $_REQUEST['keyword'];
		$reload = "menu.php";
		$sql =  "SELECT * FROM tbl_menu WHERE menu_title LIKE '%$keyword%'";
		$result = $connect->query($sql);
	} else {
		$reload = "menu.php";
		$sql =  "SELECT * FROM tbl_menu ORDER BY menu_id ASC";
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

    if (isset($_GET['page']) && isset($_GET['disable'])) {
		$data = array('menu_status' => '0');	
		$update = update('tbl_menu', $data, "WHERE menu_id = '".$_GET['disable']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Menu successfully disabled";
	        header('Location:menu.php?page='.$_GET['page']);
			exit;
		}
    }

    if (isset($_GET['page']) && isset($_GET['enable'])) {
		$data = array('menu_status' => '1');	
		$update = update('tbl_menu', $data, "WHERE menu_id = '".$_GET['enable']."'");
		if ($update > 0) {
	        $_SESSION['msg'] = "Menu successfully enabled";
	        header("Location:menu.php?page=".$_GET['page']);
			exit;
		}
    }	

	if (isset($_GET['delete'])) {
		$menuId = $_GET['delete'];
		$sql_delete = "DELETE FROM tbl_menu WHERE menu_id = '$menuId'";
		$delete = $connect->query($sql_delete);
		if ($delete) {
			$_SESSION['msg'] = "Menu deleted successfully...";
		    header( "Location:menu.php");
		    exit;
		}
	}   	

?>

<section class="content">

	<ol class="breadcrumb">
		<li><a href="dashboard.php">Dashboard</a></li>
		<li class="active">Manage Menu</a></li>
	</ol>

	<div class="container-fluid">

		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="card corner-radius">
					<div class="header">
						<h2>MANAGE MENU</h2>
						<div class="header-dropdown m-r--5">
							<a href="menu-add.php"><button type="button" class="button button-rounded btn-offset waves-effect waves-float">ADD NEW MENU</button></a>
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
									<td width="1%"><a href="menu.php"><button type="button" class="button button-rounded waves-effect waves-float">RESET</button></a></td>
									<td width="1%"><button type="submit" class="btn bg-blue btn-circle waves-effect waves-circle waves-float"><i class="material-icons">search</i></button></td>
								</tr>
							</table>
						</form>

						<?php if ($tcount == 0) { ?>
							<p align="center" style="font-size: 110%;">There are no menus.</p>
						<?php } else { ?>							

						<table class='table table-hover table-striped table-offset'>
							<thead>
								<tr>
									<th>Menu title</th>
									<th>Wallpaper order</th>
									<th>Wallpaper type</th>
									<th width="15%">Action</th>
								</tr>
							</thead>
							<?php
							while(($count < $rpp) && ($i < $tcount)) {
								mysqli_data_seek($result, $i);
								$data = mysqli_fetch_array($result);
								?>
								<tr>
									<td style="vertical-align: middle;"><?php if ($data['menu_status'] == '1') { echo $data['menu_title']; } else { echo '<strike>'.$data['menu_title'].'</strike>'; } ?></td>
									<td style="vertical-align: middle;"><div class="uppercase"><span class="label label-rounded <?php if ($data['menu_status'] == '1') { echo 'bg-green'; } else { echo 'bg-grey'; } ?>"><?php echo $data['menu_order']; ?></span></div></td>
									<td style="vertical-align: middle;">
										<?php if ($data['menu_filter'] == 'wallpaper') { ?>
											<div class="uppercase"><span class="label label-rounded <?php if ($data['menu_status'] == '1') { echo 'bg-green'; } else { echo 'bg-grey'; } ?>">Wallpaper</span></div>
										<?php } else if ($data['menu_filter'] == 'live') { ?>
											<div class="uppercase"><span class="label label-rounded <?php if ($data['menu_status'] == '1') { echo 'bg-orange'; } else { echo 'bg-grey'; } ?>">&nbsp;&nbsp;Live&nbsp;&nbsp;</span></div>
										<?php } else if ($data['menu_filter'] == 'both') { ?>
											<div class="uppercase"><span class="label label-rounded <?php if ($data['menu_status'] == '1') { echo 'bg-green'; } else { echo 'bg-grey'; } ?>">Wallpaper</span>&nbsp;&nbsp;<span class="label label-rounded <?php if ($data['menu_status'] == '1') { echo 'bg-orange'; } else { echo 'bg-grey'; } ?>">&nbsp;&nbsp;Live&nbsp;&nbsp;</span></div>
										<?php } else { ?>

										<?php } ?>
									</td>
									<td style="vertical-align: middle;">

										<?php if ($data['menu_status'] == '1') { ?>
										<a href="menu.php?page=<?php echo $page; ?>&disable=<?php echo $data['menu_id'];?>" onclick="return confirm('Are you sure want to disable this menu?')">
											<i class="material-icons">visibility</i>
										</a>
										<?php } else { ?>
										<a href="menu.php?page=<?php echo $page; ?>&enable=<?php echo $data['menu_id'];?>" onclick="return confirm('Are you sure want to enable this menu?')">
											<i class="material-icons">visibility_off</i>
										</a>
										<?php } ?>

										<a href="menu-edit.php?id=<?php echo $data['menu_id'];?>">
											<i class="material-icons">mode_edit</i>
										</a>

										<a href="menu.php?delete=<?php echo $data['menu_id'];?>" onclick="return confirm('Are you sure want to delete this menu?')" >
											<i class="material-icons">delete</i>
										</a>
									</td>
								</tr>
								<?php
								$i++; 
								$count++;
							}
							?>
						</table>

						<?php } ?>

						<?php if ($tcount > $postPerPage) { echo pagination($reload, $page, $keyword, $tpages); } ?>

					</div>

				</div>
			</div>
		</div>
	</div>
</section>

<?php include_once('includes/footer.php'); ?>