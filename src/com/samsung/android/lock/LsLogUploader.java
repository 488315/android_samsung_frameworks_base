package com.samsung.android.lock;

import android.content.Context;
import android.content.Intent;
import android.hardware.gnss.GnssSignalType;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.share.SemShareConstants;

/* loaded from: classes6.dex */
public class LsLogUploader {
    private static final String AUTHORITY = "com.sec.android.log.sp4xkeu9ef";
    private static final String BUILD_ID;
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final String EVENT_ID;
    private static final String REPORT_ERROR_INTENT = "com.sec.android.diagmonagent.intent.REPORT_ERROR_V2";
    private static final String SERVICE_ID = "sp4xkeu9ef";
    private static final String TAG = "LsLogUploader";
    private static final String UPLOAD_MO = "uploadMO";
    private static Context mContext;
    private static LsLogType mLastUploadType;

    static {
        String str = Build.VERSION.INCREMENTAL;
        BUILD_ID = str;
        EVENT_ID = str;
    }

    public static void tryUpload(Context context) {
        mContext = context;
        LsLogType lsLogType = mLastUploadType;
        if (lsLogType != null) {
            LsLogFile.upload(lsLogType);
        }
    }

    public static boolean canUpload(LsLogType lsLogType) {
        if (mContext != null) {
            return true;
        }
        mLastUploadType = lsLogType;
        return false;
    }

    public static void sendToDiagmon(LsLogType lsLogType, String str) {
        Context context = mContext;
        if (context == null) {
            Log.w(TAG, "sendToDiagmon failed. context is null. " + lsLogType + " is reserved!");
            mLastUploadType = lsLogType;
            return;
        }
        if (Settings.System.getInt(context.getContentResolver(), "samsung_errorlog_agree", 0) != 1) {
            Log.w(TAG, "sendToDiagmon failed. errorlog_agree is not true!!");
            mLastUploadType = null;
            return;
        }
        if (!lsLogType.containsProperty(2)) {
            Log.w(TAG, "sendToDiagmon failed. Cannot upload this log : " + lsLogType);
            mLastUploadType = null;
            return;
        }
        if (!LsUtil.isShipBuild() && !LsUtil.isDevBuild()) {
            Log.w(TAG, "sendToDiagmon failed. Can upload only ship or dev!");
            mLastUploadType = null;
            return;
        }
        Log.i(TAG, "send broadcast intent to diagmon : " + str);
        Intent intent = new Intent(REPORT_ERROR_INTENT);
        Bundle bundle = new Bundle();
        intent.addFlags(32);
        try {
            bundle.putBundle("DiagMon", new Bundle());
            bundle.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putString("ServiceID", SERVICE_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("Ext", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ClientV", BUILD_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("UiMode", "0");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ResultCode", lsLogType.getErrorCode());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("EventID", EVENT_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("Description", lsLogType.getErrorCode());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("IntentOnly", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("IntentOnlyMode", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("Agree", GnssSignalType.CODE_TYPE_D);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("LogPath", str);
            intent.putExtra(UPLOAD_MO, bundle);
            intent.setFlags(32);
            intent.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
            mContext.sendBroadcast(intent);
            mLastUploadType = null;
        } catch (Exception e) {
            Log.e(TAG, "Exception while sending a bug report.", e);
        }
    }
}
