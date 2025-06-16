<?php 
    include_once('includes/header.php');
?>

<?php

    if (isset($_GET['id'])) {
        $ID = clean($_GET['id']);
        $qry    = "SELECT * FROM tbl_gallery WHERE id = '$ID'";
        $result = $connect->query($qry);
        $row    = $result->fetch_assoc();
    }

    if(isset($_POST['submit'])) {

        $categoryId = clean($_POST['cat_id']);
        $rewarded = clean($_POST['rewarded']);

        if ($_POST['upload_type'] == 'url') {

            if ($_POST['tags'] == '') {
                $sql = "SELECT * FROM tbl_category WHERE cid = '$categoryId'";
                $result = $connect->query($sql);
                $row = $result->fetch_assoc();
                $tags = $row['category_name'];
            } else {
                $tags = $_POST['tags'];
            }

            $wallpaper_image = '';
            $image_url = $_POST['image_url'];
            if ($row['image'] != '') {
                unlink('upload/'.$_POST['old_image']);
                unlink('upload/thumbs/'.$_POST['old_image']);
            }

            $imageInfo = getimagesize($image_url);
            $width = $imageInfo[0];
            $height = $imageInfo[1];            
            $image_resolution = $width." x ".$height;

            $mimeType = substrAfter($image_url, ".");
            $mimeType = strtolower($mimeType);
            if ($mimeType == 'jpg') {
                $image_mime = "image/jpeg";
            } else if ($mimeType == 'jpeg') {
                $image_mime = "image/jpeg";
            } else if ($mimeType == 'png') {
                $image_mime = "image/png";
            } else if ($mimeType == 'gif') {
                $image_mime = "image/gif";
            } else if ($mimeType == 'webp') {
                $image_mime = "image/webp";
            } else if ($mimeType == 'bmp') {
                $image_mime = "image/bmp";
            } else if ($mimeType == 'mp4') {
                $image_mime = "application/octet-stream";
            } else {
                $image_mime = "image/unknown";
            }

            $bytes = remotefileSize($_POST['image_url']);

            if ($bytes >= 1073741824) {
                $bytes = number_format($bytes / 1073741824, 2) . ' GB';
            } else if ($bytes >= 1048576) {
                $bytes = number_format($bytes / 1048576, 2) . ' MB';
            } else if ($bytes >= 1024) {
                $bytes = number_format($bytes / 1024, 2) . ' KB';
            } else if ($bytes > 1) {
                $bytes = $bytes . ' bytes';
            } else if ($bytes == 1) {
                $bytes = $bytes . ' byte';
            } else {
                $bytes = '0 bytes';
            }       

        } else {

            if ($_POST['tags'] == '') {
                $sql = "SELECT * FROM tbl_category WHERE cid = '$categoryId'";
                $result = $connect->query($sql);
                $row = $result->fetch_assoc();
                $tags = $row['category_name'];
            } else {
                $tags = $_POST['tags'];
            }

            if ($_FILES['wallpaper_image']['name'] != '') {

                $old_path = 'upload/'.$_POST['old_image'];
                if (file_exists($old_path)) {
                    unlink($old_path);
                }
                
                $old_thumbnail = 'upload/thumbs/'.$_POST['old_image'];
                if (file_exists($old_thumbnail)) {
                    unlink($old_thumbnail);
                }

                $wallpaper_image = time().'_'.$_FILES['wallpaper_image']['name'];
                $arr_ = explode(".", $wallpaper_image);
                $file_extension = end($arr_);

                $file_path = 'upload/'.$wallpaper_image;
                $thumbnail_path = 'upload/thumbs/'.$wallpaper_image;

                $file_image = $_FILES['wallpaper_image']['tmp_name'];
                $upload = copy($file_image, $file_path);

                if ($upload) {
                    if ($file_extension == "jpg" || $file_extension == "jpeg") {
                        $real_image = imagecreatefromjpeg($file_path);
                        $ox = imagesx($real_image);
                        $oy = imagesy($real_image);
                        $nx = 300;
                        $ny = floor(($nx/$ox)*$oy);
                        $tmp_image = imagecreatetruecolor($nx,$ny);
                        imagecopyresampled($tmp_image, $real_image, 0, 0, 0, 0, $nx, $ny, $ox, $oy);
                        imagejpeg($tmp_image, $thumbnail_path, 80);
                        imagedestroy($real_image);
                        imagedestroy($tmp_image);
                    } else if ($file_extension == "png") {
                        $real_image = imagecreatefrompng($file_path);
                        $ox = imagesx($real_image);
                        $oy = imagesy($real_image);
                        $nx = 300;
                        $ny = floor(($nx/$ox)*$oy);
                        $tmp_image = imagecreatetruecolor($nx, $ny);
                        imagealphablending($tmp_image, false);
                        imagesavealpha($tmp_image, true);
                        imagecopyresampled($tmp_image, $real_image, 0, 0, 0, 0, $nx, $ny, $ox, $oy);
                        $invertScaleQuality = 9 - round((80/100) * 9);
                        imagepng($tmp_image, $thumbnail_path, $invertScaleQuality);
                        imagedestroy($real_image);
                        imagedestroy($tmp_image);                        
                    }
                }
                
                $imageInfo = getimagesize($file_path);
                $width = $imageInfo[0];
                $height = $imageInfo[1];
                $image_resolution = $width." x ".$height;
                
                $mimeType = substrAfter($file_path, ".");
                $mimeType = strtolower($mimeType);
                if ($mimeType == 'jpg') {
                    $image_mime = "image/jpeg";
                } else if ($mimeType == 'jpeg') {
                    $image_mime = "image/jpeg";
                } else if ($mimeType == 'png') {
                    $image_mime = "image/png";
                } else if ($mimeType == 'gif') {
                    $image_mime = "image/gif";
                } else if ($mimeType == 'webp') {
                    $image_mime = "image/webp";
                } else if ($mimeType == 'bmp') {
                    $image_mime = "image/bmp";
                } else if ($mimeType == 'mp4') {
                    $image_mime = "application/octet-stream";
                } else {
                    $image_mime = "image/unknown";
                }

                $bytes = filesize($file_path);

                if ($bytes >= 1073741824) {
                    $bytes = number_format($bytes / 1073741824, 2) . ' GB';
                } else if ($bytes >= 1048576) {
                    $bytes = number_format($bytes / 1048576, 2) . ' MB';
                } else if ($bytes >= 1024) {
                    $bytes = number_format($bytes / 1024, 2) . ' KB';
                } else if ($bytes > 1) {
                    $bytes = $bytes . ' bytes';
                } else if ($bytes == 1) {
                    $bytes = $bytes . ' byte';
                } else {
                    $bytes = '0 bytes';
                }

            } else {
                $wallpaper_image = clean($_POST['old_image']);
                $image_resolution = clean($_POST['old_resolution']);
                $bytes = clean($_POST['old_size']);
                $image_mime = clean($_POST['old_mime']);
            }

            $image_url = '';

        }
 
        $data = array(                                           
            'cat_id'    => $categoryId,
            'image'     => $wallpaper_image,
            'image_url' => $image_url,
            'tags'      => $tags,
            'type'      => clean($_POST['upload_type']),
            'image_name' => clean($_POST['image_name']),
            'image_resolution' => $image_resolution,
            'image_size' => $bytes,
            'image_extension' => $image_mime,
            'rewarded' => $rewarded
        );  

        $hasil = update('tbl_gallery', $data, "WHERE id = '$ID'");

        if ($hasil > 0) {
            $_SESSION['msg'] = "Changes Saved...";
            header("Location:wallpaper-edit.php?id=$ID");
            exit;
        }

    }

    $sql_categories = "SELECT * FROM tbl_category";
    $result_categories = $connect->query($sql_categories);

?>

<script type="text/javascript">
    $(document).ready(function(e) {
        $("#upload_type").change(function() {
           var type=$("#upload_type").val();
           if (type == "url") {
             $("#upload").hide();
             $("#url").show();
         }
         if (type == "upload") {
             $("#url").hide();
             $("#upload").show();
         }  
     });

        $( window ).load(function() {
          var type=$("#upload_type").val();
          if (type == "url") {
             $("#upload").hide();
             $("#url").show();
         }
         if (type == "upload") {
             $("#url").hide();
             $("#upload").show();
         }
     });
    }); 

</script>

   <section class="content">
   
        <ol class="breadcrumb">
            <li><a href="dashboard.php">Dashboard</a></li>
            <li><a href="wallpaper.php">Manage Wallpaper</a></li>
            <li class="active">Edit Wallpaper</a></li>
        </ol>

       <div class="container-fluid">

            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

                    <form id="form_validation" method="post" enctype="multipart/form-data">
                    <div class="card corner-radius">
                        <div class="header">
                            <h2>EDIT WALLPAPER</h2> 
                        </div>
                        <div class="body">

                            <?php if(isset($_SESSION['msg'])) { ?>
                                <div class='alert alert-info alert-dismissible corner-radius' role='alert'>
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>&nbsp;&nbsp;</button>
                                    <?php echo $_SESSION['msg']; ?>
                                </div>
                            <?php unset($_SESSION['msg']); } ?>                            

                            <div class="row clearfix">
                                <div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Wallpaper Name</div>
                                        <div class="form-line">
                                            <input type="text" class="form-control" name="image_name" id="image_name" placeholder="Wallpaper Name" value="<?php echo $row['image_name']; ?>" required/>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Category</div>
                                        <select class="form-control show-tick" name="cat_id" id="cat_id">
                                           <?php    
                                                while ($r_c_row = mysqli_fetch_array($result_categories)) {
                                                    $sel = '';
                                                    if ($r_c_row['cid'] == $row['cat_id']) {
                                                    $sel = "selected";  
                                                }   
                                            ?>
                                            <option value="<?php echo $r_c_row['cid'];?>" <?php echo $sel; ?>><?php echo $r_c_row['category_name'];?></option>
                                                <?php } ?>
                                        </select>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Image Type</div>
                                        <select class="form-control show-tick" name="upload_type" id="upload_type">
                                            <option <?php if($row['type'] == 'upload'){ echo 'selected';} ?> value="upload">Upload</option>
                                            <option <?php if($row['type'] == 'url'){ echo 'selected';} ?> value="url">Image URL</option>
                                        </select>
                                    </div>                                    

                                    <div id="upload" class="col-sm-6">
                                        <div class="font-12 ex1">Image ( JPG, JPEG, PNG, WEBP )</div>
                                        <div class="form-group">
                                            <input type="file" name="wallpaper_image" id="wallpaper_image" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="jpg jpeg png bmp webp" data-default-file="upload/<?php echo $row['image']; ?>" data-show-remove="false"/>
                                            <div class="help-info pull-left">( Recommended resolution : 480x854 or 720x1280 or 1080x1980 pixels)</div>
                                        </div>
                                    </div>                                

                                    <div id="url">
                                        <div class="form-group col-sm-12">
                                            <div class="font-12">Image URL</div>
                                            <div class="form-line">
                                                <input type="text" class="form-control" name="image_url" id="image_url" placeholder="http://www.abc.com/image_name.jpg" value="<?php echo $row['image_url']; ?>" required/>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <?php if ($row['image_url'] != '') { ?>
                                            <div class="font-12 ex1">Image Preview</div>
                                            <div class="form-group">
                                                <input type="file" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="jpg jpeg png bmp webp" data-default-file="<?php echo $row['image_url']; ?>" data-show-remove="false" disabled/>
                                            </div>
                                            <?php } else { } ?>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Tags (Optional)</div>
                                        <div class="form-line">
                                            <input type="text" class="form-control" name="tags" id="tags" data-role="tagsinput" value="<?php echo $row['tags']; ?>" required/>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Rewarded Ad</div>
                                        <select class="form-control show-tick" name="rewarded" id="rewarded">
                                            <option <?php if($row['rewarded'] == '1'){ echo 'selected';} ?> value="1">Yes</option>
                                            <option <?php if($row['rewarded'] == '0'){ echo 'selected';} ?> value="0">No</option>
                                        </select>
                                    </div>                                    

                                    <input type="hidden" name="old_resolution" value="<?php echo $row['image_resolution'];?>">                                
                                    <input type="hidden" name="old_size" value="<?php echo $row['image_size'];?>">                                
                                    <input type="hidden" name="old_mime" value="<?php echo $row['image_extension'];?>">                                
                                    <input type="hidden" name="old_image" value="<?php echo $row['image'];?>">
                                    <input type="hidden" name="id" value="<?php echo $row['id'];?>">

                                    <div class="col-sm-12">
                                        <button type="submit" name="submit" class="button button-rounded waves-effect waves-float pull-right">UPDATE</button>
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