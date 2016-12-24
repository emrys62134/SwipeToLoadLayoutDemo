package com.pei.swipetoloadlayoutdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 裴亮 on 16/12/24.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
    }

    // 绑定布局
    public abstract int setLayout();
    // 初始化组件
    public abstract void initView();
    // 初始化数据,拉取网络数据
    public abstract void initData();
    // 省略强转
    public <T extends View> T bindView(int id){
        return (T)findViewById(id);
    }
}
