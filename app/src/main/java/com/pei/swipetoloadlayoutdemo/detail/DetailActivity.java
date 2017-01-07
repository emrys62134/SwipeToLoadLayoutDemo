package com.pei.swipetoloadlayoutdemo.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pei.swipetoloadlayoutdemo.R;
import com.pei.swipetoloadlayoutdemo.base.BaseActivity;

/**
 * Created by 裴亮 on 17/1/7.
 */
public class DetailActivity extends BaseActivity {

    private WebView webView;
    private String url;
    private String title;
    private WebViewClient webViewClient;
    private WebSettings webSettings;
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;

    @Override
    public int setLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("data");
        webView = bindView(R.id.web_view);
        relativeLayout = bindView(R.id.rl);
        ImageView iv = bindView(R.id.iv_111);
        animationDrawable = (AnimationDrawable) iv.getDrawable();

    }

    @Override
    public void initData() {
//        webViewClient = new WebViewClient();
//        webView.setWebViewClient(webViewClient);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }
            // webView 加载数据之前执行
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                relativeLayout.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                animationDrawable.start();
            }
            // webView 加载数据结束之后执行
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                relativeLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                animationDrawable.stop();
            }
        });
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl(url);

    }
}
