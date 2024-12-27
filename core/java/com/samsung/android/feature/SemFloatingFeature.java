package com.samsung.android.feature;

import android.os.SystemProperties;
import android.util.Log;

import java.util.Hashtable;

/* loaded from: classes6.dex */
public final class SemFloatingFeature implements IFloatingFeature {
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final int DEFAULT_INT_VALUE = -1;
    private static final String DEFAULT_STRING_VALUE = "";
    private static final String FEATURE_XML = "/system/etc/floating_feature.xml";
    private static final boolean LOG_ENABLED;
    private static final String TAG = "SemFloatingFeature";
    private static final SemFloatingFeature sInstance;
    private final Hashtable<String, String> mFeatureList = new Hashtable<>();

    static {
        boolean productShip = true;
        try {
            productShip = Boolean.parseBoolean(SystemProperties.get("ro.product_ship"));
        } catch (Exception e) {
        }
        LOG_ENABLED = !productShip;
        sInstance = new SemFloatingFeature();
    }

    private static void logw(Object message) {
        if (LOG_ENABLED) {
            Log.w(TAG, message.toString());
        }
    }

    private static void loge(Object message) {
        if (LOG_ENABLED) {
            Log.e(TAG, message.toString());
        }
    }

    public static SemFloatingFeature getInstance() {
        return sInstance;
    }

    private SemFloatingFeature() {
        try {
            loadFeatureFile();
        } catch (Exception e) {
            loge(e);
        }
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public boolean getBoolean(String tag) {
        if (tag == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return false;
        }
        return Boolean.parseBoolean(original);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public boolean getBoolean(String tag, boolean defaultValue) {
        logw(
                "You called API `boolean getBoolean(String tag, String defaultValue)` with feature"
                    + " ["
                        + tag
                        + "].It has been deprecated after android Q. Instead, please Use `boolean"
                        + " getBoolean(String tag)`");
        if (tag == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? defaultValue : Boolean.parseBoolean(original);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public String getString(String tag) {
        if (tag == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? "" : original;
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public String getString(String tag, String defaultValue) {
        logw(
                "You called API `String getString(String tag, String defaultValue)` with feature ["
                        + tag
                        + "].It has been deprecated after android Q. Instead, please Use `String"
                        + " getString(String tag)`");
        if (tag == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? defaultValue : original;
    }

    public int getInteger(String tag) {
        return getInt(tag);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public int getInt(String tag) {
        if (tag == null) {
            loge("The first argument of getInt() cannot be null.");
            return -1;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return -1;
        }
        try {
            return Integer.parseInt(original);
        } catch (Exception e) {
            loge(String.format("[%s] cannot be parsed to Integer value", original));
            return -1;
        }
    }

    @Deprecated
    public int getInteger(String tag, int defaultValue) {
        return getInt(tag, defaultValue);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public int getInt(String tag, int defaultValue) {
        logw(
                "You called API `int getInt(String tag, int defaultValue)` with feature ["
                        + tag
                        + "].It has been deprecated after android Q. Instead, please Use `int"
                        + " getInt(String tag)`");
        if (tag == null) {
            loge("The first argument of getInt() cannot be null.");
            return defaultValue;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(original);
        } catch (Exception e) {
            loge(String.format("[%s] cannot be parsed to Integer value", original));
            return defaultValue;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:11|(2:44|45)(2:13|(2:15|(2:18|(6:20|21|23|24|25|26)(2:30|31))))|35|36|38|39|40|26) */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0086, code lost:

       r8 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0087, code lost:

       loge(r8.toString());
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadFeatureFile() {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.feature.SemFloatingFeature.loadFeatureFile():void");
    }
}
