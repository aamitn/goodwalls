<?php 
    include_once('includes/header.php');
    require_once('assets/plugins/getid3/getid3.php'); 
?>

<?php

    error_reporting(0);
    $getID3 = new getID3; 

    $cat_qry = "SELECT * FROM tbl_category ORDER BY category_name";
    $cat_result = $connect->query($cat_qry); 
    
    if(isset($_POST['submit'])) {

        $categoryId = clean($_POST['cat_id']);
        $imageUrl = clean($_POST['image_url']);
        $imageFormat = clean($_POST['image_format']);
        $rewarded = clean($_POST['rewarded']);

        if ($_POST['upload_type'] == 'url') {

            if ($_POST['tags'] == '') {
                $sql = "SELECT * FROM tbl_category WHERE cid = '$categoryId'";
                $result = mysqli_query($connect, $sql);
                $row = mysqli_fetch_assoc($result);
                $tags = $row['category_name'];
            } else {
                $tags = clean($_POST['tags']);
            }

            //get thumbnail and image dimensions
            if ($imageFormat == 'mp4') {
                $imageThumbnail = time().'_'.$_FILES['image_thumbnail']['name'];
                $imageThumbnailTmp = $_FILES['image_thumbnail']['tmp_name'];
                $filePath = 'upload/thumbs/'.$imageThumbnail;
                copy($imageThumbnailTmp, $filePath);

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

            $mimeType = substrAfter($imageUrl, ".");
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

            $data = array( 
                'cat_id'    => $categoryId,
                'image'     => '',
                'image_thumb' => $imageThumbnail,
                'image_url' => $imageUrl,
                'tags'      => $tags,
                'type'      => clean($_POST['upload_type']),
                'image_name' => clean($_POST['image_name']),
                'image_resolution' => $width." x ".$height,
                'image_size' => $bytes,
                'image_extension' => $image_mime,
                'rewarded' => $rewarded
            );  

            $qry = insert('tbl_gallery', $data);

        } else {

            if ($_POST['tags'] == '') {
                $sql = "SELECT * FROM tbl_category WHERE cid = '$categoryId'";
                $result = mysqli_query($connect, $sql);
                $row = mysqli_fetch_assoc($result);
                $tags = $row['category_name'];
            } else {
                $tags = $_POST['tags'];
            }

            if ($imageFormat == 'mp4') {
                $imageThumbnail = time().'_'.$_FILES['image_thumbnail']['name'];
                $imageThumbnailTmp = $_FILES['image_thumbnail']['tmp_name'];
                $filePath = 'upload/thumbs/'.$imageThumbnail;
                copy($imageThumbnailTmp, $filePath);
            } else {
                $imageThumbnail = '';
            }         

            $count = count($_FILES['wallpaper_image']['name']);

            for ($i = 0; $i < $count; $i++) {

                $image_name = time()."_".$_FILES['wallpaper_image']['name'][$i];
                         
                $file_path = 'upload/'.$image_name;
                $thumbnail_path = 'upload/thumbs/'.$image_name;
                $file_images = $_FILES["wallpaper_image"]["tmp_name"][$i];
                $upload = copy($file_images, $file_path);

                //get image dimensions
                if ($imageFormat == 'mp4') {
                    $file = $getID3->analyze($file_path);
                    $width = $file['video']['resolution_x'];
                    $height = $file['video']['resolution_y'];
                    $imageResolution = $width." x ".$height;
                } else {
                    if ($upload) {
                        $real_image = imagecreatefromgif($file_path);
                        $ox = imagesx($real_image);
                        $oy = imagesy($real_image);
                        $nx = 300;
                        $ny = floor(($nx/$ox)*$oy);
                        $tmp_image = imagecreatetruecolor($nx,$ny);
                        imagecopyresampled($tmp_image, $real_image, 0, 0, 0, 0, $nx, $ny, $ox, $oy);
                        imagegif($tmp_image, $thumbnail_path);
                        imagedestroy($real_image);
                        imagedestroy($tmp_image);
                    }
                    $imageInfo = getimagesize($file_path);
                    $width = $imageInfo[0];
                    $height = $imageInfo[1];
                    $imageResolution = $width." x ".$height;
                }

                //get mime type
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

                //get file size
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

                $data = array(
                    'cat_id'    => $categoryId,
                    'image'     => $image_name,
                    'image_thumb' => $imageThumbnail,
                    'image_url' => '',
                    'tags'      => $tags,
                    'type'      => clean($_POST['upload_type']),
                    'image_name' => clean($_POST['image_name']),
                    'image_resolution' => $imageResolution,
                    'image_size' => $bytes,
                    'image_extension' => $image_mime,
                    'rewarded' => $rewarded
                );  

                $qry = insert('tbl_gallery', $data);
            }

        }       

        $_SESSION['msg'] = "Wallpapers added Successfully...";
        header("Location:live-add.php");
        exit;   
         
    }
      
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
            <li class="active">Add Live Wallpaper</a></li>
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
                                            <input type="text" class="form-control" name="image_name" id="image_name" placeholder="Wallpaper Name" required/>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Category</div>
                                        <select class="form-control show-tick" name="cat_id" id="cat_id">
                                            <?php
                                                while ($cat_row = mysqli_fetch_array($cat_result)) {
                                            ?>                                   
                                                <option value="<?php echo $cat_row['cid'];?>"><?php echo $cat_row['category_name'];?></option>                   
                                            <?php
                                                }
                                            ?>
                                        </select>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Image Source</div>
                                        <select class="form-control show-tick" name="upload_type" id="upload_type">
                                                <option value="upload">Upload</option>
                                                <option value="url">URL</option>
                                        </select>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Format</div>
                                        <select class="form-control show-tick" name="image_format" id="image_format">
                                                <option value="gif">GIF</option>
                                                <option value="mp4">MP4</option>
                                        </select>
                                    </div>

                                    <div id="mp4">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <div class="font-12 ex1">Live Wallpaper Thumbnail ( JPG, JPEG, PNG)</div>
                                                <input type="file" name="image_thumbnail" id="image_thumbnail" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="jpg jpeg png gif bmp webp" required />
                                                <div class="help-info pull-left">( Live wallpaper with mp4 format must upload thumbnail )</div>
                                            </div>
                                        </div>
                                        <div class="col-sm-12" style="margin-bottom: 0px;"></div>
                                    </div>                                                            

                                    <div id="upload">
                                        <div class="col-sm-6">
                                            <div class="font-12 ex1">Live Wallpaper ( GIF, MP4 )</div>
                                            <div class="form-group">
                                                <input type="file" name="wallpaper_image[]" id="fileupload" class="dropify-image" data-max-file-size="99M" data-allowed-file-extensions="gif mp4" required/>
                                                <div class="help-info pull-left">( Upload your live wallpaper file here )</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div id="url">
                                        <div class="form-group col-sm-12">
                                            <div class="font-12">Live Wallpaper URL</div>
                                            <div class="form-line">
                                                <input type="text" class="form-control" name="image_url" id="image_url" placeholder="https://www.example.com/live-wallpaper.mp4" required/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Tags (Optional)</div>
                                        <div class="form-line">
                                            <input type="text" class="form-control" name="tags" id="tags" data-role="tagsinput" placeholder="add tags" required/>
                                        </div>
                                    </div>

                                    <div class="form-group col-sm-12">
                                        <div class="font-12">Rewarded Ad</div>
                                        <select class="form-control show-tick" name="rewarded" id="rewarded">
                                           <option value="1">Yes</option>
                                           <option value="0" selected>No</option>
                                        </select>
                                    </div>                                                                                              

                                    <div class="col-sm-12">
                                    <button type="submit" name="submit" class="button button-rounded waves-effect waves-float pull-right">SUBMIT</button>
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