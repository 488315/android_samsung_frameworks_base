package com.samsung.android.feature;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemCarrierFeature {
    private static final String DEBUG_LEVEL_HIGH = "0x4948";
    private static final String LOG_TAG = "SemCarrierFeature";
    private Map<Integer, Integer> mCanonicalId;
    private Map<Integer, Map<String, String>> mDefaultFeatureList;
    private Map<Integer, String> mFeaturePath;
    private Map<Integer, Integer> mFeatureVersion;
    private Map<Integer, Map<String, String>> mLastFeatureList;
    private Map<Integer, String> mLastFeaturePath;
    private Map<Integer, String> mOmcVersion;
    private Map<Integer, Map<String, String>> mSpecificFeatureList;
    static final boolean DEBUG = isDebugEnabled();
    static final boolean TEST = isTestEnabled();

    private static class SemCarrierFeatureHolder {
        private static SemCarrierFeature INSTANCE = new SemCarrierFeature();

        private SemCarrierFeatureHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void createInstance() {
            INSTANCE = new SemCarrierFeature();
        }
    }

    public static SemCarrierFeature getInstance() {
        return SemCarrierFeatureHolder.INSTANCE;
    }

    @Deprecated(forRemoval = true, since = "16.0")
    public static SemCarrierFeature createInstance() {
        SemCarrierFeatureHolder.createInstance();
        return SemCarrierFeatureHolder.INSTANCE;
    }

    private SemCarrierFeature() {
        this.mFeaturePath = new LinkedHashMap();
        this.mLastFeaturePath = new LinkedHashMap();
        this.mFeatureVersion = new LinkedHashMap();
        this.mOmcVersion = new LinkedHashMap();
        this.mCanonicalId = new LinkedHashMap();
        this.mDefaultFeatureList = new LinkedHashMap();
        this.mSpecificFeatureList = new LinkedHashMap();
        this.mLastFeatureList = new LinkedHashMap();
        for (int i = 0; i < FeatureUtil.readSimCount(); i++) {
            this.mFeaturePath.put(Integer.valueOf(i), FeatureUtil.getSystemFeaturePath(i, false));
            this.mLastFeaturePath.put(Integer.valueOf(i), FeatureUtil.getSystemFeaturePath(i, true));
            this.mFeatureVersion.put(Integer.valueOf(i), Integer.valueOf(FeatureUtil.getLastFeatureVersion(i)));
            this.mOmcVersion.put(Integer.valueOf(i), FeatureUtil.getOmcVersion());
            loadDefaultFeatures(i);
            loadSpecificFeatures(i);
            loadLastFeatures(i);
        }
    }

    private static boolean isDebugEnabled() {
        return DEBUG_LEVEL_HIGH.equals(SystemProperties.get("ro.boot.debug_level", "")) && !SystemProperties.getBoolean("ro.product_ship", true);
    }

    private static boolean isTestEnabled() {
        return !SystemProperties.getBoolean("ro.product_ship", true);
    }

    private synchronized String get(int i, String str, boolean z) {
        if (DEBUG) {
            Log.d(LOG_TAG, "[get] phoneId:" + i + " key:" + str + " checkLastSim:" + z);
        }
        if (i != 0 && i != 1) {
            Log.d(LOG_TAG, "[get] phoneId should be 0 or 1");
            return null;
        }
        if (isFeatureChanged(i)) {
            String systemFeaturePath = FeatureUtil.getSystemFeaturePath(i, false);
            String systemFeaturePath2 = FeatureUtil.getSystemFeaturePath(i, true);
            int lastFeatureVersion = FeatureUtil.getLastFeatureVersion(i);
            String omcVersion = FeatureUtil.getOmcVersion();
            String str2 = LOG_TAG;
            Log.d(str2, "[get] CarrierFeature is changed : [" + i + "] " + this.mFeatureVersion.get(Integer.valueOf(i)) + " / " + this.mCanonicalId.get(Integer.valueOf(i)) + " -> " + lastFeatureVersion + " / " + FeatureUtil.getLastCanonicalID(i));
            StringBuilder sb = new StringBuilder("[get] last path : ");
            sb.append(this.mFeaturePath.get(Integer.valueOf(i)));
            sb.append("  ");
            sb.append(this.mLastFeaturePath.get(Integer.valueOf(i)));
            Log.d(str2, sb.toString());
            StringBuilder sb2 = new StringBuilder("[get] current path : ");
            sb2.append(systemFeaturePath);
            sb2.append("  ");
            sb2.append(systemFeaturePath2);
            Log.d(str2, sb2.toString());
            this.mFeaturePath.put(Integer.valueOf(i), systemFeaturePath);
            this.mLastFeaturePath.put(Integer.valueOf(i), systemFeaturePath2);
            this.mFeatureVersion.put(Integer.valueOf(i), Integer.valueOf(lastFeatureVersion));
            this.mOmcVersion.put(Integer.valueOf(i), omcVersion);
            loadDefaultFeatures(i);
            loadSpecificFeatures(i);
            loadLastFeatures(i);
        }
        if (z) {
            if (this.mCanonicalId.get(Integer.valueOf(i)).intValue() > FeatureUtil.getDefaultCanonicalID()) {
                return this.mLastFeatureList.get(Integer.valueOf(i)).get(str);
            }
        } else if (this.mCanonicalId.get(Integer.valueOf(i)).intValue() != FeatureUtil.getDefaultCanonicalID() && this.mCanonicalId.get(Integer.valueOf(i)).intValue() == FeatureUtil.getCurrentCanonicalID(i)) {
            return this.mSpecificFeatureList.get(Integer.valueOf(i)).get(str);
        }
        return this.mDefaultFeatureList.get(Integer.valueOf(i)).get(str);
    }

    private boolean isFeatureChanged(int i) {
        return isCurrentFileChanged(i) || isLastFileChanged(i) || isCarrierIdChanged(i);
    }

    private boolean isCurrentFileChanged(int i) {
        return isFeaturePathChanged(i) || isFeatureVersionChanged(i) || isOmcVersionChanged(i);
    }

    private boolean isLastFileChanged(int i) {
        return isLastFeaturePathChanged(i);
    }

    private boolean isCarrierIdChanged(int i) {
        return this.mCanonicalId.get(Integer.valueOf(i)).intValue() != FeatureUtil.getLastCanonicalID(i);
    }

    private boolean isFeaturePathChanged(int i) {
        return !TextUtils.equals(this.mFeaturePath.get(Integer.valueOf(i)), FeatureUtil.getSystemFeaturePath(i, false));
    }

    private boolean isLastFeaturePathChanged(int i) {
        return !TextUtils.equals(this.mLastFeaturePath.get(Integer.valueOf(i)), FeatureUtil.getSystemFeaturePath(i, true));
    }

    private boolean isFeatureVersionChanged(int i) {
        return this.mFeatureVersion.get(Integer.valueOf(i)).intValue() != FeatureUtil.getLastFeatureVersion(i);
    }

    private boolean isOmcVersionChanged(int i) {
        return !TextUtils.equals(this.mOmcVersion.get(Integer.valueOf(i)), FeatureUtil.getOmcVersion());
    }

    private void loadDefaultFeatures(int i) {
        Log.d(LOG_TAG, "update default carrier features");
        SecCarrier carrierFeature = FeatureUtil.getCarrierFeature(i, -1, false);
        if (carrierFeature != null) {
            setSlotDefaultFeatureList(i, carrierFeature);
        }
    }

    private void setSlotDefaultFeatureList(int i, SecCarrier secCarrier) {
        this.mDefaultFeatureList.put(Integer.valueOf(i), secCarrier.getFeature());
    }

    private void loadSpecificFeatures(int i) {
        this.mCanonicalId.put(Integer.valueOf(i), Integer.valueOf(FeatureUtil.getDefaultCanonicalID()));
        int lastCanonicalID = FeatureUtil.getLastCanonicalID(i);
        Log.d(LOG_TAG, "update specific features : " + i + "/" + lastCanonicalID);
        SecCarrier carrierFeature = FeatureUtil.getCarrierFeature(i, lastCanonicalID, false);
        if (carrierFeature != null) {
            setSlotSpecificFeatureList(i, carrierFeature, lastCanonicalID);
        }
    }

    private void setSlotSpecificFeatureList(int i, SecCarrier secCarrier, int i2) {
        this.mCanonicalId.put(Integer.valueOf(i), Integer.valueOf(i2));
        this.mSpecificFeatureList.put(Integer.valueOf(i), secCarrier.getFeature());
    }

    private void loadLastFeatures(int i) {
        int lastCanonicalID = FeatureUtil.getLastCanonicalID(i);
        Log.d(LOG_TAG, "update last features : " + i + "/" + lastCanonicalID);
        SecCarrier carrierFeature = FeatureUtil.getCarrierFeature(i, lastCanonicalID, true);
        if (carrierFeature != null) {
            setSlotLastFeatureList(i, carrierFeature);
        }
    }

    private void setSlotLastFeatureList(int i, SecCarrier secCarrier) {
        this.mLastFeatureList.put(Integer.valueOf(i), secCarrier.getFeature());
    }

    public int getCarrierId(int i, boolean z) {
        if (z) {
            return FeatureUtil.getLastCanonicalID(i);
        }
        return FeatureUtil.getCurrentCanonicalID(i);
    }

    public boolean getBoolean(int i, String str, boolean z, boolean z2) {
        try {
            String str2 = get(i, str, z2);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getBoolean] key:" + str + " value:" + str2);
            }
            if (str2 != null) {
                return Boolean.parseBoolean(str2);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getBoolean] Exception" + e);
                e.printStackTrace();
            }
        }
        return z;
    }

    public boolean[] getBooleanArray(int i, String str, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getBooleanArray] key:" + str + " value:" + str2);
            }
            if (str2 == null) {
                return null;
            }
            String[] strArrSplit = str2.split(",");
            boolean[] zArr = new boolean[strArrSplit.length];
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                zArr[i2] = Boolean.parseBoolean(strArrSplit[i2].trim());
            }
            return zArr;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getBooleanArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public int getInt(int i, String str, int i2, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getInt] key:" + str + " value:" + str2);
            }
            if (str2 != null) {
                return Integer.parseInt(str2);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getInt] Exception" + e);
                e.printStackTrace();
            }
        }
        return i2;
    }

    public int[] getIntArray(int i, String str, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getIntArray] key:" + str + " value:" + str2);
            }
            if (str2 == null) {
                return null;
            }
            String[] strArrSplit = str2.split(",");
            int[] iArr = new int[strArrSplit.length];
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                iArr[i2] = Integer.parseInt(strArrSplit[i2].trim());
            }
            return iArr;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getIntArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public long getLong(int i, String str, long j, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getLong] key:" + str + " value:" + str2);
            }
            if (str2 != null) {
                return Long.parseLong(str2);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getLong] Exception" + e);
                e.printStackTrace();
            }
        }
        return j;
    }

    public long[] getLongArray(int i, String str, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getLongArray] key:" + str + " value:" + str2);
            }
            if (str2 == null) {
                return null;
            }
            String[] strArrSplit = str2.split(",");
            long[] jArr = new long[strArrSplit.length];
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                jArr[i2] = Long.parseLong(strArrSplit[i2].trim());
            }
            return jArr;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getLongArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public double getDouble(int i, String str, double d, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getDouble] key:" + str + " value:" + str2);
            }
            if (str2 != null) {
                return Double.parseDouble(str2);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getDouble] Exception" + e);
                e.printStackTrace();
            }
        }
        return d;
    }

    public double[] getDoubleArray(int i, String str, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getDoubleArray] key:" + str + " value:" + str2);
            }
            if (str2 == null) {
                return null;
            }
            String[] strArrSplit = str2.split(",");
            double[] dArr = new double[strArrSplit.length];
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                dArr[i2] = Double.parseDouble(strArrSplit[i2].trim());
            }
            return dArr;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getDoubleArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getString(int i, String str, String str2, boolean z) {
        String str3;
        try {
            str3 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getString] key:" + str + " value:" + str3);
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getString] Exception" + e);
                e.printStackTrace();
            }
        }
        return str3 != null ? str3 : str2;
    }

    public String[] getStringArray(int i, String str, boolean z) {
        try {
            String str2 = get(i, str, z);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getStringArray] key:" + str + " value:" + str2);
            }
            if (str2 == null) {
                return null;
            }
            return str2.split(",");
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getStringArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }
}
