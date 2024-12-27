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
    private final ConcurrentHashMap<String, SmLib_CheckUpdateCallback> mUpdateCallbackMap =
            new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Result> mCache = new ConcurrentHashMap<>();
    private long mLastCheckTime = -1;

    public SmLib_IafdSmAPIManager(Context context) {
        this.mContext = context;
    }

    public void checkUpdate(
            String pkgName, long versionCode, SmLib_CheckUpdateCallback checkUpdateCallback) {
        if (TextUtils.isEmpty(pkgName)) {
            checkUpdateCallback.onResult(-1, -1L, null, null);
            Log.e(TAG, "pkgName is null");
            return;
        }
        if (this.mContentObserver == null) {
            this.mContentObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.sec.android.iaft.SmLib_IafdSmAPIManager.1
                        @Override // android.database.ContentObserver
                        public void onChange(boolean selfChange, Uri uri) {
                            Log.i(
                                    SmLib_IafdSmAPIManager.TAG,
                                    "update check done, content uri " + uri.toString());
                            String pkgName2 =
                                    uri.getQueryParameter(SmLib_IafdConstant.KEY_PACKAGE_NAME);
                            long versionCode2 =
                                    Long.valueOf(
                                                    uri.getQueryParameter(
                                                            SmLib_IafdConstant.KEY_VERSION_CODE))
                                            .longValue();
                            int resultCode =
                                    Integer.valueOf(uri.getQueryParameter("resultCode")).intValue();
                            String versionName = uri.getQueryParameter("versionName");
                            Result result = new Result();
                            result.resultCode = resultCode;
                            result.versionCode = versionCode2;
                            result.versionName = versionName;
                            result.pkgName = pkgName2;
                            SmLib_IafdSmAPIManager.this.mCache.put(pkgName2, result);
                            SmLib_CheckUpdateCallback updateCallback =
                                    (SmLib_CheckUpdateCallback)
                                            SmLib_IafdSmAPIManager.this.mUpdateCallbackMap.get(
                                                    pkgName2);
                            Log.i(
                                    SmLib_IafdSmAPIManager.TAG,
                                    "updateCallback is null = " + (updateCallback == null));
                            if (updateCallback != null) {
                                updateCallback.onResult(
                                        resultCode, versionCode2, versionName, pkgName2);
                                SmLib_IafdSmAPIManager.this.mUpdateCallbackMap.remove(pkgName2);
                            }
                        }
                    };
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            SmLib_IafdConstant.IAFD_STUB_EX_CHECK_UPDATE_API,
                            true,
                            this.mContentObserver);
        }
        if (this.mLastCheckTime == -1) {
            this.mLastCheckTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.mLastCheckTime > AlarmManager.INTERVAL_HALF_DAY) {
            this.mLastCheckTime = System.currentTimeMillis();
            Log.i(TAG, "cache is expired clear it");
            this.mCache.clear();
        }
        Result result = this.mCache.get(pkgName);
        if (result != null && checkUpdateCallback != null) {
            Log.i(TAG, "using cache ");
            checkUpdateCallback.onResult(
                    result.resultCode, result.versionCode, result.versionName, result.pkgName);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(SmLib_IafdConstant.KEY_PACKAGE_NAME, pkgName);
        bundle.putLong(SmLib_IafdConstant.KEY_VERSION_CODE, versionCode);
        this.mUpdateCallbackMap.put(pkgName, checkUpdateCallback);
        this.mContext
                .getContentResolver()
                .call(
                        SmLib_IafdConstant.DC_API_AUTHORITY,
                        SmLib_IafdConstant.METHOD_CHECK_UPDATE,
                        (String) null,
                        bundle);
    }

    public void reportErrorDataToServer(
            String pkgName,
            int userId,
            int errorType,
            String errorStack,
            String errorComponent,
            long versionCode,
            String appName,
            String versionName) {
        Bundle bundle = new Bundle();
        bundle.putString(SmLib_IafdConstant.KEY_PACKAGE_NAME, pkgName);
        bundle.putInt(SmLib_IafdConstant.KEY_USER_ID, userId);
        bundle.putInt("type", errorType);
        bundle.putString(SmLib_IafdConstant.KEY_ERROR_STACK, errorStack);
        bundle.putString("component", errorComponent);
        bundle.putLong(SmLib_IafdConstant.KEY_VERSION_CODE, versionCode);
        bundle.putString(SmLib_IafdConstant.KEY_APP_NAME, appName);
        bundle.putString(SmLib_IafdConstant.KEY_VERSION_NAME, versionName);
        this.mContext
                .getContentResolver()
                .call(
                        SmLib_IafdConstant.DC_API_AUTHORITY,
                        SmLib_IafdConstant.METHOD_REPORT_TO_SERVER,
                        (String) null,
                        bundle);
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

        private Result() {}
    }
}
