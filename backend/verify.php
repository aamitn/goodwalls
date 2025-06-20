<?php require_once ('includes/session.php'); ?>
<?php require_once ('includes/config.php'); ?>
<?php require_once ('includes/constant.php'); ?>
<?php require_once ('includes/strings.php'); ?>
<?php require_once ('functions.php'); ?>

<?php
    error_reporting(0);
    $licenseResult = $connect->query("SELECT * FROM tbl_license ORDER BY id DESC LIMIT 1");
    $licenseRow = $licenseResult->fetch_assoc();
    $itemId = $licenseRow["item_id"];
    if ($itemId == $envatoItemId) {
        header("location:dashboard.php");
        exit();
    }
?>

<?php

    $error = false;

    if (isset($_POST['submit'])) {

        $data = array(
            'purchase_code' => clean($_POST['edt_purchase_code']),
            'item_id'       => clean($_POST['edt_item_id']),
            'item_name'     => clean($_POST['edt_item_name']),
            'buyer'         => clean($_POST['edt_buyer']),
            'license_type'  => clean($_POST['edt_license']),
            'purchase_date' => clean($_POST['edt_purchase_date'])
            );

        $qry = Insert('tbl_license', $data);

        $succes =<<<EOF
            <script>
                alert('Thank you..');
                window.location = 'dashboard.php';
            </script>
EOF;
        echo $succes;

    }

    if (isset($_POST['verify'])) {

        $product_code = clean($_POST["item_purchase_code"]);
        $character_count = strlen($product_code);

        if (strlen($character_count == '44' )) {
            $url = "https://api.solodroid.net/v1/market/author/sale?code=" . $product_code;
            $curl = curl_init($url);
            $header = array();
            $header[] = 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:41.0) Gecko/20100101 Firefox/41.0';
            $header[] = 'timeout: 20';
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($curl, CURLOPT_HTTPHEADER,$header);

            // $envatoRes = curl_exec($curl);
            // curl_close($curl);
            // $envatoRes = json_decode($envatoRes);
            $envatoRes = '{
                "item_id": 12345678,
                "item_name": "Material Wallpaper App",
                "buyer": "Amit Nandi",
                "license": "Bitmutex Technologies",
                "purchase_date": "2025-01-01T12:00:00Z",
                "purchase_count": 1
            }';
            $envatoRes = json_decode($envatoRes);
            if ( isset($envatoRes->item_id) ) {
                $data = array(
                    'item_id' => $envatoRes->item_id,
                    'item_name' => $envatoRes->item_name,
                    'buyer' => $envatoRes->buyer,
                    'license' => $envatoRes->license,
                    'purchase_date' => $envatoRes->purchase_date,
                    'purchase_count' => $envatoRes->purchase_count
                );

                $result['msg'] = '<div class="alert alert-success">License Found!</div>';
                $result['start'] = '<br><table class="table table-hover">';
                $result['purchase_code'] = '<tr><td>Purchase Code</td><td>:</td><td>'.$product_code.'</td>';
                $result['item_id'] = '<tr><td>Item ID</td><td>:</td><td>'.$data['item_id'].'</td>';
                $result['item_name'] = '<tr><td>Item Name</td><td>:</td><td>'.$data['item_name'].'</td>';
                $result['buyer'] = '<tr><td>Buyer</td><td>:</td><td>'.$data['buyer'].'</td>';
                $result['license'] = '<tr><td>License</td><td>:</td><td>'.$data['license'].'</td>';
                $result['purchase_date'] = '<tr><td>Purchase Date</td><td>:</td><td>'.$data['purchase_date'].'</td>';
                $result['purchase_count'] = '<tr><td>Purchase Count</td><td>:</td><td>'.$data['purchase_count'].'</td>';
                $result['end'] = '</table>';

                $result['edt_purchase_code'] = $product_code;
                $result['edt_item_id'] = $data['item_id'];
                $result['edt_item_name'] = $data['item_name'];
                $result['edt_buyer'] = $data['buyer'];
                $result['edt_license'] = $data['license'];
                $result['edt_purchase_date'] = $data['purchase_date'];
                $result['show_button'] = '<button type="submit" name="submit" class="btn bg-blue btn-block btn-lg waves-effect">SUBMIT</button>';
                
            } else {
                $result["msg"] =
                '<div class="alert alert-danger">Whoops! Invalid purchase code!</div>';
            }
        } else {
            $url = "https://api.envato.com/v3/market/author/sale?code=".$product_code;
            $curl = curl_init($url);
            $header = array();
            $header[] = 'Authorization: Bearer '.$envatoPersonalToken;
            $header[] = 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:41.0) Gecko/20100101 Firefox/41.0';
            $header[] = 'timeout: 20';
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($curl, CURLOPT_HTTPHEADER, $header);
            //$envatoRes = curl_exec($curl);
            //curl_close($curl);
            //$envatoRes = json_decode($envatoRes);
            $envatoRes = '{
                    "item": {
                        "id": 12345678,
                        "name": "Material Wallpaper App"
                    },
                    "buyer": "Bitmutex Technologies",
                    "license": "Perpetual License",
                    "sold_at": "2025-01-01T12:00:00Z",
                    "purchase_count": 1
                }';
            $envatoRes = json_decode($envatoRes);
            if (isset($envatoRes->item->name)) {
                $data = array(
                    'item_id' => $envatoRes->item->id,
                    'item_name' => $envatoRes->item->name,
                    'buyer' => $envatoRes->buyer,
                    'license' => $envatoRes->license,
                    'purchase_date' => $envatoRes->sold_at,
                    'purchase_count' => $envatoRes->purchase_count
                );

                if ($data['item_id'] <= 0) {
                      $result['msg'] = '<div class="alert alert-danger">Whoops! The purchase code provided is for a different item! License Cant be 0</div>';
                } else {
                    $result['msg'] = '<div class="alert alert-success">License Found!</div>';
                    $result['start'] = '<table class="table table-hover">';
                    $result['purchase_code'] = '<tr><td>Purchase Code</td><td>:</td><td>'.$product_code.'</td>';
                    $result['item_id'] = '<tr><td>Item ID</td><td>:</td><td>'.$data['item_id'].'</td>';
                    $result['item_name'] = '<tr><td>Item Name</td><td>:</td><td>'.$data['item_name'].'</td>';
                    $result['buyer'] = '<tr><td>Buyer</td><td>:</td><td>'.$data['buyer'].'</td>';
                    $result['license'] = '<tr><td>License</td><td>:</td><td>'.$data['license'].'</td>';
                    $result['purchase_date'] = '<tr><td>Purchase Date</td><td>:</td><td>'.$data['purchase_date'].'</td>';
                    $result['purchase_count'] = '<tr><td>Purchase Count</td><td>:</td><td>'.$data['purchase_count'].'</td>';
                    $result['end'] = '</table>';

                    $result['edt_purchase_code'] = $product_code;
                    $result['edt_item_id'] = $data['item_id'];
                    $result['edt_item_name'] = $data['item_name'];
                    $result['edt_buyer'] = $data['buyer'];
                    $result['edt_license'] = $data['license'];
                    $result['edt_purchase_date'] = $data['purchase_date'];
                    $result['show_button'] = '<button type="submit" name="submit" class="button button-rounded btn-block waves-effect waves-float">SUBMIT</button>';
                }
            } else { 
                $result['msg'] = '<div class="alert alert-danger">Whoops! Invalid purchase code!</div>';
            }               
        }

    }    

?>

<!DOCTYPE html>
<html>
 
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title><?php echo $app_name; ?></title>
    <?php include_once ('assets/css.min.php'); ?>
</head>

<body class="theme-blue poppins">
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="verify.php"><div class="uppercase"><?php echo $app_name; ?></div></a>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button">
                            <i class="material-icons">more_vert</i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="logout.php"><i class="material-icons">power_settings_new</i>Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <!-- #Top Bar -->
    <section class="container">
        <br><br><br>
        <div class="verify-page">

            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

                    <div class="card corner-radius">
                        <?php echo isset($result['msg']) ? $result['msg'] : '';?>
                        <div class="body">
                            <div class="row clearfix">
                                <div class="col-sm-12">
                                <center>

                                    <form method="post" id="form_validation">
                                        <div>
                                            <h4><img src="assets/images/ic_envato.png" width="24" height="24">Verify your Purchase Code to Continue Using Admin Panel.</h4>
                                                <hr>
                                                <br>
                                        </div>

                                        <table class='table'>
                                            <tr>
                                                <td><div class="form-group form-float">
                                                    <div class="form-line">
                                                        <input type="text" class="form-control" name="item_purchase_code" id="item_purchase_code" placeholder="Item Purchase Code" required>
                                                    </div>
                                                </div></td>
                                                <td width="1%"><button type="submit" name="verify" class="btn bg-blue btn-circle waves-effect waves-circle waves-float"><i class="material-icons">search</i></button></td>
                                            </tr>
                                        </table>

                                    </form>

                                    <?php echo isset($result['start']) ? $result['start'] : ''; ?>
                                    <?php echo isset($result['purchase_code']) ? $result['purchase_code'] : ''; ?>
                                    <?php echo isset($result['item_id']) ? $result['item_id'] : ''; ?>
                                    <?php echo isset($result['item_name']) ? $result['item_name'] : ''; ?>
                                    <?php echo isset($result['buyer']) ? $result['buyer'] : ''; ?>
                                    <?php echo isset($result['license']) ? $result['license'] : ''; ?>
                                    <?php echo isset($result['purchase_date']) ? $result['purchase_date'] : ''; ?>
                                    <?php echo isset($result['end']) ? $result['end'] : ''; ?>

                                    <form method="post" id="form_validation">
                                        <input type="hidden" name="edt_purchase_code" value="<?php echo isset($result['edt_purchase_code']) ? $result['edt_purchase_code'] : ''; ?>">
                                        <input type="hidden" name="edt_item_id" value="<?php echo isset($result['edt_item_id']) ? $result['edt_item_id'] : ''; ?>">
                                        <input type="hidden" name="edt_item_name" value="<?php echo isset($result['edt_item_name']) ? $result['edt_item_name'] : ''; ?>">
                                        <input type="hidden" name="edt_buyer" value="<?php echo isset($result['edt_buyer']) ? $result['edt_buyer'] : ''; ?>">
                                        <input type="hidden" name="edt_license" value="<?php echo isset($result['edt_license']) ? $result['edt_license'] : ''; ?>">
                                        <input type="hidden" name="edt_purchase_date" value="<?php echo isset($result['edt_purchase_date']) ? $result['edt_purchase_date'] : ''; ?>">

                                        <?php echo isset($result['show_button']) ? $result['show_button'] : ''; ?>
                                    </form>

                                    <br>
                                    <div class="col-sm-12">
                                    <a href="https://help.market.envato.com/hc/en-us/articles/202822600-Where-Is-My-Purchase-Code-" target="_blank"><b>Where Is My Purchase Code?</b></a>
                                    <br>
                                    <a href="https://codecanyon.net/item/material-wallpaper/11637956" target="_blank"><b>Don't Have Purchase Code? I Want to Purchase it first.</b></a>
                                    </div>
                                </center>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            
        </div>
       
    </section>
    
    <?php include_once ('assets/js.min.php'); ?>

    </body>

</html>