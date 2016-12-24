package com.pei.swipetoloadlayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

public class MainActivity extends BaseActivity implements OnLoadMoreListener, OnRefreshListener {

    private String url = "http://food.boohee.com/fb/v1/feeds/category_feed?page=1&category=3&per=10"; // 第一页正常展示的数据url
    public String headUrl = "http://food.boohee.com/fb/v1/feeds/category_feed?page="; // 加载拼接网址上部分
    public String footUrl = "&category=3&per=10"; // 加载拼接网址下部分
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView listView;
    private NetBean bean; // listView要显示的数据类
    private MyAdapter adapter; // listViewAdapter
    private int page = 1; // 初始页
    private String newUrl; // 上拉加载拼接网址

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initViews(); // 初始化组件

    }

    private void initViews() {
        swipeToLoadLayout = bindView(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this); // 刷新监听事件
        swipeToLoadLayout.setOnLoadMoreListener(this); // 加载监听事件
        listView = bindView(R.id.swipe_target);
        adapter = new MyAdapter();
        listView.setAdapter(adapter); // 将装载数据的adapter设置给listview
    }

    @Override
    public void initData() {
        getNetData(); // 获取网络数据
    }

    private void getNetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
    }

    // 上拉加载的方法
    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                newUrl = headUrl + (page + 1) + footUrl;
                getData(); // 获取上拉加载的数据
                page++;
            }
        }, 2000);
    }

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
    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                getNetData(); // 再一次获取网络数据
            }

        }, 2000);
    }
}
