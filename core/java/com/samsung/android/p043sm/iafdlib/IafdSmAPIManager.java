package com.samsung.android.p043sm.iafdlib;

import android.app.AlarmManager;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
public class IafdSmAPIManager {
    private static final String TAG = "Dc.IafdSmAPIManager";
    private ContentObserver mContentObserver;
    private final Context mContext;
    private final ConcurrentHashMap<String, CheckUpdateCallback> mUpdateCallbackMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Result> mCache = new ConcurrentHashMap<>();
    private long mLastCheckTime = -1;

    public IafdSmAPIManager(Context context) {
        this.mContext = context;
    }

    public void checkUpdate(String pkgName, long versionCode, CheckUpdateCallback checkUpdateCallback) {
        if (TextUtils.isEmpty(pkgName)) {
            checkUpdateCallback.onResult(-1, -1L, null, null);
            Log.m96e(TAG, "pkgName is null");
            return;
        }
        if (this.mContentObserver == null) {
            this.mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.samsung.android.sm.iafdlib.IafdSmAPIManager.1
                @Override // android.database.ContentObserver
                public void onChange(boolean selfChange, Uri uri) {
                    Log.m98i(IafdSmAPIManager.TAG, "update check done, content uri " + uri.toString());
                    String pkgName2 = uri.getQueryParameter(IafdConstant.KEY_PACKAGE_NAME);
                    long versionCode2 = Long.valueOf(uri.getQueryParameter(IafdConstant.KEY_VERSION_CODE)).longValue();
                    int resultCode = Integer.valueOf(uri.getQueryParameter("resultCode")).intValue();
                    String versionName = uri.getQueryParameter("versionName");
                    Result result = new Result();
                    result.resultCode = resultCode;
                    result.versionCode = versionCode2;
                    result.versionName = versionName;
                    result.pkgName = pkgName2;
                    IafdSmAPIManager.this.mCache.put(pkgName2, result);
                    CheckUpdateCallback updateCallback = (CheckUpdateCallback) IafdSmAPIManager.this.mUpdateCallbackMap.get(pkgName2);
                    Log.m98i(IafdSmAPIManager.TAG, "updateCallback is null = " + (updateCallback == null));
                    if (updateCallback != null) {
                        updateCallback.onResult(resultCode, versionCode2, versionName, pkgName2);
                        IafdSmAPIManager.this.mUpdateCallbackMap.remove(pkgName2);
                    }
                }
            };
            this.mContext.getContentResolver().registerContentObserver(IafdConstant.IAFD_STUB_EX_CHECK_UPDATE_API, true, this.mContentObserver);
        }
        if (this.mLastCheckTime == -1) {
            this.mLastCheckTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.mLastCheckTime > AlarmManager.INTERVAL_HALF_DAY) {
            this.mLastCheckTime = System.currentTimeMillis();
            Log.m98i(TAG, "cache is expired clear it");
            this.mCache.clear();
        }
        Result result = this.mCache.get(pkgName);
        if (result != null && checkUpdateCallback != null) {
            Log.m98i(TAG, "using cache ");
            checkUpdateCallback.onResult(result.resultCode, result.versionCode, result.versionName, result.pkgName);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(IafdConstant.KEY_PACKAGE_NAME, pkgName);
        bundle.putLong(IafdConstant.KEY_VERSION_CODE, versionCode);
        this.mUpdateCallbackMap.put(pkgName, checkUpdateCallback);
        this.mContext.getContentResolver().call(IafdConstant.DC_API_AUTHORITY, IafdConstant.METHOD_CHECK_UPDATE, (String) null, bundle);
    }

    public void reportErrorDataToServer(String pkgName, int userId, int errorType, String errorStack, String errorComponent, long versionCode, String appName, String versionName) {
        Bundle bundle = new Bundle();
        bundle.putString(IafdConstant.KEY_PACKAGE_NAME, pkgName);
        bundle.putInt(IafdConstant.KEY_USER_ID, userId);
        bundle.putInt("type", errorType);
        bundle.putString(IafdConstant.KEY_ERROR_STACK, errorStack);
        bundle.putString("component", errorComponent);
        bundle.putLong(IafdConstant.KEY_VERSION_CODE, versionCode);
        bundle.putString(IafdConstant.KEY_APP_NAME, appName);
        bundle.putString(IafdConstant.KEY_VERSION_NAME, versionName);
        this.mContext.getContentResolver().call(IafdConstant.DC_API_AUTHORITY, IafdConstant.METHOD_REPORT_TO_SERVER, (String) null, bundle);
    }

    public void onDestroy() {
        try {
            Log.m96e(TAG, "unregisterContentObserver");
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
            this.mCache.clear();
            this.mUpdateCallbackMap.clear();
        } catch (Exception e) {
            Log.m97e(TAG, "onDestroy ", e);
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
