package com.leotenebris.frostweb.AddOns;

import android.content.SharedPreferences;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.leotenebris.frostweb.AddOns.ObjManager;
import java.util.Map;

public class JsManager {
    private ObjManager objManager;
    private String sharedPrefsName = addons;

    public JsManager() {
        objManager = ObjManager.getInstance();
    }

    public void init(WebView webView) {
        SharedPreferences sharedPrefs = webView.getContext().getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        objManager.setContext(webView.getContext());
        Map<String, ?> allPrefs = sharedPrefs.getAll();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                updateJavaScript(key);
            }
        }
    }

    private void updateJavaScript(String scriptKey) {
        String (scriptKey)Obj;
        if ((scriptKey)Obj == null) {
            (scriptKey)Obj = objManager.getObj(scriptKey, true);
        }
        if (objManager.getModified(scriptKey)) {
            (scriptKey)Obj = objManager.getObj(scriptKey, true);
        }
        String (scriptKey)JsCode = objManager.getJsCode(scriptKey);
        if ((scriptKey)Obj != null && (scriptKey)JsCode != null) {
            String javascript = (scriptKey)Obj + "\n" + (scriptKey)JsCode;
            webView.evaluateJavascript(javascript, null);
        }
    }
}
