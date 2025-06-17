package com.app.goodwalls1.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.app.goodwalls1.R;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.Tools;
import com.google.android.material.snackbar.Snackbar;

public class ActivityRedirect extends AppCompatActivity {

    SharedPref sharedPref;
    ImageButton btnClose;
    Button btnRedirect;
    String redirectUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_redirect);
        Tools.setNavigation(this);
        sharedPref = new SharedPref(this);

        if (getIntent() != null) {
            redirectUrl = getIntent().getStringExtra("redirect_url");
        }

        initView();
    }

    private void initView() {
        btnClose = findViewById(R.id.btn_close);
        btnRedirect = findViewById(R.id.btn_redirect);

        btnClose.setOnClickListener(view -> {
            finish();
            Constant.isAppOpen = false;
        });

        btnRedirect.setOnClickListener(view -> {
            if (redirectUrl.equals("")) {
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.redirect_error), Snackbar.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(redirectUrl)));
                finish();
                Constant.isAppOpen = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.isAppOpen = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.isAppOpen = false;
    }

}
