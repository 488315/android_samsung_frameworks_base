package vendor.samsung.frameworks.codecsolution;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public class Logging {
    private static final String ACTION_DMA_LOGGING = "com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY";
    private static final String ACTION_GENERAL_MULTI_LOGGING = "com.samsung.android.providers.context.log.action.USE_MULTI_APP_FEATURE_SURVEY";
    private static final String ACTION_GENERAL_SINGLE_LOGGING = "com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY";
    private static final String ACTION_STATUS_MULTI_LOGGING = "com.samsung.android.providers.context.log.action.REPORT_MULTI_APP_STATUS_SURVEY";
    private static final String ACTION_STATUS_SINGLE_LOGGING = "com.samsung.android.providers.context.log.action.REPORT_APP_STATUS_SURVEY";
    private static final String APP_ID = "com.samsung.android.codecsolution";
    private static final String CONTEXT_PACKAGE_NAME = "com.samsung.android.providers.context";
    private static final String DMA_PACKAGE_NAME = "com.sec.android.diagmonagent";
    private static final int DMA_SUPPORT_VERSION = 540000000;
    private static final String PERMISSION_SURVEY = "com.samsung.android.providrs.context.permission.WRITE_USE_APP_FEATURE_SURVEY";
    private static final String SA_TRACKING_ID = "4I1-399-549798";
    private static final String TAG = "CodecSolution_Logging";
    static final Boolean sEnableSurveyFeature = Boolean.valueOf(SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE"));
    private Context mContext;
    private Boolean mIsSupportDMA;

    public Logging(Context context) {
        this.mIsSupportDMA = Boolean.FALSE;
        this.mContext = context;
        this.mIsSupportDMA = Boolean.valueOf(checkVersionOfDMA(context));
    }

    public static boolean checkVersionOfDMA(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(DMA_PACKAGE_NAME, 0);
            Log.d(TAG, "dma pkg: " + packageInfo.versionCode);
            return packageInfo.versionCode >= DMA_SUPPORT_VERSION;
        } catch (Exception unused) {
            return false;
        }
    }

    public void insertContextLog(String str, String str2, long j) {
        Intent intent = new Intent();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", APP_ID);
        contentValues.put("feature", str);
        if (str2 != null) {
            contentValues.put("extra", str2);
        }
        if (j > -1) {
            contentValues.put("value", Long.valueOf(j));
        }
        intent.setAction(ACTION_GENERAL_SINGLE_LOGGING);
        intent.setPackage(CONTEXT_PACKAGE_NAME);
        intent.putExtra("data", contentValues);
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void insertDiagmonLog(String str, String str2, long j) {
        if (this.mContext.checkCallingOrSelfPermission(PERMISSION_SURVEY) != 0) {
            Log.d(TAG, "no permission");
            return;
        }
        Log.d(TAG, "permission granted");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("tracking_id", SA_TRACKING_ID);
        bundle.putString("feature", str);
        bundle.putString("is_summary", "true");
        if (str2 != null) {
            bundle.putString("extra", str2);
        }
        if (j > -1) {
            bundle.putLong("value", j);
        }
        bundle.putString("type", "ev");
        intent.setAction(ACTION_DMA_LOGGING);
        intent.putExtras(bundle);
        intent.setPackage(DMA_PACKAGE_NAME);
        try {
            this.mContext.sendBroadcast(intent);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void insertLog(String str, String str2) {
        insertLog(str, str2, -1L);
    }

    public void insertLog(String str, String str2, long j) {
        Log.d(TAG, "dma : " + this.mIsSupportDMA + ", feature: " + str + ", extra: " + str2 + ", value: " + j);
        if (sEnableSurveyFeature.booleanValue()) {
            if (this.mIsSupportDMA.booleanValue()) {
                Log.d(TAG, "insert diagmon log");
                insertDiagmonLog(str, str2, j);
            } else {
                Log.d(TAG, "insert context log");
                insertContextLog(str, str2, j);
            }
        }
    }
}
