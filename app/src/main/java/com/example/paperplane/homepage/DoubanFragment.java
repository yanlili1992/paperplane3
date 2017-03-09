package com.example.paperplane.homepage;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.example.paperplane.adapter.DoubanMomentAdapter;
import com.example.paperplane.bean.DoubanMomentNews;
import com.example.paperplane.interfaze.OnRecyclerViewOnClickListener;

import java.util.ArrayList;

import static android.R.attr.dial;
import static android.R.attr.minDate;

/**
 * Created by liuht on 2017/3/8.
 */

public class DoubanFragment extends Fragment implements DoubanContract.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;

    private DoubanMomentAdapter adapter;
    private DoubanContract.Presenter presenter;

    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    //require an empty constructor
    public DoubanFragment(){}
    public static DoubanFragment newInstance(){return new DoubanFragment();}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        initViews(view);
        presenter.start();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                presenter.loadPosts(Calendar.getInstance().getTimeInMillis(),true);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            boolean isSlidingToLast = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //当不滚动时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    //获取最后一个完全显示的itemposition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判断是否滚动到底部并且是向下滑动
                    if(lastVisibleItem==(totalItemCount-1)&&isSlidingToLast){
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, --mDay);
                        presenter.loadMore(c.getTimeInMillis());
                    }
                }
                super.onScrollStateChanged(recyclerView,newState)
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView,dx,dy);
                isSlidingToLast=dy>0;
                //隐藏或者显示fab
                if(dy>0)
                    fab.hide();
            }else{
                fab.show();
            }

        });
        return view;
    }
    @Override
    public void setPresenter(DoubanContract.Presenter presenter){
        if(presenter!=null){
            this.presenter = presenter;
        }
    }
    @Override
    public void initViews(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setRippleColor(getResources().getColor(R.color.colorPrimaryDark));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);

    }
    @Override
    public void startLoading(){refreshLayout.setRefreshing(false);}
    @Override
    public void showLoadignError(){
        Snackbar.make(fab,"加载失败",Snackbar.LENGTH_INDEFINITE).setAction("重试",new View.OnClickListener(){
            @Override
            public void onClick(View v){
                presenter.refresh();
            }
        }).show();
    }
    @Override
    public void showResults(ArrayList<DoubanMomentNews.posts> list){
        if(adapter==null){
            adapter = new DoubanMomentAdapter(getContext(),list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener(){
                @Override
                public void OnItemCLick(View v, int position){
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void showPickDialog(){
        Calendar now = Calendar.getInstance();
        now.set(mYear,mMonth,mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog(.OnDateSetListener(){
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year,monthOfYear,dayOfMonth);
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                presenter.loadPosts(temp.getTimeInMillis(),true);
            }
        },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH)));

        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2014,5,12);
        dialog.setMinDate(minDate);
        //set the dialog not vibrate when date change, default value is true
        dialog.vibrate(false);

        dialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
    }
}
