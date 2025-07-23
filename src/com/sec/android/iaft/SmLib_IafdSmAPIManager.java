package com.sec.android.iaft;

import android.app.AlarmManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class SmLib_IafdSmAPIManager {
    private static final String TAG = "Dc.IafdSmAPIManager";
    private ContentObserver mContentObserver;
    private final Context mContext;
    private final ConcurrentHashMap<String, SmLib_CheckUpdateCallback> mUpdateCallbackMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Result> mCache = new ConcurrentHashMap<>();
    private long mLastCheckTime = -1;

    public SmLib_IafdSmAPIManager(Context context) {
        this.mContext = context;
    }

    public void checkUpdate(String str, long j, SmLib_CheckUpdateCallback smLib_CheckUpdateCallback) {
        if (TextUtils.isEmpty(str)) {
            smLib_CheckUpdateCallback.onResult(-1, -1L, null, null);
            Log.e(TAG, "pkgName is null");
            return;
        }
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.sec.android.iaft.SmLib_IafdSmAPIManager.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    Log.i(SmLib_IafdSmAPIManager.TAG, "update check done, content uri " + uri.toString());
                    String queryParameter = uri.getQueryParameter(SmLib_IafdConstant.KEY_PACKAGE_NAME);
                    long longValue = Long.valueOf(uri.getQueryParameter(SmLib_IafdConstant.KEY_VERSION_CODE)).longValue();
                    int intValue = Integer.valueOf(uri.getQueryParameter("resultCode")).intValue();
                    String queryParameter2 = uri.getQueryParameter("versionName");
                    Result result = new Result();
                    result.resultCode = intValue;
                    result.versionCode = longValue;
                    result.versionName = queryParameter2;
                    result.pkgName = queryParameter;
                    SmLib_IafdSmAPIManager.this.mCache.put(queryParameter, result);
                    SmLib_CheckUpdateCallback smLib_CheckUpdateCallback2 = (SmLib_CheckUpdateCallback) SmLib_IafdSmAPIManager.this.mUpdateCallbackMap.get(queryParameter);
                    StringBuilder sb = new StringBuilder("updateCallback is null = ");
                    sb.append(smLib_CheckUpdateCallback2 == null);
                    Log.i(SmLib_IafdSmAPIManager.TAG, sb.toString());
                    if (smLib_CheckUpdateCallback2 != null) {
                        smLib_CheckUpdateCallback2.onResult(intValue, longValue, queryParameter2, queryParameter);
                        SmLib_IafdSmAPIManager.this.mUpdateCallbackMap.remove(queryParameter);
                    }
                }
            };
            this.mContext.getContentResolver().registerContentObserver(SmLib_IafdConstant.IAFD_STUB_EX_CHECK_UPDATE_API, true, this.mContentObserver);
        }
        if (this.mLastCheckTime == -1) {
            this.mLastCheckTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.mLastCheckTime > AlarmManager.INTERVAL_HALF_DAY) {
            this.mLastCheckTime = System.currentTimeMillis();
            Log.i(TAG, "cache is expired clear it");
            this.mCache.clear();
        }
        Result result = this.mCache.get(str);
        if (result != null && smLib_CheckUpdateCallback != null) {
            Log.i(TAG, "using cache ");
            smLib_CheckUpdateCallback.onResult(result.resultCode, result.versionCode, result.versionName, result.pkgName);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(SmLib_IafdConstant.KEY_PACKAGE_NAME, str);
        bundle.putLong(SmLib_IafdConstant.KEY_VERSION_CODE, j);
        this.mUpdateCallbackMap.put(str, smLib_CheckUpdateCallback);
        this.mContext.getContentResolver().call(SmLib_IafdConstant.DC_API_AUTHORITY, SmLib_IafdConstant.METHOD_CHECK_UPDATE, (String) null, bundle);
    }

    public void reportErrorDataToServer(String str, int i, int i2, String str2, String str3, long j, String str4, String str5) {
        Bundle bundle = new Bundle();
        bundle.putString(SmLib_IafdConstant.KEY_PACKAGE_NAME, str);
        bundle.putInt(SmLib_IafdConstant.KEY_USER_ID, i);
        bundle.putInt("type", i2);
        bundle.putString(SmLib_IafdConstant.KEY_ERROR_STACK, str2);
        bundle.putString("component", str3);
        bundle.putLong(SmLib_IafdConstant.KEY_VERSION_CODE, j);
        bundle.putString(SmLib_IafdConstant.KEY_APP_NAME, str4);
        bundle.putString(SmLib_IafdConstant.KEY_VERSION_NAME, str5);
        this.mContext.getContentResolver().call(SmLib_IafdConstant.DC_API_AUTHORITY, SmLib_IafdConstant.METHOD_REPORT_TO_SERVER, (String) null, bundle);
    }

    public void onDestroy() {
        try {
            Log.e(TAG, "unregisterContentObserver");
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
            this.mCache.clear();
            this.mUpdateCallbackMap.clear();
        } catch (Exception e) {
            Log.e(TAG, "onDestroy ", e);
        }
    }

    private static class Result {
        public String pkgName;
        public int resultCode;
        public long versionCode;
        public String versionName;

        private Result() {
        }
    }
}
