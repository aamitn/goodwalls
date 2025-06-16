<?php
    include_once('includes/header.php');
    require_once('assets/plugins/getid3/getid3.php'); 
?>

<?php

    error_reporting(0);

    $getID3 = new getID3; 

    if (isset($_GET['id'])) {
        $ID = clean($_GET['id']);
        $qry    = "SELECT * FROM tbl_gallery WHERE id = '$ID'";
        $result = $connect->query($qry);
        $row    = $result->fetch_assoc();
    }
    
    if(isset($_POST['submit'])) {

        $categoryId = clean($_POST['cat_id']);
        $imageFormat = clean($_POST['image_format']);
        $rewarded = clean($_POST['rewarded']);

        if ($_POST['tags'] == '') {
            $sql = "SELECT * FROM tbl_category WHERE cid = '$categoryId'";
            $result = mysqli_query($connect, $sql);
            $row = mysqli_fetch_assoc($result);
            $tags = $row['category_name'];
        } else {
            $tags = clean($_POST['tags']);
        }

        if ($_POST['upload_type'] == 'url') {

            $wallpaperImage = '';
            $imageUrl = clean($_POST['image_url']);

            //get thumbnail and image dimensions
            if ($imageFormat == 'mp4') {
                if ($_FILES['image_thumbnail']['name'] != '') {
                    unlink('upload/thumbs/'.$_POST['old_thumbnail']);
                    $imageThumbnail = time().'_'.$_FILES['image_thumbnail']['name'];
                    $imageThumbnailTmp = $_FILES['image_thumbnail']['tmp_name'];
                    $filePath = 'upload/thumbs/'.$imageThumbnail;
                    copy($imageThumbnailTmp, $filePath);
                } else {
                    $imageThumbnail = clean($_POST['old_thumbnail']);
                }
                $filename = tempnam('/tmp','getid3');
                $ch = curl_init($imageUrl);
                $fp = fopen($filename, 'wb');
                curl_setopt($ch, CURLOPT_FILE, $fp);
                curl_setopt($ch, CURLOPT_HEADER, 0);
                curl_exec($ch);
                if (!curl_errno($ch)) {
                    $file = $getID3->analyze($filename);
                    unlink($filename);
                    $width = $file['video']['resolution_x'];
                    $height = $file['video']['resolution_y'];
                    $imageResolution = $width." x ".$height;
                } else {
                    $imageResolution = 'Unknown';
                }
                curl_close($ch);
                fclose($fp);
            } else {
                $imageThumbnail = '';
                $imageInfo = getimagesize($imageUrl);
                $width = $imageInfo[0];
                $height = $imageInfo[1];
                $imageResolution = $width." x ".$height;
            }

            //get mime type
            $mimeType = substrAfter($imageUrl, ".");
            $mimeType = strtolower($mimeType);
            if ($mimeType == 'jpg') {
                $imageMime = "image/jpeg";
            } else if ($mimeType == 'jpeg') {
                $imageMime = "image/jpeg";
            } else if ($mimeType == 'png') {
                $imageMime = "image/png";
            } else if ($mimeType == 'gif') {
                $imageMime = "image/gif";
            } else if ($mimeType == 'webp') {
                $imageMime = "image/webp";
            } else if ($mimeType == 'bmp') {
                $imageMime = "image/bmp";
            } else if ($mimeType == 'mp4') {
                $imageMime = "application/octet-stream";
            } else {
                $imageMime = "image/unknown";
            }

            //get file size
            $bytes = remotefileSize($imageUrl);
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

            if ($imageFormat == 'mp4') {
                if ($_FILES['image_thumbnail']['name'] != '') {
                    unlink('upload/thumbs/'.$_POST['old_thumbnail']);
                    $imageThumbnail = time().'_'.$_FILES['image_thumbnail']['name'];
                    $imageThumbnailTmp = $_FILES['image_thumbnail']['tmp_name'];
                    $filePath = 'upload/thumbs/'.$imageThumbnail;
                    copy($imageThumbnailTmp, $filePath);
                } else {
                    $imageThumbnail = clean($_POST['old_thumbnail']);
                }
            } else {
                $imageThumbnail = '';
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

                $wallpaperImage = time().'_'.$_FILES['wallpaper_image']['name'];
                $fileImage      = $_FILES['wallpaper_image']['tmp_name'];

                $filePath       = 'upload/'.$wallpaperImage;
                $thumbnailPath  = 'upload/thumbs/'.$wallpaperImage;

                $upload = copy($fileImage, $filePath);

                if ($imageFormat == 'mp4') {
                    $file = $getID3->analyze($filePath);
                    $width = $file['video']['resolution_x'];
                    $height = $file['video']['resolution_y'];
                } else {
                    if ($upload) {
                        $realImage = imagecreatefromgif($filePath);
                        $ox = imagesx($realImage);
                        $oy = imagesy($realImage);
                        $nx = 300;
                        $ny = floor(($nx/$ox)*$oy);
                        $tmpImage = imagecreatetruecolor($nx,$ny);
                        imagecopyresampled($tmpImage, $realImage, 0, 0, 0, 0, $nx, $ny, $ox, $oy);
                        imagegif($tmpImage, $thumbnailPath);
                        imagedestroy($realImage);
                        imagedestroy($tmpImage);
                    }
                    $imageInfo = getimagesize($filePath);
                    $width = $imageInfo[0];
                    $height = $imageInfo[1];
                }

                //get resolution
                $imageResolution = $width." x ".$height;
                
                $mimeType = substrAfter($filePath, ".");
                $mimeType = strtolower($mimeType);
                if ($mimeType == 'jpg') {
                    $imageMime = "image/jpeg";
                } else if ($mimeType == 'jpeg') {
                    $imageMime = "image/jpeg";
                } else if ($mimeType == 'png') {
                    $imageMime = "image/png";
                } else if ($mimeType == 'gif') {
                    $imageMime = "image/gif";
                } else if ($mimeType == 'webp') {
                    $imageMime = "image/webp";
                } else if ($mimeType == 'bmp') {
                    $imageMime = "image/bmp";
                } else if ($mimeType == 'mp4') {
                    $imageMime = "application/octet-stream";
                } else {
                    $imageMime = "image/unknown";
                }
                
                //get file size
                $bytes = filesize($filePath);
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
                $wallpaperImage = clean($_POST['old_image']);
                $imageResolution = clean($_POST['old_resolution']);
                $bytes = clean($_POST['old_size']);
                $imageMime = clean($_POST['old_mime']);               
            }
            
            $imageUrl = '';

        }   

        $data = array(
            'cat_id'    => $categoryId,
            'image'     => $wallpaperImage,
            'image_thumb' => $imageThumbnail,
            'image_url' => $imageUrl,
            'tags'      => $tags,
            'type'      => clean($_POST['upload_type']),
            'image_name' => clean($_POST['image_name']),
            'image_resolution' => $imageResolution,
            'image_size' => $bytes,
            'image_extension' => $imageMime,
            'rewarded' => $rewarded
        );  

        $update = update('tbl_gallery', $data, "WHERE id = '$ID'");

        if ($update > 0) {
            $_SESSION['msg'] = "Changes Saved...";
            header("Location:live-edit.php?id=$ID");
            exit;
        }
         
    }

    $sql_categories = "SELECT * FROM tbl_category";
    $result_categories = $connect->query($sql_categories);
      
?>

<script type="text/javascript">
    $(document).ready(function(e) {
        $("#upload_type").change(function() {
            var type = $("#upload_type").val();
            if (type == "url") {
                $("#upload").hide();
                $("#url").show();
            }
            if (type == "upload") {
                $("#url").hide();
                $("#upload").show();
            }
        });

        $(window).load(function() {
            var type = $("#upload_type").val();
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

<script type="text/javascript">
    $(document).ready(function(e) {
        $("#image_format").change(function() {
            var type = $("#image_format").val();
            if (type == "gif") {
                $("#gif").show();
                $("#mp4").hide();
            }
            if (type == "mp4") {
                $("#gif").hide();
                $("#mp4").show();
            }
        });

        $(window).load(function() {
            var type = $("#image_format").val();
            if (type == "gif") {
                $("#gif").show();
                $("#mp4").hide();
            }
            if (type == "mp4") {
                $("#gif").hide();
                $("#mp4").show();
            }
        });
    });
</script>

   <section class="content">
   
        <ol class="breadcrumb">
            <li><a href="dashboard.php">Dashboard</a></li>
            <li><a href="live.php">Manage Live Wallpaper</a></li>
            <li class="active">Edit Live Wallpaper</a></li>
        </ol>

       <div class="container-fluid">

            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

                    <form id="form_validation" method="post" enctype="multipart/form-data">
                    <div class="card corner-radius">
                        <div class="header">
                            <h2>ADD LIVE WALLPAPER</h2> 
                        </div>
                        <div class="body">
                            
                            <?php if(isset($_SESSION['msg'])) { ?>
                            <div class='alert alert-info alert-dismissible corner-radius' role='alert'>
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>&nbsp;&nbsp;</button>
                                <?php echo $_SESSION['msg']; ?>
                            </div>
                            <?php unset($_SESSION['msg']); } ?>

                            <div class="row clearfix">

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
                                                while ($row_category = mysqli_fetch_array($result_categories)) {
                                                    $sel = '';
                                                    if ($row_category['cid'] == $row['cat_id']) {
                                                    $sel = "selected";  
                                                }   
                                            ?>
                                            <option value="<?php echo $row_category['cid'];?>" <?php echo $sel; ?>><?php echo $row_category['category_name'];?></option>
                                                <?php } ?>
                                        </select>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Image Source</div>
                                        <select class="form-control show-tick" name="upload_type" id="upload_type">
                                            <option <?php if($row['type'] == 'upload'){ echo 'selected';} ?> value="upload">Upload</option>
                                            <option <?php if($row['type'] == 'url'){ echo 'selected';} ?> value="url">URL</option>
                                        </select>
                                    </div>

                                    <div class="form-group col-sm-12">
                                       <div class="font-12">Format</div>
                                       <select class="form-control show-tick" name="image_format" id="image_format">
                                            <option <?php if($row['image_extension'] == 'image/gif'){ echo 'selected';} ?> value="gif">GIF</option>
                                            <option <?php if($row['image_extension'] == 'application/octet-stream'){ echo 'selected';} ?> value="mp4">MP4</option>
                                        </select>
                                    </div>

                                    <div id="mp4">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <div class="font-12 ex1">Live Wallpaper Thumbnail ( JPG, JPEG, PNG)</div>
                                                <input type="file" name="image_thumbnail" id="image_thumbnail" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="jpg jpeg png gif bmp webp" data-default-file="upload/thumbs/<?php echo $row['image_thumb']; ?>" data-show-remove="false" />
                                                <div class="help-info pull-left">( Live wallpaper with mp4 format must upload thumbnail )</div>
                                            </div>
                                        </div>
                                        <div class="col-sm-12" style="margin-bottom: 0px;"></div>
                                    </div>                                                       

                                    <div id="upload">
                                        <div class="col-sm-6">
                                            <div class="font-12 ex1">Live Wallpaper ( GIF, MP4 )</div>
                                            <?php if ($row['image_extension'] == 'application/octet-stream') { ?>
                                                <input type="file" name="wallpaper_image" accept="image/gif, video/mp4"/><br>
                                                <div class="font-12 ex1">Live Wallpaper Preview</div>
                                                <video width="100%" height="200px" style="background-color: #000;" controls loop>
                                                    <source src="upload/<?php echo $row['image']; ?>" type="video/mp4">
                                                    Your browser does not support the video tag.
                                                </video>
                                            <?php } else { ?>
                                                <div class="form-group">
                                                    <input type="file" name="wallpaper_image" id="fileupload" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="gif mp4" data-default-file="upload/<?php echo $row['image']; ?>" data-show-remove="false"/>
                                                    <div class="help-info pull-left">( Upload your live wallpaper file here )</div>
                                                </div>
                                            <?php } ?>
                                        </div>
                                    </div>

                                    <div id="url">
                                        <div class="form-group col-sm-12">
                                            <div class="font-12">Live Wallpaper URL</div>
                                            <div class="form-line">
                                                <input type="text" class="form-control" name="image_url" id="image_url" placeholder="https://www.example.com/live-wallpaper.mp4" value="<?php echo $row['image_url']; ?>" required/>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <?php if ($row['image_url'] != '') { ?>
                                            <div class="font-12 ex1">Live Wallpaper Preview</div>
                                            <div class="form-group">
                                                <?php if ($row['image_extension'] == 'application/octet-stream') { ?>
                                                    <video width="100%" height="200px" style="background-color: #000;" controls loop>
                                                        <source src="<?php echo $row['image_url']; ?>" type="video/mp4">
                                                        Your browser does not support the video tag.
                                                    </video>
                                                <?php } else { ?>
                                                    <input type="file" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="jpg jpeg png bmp webp" data-default-file="<?php echo $row['image_url']; ?>" data-show-remove="false" disabled/>
                                                <?php } ?>
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
                                    <input type="hidden" name="old_thumbnail" value="<?php echo $row['image_thumb'];?>">
                                    <input type="hidden" name="id" value="<?php echo $row['id'];?>">

                                    <div class="col-sm-12">
                                    <button type="submit" name="submit" class="button button-rounded waves-effect waves-float pull-right">UPDATE</button>
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