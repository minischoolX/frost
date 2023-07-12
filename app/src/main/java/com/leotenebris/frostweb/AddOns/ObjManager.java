package com.leotenebris.frostweb.AddOns;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONException;


public class ObjManager {
    private static ObjManager instance;

    private Context context;
    private Map<String, Boolean> modifiedMap;
    private boolean isModifiedStatesInitialized;

    private ObjManager() {
        // Private constructor to prevent instantiation
        modifiedMap = new HashMap<>();
        isModifiedStatesInitialized = false;
    }

    public static synchronized ObjManager getInstance() {
        if (instance == null) {
            instance = new ObjManager();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
        initializeModifiedStates();
    }

    private void initializeModifiedStates() {
        if (!isModifiedStatesInitialized && context != null) {
            SharedPreferences prefs = context.getSharedPreferences("addons", Context.MODE_PRIVATE);
            Map<String, ?> allPrefs = prefs.getAll();

            for (String key : allPrefs.keySet()) {
                modifiedMap.put(key, false);
            }
            isModifiedStatesInitialized = true;
        }
    }

    public String getJsCode (String key) {
        String dummyJsCode = "dummy js code";
        return dummyJsCode;
    }
    
    public String getObj(Context context, String key, Boolean modified) {
        if (context == null) {
            throw new IllegalStateException("Context is not set. Call setContext() before using the ObjManager.");
        }

        SharedPreferences objPrefs = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = objPrefs.getAll();
        JSONObject obj = new JSONObject();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            try {
                String valueAsString;
                Object value = entry.getValue();
                if (value instanceof Boolean) {
                    valueAsString = ((Boolean) value) ? "on" : "off";
                } else {
                    valueAsString = String.valueOf(value);
                }
                obj.put(entry.getKey(), valueAsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (modified) {
            setModified(key, false);
        }

        return obj.toString();
    }

    public boolean getModified(String key) {
        if (modifiedMap.containsKey(key)) {
            return modifiedMap.get(key);
        }
        return false;
    }

    public void setModified(String key, boolean value) {
        modifiedMap.put(key, value);
    }
}
