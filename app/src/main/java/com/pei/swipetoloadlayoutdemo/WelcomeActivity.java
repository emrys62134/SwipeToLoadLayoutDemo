package com.pei.swipetoloadlayoutdemo;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.pei.swipetoloadlayoutdemo.base.BaseActivity;

/**
 * Created by 裴亮 on 17/1/7.
 */

public class WelcomeActivity extends BaseActivity{
    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        ImageView iv = bindView(R.id.iv_welcome);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public void initData() {

    }
}
