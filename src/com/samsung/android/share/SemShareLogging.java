package com.samsung.android.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes6.dex */
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
        return this.mContext.checkCallingOrSelfPermission(SemShareConstants.SURVERY_PERMISSION) == 0;
    }

    private boolean hasDMA() {
        try {
            this.mContext.getPackageManager().getPackageInfo(SemShareConstants.DMA_SURVEY_DMA_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "isSupportDMALogging: ", e);
            return false;
        } catch (Exception e2) {
            Log.w(TAG, "isSupportDMALogging: ", e2);
            return false;
        }
    }

    private static Bundle getFeatureBundle(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(SemShareConstants.DMA_SURVEY_FEATURE_TRACKING_ID, SemShareConstants.DMA_SURVEY_DETAIL_TRACKING_ID);
        bundle.putString("feature", str);
        bundle.putString("type", SemShareConstants.SURVEY_CONTENT_TYPE_VALUE);
        bundle.putString(SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE, SemShareConstants.SURVEY_EXTRA_OWN_PACKAGE_VALUE);
        return bundle;
    }

    private static Intent getSurveyIntent(Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(SemShareConstants.DMA_SURVEY_DMA_ACTION);
        intent.putExtras(bundle);
        intent.setPackage(SemShareConstants.DMA_SURVEY_DMA_PACKAGE);
        return intent;
    }

    private void sendLog(Bundle bundle) {
        this.mContext.sendBroadcast(getSurveyIntent(bundle));
    }

    private void insertLogWithDimension(String str, HashMap<String, String> hashMap) {
        if (this.mHasDMA && hasSurveyPermission()) {
            Bundle featureBundle = getFeatureBundle(str);
            if (hashMap != null) {
                featureBundle.putSerializable(SemShareConstants.SURVEY_CONTENT_DIMENSION, hashMap);
            }
            sendLog(featureBundle);
        }
    }

    public void semInsertStartSelectLog(String str, String str2, String str3, String str4, boolean z) {
        String str5;
        if (this.mHasDMA && hasSurveyPermission()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("caller", str);
            hashMap.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_CALLEE, str2);
            hashMap.put("mime", str3);
            hashMap.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_MIME_CALLEE, String.format("%s_%s", str3, str2));
            hashMap.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ACTION, str4);
            if (z) {
                str5 = "0";
            } else {
                str5 = "1";
            }
            hashMap.put(SemShareConstants.DMA_SURVEY_KEY_RESOLVER_ONCE_ALWAYS, str5);
            insertLogWithDimension(SemShareConstants.DMA_SURVEY_FEATURE_RESOLVER, hashMap);
        }
    }
}
