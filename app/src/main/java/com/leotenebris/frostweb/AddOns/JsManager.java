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
            String keyObj = null;
            String keyJsCode = null;
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                updateJavaScript(webView, key, keyObj, keyJsCode);
                //Toast.makeText(webView.getContext(), "JSManager initialized with : " + "\n" + "key :" + key + "\n" + "keyObj :" + keyObj + "\n" + "keyJsCode :" + keyJsCode , Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(webView.getContext(), "JSManager initialized failed " , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateJavaScript(WebView webView, String scriptKey, String objHelper, String jsCode) {
        String toastHelper;
        if (objHelper == null) {
            objHelper = objManager.getObj(webView.getContext(), scriptKey, true);
            toastHelper = objHelper + " was null";
        } else {
            if (objHelper != null && objManager.getModified(scriptKey)) {
                objHelper = objManager.getObj(webView.getContext(), scriptKey, true);
                toastHelper = objHelper + " was non null and addons was modified";
            } else {
                toastHelper = objHelper + " was non null and addons unmodified";
            }
        }
        jsCode = objManager.getJsCode(scriptKey);
        if (objHelper != null && jsCode != null) {
            String javascript = objHelper + "\n" + jsCode;
            Toast.makeText(webView.getContext(),toastHelper, Toast.LENGTH_LONG).show();
            webView.evaluateJavascript(javascript, null);
        }
    }
}
