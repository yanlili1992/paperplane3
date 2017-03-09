package com.example.paperplane.homepage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paperplane.adapter.GuokrNewsAdapter;
import com.example.paperplane.bean.GuokrHandpickNews;
import com.example.paperplane.interfaze.OnRecyclerViewOnClickListener;

import java.util.ArrayList;

/**
 * Created by liuht on 2017/3/8.
 */

public class GuokrFragment extends Fragment implements GuokrContract.View{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private GuokrNewsAdapter adapter;
    private GuokrContract.Presenter presenter;

    //require an empty constructor
    public GuokrFragment{}
    public static GuokrFragment newInstance(){return new GuokrFragment();}
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
                presenter.refresh();
            }
        });
        return true;
    }
    @Override
    public void setPresenter(GuokrContract.Presenter presenter){
        if(presenter!=null){
            this.presenter = presenter;
        }
    }
    @Override
    public void initViews(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }
    @Override
    public void showError(){
        Snackbar.make(refreshLayout,R.string.loaded_failed,Snackbar.LENGTH_INDEFINITE)
                .setAction("重试",new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        presenter.refresh();
                    }
                }).show();
    }
    @Override
    public void showResults(ArrayList<GuokrHandpickNews.result> list){
        if(adapter == null){
            adapter = new GuokrNewsAdapter(getContext(),list);
            adapter.setItemClickListener(new OnRecyclerViewOnClickListener(){
                @Override
                public void OnItemClick(View v, int position){
                    presenter.startReading(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataChanged();
        }
    }
    @Override
    public void showLoading(){refreshLayout.setRefreshing(true);}
    @Override
    public void stopLoading(){
        refreshLayout.setRefreshing(false);
    }

}
