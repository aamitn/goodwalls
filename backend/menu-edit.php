<?php include_once('includes/header.php'); ?>

<?php

    if (isset($_GET['id'])) {

    	$ID = clean($_GET['id']);
        $sql_menu = "SELECT * FROM tbl_menu WHERE menu_id = '$ID'";
        $result = $connect->query($sql_menu);
        $row = $result->fetch_assoc();

    }

    if (isset($_POST['submit'])) {
 
        $data = array(
            'menu_title'  => clean($_POST['menu_title']),
            'menu_order'  => clean($_POST['menu_order']),
            'menu_filter' => clean($_POST['menu_filter'])
        );

        $update = update('tbl_menu', $data, "WHERE menu_id = '$ID'");

        if ($update > 0) {
            $_SESSION['msg'] = "Changes Saved...";
            header("Location:menu-edit.php?id=$ID");
            exit;
        }

    }

	$orders = array(
		'recent' => 'Recent',
		'oldest' => 'Oldest',
		'featured' => 'Featured',
		'popular' => 'Popular',
		'download' => 'Download',
		'random' =>'Random'
	);


	$filters = array(
		'wallpaper' => 'Wallpaper',
		'live' => 'Live Wallpaper',
		'both' => 'Both (Wallpaper & Live Wallpaper)'
	);

 	$category_sql = "SELECT * FROM tbl_category";
	$category_result = $connect->query($category_sql);	

?>

<section class="content">

	<ol class="breadcrumb">
		<li><a href="dashboard.php">Dashboard</a></li>
		<li><a href="menu.php">Manage Menu</a></li>
		<li class="active">Edit Menu</a></li>
	</ol>

	<div class="container-fluid">

		<div class="row clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

				<form id="form_validation" method="post" enctype="multipart/form-data">
					<div class="card corner-radius">
						<div class="header">
							<h2>ADD MENU</h2>
						</div>
						<div class="body">

							<?php if(isset($_SESSION['msg'])) { ?>
							<div class='alert alert-info alert-dismissible corner-radius' role='alert'>
								<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>&nbsp;&nbsp;</button>
								<?php echo $_SESSION['msg']; ?>
							</div>
							<?php unset($_SESSION['msg']); } ?>                            

							<div class="row clearfix">

								<div class="col-sm-12">
									<div class="form-group form-float">
										<div class="form-line">
											<div class="font-12">Menu title</div>
											<input type="text" class="form-control" name="menu_title" id="menu_title" value="<?php echo $row['menu_title']; ?>"  placeholder="Menu Title" required>
										</div>
									</div>

									<div>
										<div class="form-group">
											<div class="font-12">Wallpaper order</div>
											<select class="form-control show-tick" name="menu_order" id="menu_order">
												<?php foreach ($orders as $value => $name) { ?>
												<option value="<?php echo $value; ?>" <?php if ($value == $row['menu_order']) {echo 'selected';} else { echo ''; }?>>
													<?php echo $name; ?>	
												</option>
												<?php } ?>
											</select>
										</div>
									</div>

									<div>
										<div class="form-group">
											<div class="font-12">Wallpaper type</div>
											<select class="form-control show-tick" name="menu_filter" id="menu_filter">
												<?php foreach ($filters as $value => $name) { ?>
												<option value="<?php echo $value; ?>" <?php if ($value == $row['menu_filter']) {echo 'selected';} else { echo ''; }?>>
													<?php echo $name; ?>	
												</option>
												<?php } ?>
											</select>
										</div>
									</div>

                                    <div class="form-group">
                                        <div class="font-12">Wallpaper category</div>
                                        <select class="form-control show-tick" name="menu_category" id="menu_category">
                                        	<?php if ($row['menu_category'] == '0') { ?>
                                        		<option value="0" selected>All categories</option> 
	                                            <?php while ($category_row = mysqli_fetch_array($category_result)) { ?>
											    	<option value="<?php echo $category_row['cid'];?>"><?php echo $category_row['category_name'];?></option>
											    <?php } ?>

                                        	<?php } else { ?>
												<option value="0">All categories</option> 
	                                            <?php 	
													while ($category_row = mysqli_fetch_array($category_result)) {
														$sel = '';
														if ($category_row['cid'] == $row['menu_category']) {
														$sel = "selected";	
													}
												?>
											    	<option value="<?php echo $category_row['cid'];?>" <?php echo $sel; ?>><?php echo $category_row['category_name'];?></option>
											    <?php } ?>
											<?php } ?>
                                        </select>
                                    </div>									

									<input type="hidden" name="id" id="id" value="<?php echo $row['menu_id']; ?>" >									

									<div class="col-sm-12">
										<button class="button button-rounded waves-effect waves-float pull-right" type="submit" name="submit">SUBMIT</button>
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