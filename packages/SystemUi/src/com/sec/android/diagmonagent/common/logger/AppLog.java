package com.sec.android.diagmonagent.common.logger;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AppLog {
    public static String SERVICE_ID_TAG = "";
    public static Context mContext = null;
    public static String mServiceId = "";
    public static AppLogData sInstance;

    /* renamed from: d */
    public static void m269d(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            Log.d("DIAGMON_SDK", str);
            return;
        }
        AppLogData appLogData = sInstance;
        String str2 = SERVICE_ID_TAG;
        appLogData.getClass();
        try {
            appLogData.makeAdditionalData(str2);
            appLogData.printToFile("[d]", "DIAGMON_SDK" + appLogData.mMessagePrefix + str);
            StringBuilder sb = new StringBuilder("DIAGMON_SDK");
            sb.append(appLogData.mMessagePrefix);
            Log.d(sb.toString(), str);
        } catch (Exception e) {
            Log.e("DIAGMON_SDK", e.getMessage());
        }
    }

    /* renamed from: e */
    public static void m270e(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            Log.e("DIAGMON_SDK", str);
            return;
        }
        AppLogData appLogData = sInstance;
        String str2 = SERVICE_ID_TAG;
        appLogData.getClass();
        try {
            appLogData.makeAdditionalData(str2);
            appLogData.printToFile("[e]", "DIAGMON_SDK" + appLogData.mMessagePrefix + str);
            StringBuilder sb = new StringBuilder("DIAGMON_SDK");
            sb.append(appLogData.mMessagePrefix);
            Log.e(sb.toString(), str);
        } catch (Exception e) {
            Log.e("DIAGMON_SDK", e.getMessage());
        }
    }

    /* renamed from: i */
    public static void m271i(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            Log.i("DIAGMON_SDK", str);
            return;
        }
        AppLogData appLogData = sInstance;
        String str2 = SERVICE_ID_TAG;
        appLogData.getClass();
        try {
            appLogData.makeAdditionalData(str2);
            appLogData.printToFile("[i]", "DIAGMON_SDK" + appLogData.mMessagePrefix + str);
            StringBuilder sb = new StringBuilder("DIAGMON_SDK");
            sb.append(appLogData.mMessagePrefix);
            Log.i(sb.toString(), str);
        } catch (Exception e) {
            Log.e("DIAGMON_SDK", e.getMessage());
        }
    }

    /* renamed from: w */
    public static void m272w(String str) {
        if (mContext == null || TextUtils.isEmpty(mServiceId)) {
            Log.w("DIAGMON_SDK", str);
            return;
        }
        AppLogData appLogData = sInstance;
        String str2 = SERVICE_ID_TAG;
        appLogData.getClass();
        try {
            appLogData.makeAdditionalData(str2);
            appLogData.printToFile("[w]", "DIAGMON_SDK" + appLogData.mMessagePrefix + str);
            StringBuilder sb = new StringBuilder("DIAGMON_SDK");
            sb.append(appLogData.mMessagePrefix);
            Log.w(sb.toString(), str);
        } catch (Exception e) {
            Log.e("DIAGMON_SDK", e.getMessage());
        }
    }
}
