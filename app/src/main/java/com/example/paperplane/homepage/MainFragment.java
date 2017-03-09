package com.example.paperplane.homepage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.paperplane.R;
import com.example.paperplane.adapter.MainPagerAdapter;

import java.util.Random;

/**
 * Created by liyanli on 2017/3/8.
 */

public class MainFragment extends Fragment {

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;

    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanFragment doubanFragment;

    private ZhihuDailyPresenter zhihuDailyPrsenter;
    private GuokrPresenter guokrPresenter;
    private DoubanPresenter doubanPresenter;

    public MainFragment(){};

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        if(savedInstanceState!=null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(
                    savedInstanceState,"zhihu");
            guokrFragment = (GuokrFragment) getFragmentManager().getFragment(
                    savedInstanceState,"guokr");
            doubanFragment = (DoubanFragment) manager.getFragment(savedInstanceState,"douban");
        }else{
            zhihuDailyFragment = ZhihuDailyFragment.newInstance();
            guokrFragment = GuokrFragment.newInstance();
            doubanFragment = DoubanFragment.newInstance();
        }

        zhihuDailyPrsenter = new ZhihuDailyPresenter(context,zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(context,guokrFragment);
        doubanPresenter = new DoubanPresenter(context,doubanFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);

        setHasOptionsMenu(true);

        //当tab layout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if(tab.getPosition()==1){
                    fab.hide();
                }else{
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        return view;
    }

    private void initViews(View view){
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        adapter = new MainPagerAdapter(
                getChildFragmentManager(),
                context,
                zhihuDailyFragment,
                guokrFragment,
                doubanFragment);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id==R.id.action_feel_lucky){
            feelLucky();
        }
        return true;
    }


    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState,"zhihu",zhihuDailyFragment);
        manager.putFragment(outState,"guokr",guokrFragment);
        manager.putFragment(outState,"douabn",doubanFragment);
    }


    public void feelLucky(){
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type){
            case 0:
                zhihuDailyPrsenter.feelLucky();
                break;
            case 1:
                guokrPresenter.feelLucky();
                break;
           default:
                doubanPresenter.feelLucky();
                break;

        }
    }

    public MainPagerAdapter getAdapter(){
        return adapter;
    }

}
