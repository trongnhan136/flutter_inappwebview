package com.pichillilorenzo.flutter_inappwebview_android.webview;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.pichillilorenzo.flutter_inappwebview_android.webview.in_app_webview.InAppWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class JavascriptContentInterface {

    private static final String LOG_TAG = "JSBridgeInterface_JavascriptContentInterface";

    private InAppWebView inAppWebView;



    public JavascriptContentInterface(InAppWebView inAppWebView) {
        this.inAppWebView = inAppWebView;
    }

    @JavascriptInterface
    public void displayContent(String pUrl) {
        sendToFlutter("displayContent", pUrl, null);
    }

    @JavascriptInterface
    public void displayUrlWithCrmApp(String pUrl) {
        sendToFlutter("displayUrlWithCrmApp", pUrl, null);
    }


    @JavascriptInterface
    public void displayUrl(String pUrl, String title) {
        sendToFlutter("displayUrl", pUrl, title);
    }

    @JavascriptInterface
    public void displayUrl(String pUrl) {
        sendToFlutter("displayUrl", pUrl, null);
    }

    @JavascriptInterface
    public void openScanQrCode() {
        sendToFlutter("openScanQrCode", null, null);
    }

    @JavascriptInterface
    public void openMenu(String id, String name) {
        sendToFlutter("openMenu", id, name);
    }


    @JavascriptInterface
    public void ShowNative_Menu(boolean show) {
        sendToFlutter("ShowNative_Menu", show + "", null);
    }

    @JavascriptInterface
    public void unreadAvatar(String number) {
        sendToFlutter("unreadAvatar", number, null);
    }


    @JavascriptInterface
    public void getMobileMenus(String menu) {
        if (!TextUtils.isEmpty(menu)) {
            sendToFlutter("getMobileMenus", menu, null);
        }
    }

    @JavascriptInterface
    public void displayWeb(String pUrl) {
        sendToFlutter("displayWeb", pUrl, null);
    }

    @JavascriptInterface
    public String getMobileAppConfig() {
        JSONObject object = new JSONObject();
        int useNewUI = 1;
        try {
            object
                    .put("isOpenNoticeNativePage", useNewUI)
                    .put("isSupportNativeMenu", useNewUI);

            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            String json = String.format(Locale.ENGLISH, "{\"isOpenNoticeNativePage\":\"%d\",\"isSupportNativeMenu\":\"%d\":\"%d\"}",
                    useNewUI, useNewUI);
            return json;
        }
    }

    @JavascriptInterface
    public void openNoticePage(String noticePage) {
        sendToFlutter("displayWeb", noticePage, null);
    }

    @JavascriptInterface
    public void printWebView() {
        sendToFlutter("printWebView", null, null);
    }

    @JavascriptInterface
    public void printWebView(String htmlContent) {
        sendToFlutter("printWebView", htmlContent, null);
    }

    @JavascriptInterface
    public void stopShowLoading() {
        sendToFlutter("stopShowLoading", null, null);
    }

    @JavascriptInterface
    public void startShowLoading() {
        sendToFlutter("startShowLoading", null, null);
    }

    @JavascriptInterface
    public void onClickContentSimpleWrite() {
        sendToFlutter("onClickContentSimpleWrite", null, null);
    }

    @JavascriptInterface
    public void openHRApp() {
        sendToFlutter("openHRApp", null, null);
    }

    @JavascriptInterface
    public void openMailApp(String mid) {
        sendToFlutter("openMailApp", mid, null);
    }

    @JavascriptInterface
    public void openApprovalApp(String map) {
        sendToFlutter("openApprovalApp", map, null);
    }

    @JavascriptInterface
    public void onChangeLanguage(String lang) {
        sendToFlutter("onChangeLanguage", lang, null);
    }

    @JavascriptInterface
    public void closeWeb() {
        sendToFlutter("closeWeb", null, null);
    }


    private static String wrapToJsonStrings(String method, String arg1, String args2) {
        try {
            JSONArray array = new JSONArray();
            array.put(method);
            if(arg1 != null){
                array.put(arg1);
            }
            if(args2 != null){
                array.put(args2);
            }
            return array.toString(); // Returns e.g. ["your input"]
        } catch (Exception e) {
            return "[]";
        }
    }

    void sendToFlutter(String method, String arg1, String args2){
        final Handler handler = new Handler(inAppWebView.getWebViewLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (inAppWebView.channelDelegate != null) {
                    // invoke flutter javascript handler and send back flutter data as a JSON Object to javascript
                    inAppWebView.channelDelegate.onCallJsHandler("observe", wrapToJsonStrings(method, arg1, args2), new WebViewChannelDelegate.CallJsHandlerCallback() {
                        @Override
                        public void defaultBehaviour(@Nullable Object json) {
                        }

                        @Override
                        public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {

                        }
                    });
                }
            }
        });

    }

    public void dispose() {
        inAppWebView = null;
    }
}
