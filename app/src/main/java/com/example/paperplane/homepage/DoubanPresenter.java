package com.example.paperplane.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.example.paperplane.bean.BeanType;
import com.example.paperplane.bean.DoubanMomentNews;
import com.example.paperplane.bean.StringModelImpl;
import com.example.paperplane.db.DatabaseHelper;
import com.example.paperplane.detail.DetailActivity;
import com.example.paperplane.interfaze.OnStringListener;
import com.example.paperplane.service.CacheService;
import com.example.paperplane.util.Api;
import com.example.paperplane.util.DateFormatter;
import com.example.paperplane.util.NetworkState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuht on 2017/3/8.
 */

public class DoubanPresenter implements DoubanContract.Presenter{

    private DoubanContract.View view;
    private Context context;
    private StringModelImpl model;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Gson gson = new Gson();

    private ArrayList<DoubanMomentNews.posts> list = new ArrayList<>();

    public DoubanPresenter(Context context,DoubanContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        dbHelper = new DatabaseHelper(context,"History.db",null,5);
        db = dbHelper.getReadableDatabase();
    }
    @Override
    public void startReading(int position){
        DoubanMomentNews.posts item = list.get(position);
        Intent intent = new Intent(context, DetailActivity.class);

        intent.putExtra("type", BeanType.TYPE_DOUBAN);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        if (item.getThumbs().size() == 0){
            intent.putExtra("coverUrl", "");
        } else {
            intent.putExtra("coverUrl", item.getThumbs().get(0).getMedium().getUrl());
        }
        context.startActivity(intent);
    }
    @Override
    public void loadPosts(long date, final boolean clearing){
        if(clearing){
            view.stopLoading();
        }
        if(NetworkState.networkConnected(context)){
            model.load(Api.DOUBAN_MOMENT+new DateFormatter().DoubanDateFormat(date),new OnStringListener(){
                @Override
                public void onSuccess(String result){
                    try{
                        DoubanMomentNews post = gson.fromJson(result,DoubanMomentNews.class);
                        ContentValues values = new ContentValues();
                        if(clearing){
                            list.clear();
                        }
                        for(DoubanMomentNews.posts item:post.getPosts()){
                            list.add(item);
                            if(!queryIfIDExists(item.getId())){
                                db.beginTransaction();
                                try{
                                    values.put("douban_id",item.getId());
                                    values.put("douban_news", gson.toJson(item));
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = format.parse(item.getPublished_time());
                                    values.put("douban_time", date.getTime() / 1000);
                                    values.put("douban_content", "");
                                    db.insert("Douban", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }finally {
                                    db.endTransaction();
                                }
                            }
                            Intent intent = new Intent("com.example.paperplane.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_DOUBAN);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                        view.showResults(list);
                    }catch (JsonSyntaxException e){
                        view.showLoadingError();
                    }
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error){
                    view.stopLoading();
                    view.showLoadingError();
                }
            });
        }else{
            if(clearing){
                list.clear();
                Cursor cursor = db.query("Douban",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        DoubanMomentNews.posts post = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")),DoubanMomentNews.class);
                        list.add(post);
                    }while ((cursor.moveToNext()));
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            }
        }
    }
    @Override
    public void refresh(){loadPosts((Calendar.getInstance().getTimeInMillis(),true));}
    @Override
    public void loadMore(long date){loadPosts(date,false);}
    @Override
    public void feelLucky(){
        if(list.isEmpty()){
            view.showLoadingError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }
    @Override
    public void start(){refresh();}
    private boolean queryIfIDExists(int id){
        Cursor cursor = db.query("Douban",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(id==cursor.getInt(cursor.getColumnIndex("douabn_id"))){
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
}
