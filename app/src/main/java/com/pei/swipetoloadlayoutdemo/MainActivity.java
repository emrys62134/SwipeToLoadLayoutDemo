package com.pei.swipetoloadlayoutdemo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.pei.swipetoloadlayoutdemo.base.BaseActivity;
import com.pei.swipetoloadlayoutdemo.detail.DetailActivity;

public class MainActivity extends BaseActivity  {

    private String url = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=3&per=10"; // 第一页正常展示的数据url
    public String headUrl = "http://food.boohee.com/fb/v1/feeds/category_feed?page="; // 加载拼接网址上部分
    public String footUrl = "&category=3&per=10"; // 加载拼接网址下部分
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView listView;
    private NetBean bean; // listView要显示的数据类
    private MyAdapter adapter; // listViewAdapter
    private int page = 1; // 初始页
    private String newUrl; // 上拉加载拼接网址
    private LinearLayout ll;
    private AnimationDrawable animationDrawable;
    private RelativeLayout r;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initViews(); // 初始化组件

    }

    private void initViews() {
        ImageView iv = bindView(R.id.iv_11111);
        animationDrawable = (AnimationDrawable) iv.getDrawable();
//        swipeToLoadLayout = bindView(R.id.swipeToLoadLayout);
//        swipeToLoadLayout.setOnRefreshListener(this); // 刷新监听事件
//        swipeToLoadLayout.setOnLoadMoreListener(this); // 加载监听事件
        listView = bindView(R.id.swipe_target);
        r = bindView(R.id.rl1);

        adapter = new MyAdapter();
        listView.setAdapter(adapter); // 将装载数据的adapter设置给listview

    }

    @Override
    public void initData() {
        animationDrawable.start();
        getNetData(); // 获取网络数据
    }

    private void getNetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                animationDrawable.stop();
                r.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                bean = gson.fromJson(response, NetBean.class);
                adapter.setBean(bean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("data", bean.getFeeds().get(i).getLink());
                startActivity(intent);
            }
        });
    }

    // 上拉加载的方法
//    @Override
//    public void onLoadMore() {
//        swipeToLoadLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeToLoadLayout.setLoadingMore(false);
//                newUrl = headUrl + (page + 1) + footUrl;
//                getData(); // 获取上拉加载的数据
//                page++;
//            }
//        }, 2000);
//    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(newUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, NetBean.class);
                adapter.addMore(bean); // 将加载后的数据添加到原来的数据类里
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    // 刷新方法
//    @Override
//    public void onRefresh() {
//        swipeToLoadLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeToLoadLayout.setRefreshing(false);
//                getNetData(); // 再一次获取网络数据
//            }
//
//        }, 2000);
//    }
}
