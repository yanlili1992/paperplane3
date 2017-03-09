package com.example.paperplane.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.paperplane.bean.DoubanMomentNews;
import com.example.paperplane.bean.GuokrHandpickNews;
import com.example.paperplane.bean.ZhihuDailyNews;
import com.example.paperplane.interfaze.OnRecyclerViewOnClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuht on 2017/3/8.
 */

public class BookmarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final LayoutInflater inflater;
    private List<DoubanMomentNews.posts> doubanList;
    private List<GuokrHandpickNews.result> guokrList;
    private List<ZhihuDailyNews.Question> zhihuList;

    private List<Integer> types;//to store which type the item is

    private OnRecyclerViewOnClickListener listener;

    public static final int TYPE_ZHIHU_NORMAL = 0;
    public static final int TYPE_ZHIHU_WITH_HEADER =1;
    public static final int TYPE_GUOKR_NORMAL = 2;
    public static final int TYPE_GUOKR_WITH_HEADER = 3;
    public static final int TYPE_DOUBAN_NORMAL = 4;
    public static final int TYPE_DOUBAN_WITH_HEADER = 5;

    public BookmarksAdapter(Context context,
                            ArrayList<ZhihuDailyNews.Question> zhihuList,
                            ArrayList<GuokrHandpickNews.result> guokrList,
                            ArrayList<DoubanMomentNews.posts> doubanList,
                            ArrayList<Integer> types
    ){
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.zhihuList = zhihuList;
        this.guokrList = guokrList;
        this.doubanList = doubanList;
        //type size = zhihuList.size + guokrList.size + doubanList.size
        this.types = types;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        switch (viewType){
            case TYPE_DOUBAN_NORMAL:
            case TYPE_GUOKR_NORMAL:
            case TYPE_ZHIHU_NORMAL:
                return new NormalVIewHolder(inflater.inflate(R.layout.home_list_item_layout,parent,false),this.listener);
        }
        return new WithTypeViewHolder(inflater.inflate(R.layout.bookmark_header,parent,false));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        switch (types.get(position)){
            case TYPE_ZHIHU_WITH_HEADER:
                ((WithTypeViewHolder)holder).textViewType.setText("知乎日报");
                break;
            case TYPE_ZHIHU_NORMAL:
                if(!zhihuList.isEmpty()){
                    ZhihuDailyNews.Question q = zhihuList.get(position-1);
                    Glide.with(context)
                            .load(q.getTitle().get(0))
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(((NormalViewHolder)holder).imageView);

                    ((NormalViewHolder)holder).textViewTitle.setText(q.getTitle());
                }
                break;
            case TYPE_ZHIHU_NORMAL:
                if (!zhihuList.isEmpty()) {
                    ZhihuDailyNews.Question q = zhihuList.get(position - 1);

                    Glide.with(context)
                            .load(q.getImages().get(0))
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(((NormalViewHolder)holder).imageView);

                    ((NormalViewHolder)holder).textViewTitle.setText(q.getTitle());
                }

                break;

            case TYPE_GUOKR_WITH_HEADER:

                ((WithTypeViewHolder)holder).textViewType.setText(R.string.guokr_handpick);

                break;

            case TYPE_GUOKR_NORMAL:

                if (!guokrList.isEmpty()) {
                    GuokrHandpickNews.result result = guokrList.get(position - zhihuList.size() - 2);

                    Glide.with(context)
                            .load(result.getHeadline_img_tb())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(((NormalViewHolder) holder).imageView);

                    ((NormalViewHolder) holder).textViewTitle.setText(result.getTitle());
                }

                break;

            case TYPE_DOUBAN_WITH_HEADER:

                ((WithTypeViewHolder)holder).textViewType.setText(context.getResources().getString(R.string.douban_moment));

                break;
            case TYPE_DOUBAN_NORMAL:
                if (!doubanList.isEmpty()) {
                    DoubanMomentNews.posts post = doubanList.get(position - zhihuList.size() - guokrList.size() - 3);

                    if (post.getThumbs().size() == 0) {
                        ((NormalViewHolder)holder).imageView.setVisibility(View.INVISIBLE);
                    } else {
                        Glide.with(context)
                                .load(post.getThumbs().get(0).getMedium().getUrl())
                                .asBitmap()
                                .placeholder(R.drawable.placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .error(R.drawable.placeholder)
                                .centerCrop()
                                .into(((NormalViewHolder)holder).imageView);
                    }

                    ((NormalViewHolder)holder).textViewTitle.setText(post.getTitle());
                }

                break;

            default:
                break;
        }
    }
    @Override
    public void getItemViewType(int position) {return types.get(position);}
    @Override
    public int getItemCount(){
        return types.size();
    }
    public void setItemListener(OnRecyclerViewOnClickListener listener){
        this.listener = listener;
    }
    public class NormalViewHolder extends RecyclerVIew.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textViewTitle;
        OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewOnClickListener listener){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewCover);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(listener!=null){
                listener.OnItemClick(v.getLayoutPosition());
            }
        }
        @Override
        public WithTypeViewHolder(View itemView){
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewType);
        }
    }
}
