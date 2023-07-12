package com.leotenebris.frostweb.AddOns;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.leotenebris.frostweb.AddOns.ObjManager;
import java.util.Map;

import android.widget.Toast;

public class JsManager {
    private ObjManager objManager;
    private String sharedPrefsName = "addons";

    public JsManager() {
        objManager = ObjManager.getInstance();
    }

    public void init(WebView webView) {
        SharedPreferences sharedPrefs = webView.getContext().getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = sharedPrefs.getAll();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();
            String keyObj = null;
            String keyJsCode = null;
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                updateJavaScript(webView, key, keyObj, keyJsCode);
            }
        }
    }

    private void updateJavaScript(WebView webView, String scriptKey, String objHelper, String jsCode) {
        objHelper = objManager.getObj(webView.getContext(), scriptKey);
        jsCode = objManager.getJsCode(scriptKey);
        if (objHelper != null && jsCode != null) {
            String javascript = objHelper + "\n" + jsCode;
            Toast.makeText(webView.getContext(), javascript, Toast.LENGTH_LONG).show();
            webView.evaluateJavascript(javascript, null);
        }
    }
}
