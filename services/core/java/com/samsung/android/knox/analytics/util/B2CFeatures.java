package com.samsung.android.knox.analytics.util;

import android.content.ContentValues;
import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;

import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.Iterator;
import java.util.List;

public final class B2CFeatures {
    public static final String ACTION_B2C_ACTIVATION =
            "com.samsung.android.knox.analytics.intent.action.B2C_ACTIVATION";
    public static final String EXTRA_B2C_ACTIVATION_STATUS =
            "com.samsung.android.knox.analytics.intent.extra.B2C_ACTIVATION_STATUS";
    public static final String EXTRA_B2C_FEATURE =
            "com.samsung.android.knox.analytics.intent.extra.B2C_FEATURE";
    public static final String EXTRA_B2C_PACKAGE_NAME =
            "com.samsung.android.knox.analytics.intent.extra.B2C_PACKAGE_NAME";
    public static final String TAG = "[KnoxAnalytics] B2CFeatures";

    public static void addOrRemoveB2CFeature(Context context, String str, String str2, boolean z) {
        String str3 = TAG;
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "addOrRemoveB2CFeature() - package ",
                        str,
                        " feature ",
                        str2,
                        " shouldAdd? ");
        m.append(z);
        Log.d(str3, m.toString());
        if (z && str2 != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("packageName", str);
            contentValues.put("feature_name", str2);
            context.getContentResolver().insert(Contract.B2CFeatures.CONTENT_URI, contentValues);
            return;
        }
        if (z) {
            return;
        }
        String b2CFeatureByPackage =
                KnoxAnalyticsQueryResolver.getB2CFeatureByPackage(context, str);
        context.getContentResolver()
                .delete(Contract.B2CFeatures.CONTENT_URI, "packageName", new String[] {str});
        if (b2CFeatureByPackage != null) {
            context.getContentResolver()
                    .delete(
                            Contract.FeaturesWhitelist.CONTENT_URI,
                            LauncherConfigurationInternal.KEY_FEATURE_INT,
                            new String[] {b2CFeatureByPackage});
        }
    }

    public static void applyWhitelistForB2CFeatures(Context context) {
        Log.d(TAG, "applyWhitelistForB2CFeatures()");
        List b2CFeaturesList = KnoxAnalyticsQueryResolver.getB2CFeaturesList(context);
        if (b2CFeaturesList.isEmpty()) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        Iterator it = b2CFeaturesList.iterator();
        while (it.hasNext()) {
            contentValues.put((String) it.next(), (Integer) 0);
        }
        contentValues.put("KNOX_ANALYTICS", (Integer) 0);
        context.getContentResolver().insert(Contract.FeaturesWhitelist.CONTENT_URI, contentValues);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("*", (Integer) 0);
        context.getContentResolver().insert(Contract.FeaturesBlacklist.CONTENT_URI, contentValues2);
    }

    public static boolean getB2CActivationStatus(Context context) {
        String str = TAG;
        Log.d(str, "getB2CActivationStatus()");
        boolean z = !KnoxAnalyticsQueryResolver.getB2CFeaturePackageList(context).isEmpty();
        Log.d(str, "getB2CActivationStatus() - status " + z);
        return z;
    }

    public static void removeB2CFeaturesFromWhitelist(Context context) {
        Log.d(TAG, "removeB2CFeaturesFromWhitelist()");
        List b2CFeaturesList = KnoxAnalyticsQueryResolver.getB2CFeaturesList(context);
        if (!b2CFeaturesList.isEmpty()) {
            b2CFeaturesList.add("KNOX_ANALYTICS");
            context.getContentResolver()
                    .delete(
                            Contract.FeaturesWhitelist.CONTENT_URI,
                            LauncherConfigurationInternal.KEY_FEATURE_INT,
                            (String[]) b2CFeaturesList.toArray(new String[0]));
        }
        try {
            context.getContentResolver()
                    .delete(
                            Contract.FeaturesBlacklist.CONTENT_URI,
                            LauncherConfigurationInternal.KEY_FEATURE_INT,
                            new String[] {"*"});
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Failed to delete from FeaturesBlacklist", e);
        }
    }
}
