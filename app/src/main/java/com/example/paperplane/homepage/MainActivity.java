package com.example.paperplane.homepage;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.paperplane.R;
import com.example.paperplane.about.AboutPreferenceActivity;
import com.example.paperplane.bookmarks.BookmarksFragment;
import com.example.paperplane.bookmarks.BookmarksPresenter;
import com.example.paperplane.service.CacheService;
import com.example.paperplane.settings.SettingsPreferenceActivity;

import static android.R.id.toggle;

/**
 * Created by liyanli on 2017/3/8.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MainFragment mainFragment;
    private BookmarksFragment bookmarksFragment;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    public static final String ACTION_BOOKMARKS="com.example.paperplane.bookmarks";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initViews();
        // 恢复fragment的状态
        if(savedInstanceState!=null){
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState,"MainFragment");
            bookmarksFragment = (BookmarksFragment) getSupportFragmentManager().getFragment(savedInstanceState,"BookmarksFragment");
        }else{
            mainFragment = MainFragment.newInstance();
            bookmarksFragment = BookmarksFragment.newInstance();
        }

        if(!mainFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment,mainFragment,"MainFragment")
                    .commit();
        }

        if(!bookmarksFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.layout_fragment,bookmarksFragment,"BookmarksFragment")
                    .commit();
        }
        // 实例化BookmarksPresenter
        new BookmarksPresenter(MainActivity.this,bookmarksFragment);

        String action = getIntent().getAction();

        if(action.equals(ACTION_BOOKMARKS)){
            showBookmarksFragment();
            navigationView.setCheckedItem(R.id.nav_bookmarks);
        }else{
            showMainFragment();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        startService(new Intent(this,CacheService.class));
    }

    private void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showMainFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(mainFragment);
        fragmentTransaction.hide(bookmarksFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("纸飞机");
    }
    //显示BookmarksFragment并设置Title
    private void showBookmarksFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(bookmarksFragment);
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("收藏");

        if(bookmarksFragment.isAdded()){
            bookmarksFragment.notifyDataChanged();
        }
    }

    @Override
    protected void onDestroy(){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(CacheService.class.getName().equals(service.service.getClassName())){
                stopService(new Intent(this,CacheService.class));
            }
        }
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if(id==R.id.nav_home){
            showMainFragment();
        }else if(id==R.id.nav_bookmarks){
            showBookmarksFragment();
        }else if(id==R.id.nav_change_theme){
            //change the day/night mode after the drawer closed
            drawer.addDrawerListener(new DrawerLayout.DrawerListener(){
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset){

                }
                @Override
                public void onDrawerOpened(View drawerView){

                }

                @Override
                public void onDrawerClosed(View drawerView){
                    SharedPreferences sp = getSharedPreferences("user_settings",MODE_PRIVATE);
                    if((getResources().getConfiguration().uiMode& Configuration.UI_MODE_NIGHT_MASK)==Configuration.UI_MODE_NIGHT_YES){
                        sp.edit().putInt("theme",0).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }else{
                        sp.edit().putInt("theme",1).apply();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }

                @Override
                public void onDrawerStateChanged(int newState){

                }
            });
        }else if(id==R.id.nav_settings){
            startActivity(new Intent(this,SettingsPreferenceActivity.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(this,AboutPreferenceActivity.class));
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    //存储Fragment的状态
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(mainFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"MainFragment",mainFragment);
        }

        if(bookmarksFragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"BookmarksFragment",bookmarksFragment);
        }
    }
}
