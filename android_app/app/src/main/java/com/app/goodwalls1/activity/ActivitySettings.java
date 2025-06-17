package com.app.goodwalls1.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.goodwalls1.BuildConfig;
import com.app.goodwalls1.R;
import com.app.goodwalls1.adapter.AdapterSearch;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.util.Constant;
import com.app.goodwalls1.util.Tools;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ActivitySettings extends AppCompatActivity {

    TextView txtCacheSize;
    TextView txtPath;
    MaterialSwitch switchTheme;
    private String singleChoiceSelected;
    SharedPref sharedPref;
    LinearLayout parentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.getTheme(this);
        setContentView(R.layout.activity_settings);
        sharedPref = new SharedPref(this);
        parentView = findViewById(R.id.parent_view);

        Tools.setNavigation(this);
        Tools.setupToolbar(this, findViewById(R.id.appbar_layout), findViewById(R.id.toolbar), getString(R.string.menu_settings), true);

        switchTheme = findViewById(R.id.switch_theme);
        switchTheme.setChecked(sharedPref.getIsDarkTheme());
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPref.setIsDarkTheme(isChecked);
            Tools.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }, 250);
        });

        findViewById(R.id.btn_switch_theme).setOnClickListener(v -> {
            if (switchTheme.isChecked()) {
                sharedPref.setIsDarkTheme(false);
                switchTheme.setChecked(false);
            } else {
                sharedPref.setIsDarkTheme(true);
                switchTheme.setChecked(true);
            }
            Tools.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }, 250);
        });

        getDisplayWallpaper();
        getDisplayCategory();

        findViewById(R.id.btn_notification).setOnClickListener(v -> {
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, BuildConfig.APPLICATION_ID);
            } else {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("app_package", BuildConfig.APPLICATION_ID);
                intent.putExtra("app_uid", getApplicationInfo().uid);
            }
            startActivity(intent);
        });

        txtCacheSize = findViewById(R.id.txt_cache_size);
        initializeCache();

        txtPath = findViewById(R.id.txt_path);
        String path;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + getString(R.string.app_name);
        } else {
            path = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name);
        }
        txtPath.setText(path);

        findViewById(R.id.btn_clear_cache).setOnClickListener(v -> clearCache());

        findViewById(R.id.btn_clear_search).setOnClickListener(v -> clearSearchHistory());

        findViewById(R.id.btn_about).setOnClickListener(view -> aboutDialog());

        findViewById(R.id.btn_privacy_policy).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ActivityWebView.class);
            intent.putExtra("title", getString(R.string.title_setting_privacy));
            intent.putExtra("url", sharedPref.getPrivacyPolicy());
            startActivity(intent);
        });

        findViewById(R.id.btn_rate).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
        });

        findViewById(R.id.btn_share).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + "\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            intent.setType("text/plain");
            startActivity(intent);
        });

        findViewById(R.id.btn_more_apps).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPref.getMoreAppsUrl())));
        });

    }

    private void getDisplayWallpaper() {
        final TextView txtWallpaperViewType = findViewById(R.id.txt_wallpaper_view_type);
        if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
            txtWallpaperViewType.setText(R.string.option_menu_wallpaper_grid_3);
        } else {
            txtWallpaperViewType.setText(R.string.option_menu_wallpaper_grid_2);
        }

        findViewById(R.id.btn_wallpaper_view_type).setOnClickListener(v -> {
            String[] items = getResources().getStringArray(R.array.dialog_wallpaper_view_type);
            int itemSelected;
            if (sharedPref.getWallpaperSpanCount() == Constant.WALLPAPER_3_COLUMNS) {
                itemSelected = sharedPref.getDisplayWallpaperPosition(1);
                singleChoiceSelected = items[sharedPref.getDisplayWallpaperPosition(1)];
            } else {
                itemSelected = sharedPref.getDisplayWallpaperPosition(0);
                singleChoiceSelected = items[sharedPref.getDisplayWallpaperPosition(0)];
            }

            new MaterialAlertDialogBuilder(ActivitySettings.this)
                    .setTitle(getString(R.string.title_setting_display_wallpaper))
                    .setSingleChoiceItems(items, itemSelected, (dialogInterface, i) -> singleChoiceSelected = items[i])
                    .setPositiveButton(R.string.dialog_option_ok, (dialogInterface, i) -> {
                        if (singleChoiceSelected.equals(getResources().getString(R.string.option_menu_wallpaper_grid_2))) {
                            if (sharedPref.getWallpaperSpanCount() != Constant.WALLPAPER_2_COLUMNS) {
                                sharedPref.setWallpaperSpanCount(Constant.WALLPAPER_2_COLUMNS);
                                txtWallpaperViewType.setText(R.string.option_menu_wallpaper_grid_2);
                            }
                            sharedPref.updateDisplayWallpaperPosition(0);
                        } else if (singleChoiceSelected.equals(getResources().getString(R.string.option_menu_wallpaper_grid_3))) {
                            if (sharedPref.getWallpaperSpanCount() != Constant.WALLPAPER_3_COLUMNS) {
                                sharedPref.setWallpaperSpanCount(Constant.WALLPAPER_3_COLUMNS);
                                txtWallpaperViewType.setText(R.string.option_menu_wallpaper_grid_3);
                            }
                            sharedPref.updateDisplayWallpaperPosition(1);
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("wallpaper_position", "wallpaper_position");
                        startActivity(intent);

                        dialogInterface.dismiss();
                    })
                    .setNegativeButton(R.string.dialog_option_cancel, null)
                    .show();
        });
    }

    private void getDisplayCategory() {
        final TextView txtCategoryViewType = findViewById(R.id.txt_category_view_type);
        if (sharedPref.getCategorySpanCount() == Constant.CATEGORY_LIST) {
            txtCategoryViewType.setText(R.string.option_menu_category_list);
        } else if (sharedPref.getCategorySpanCount() == Constant.CATEGORY_GRID_2) {
            txtCategoryViewType.setText(R.string.option_menu_category_grid_2);
        }
        findViewById(R.id.btn_category_view_type).setOnClickListener(v -> {
            String[] items = getResources().getStringArray(R.array.dialog_category_view_type);

            int itemSelected;
            if (sharedPref.getCategorySpanCount() == Constant.CATEGORY_GRID_2) {
                itemSelected = sharedPref.getDisplayCategoryPosition(1);
                singleChoiceSelected = items[sharedPref.getDisplayCategoryPosition(1)];
            } else {
                itemSelected = sharedPref.getDisplayCategoryPosition(0);
                singleChoiceSelected = items[sharedPref.getDisplayCategoryPosition(0)];
            }

            new MaterialAlertDialogBuilder(ActivitySettings.this)
                    .setTitle(getString(R.string.title_setting_display_category))
                    .setSingleChoiceItems(items, itemSelected, (dialogInterface, i) -> singleChoiceSelected = items[i])
                    .setPositiveButton(R.string.dialog_option_ok, (dialogInterface, i) -> {
                        if (singleChoiceSelected.equals(getResources().getString(R.string.option_menu_category_list))) {
                            if (sharedPref.getCategorySpanCount() != Constant.CATEGORY_LIST) {
                                sharedPref.updateCategorySpanCount(Constant.CATEGORY_LIST);
                                txtCategoryViewType.setText(R.string.option_menu_category_list);
                            }
                            sharedPref.updateDisplayCategoryPosition(0);
                        } else if (singleChoiceSelected.equals(getResources().getString(R.string.option_menu_category_grid_2))) {
                            if (sharedPref.getCategorySpanCount() != Constant.CATEGORY_GRID_2) {
                                sharedPref.updateCategorySpanCount(Constant.CATEGORY_GRID_2);
                                txtCategoryViewType.setText(R.string.option_menu_category_grid_2);
                            }
                            sharedPref.updateDisplayCategoryPosition(1);
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("category_position", "category_position");
                        startActivity(intent);

                        dialogInterface.dismiss();
                    })
                    .setNegativeButton(R.string.dialog_option_cancel, null)
                    .show();
        });
    }

    private void clearCache() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(ActivitySettings.this);
        dialog.setMessage(R.string.msg_clear_cache);
        dialog.setPositiveButton(R.string.dialog_option_yes, (dialogInterface, i) -> {
            FileUtils.deleteQuietly(getCacheDir());
            FileUtils.deleteQuietly(getExternalCacheDir());
            txtCacheSize.setText(getString(R.string.sub_setting_clear_cache_start) + " 0 Bytes " + getString(R.string.sub_setting_clear_cache_end));
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.msg_cache_cleared), Snackbar.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton(R.string.dialog_option_cancel, null);
        dialog.show();
    }

    private void clearSearchHistory() {
        AdapterSearch adapterSearch = new AdapterSearch(this);
        if (adapterSearch.getItemCount() > 0) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ActivitySettings.this);
            builder.setTitle(getString(R.string.title_dialog_clear_search_history));
            builder.setMessage(getString(R.string.msg_dialog_clear_search_history));
            builder.setPositiveButton(R.string.dialog_option_yes, (di, i) -> {
                adapterSearch.clearSearchHistory();
                Snackbar.make(parentView, getString(R.string.clearing_success), Snackbar.LENGTH_SHORT).show();
            });
            builder.setNegativeButton(R.string.dialog_option_cancel, null);
            builder.show();
        } else {
            Snackbar.make(parentView, getString(R.string.clearing_empty), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void initializeCache() {
        txtCacheSize.setText(getString(R.string.sub_setting_clear_cache_start) + " " + readableFileSize((0 + getDirSize(getCacheDir())) + getDirSize(getExternalCacheDir())) + " " + getString(R.string.sub_setting_clear_cache_end));
    }

    public long getDirSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0 Bytes";
        }
        String[] units = new String[]{"Bytes", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10((double) size) / Math.log10(1024.0d));
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        double d = (double) size;
        double pow = Math.pow(1024.0d, (double) digitGroups);
        Double.isNaN(d);
        stringBuilder.append(decimalFormat.format(d / pow));
        stringBuilder.append(" ");
        stringBuilder.append(units[digitGroups]);
        return stringBuilder.toString();
    }

    public void aboutDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(ActivitySettings.this);
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_about, null);

        TextView txt_app_version = view.findViewById(R.id.txt_app_version);
        txt_app_version.setText(getString(R.string.msg_about_version) + " " + BuildConfig.VERSION_NAME);

        // 🔽 Set dynamic copyright year
        TextView txt_app_copyright = view.findViewById(R.id.txt_app_copyright);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        txt_app_copyright.setText(getString(R.string.app_copyright, year));

        final MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(ActivitySettings.this);
        alert.setView(view);
        alert.setPositiveButton(R.string.dialog_option_ok, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
