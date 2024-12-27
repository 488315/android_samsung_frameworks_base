package com.samsung.android.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;

public class SemShareLogging {
    private static final boolean DEBUG = false;
    private static final String TAG = "SemShareLogging";
    private final Context mContext;
    private boolean mHasDMA;

    public SemShareLogging(Context context) {
        this.mHasDMA = false;
        this.mContext = context;
        this.mHasDMA = hasDMA();
    }

    private boolean hasSurveyPermission() {
        return this.mContext.checkCallingOrSelfPermission(SemShareConstants.SURVERY_PERMISSION)
                == 0;
    }

    private boolean hasDMA() {
        try {
            this.mContext
                    .getPackageManager()
                    .getPackageInfo(SemShareConstants.DMA_SURVEY_DMA_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "isSupportDMALogging: ", e);
            return false;
        } catch (Exception e2) {
            Log.w(TAG, "isSupportDMALogging: ", e2);
            return false;
        }
    }

    private static Bundle getFeatureBundle(String feature) {
        Bundle bundle = new Bundle();
        bundle.putString(
                SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID,
                SemShareConstants.DMA_SURVEY_DETAIL_TRACKING_ID);
        bundle.putString("feature", feature);
        bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
        bundle.putString(
                SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE,
                SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE_VALUE);
        return bundle;
    }

    private static Intent getSurveyIntent(Bundle bundle) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(SemShareConstants.DMA_SURVEY_DMA_ACTION);
        broadcastIntent.putExtras(bundle);
        broadcastIntent.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
        return broadcastIntent;
    }

    private void sendLog(Bundle bundle) {
        Intent broadcastIntent = getSurveyIntent(bundle);
        this.mContext.sendBroadcast(broadcastIntent);
    }

    private void insertLogWithDimension(String feature, HashMap<String, String> dimension) {
        if (!this.mHasDMA || !hasSurveyPermission()) {
            return;
        }
        Bundle bundle = getFeatureBundle(feature);
        if (dimension != null) {
            bundle.putSerializable(SemShareConstants.SURVEY_CONTENT_DIMENSION, dimension);
        }
        sendLog(bundle);
    }

    public void semInsertStartSelectLog(
            String caller, String callee, String mimeType, String action, boolean isAlways) {
        String str;
        if (!this.mHasDMA || !hasSurveyPermission()) {
            return;
        }
        HashMap<String, String> dimension = new HashMap<>();
        dimension.put("caller", caller);
        dimension.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_CALLEE, callee);
        dimension.put("mime", mimeType);
        dimension.put(
                SemShareConstants.DMA_SURVEY_KEY_RESOLVER_MIME_CALLEE,
                String.format("%s_%s", mimeType, callee));
        dimension.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, action);
        if (isAlways) {
            str = "0";
        } else {
            str = "1";
        }
        dimension.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ONCE_ALWAYS, str);
        insertLogWithDimension(SemShareConstants.DMA_SURVEY_FEATURE_RESOLVER, dimension);
    }
}
