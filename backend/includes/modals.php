<div class="modal fade" id="modal-package-name" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">What is my package name?</h4>
            </div>
            <div class="modal-body">
                <li>Open Android Studio Project</li>
                <li>Select <b></> Gradle Scripts > build.gradle (Module: app)</b><img src="assets/images/package_name.jpg" class="img-responsive"></li>                            
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-server-key" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Obtaining your Firebase Server API Key</h4>
            </div>
            <div class="modal-body">
                <p>Firebase provides Server API Key to identify your firebase app. To obtain your Server API Key, go to firebase console, select the project and go to settings, select Cloud Messaging tab and copy your Server key.</p>
                <img src="assets/images/fcm-server-key.jpg" class="img-responsive">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-api-key" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Where I have to put my API Key?</h4>
            </div>
            <div class="modal-body">
                <ol>
                    <li>for security needed, Update <b>API_KEY</b> String value.</li>
                    <li>Open Android Studio Project.</li>
                    <li>Click <b>CHANGE API KEY</b> to generate new API Key.</li>
                    <li>go to app > java > yourpackage name > <b>Config.java</b>, and update with your own API Key. <img src="assets/images/api_key.jpg" class="img-responsive"></li>
                </ol>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-onesignal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Where do I get my OneSignal APP ID and Rest API Key?</h4>
            </div>
            <div class="modal-body">
                <ol>
                    <li>Login to your <a href="https://onesignal.com/" target="_blank"><b>OneSignal</b></a> Account and select your app</li>
                    <li>Select <b></> SETTINGS > Keys & IDs.</b> <img src="assets/images/onesignal_key.jpg" class="img-responsive"></li>
                    <li>Your <b>fcm_notification_topic</b> and <b>onesignal_app_id</b> values must be the same as written in <b>res/values/notifications.xml</b></li>
                    <li><img src="assets/images/notifications.jpg" class="img-responsive"></li>
                </ol>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>  

<div class="modal fade" id="modal-admob-app-id" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Update your AdMob App ID</h4>
            </div>
            <div class="modal-body">
                <p>Put your <b>AdMob App ID</b> to <b>res/value/ads.xml</b> in the <b>admob_app_id</b> string tag.</p>
                <img src="assets/images/admob_app_id.jpg" class="img-responsive">
                <p><b>Important:</b> This step is required! Failure to add your app id in the string tag results in a crash with the message: The Google Mobile Ads SDK was initialized incorrectly.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-applovin-sdk-key" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Update your AppLovin SDK Key</h4>
            </div>
            <div class="modal-body">
                <p>Put your <b>AppLovin SDK Key</b> to <b>res/value/ads.xml</b> in the <b>applovin_sdk_key</b> string tag.</p>
                <img src="assets/images/admob_app_id.jpg" class="img-responsive">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal-notifications" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="largeModalLabel">Push Notifications</h4>
            </div>
            <div class="modal-body">
                <p>Your <b>fcm_notification_topic</b> and <b>onesignal_app_id</b> values must be the same as written in <b>res/values/notifications.xml</b></p>
                <img src="assets/images/notifications.jpg" class="img-responsive">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link waves-effect" data-dismiss="modal">OK, I AM UNDERSTAND</button>
            </div>
        </div>
    </div>
</div>