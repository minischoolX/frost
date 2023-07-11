package com.leotenebris.frostweb.AddOns;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

public class ObjManager {
    private static ObjManager instance;

//    private JSONObject jsonObject;
  
    private boolean adBlockerModified;
    private boolean videoEnhancerModified;
    private boolean readAloudModified;
    private boolean adBlockerObjModified;
    private boolean videoEnhancerObjModified;
    private boolean readAloudObjModified;

    private ObjManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized ObjManager getInstance() {
        if (instance == null) {
            instance = new ObjManager();
        }
        return instance;
    }

    public boolean getAdBlockerModified() {
        return adBlockerModified;
    }

    public void setAdBlockerModified(boolean value) {
        adBlockerModified = value;
    }

    public boolean getVideoEnhancerModified() {
        return videoEnhancerModified;
    }

    public void setVideoEnhancerModified(boolean value) {
        videoEnhancerModified = value;
    }

    public boolean getReadAloudModified() {
        return readAloudModified;
    }

    public void setReadAloudModified(boolean value) {
        readAloudModified = value;
    }

    public boolean getAdBlockerObjModified() {
        return adBlockerObjModified;
    }

    public void setAdBlockerObjModified(boolean value) {
        adBlockerObjModified = value;
    }

    public boolean getVideoEnhancerObjModified() {
        return videoEnhancerObjModified;
    }

    public void setVideoEnhancerObjModified(boolean value) {
        videoEnhancerObjModified = value;
    }

    public boolean getReadAloudObjModified() {
        return readAloudObjModified;
    }

    public void setReadAloudObjModified(boolean value) {
        readAloudObjModified = value;
    }

    public String getVideoEnhancerObj(Context context) {
        SharedPreferences videoEnhancerObjPrefs = context.getSharedPreferences("videoEnhancer", Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = videoEnhancerObjPrefs.getAll();
        JSONObject videoEnhancerObj = new JSONObject();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            try {
                String valueAsString;
                Object value = entry.getValue();
                if (value instanceof Boolean) {
                    valueAsString = ((Boolean) value) ? "on" : "off";
                } else {
                    valueAsString = String.valueOf(value);
                }
                videoEnhancerObj.put(entry.getKey(), valueAsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setVideoEnhancerModified(false);
        setVideoEnhancerObjModified(true);
        return videoEnhancerObj.toString();
    }
}
