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
        objManager.setContext(webView.getContext());
        Map<String, ?> allPrefs = sharedPrefs.getAll();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();
            String keyObj = key + "Obj";
            String keyJsCode = key + "JsCode";
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                updateJavaScript(webView, key, keyObj, keyJsCode);
            }
        }
    }

    private void updateJavaScript(WebView webView, String scriptKey, String objHelper, String jsCode) {
        if (objHelper == null) {
            objHelper = objManager.getObj(scriptKey, true);
        }
        if (objManager.getModified(scriptKey)) {
            objHelper = objManager.getObj(scriptKey, true);
        }
        jsCode = objManager.getJsCode(scriptKey);
        if (objHelper != null && jsCode != null) {
            String javascript = objHelper + "\n" + jsCode;
            Toast.makeText(webView.getContext(), "javaScript injected from : " + scriptKey + "\n" + javascript, Toast.LENGTH_LONG).show();
            webView.evaluateJavascript(javascript, null);
        }
    }
}
