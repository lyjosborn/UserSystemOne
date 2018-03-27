package com.example.usersystemone;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class DashboardActivity extends BasicActivity {

    private TextView welcomeText;
    private Button fetchWebView;
    private Button logOffButton;
    private WebView webView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeText = (TextView) findViewById(R.id.welcomeText);
        fetchWebView = (Button) findViewById(R.id.fetchWebView);
        logOffButton = (Button) findViewById(R.id.logoff);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        welcomeText.setText("Welcome " + userId);

        loadWebView();

        fetchWebView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(DashboardActivity.this, "Fetch Web View", Toast.LENGTH_SHORT).show();
                                                sendInfoToJs(userId);
                                            }
                                        });


        logOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.usersystemone.FORCE_OFFLINE");
                Log.i(Tag, "111111111");
                sendBroadcast(intent);
            }
        });
    }

    private void loadWebView(){
        WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JsInterface(), "AndroidWebView");
        webView.loadUrl("file:///android_asset/index.html");
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    public void sendInfoToJs(String name) {
        webView.loadUrl("javascript:world('" + name + "')");
    }

    private class JsInterface {

        /**
         * JS ---传值---> Android
         * 在js中调用window.AndroidWebView.showToast(name)，便会触发showToast方法。
         *
         * @param text showToast方法中的响应参数
         */
        @JavascriptInterface
        public void showToast(String text) {
            Log.d("DashboardActivity", "showToast");
            Log.d("DashboardActivity", "text " + text);
            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setTitle("Android与JS交互信息对话框");
            builder.setMessage(text);
            builder.setPositiveButton("确定", null);
            builder.setNegativeButton("取消", null);
            builder.create().show();
        }
    }

}
