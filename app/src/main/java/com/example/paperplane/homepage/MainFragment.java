package com.example.paperplane.homepage;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TableLayout;

import com.example.paperplane.R;

import java.util.Random;

/**
 * Created by liuht on 2017/3/7.
 */

public class MainFragment extends Fragment {

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;

    private ZhiuhDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanFragment doubanFragment;

    private ZhihuDailyPrsenter zhihuDailyPrsenter;
    private GuokrPresenter guokrPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;

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
                doubanFragment
        );
        viewPager.setAdapter(adapter);]
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

    @Override
    public void onSavedInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState,"zhihu",zhihuDailyFragment);
        manager.putFragment(outState,"guokr",guokrFragment);
        manager.putFragment(outState,"douabn",doubanFragment);
    }

    @Override
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
                doubanMomentPresenter.feelLucky();
                break;

        }
    }

    public MainPagerAdaptr getAdapter(){
        return adapter;
    }

}
