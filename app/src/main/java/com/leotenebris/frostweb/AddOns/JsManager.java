package com.leotenebris.frostweb.AddOns;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsManager {
    private static JsManager instance;
    private String sharedPrefsName;
    private Map<String, String> objHelperMap;
    private Map<String, String> jsCodeMap;
    private Map<String, Boolean> modifiedMap;
    private Context appContext;

    private JsManager(Context context) {
        objHelperMap = new HashMap<>();
        jsCodeMap = new HashMap<>();
        modifiedMap = new HashMap<>();
        appContext = context.getApplicationContext();
        sharedPrefsName = context.getString(R.string.prefs_name);
        initializeMaps();
    }

    public static synchronized JsManager getInstance() {
        if (instance == null) {
            //throw new IllegalStateException("JsManager is not initialized. Call initialize(Context) first.");
        }
        return instance;
    }

    public static synchronized void initialize(Context context) {
        if (instance == null) {
            instance = new JsManager(context);
        }
    }

    private void initializeMaps() {
        // Initialize all keys in objHelperMap, jsCodeMap, and modifiedMap with false values
        SharedPreferences sharedPrefs = appContext.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = sharedPrefs.getAll();

        for (String key : allPrefs.keySet()) {
            objHelperMap.put(key, null);
            jsCodeMap.put(key, null);
            modifiedMap.put(key, false);
        }
    }

    public void init(WebView webView, String url) {
        SharedPreferences sharedPrefs = appContext.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        Map<String, ?> allPrefs = sharedPrefs.getAll();
        String objHelper;
        String jsCode;

        // ensure every object state is initialized and upto date
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String key = entry.getKey();

            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                if (getObjHelper(key) == null || getJsCodeHelper(key) == null) {
                    setObjHelper(key);
                    setJsCodeHelper(key);
                } else if (getModified(key)) {
                    setObjHelper(key);
                    setJsCodeHelper(key);
                    setModified(key, false)
                }
            }
        }

        objHelper = "var defaultObj=" + getObjHelper(key) + ",user_settings=defaultObj;";
        jsCode = getJsCodeHelper(key);
        if (objHelper != null && jsCode != null) {
            String javascript = objHelper + "\n" + jsCode;
            Toast.makeText(appContext, javascript, Toast.LENGTH_LONG).show();
            if (key == "videoEnhancer") {
                if (url.matches("^(https?://(www|m)\\.youtube\\.com/.*)|(https?://.*\\.youtube-nocookie\\.com/embed/.*)|(https?://youtube\\.googleapis\\.com/embed/.*)|(https?://raingart\\.github\\.io/options\\.html.*)$") &&
                    !url.matches("^(https?://.*\\.youtube\\.com/.*\\.xml.*)|(https?://.*\\.youtube\\.com/error.*)|(https?://music\\.youtube\\.com/.*)|(https?://accounts\\.youtube\\.com/.*)|(https?://studio\\.youtube\\.com/.*)|(https?://.*\\.youtube\\.com/redirect\\?.*)$")) {

                webView.evaluateJavascript(javascript, null);

                }
            } else {
                webView.evaluateJavascript(javascript, null);
            }
        }
//        objHelper = getObjHelper(key);
//var defaultObj={"square-avatars":"on","user-api-key":"",lang_code:"en"},user_settings=defaultObj;
//"var defaultObj=" + "{\"square-avatars\":\"on\",\"user-api-key\":\"\",lang_code:\"en\"}" + ",user_settings=defaultObj;";
    }

    public Boolean getModified(String key) {
        return modifiedMap.get(key);
    }
    
    public void setModified(String key, Boolean value) {
        modifiedMap.put(key, value);
    }

    public String getObjHelper(String key) {
        return objHelperMap.get(key);
    }

    public String setObjHelper(String key) {
        return objHelperMap.put(key, getObj(key));
    }

    public String getJsCodeHelper(String key) {
        return jsCodeHelperMap.get(key);
    }

    public String setJsCodeHelper(String key) {
        return jsCodeHelperMap.put(key, getJsCode(key));
    }

    public String getObj(String key) {
        SharedPreferences objPrefs = appContext.getSharedPreferences(key, Context.MODE_PRIVATE);
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
        return obj.toString();
    }

    public String getJsCode(String key) {
        String dummyCode;
        dummyCode = "var $jscomp=$jscomp||{};$jscomp.scope={};$jscomp.createTemplateTagFirstArg=function(a){return a.raw=a};$jscomp.createTemplateTagFirstArgWithRaw=function(a,c){a.raw=c;return a};$jscomp.arrayIteratorImpl=function(a){var c=0;return function(){return c<a.length?{done:!1,value:a[c++]}:{done:!0}}};$jscomp.arrayIterator=function(a){return{next:$jscomp.arrayIteratorImpl(a)}};\r\n$jscomp.makeIterator=function(a){var c=\"undefined\"!=typeof Symbol&&Symbol.iterator&&a[Symbol.iterator];if(c)return c.call(a);if(\"number\"==typeof a.length)return $jscomp.arrayIterator(a);throw Error(String(a)+\" is not an iterable or ArrayLike\");};$jscomp.arrayFromIterator=function(a){for(var c,d=[];!(c=a.next()).done;)d.push(c.value);return d};$jscomp.arrayFromIterable=function(a){return a instanceof Array?a:$jscomp.arrayFromIterator($jscomp.makeIterator(a))};\r\nwindow.nova_plugins=[];\r\nwindow.nova_plugins.push({id:\"square-avatars\",title:\"Square avatars\",run_on_pages:\"*, -live_chat\",section:\"comments\",desc:\"Make user images squared\",_runtime:function(a){NOVA.css.push(\"yt-img-shadow,\\n.ytp-title-channel-logo,\\n#player .ytp-title-channel,\\nytm-profile-icon,\\na.ytd-thumbnail {\\n               border-radius: 0 !important;\\n            }\");NOVA.waitUntil(function(){var c,d;if(window.yt&&(obj=null==(c=yt)?void 0:null==(d=c.config_)?void 0:d.EXPERIMENT_FLAGS)&&Object.keys(obj).length)return yt.config_.EXPERIMENT_FLAGS.web_rounded_thumbnails=\r\n!1,!0})}});\r\nvar NOVA={waitSelector:function(a,c){a=void 0===a?required():a;return\"string\"!==typeof a?console.error(\"wait > selector:\",typeof a):null==c||!c.container||c.container instanceof HTMLElement?a.includes(\":has(\")&&!CSS.supports(\"selector(:has(*))\")?new Promise(function(d,f){console.warn(\'CSS \":has()\" unsupported\');f(\'CSS \":has()\" unsupported\')}):new Promise(function(d){if(element=((null==c?void 0:c.container)||document.body||document).querySelector(a))return d(element);var f=new MutationObserver(function(e,g){for(var l=\r\n$jscomp.makeIterator(e),n=l.next();!n.done;n=l.next()){n=$jscomp.makeIterator(n.value.addedNodes);for(var b=n.next();!b.done;b=n.next())if(b=b.value,[1,3,8].includes(b.nodeType)&&b instanceof HTMLElement){if(b.matches&&b.matches(a))return g.disconnect(),d(b);if((parentEl=b.parentElement||b)&&parentEl instanceof HTMLElement&&(element=parentEl.querySelector(a)))return g.disconnect(),d(element)}}var h,m;if(\"loading\"!=(null==(h=document)?void 0:h.readyState)&&(element=((null==c?void 0:c.container)||(null==\r\n(m=document)?void 0:m.body)||document).querySelector(a)))return g.disconnect(),d(element)});f.observe((null==c?void 0:c.container)||document.body||document.documentElement||document,{childList:!0,subtree:!0,attributes:!0});if(null==c?0:c.stop_on_page_change){var k=function(){return this.prevURL===location.href?!1:this.prevURL=location.href};k();window.addEventListener(\"transitionend\",function(e){k()&&f.disconnect()})}}):console.error(\"wait > container not HTMLElement:\",c.container)},waitUntil:function(a,\r\nc){a=void 0===a?required():a;c=void 0===c?100:c;return\"function\"!==typeof a?console.error(\"waitUntil > condition is not fn:\",typeof a):new Promise(function(d){if(result=a())d(result);else var f=setInterval(function(){if(result=a())clearInterval(f),d(result)},c)})},delay:function(a){a=void 0===a?100:a;return new Promise(function(c){return setTimeout(c,a)})},css:{push:function(a,c,d){function f(k){k=void 0===k?required():k;if(k.endsWith(\".css\")){var e=document.createElement(\"link\");e.rel=\"sheet\";e.href=\r\nk}else(e=document.getElementById(\"NOVA-style\"))||(e=document.createElement(\"style\"),e.type=\"text/css\",e.id=\"NOVA-style\",e=(document.head||document.documentElement).appendChild(e));e.textContent+=\"\\n\"+k.replace(/\\n+\\s{2,}/g,\" \")+\"\\n\"}a=void 0===a?required():a;if(\"object\"===typeof a){if(!c)return console.error.apply(console,[\"injectStyle > empty json-selector:\"].concat($jscomp.arrayFromIterable(arguments)));f(c+function(k){var e=\"\";Object.entries(k).forEach(function(g){var l=$jscomp.makeIterator(g);\r\ng=l.next().value;l=l.next().value;e+=g+\":\"+l+(d?\" !important\":\"\")+\";\"});return\"{ \"+e+\" }\"}(a))}else a&&\"string\"===typeof a?document.head?f(a):window.addEventListener(\"load\",function(){return f(a)},{capture:!0,once:!0}):console.error(\"addStyle > css:\",typeof a)},getValue:function(a,c){a=void 0===a?required():a;c=void 0===c?required():c;var d;return(el=a instanceof HTMLElement?a:null==(d=document.body)?void 0:d.querySelector(a))?getComputedStyle(el).getPropertyValue(c):null}},getPlayerState:function(a){return{\"-1\":\"UNSTARTED\",\r\n0:\"ENDED\",1:\"PLAYING\",2:\"PAUSED\",3:\"BUFFERING\",5:\"CUED\"}[a||movie_player.getPlayerState()]},videoElement:function(){document.addEventListener(\"canplay\",function(a){a=a.target;a.matches(\"#movie_player:not(.ad-showing) video\")&&(NOVA.videoElement=a)},{capture:!0,once:!0});document.addEventListener(\"play\",function(a){a=a.target;a.matches(\"#movie_player:not(.ad-showing) video\")&&(NOVA.videoElement=a)},!0)}(),isFullscreen:function(){return movie_player.classList.contains(\"ytp-fullscreen\")||movie_player.hasOwnProperty(\"isFullscreen\")&&\r\nmovie_player.isFullscreen()},log:function(){this.DEBUG&&arguments.length&&(console.groupCollapsed.apply(console,arguments),console.trace(),console.groupEnd())}},Plugins={run:function(a){function c(b){var h=(null==b?void 0:b.id)&&b.run_on_pages&&\"function\"===typeof b._runtime;h||console.error(\"plugin invalid:\\n\",{id:null==b?void 0:b.id,run_on_pages:null==b?void 0:b.run_on_pages,_runtime:\"function\"===typeof(null==b?void 0:b._runtime)});return h}var d=a.user_settings,f=a.app_ver,k;if(null==(k=window.nova_plugins)||\r\n!k.length)return console.error(\"nova_plugins empty\",window.nova_plugins);if(!d)return console.error(\"user_settings empty\",d);NOVA.currentPage=function(){var b=location.pathname.split(\"/\").filter(Boolean),h=$jscomp.makeIterator([b[0],b.pop()]);b=h.next().value;h=h.next().value;NOVA.channelTab=\"featured videos shorts streams playlists community channels about\".split(\" \").includes(h)?h:!1;return\"live_chat\"!=b&&([\"channel\",\"c\",\"user\"].includes(b)||(null==b?0:b.startsWith(\"@\"))||/[A-Z\\d_]/.test(b)||NOVA.channelTab)?\r\n\"channel\":\"clip\"==b?\"watch\":b||\"home\"}();NOVA.isMobile=\"m.youtube.com\"==location.host;var e=[],g,l,n;null==(n=window.nova_plugins)||n.forEach(function(b){var h,m=null==b?void 0:null==(h=b.run_on_pages)?void 0:h.split(\",\").map(function(p){return p.trim().toLowerCase()}).filter(Boolean);l=0;g=!1;if(!c(b))console.error(\"Plugin invalid\\n\",b),alert(\"Plugin invalid: \"+(null==b?void 0:b.id)),g=\"INVALID\";else if(b.was_init&&!b.restart_on_location_change)g=\"skiped\";else if(!d.hasOwnProperty(b.id))g=\"off\";\r\nelse if(((null==m?0:m.includes(NOVA.currentPage))||(null==m?0:m.includes(\"*\"))&&(null==m||!m.includes(\"-\"+NOVA.currentPage)))&&(!NOVA.isMobile||NOVA.isMobile&&(null==m||!m.includes(\"-mobile\"))))try{var q=performance.now();b.was_init=!0;b._runtime(d);l=(performance.now()-q).toFixed(2);g=!0}catch(p){console.groupEnd(\"plugins status\"),console.error(\"[ERROR PLUGIN] \"+b.id+\"\\n\"+p.stack+\"\\n\\nPlease report the bug: https://github.com/raingart/Nova-YouTube-extension/issues/new?body=\"+encodeURIComponent(f+\r\n\" | \"+navigator.userAgent)),d.report_issues&&_pluginsCaptureException&&_pluginsCaptureException({trace_name:b.id,err_stack:p.stack,app_ver:f,confirm_msg:\'ERROR in Nova YouTube\\u2122\\n\\nCrash plugin: \"\'+(b.title||b.id)+\'\"\\nPlease report the bug or disable the plugin\\n\\nSend the bug raport to developer?\'}),console.groupCollapsed(\"plugins status\"),g=\"ERROR\"}e.push({launched:g,name:null==b?void 0:b.id,\"time init (ms)\":l})});console.table(e);console.groupEnd(\"plugins status\")}};landerPlugins();\r\nfunction landerPlugins(){function a(){var d=setInterval(function(){var f;if(\"loading\"==(null==(f=document)?void 0:f.readyState))return console.debug(\"waiting, page loading..\");clearInterval(d);console.groupCollapsed(\"plugins status\");Plugins.run({user_settings:defaultObj,app_ver:\"0.43.0\"})},500)}a();var c=location.href;(isMobile=\"m.youtube.com\"==location.host)?window.addEventListener(\"transitionend\",function(d){return\"progress\"==d.target.id&&isURLChange()&&a()}):document.addEventListener(\"yt-navigate-start\",\r\nfunction(){return(c==location.href?!1:c=location.href)&&a()})}function _pluginsCaptureException(a){};"
        return dummyCode;
    }
}
