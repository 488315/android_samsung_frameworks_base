package com.samsung.android.share;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.PackageInfo;
import android.p009os.Bundle;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class SemShareLogging {
    private static final boolean DEBUG = false;
    private static final String TAG = "SemShareLogging";
    private Context mContext;
    private Intent mIntent;
    private boolean mIsSupportDMA;

    public SemShareLogging(Context context, Intent intent) {
        this.mIsSupportDMA = false;
        this.mContext = context;
        this.mIntent = intent;
        this.mIsSupportDMA = checkDMASupport();
    }

    public boolean isSupportDMA() {
        return this.mIsSupportDMA;
    }

    private boolean checkSurveyCondition(Intent intent, String feature) {
        String action = intent.getAction();
        return Intent.ACTION_SEND.equals(action) || Intent.ACTION_SEND_MULTIPLE.equals(action) || SemShareConstants.DMA_SURVEY_FEATURE_RESOLVER_ONCE_ALWAYS.equals(feature) || SemShareConstants.DMA_SURVEY_FEATURE_RESOLVER_PICKED_APP.equals(feature);
    }

    private boolean checkDMASupport() {
        try {
            PackageInfo pkgInfo = this.mContext.getPackageManager().getPackageInfo(SemShareConstants.DMA_SURVEY_DMA_PACKAGE, 0);
            return pkgInfo.versionCode >= 540000000;
        } catch (Exception e) {
            Log.m103w(TAG, "isSupportDMALogging: ", e);
            return false;
        }
    }

    public void insertLog(String feature, String extra) {
        if (checkSurveyCondition(this.mIntent, feature)) {
            if (this.mContext.checkCallingOrSelfPermission(SemShareConstants.SURVERY_PERMISSION) == 0) {
                Intent broadcastIntent = new Intent();
                if (isSupportDMA()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SemShareConstants.DMA_SURVEY_DETAIL_TRACKING_ID);
                    bundle.putString("feature", feature);
                    if (extra != null) {
                        bundle.putString(SemShareConstants.SURVEY_CONTENT_EXTRA, extra);
                    }
                    bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
                    bundle.putString(SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE, SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE_VALUE);
                    broadcastIntent.setAction(SemShareConstants.DMA_SURVEY_DMA_ACTION);
                    broadcastIntent.putExtras(bundle);
                    broadcastIntent.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(SemShareConstants.SURVEY_CONTENT_APPID, SemShareConstants.SURVEY_APP_NAME);
                    cv.put("feature", feature);
                    if (extra != null) {
                        cv.put(SemShareConstants.SURVEY_CONTENT_EXTRA, extra);
                    }
                    broadcastIntent.setAction(SemShareConstants.SURVERY_ACTION);
                    broadcastIntent.putExtra("data", cv);
                    broadcastIntent.setPackage(SemShareConstants.SURVEY_TARGET_PACKAGE);
                }
                this.mContext.sendBroadcast(broadcastIntent);
                return;
            }
            Log.m102w(TAG, "insertLog : no permission of survey");
        }
    }

    public void insertLogWithDimension(String feature, HashMap<String, String> dimension) {
        if (checkSurveyCondition(this.mIntent, feature)) {
            if (this.mContext.checkCallingOrSelfPermission(SemShareConstants.SURVERY_PERMISSION) == 0) {
                Intent broadcastIntent = new Intent();
                if (isSupportDMA()) {
                    Bundle bundle = new Bundle();
                    bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SemShareConstants.DMA_SURVEY_DETAIL_TRACKING_ID);
                    bundle.putString("feature", feature);
                    if (dimension != null) {
                        bundle.putSerializable(SemShareConstants.SURVEY_CONTENT_DIMENSION, dimension);
                    }
                    bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
                    bundle.putString(SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE, SemShareConstants.SURVERY_EXTRA_OWN_PACKAGE_VALUE);
                    broadcastIntent.setAction(SemShareConstants.DMA_SURVEY_DMA_ACTION);
                    broadcastIntent.putExtras(bundle);
                    broadcastIntent.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
                }
                this.mContext.sendBroadcast(broadcastIntent);
                return;
            }
            Log.m102w(TAG, "insertLog : no permission of survey");
        }
    }
}
