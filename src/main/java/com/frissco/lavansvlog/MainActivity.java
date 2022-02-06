package com.frissco.lavansvlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.frissco.lavansvlog.R;

public class MainActivity extends AppCompatActivity {
    public WebView webView;
    Toolbar toolbar;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Intent intent = new Intent(this,NoInternet.class);
            startActivity(intent);
        }
            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                    setTitle("Loading...");
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                    setTitle("Finished");
                }
            });
            webView.loadUrl("https://lavansvlogapp.blogspot.com/");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            setToolbar();
        }

        private void setToolbar () {
            toolbar = (Toolbar) findViewById(R.id.main_toolbar);
            ImageButton refresh = findViewById(R.id.refresh);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    restartActivity();
                }
            });
        }

        private void restartActivity () {
            this.recreate();
        }

        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if (event.getAction() == KeyEvent.ACTION_DOWN) {

                switch (keyCode) {

                    case KeyEvent.KEYCODE_BACK:
                        if (webView.canGoBack()) {

                            webView.goBack();
                        } else {

                            finish();
                        }
                        return true;
                }
            }
            return super.onKeyDown(keyCode, event);
        }

    }