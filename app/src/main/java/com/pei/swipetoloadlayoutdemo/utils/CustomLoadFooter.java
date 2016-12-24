package com.pei.swipetoloadlayoutdemo.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.pei.swipetoloadlayoutdemo.R;

/**
 * Created by 裴亮 on 16/12/24.
 */

public class CustomLoadFooter extends ImageView implements SwipeLoadMoreTrigger,
SwipeTrigger{
    public CustomLoadFooter(Context context) {
        super(context);
    }

    public CustomLoadFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
        this.setImageResource(R.drawable.refresh_load_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReset() {

    }
}
