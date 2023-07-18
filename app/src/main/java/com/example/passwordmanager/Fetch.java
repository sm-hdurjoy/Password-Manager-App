package com.example.passwordmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

class Fetch {
    private String url;
    private ResponseListener responseListener;
    private Context context;
    private String requestMethod;
    private String body = "";
    private String domain = "http://10.0.2.2:5678";

    Fetch(Context context, String requestMethod, String url, String body, ResponseListener responseListener) {
        this.context = context;
        this.requestMethod = requestMethod;
        this.url = domain+url;
        this.responseListener = responseListener;
        this.body = body;

    }

    void send() {
        WebView webView = new WebView(this.context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new CustomBridge(this.context, this.responseListener), "CustomBridge");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String _url) {
                if (requestMethod == "GET") {
                    webView.loadUrl("javascript:fetch(\""+ url +"\").then(res=>res.json()).then(a=>CustomBridge.pushResponse(JSON.stringify(a))).catch(console.log)");
                } else {
                    webView.loadUrl("javascript:fetch(\""+ url +"\",{method:\""+ requestMethod +"\",headers:{\"Content-Type\":\"application/json\"},body:\'"+body+"\'}).then(a=>a.json()).then(a=>CustomBridge.pushResponse(JSON.stringify(a))).catch(console.log)");
                }
            }
        });
        webView.loadUrl(domain + "/db");
    }
}

class CustomBridge {
    Context context;
    ResponseListener responseListener;

    public CustomBridge(Context context, ResponseListener responseListener) {
        this.context = context;
        this.responseListener = responseListener;
    }

    @JavascriptInterface
    public void pushResponse(String data) {
        responseListener.onResponse(data);
    }
}