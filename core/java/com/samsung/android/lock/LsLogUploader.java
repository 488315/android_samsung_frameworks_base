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
    private static final String REPORT_ERROR_INTENT =
            "com.sec.android.diagmonagent.intent.REPORT_ERROR_V2";
    private static final String SERVICE_ID = "sp4xkeu9ef";
    private static final String TAG = "LsLogUploader";
    private static final String UPLOAD_MO = "uploadMO";
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static Context mContext = null;
    private static LsLogType mLastUploadType = null;
    private static final String BUILD_ID = Build.VERSION.INCREMENTAL;
    private static final String EVENT_ID = BUILD_ID;

    public static void tryUpload(Context context) {
        mContext = context;
        if (mLastUploadType != null) {
            LsLogFile.upload(mLastUploadType);
        }
    }

    public static boolean canUpload(LsLogType type) {
        if (mContext == null) {
            mLastUploadType = type;
            return false;
        }
        return true;
    }

    public static void sendToDiagmon(LsLogType type, String file) {
        if (mContext == null) {
            Log.w(TAG, "sendToDiagmon failed. context is null. " + type + " is reserved!");
            mLastUploadType = type;
            return;
        }
        int errorlog_agree =
                Settings.System.getInt(mContext.getContentResolver(), "samsung_errorlog_agree", 0);
        if (errorlog_agree != 1) {
            Log.w(TAG, "sendToDiagmon failed. errorlog_agree is not true!!");
            mLastUploadType = null;
            return;
        }
        if (!type.containsProperty(2)) {
            Log.w(TAG, "sendToDiagmon failed. Cannot upload this log : " + type);
            mLastUploadType = null;
            return;
        }
        if (!LsUtil.isShipBuild() && !LsUtil.isDevBuild()) {
            Log.w(TAG, "sendToDiagmon failed. Can upload only ship or dev!");
            mLastUploadType = null;
            return;
        }
        Log.i(TAG, "send broadcast intent to diagmon : " + file);
        Intent i = new Intent(REPORT_ERROR_INTENT);
        Bundle uploadMO = new Bundle();
        i.addFlags(32);
        try {
            uploadMO.putBundle("DiagMon", new Bundle());
            uploadMO.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .putString("ServiceID", SERVICE_ID);
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .putBundle("Ext", new Bundle());
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("ClientV", BUILD_ID);
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("UiMode", "0");
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("ResultCode", type.getErrorCode());
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("WifiOnlyFeature", "1");
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("EventID", EVENT_ID);
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("Ext")
                    .putString("Description", type.getErrorCode());
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .putBundle("IntentOnly", new Bundle());
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("IntentOnly")
                    .putString("IntentOnlyMode", "1");
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("IntentOnly")
                    .putString("Agree", GnssSignalType.CODE_TYPE_D);
            uploadMO.getBundle("DiagMon")
                    .getBundle("CFailLogUpload")
                    .getBundle("IntentOnly")
                    .putString("LogPath", file);
            i.putExtra(UPLOAD_MO, uploadMO);
            i.setFlags(32);
            i.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
            mContext.sendBroadcast(i);
            mLastUploadType = null;
        } catch (Exception e) {
            Log.e(TAG, "Exception while sending a bug report.", e);
        }
    }
}
