package com.app.goodwalls1.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.goodwalls1.Config;
import com.app.goodwalls1.R;
import com.app.goodwalls1.component.RtlViewPager;
import com.app.goodwalls1.database.prefs.SharedPref;
import com.app.goodwalls1.model.Menu;
import com.app.goodwalls1.util.Tools;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentTabLayout extends Fragment {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public RtlViewPager viewPagerRTL;
    AppBarLayout tabAppbarLayout;
    SharedPref sharedPref;
    View view;
    Activity activity;
    String order;
    String filter;
    String category;
    List<Menu> menus = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        sharedPref = new SharedPref(activity);
        menus = sharedPref.getMenuList();

        tabAppbarLayout = view.findViewById(R.id.tab_appbar_layout);
        tabLayout = view.findViewById(R.id.tab_layout);

        if (menus != null && menus.size() > 1) {
            tabAppbarLayout.setVisibility(View.VISIBLE);
        } else {
            tabAppbarLayout.setVisibility(View.GONE);
        }

        if (sharedPref.getIsDarkTheme()) {
            tabLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_dark_toolbar));
        } else {
            tabLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.color_light_background));
        }
        setupViewPager(menus);
        return view;

    }

    private void setupViewPager(List<Menu> menus) {
        viewPager = view.findViewById(R.id.tab_view_pager);
        viewPagerRTL = view.findViewById(R.id.tab_view_pager_rtl);

        if (Config.ENABLE_RTL_MODE) {
            viewPager.setVisibility(View.GONE);
            viewPagerRTL.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.VISIBLE);
            viewPagerRTL.setVisibility(View.GONE);
        }

        MenuViewPagerAdapter adapter = new MenuViewPagerAdapter(getChildFragmentManager());
        if (menus != null && menus.size() > 0) {
            for (int i = 0; i < menus.size(); i++) {
                FragmentWallpaperLegacy fragment = new FragmentWallpaperLegacy();
                Bundle args = new Bundle();
                wallpaperOrderAndFilter(menus, i);
                args.putString(FragmentWallpaperLegacy.ARG_ORDER, order);
                args.putString(FragmentWallpaperLegacy.ARG_FILTER, filter);
                args.putString(FragmentWallpaperLegacy.ARG_CATEGORY, category);
                fragment.setArguments(args);
                adapter.addFrag(fragment, menus.get(i).menu_title);
            }
        } else {
            FragmentWallpaperLegacy fragment = new FragmentWallpaperLegacy();
            Bundle args = new Bundle();
            args.putString(FragmentWallpaperLegacy.ARG_ORDER, "recent");
            args.putString(FragmentWallpaperLegacy.ARG_FILTER, "both");
            args.putString(FragmentWallpaperLegacy.ARG_CATEGORY, "0");
            fragment.setArguments(args);
            adapter.addFrag(fragment, "");
        }

        if (Config.ENABLE_RTL_MODE) {
            viewPagerRTL.setAdapter(adapter);
            if (menus != null) {
                viewPagerRTL.setOffscreenPageLimit(menus.size());
            }
            tabLayout.post(() -> Tools.postDelayed(() -> tabLayout.setupWithViewPager(viewPagerRTL), 10));
        } else {
            viewPager.setAdapter(adapter);
            if (menus != null) {
                viewPager.setOffscreenPageLimit(menus.size());
            }
            tabLayout.post(() -> Tools.postDelayed(() -> tabLayout.setupWithViewPager(viewPager), 10));
        }
    }

    private void wallpaperOrderAndFilter(List<Menu> menus, int position) {
        order = menus.get(position).menu_order;
        filter = menus.get(position).menu_filter;
        category = menus.get(position).menu_category;
    }

    @SuppressWarnings("deprecation")
    public static class MenuViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> strings = new ArrayList<>();

        public MenuViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFrag(Fragment fragment, String title) {
            fragments.add(fragment);
            strings.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }

}

