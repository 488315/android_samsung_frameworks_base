package com.samsung.android.feature;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

public class SemCarrierFeature {
    private static final String DEBUG_LEVEL_HIGH = "0x4948";
    private Map<Integer, Integer> mCanonicalId;
    private Map<Integer, Map<String, String>> mDefaultFeatureList;
    private Map<Integer, Integer> mFeatureVersion;
    private Map<Integer, Map<String, String>> mLastFeatureList;
    private Map<Integer, String> mLastMatchedCode;
    private Map<Integer, String> mMatchedCode;
    private Map<Integer, Map<String, String>> mSpecificFeatureList;
    private static final String LOG_TAG = SemCarrierFeature.class.getSimpleName();
    static final boolean DEBUG = isDebugEnabled();
    static final boolean TEST = isTestEnabled();

    private static class SemCarrierFeatureHolder {
        private static SemCarrierFeature INSTANCE = new SemCarrierFeature();

        private SemCarrierFeatureHolder() {}

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
        this.mMatchedCode = new LinkedHashMap();
        this.mLastMatchedCode = new LinkedHashMap();
        this.mFeatureVersion = new LinkedHashMap();
        this.mCanonicalId = new LinkedHashMap();
        this.mDefaultFeatureList = new LinkedHashMap();
        this.mSpecificFeatureList = new LinkedHashMap();
        this.mLastFeatureList = new LinkedHashMap();
        for (int phoneId = 0; phoneId < FeatureUtil.readSimCount(); phoneId++) {
            this.mMatchedCode.put(
                    Integer.valueOf(phoneId), FeatureUtil.getMatchedCode(phoneId, false));
            this.mLastMatchedCode.put(
                    Integer.valueOf(phoneId), FeatureUtil.getMatchedCode(phoneId, true));
            this.mFeatureVersion.put(
                    Integer.valueOf(phoneId),
                    Integer.valueOf(FeatureUtil.getLastFeatureVersion(phoneId)));
            loadDefaultFeatures(phoneId);
            loadSpecificFeatures(phoneId);
            loadLastFeatures(phoneId);
        }
    }

    private static boolean isDebugEnabled() {
        String debugLevel = SystemProperties.get("ro.boot.debug_level", "");
        boolean isShipBuild = SystemProperties.getBoolean("ro.product_ship", true);
        return DEBUG_LEVEL_HIGH.equals(debugLevel) && !isShipBuild;
    }

    private static boolean isTestEnabled() {
        return !SystemProperties.getBoolean("ro.product_ship", true);
    }

    private synchronized String get(int phoneId, String key, boolean checkLastSim) {
        if (DEBUG) {
            Log.d(
                    LOG_TAG,
                    "[get] phoneId:" + phoneId + " key:" + key + " checkLastSim:" + checkLastSim);
        }
        if (phoneId != 0 && phoneId != 1) {
            Log.d(LOG_TAG, "[get] phoneId should be 0 or 1");
            return null;
        }
        if (isFeatureChanged(phoneId)) {
            String currentMatchedCode = FeatureUtil.getMatchedCode(phoneId, false);
            String lastMatchedCode = FeatureUtil.getMatchedCode(phoneId, true);
            int currentFeatureVersion = FeatureUtil.getLastFeatureVersion(phoneId);
            Log.d(
                    LOG_TAG,
                    "[get] CarrierFeature is changed : ["
                            + phoneId
                            + "] "
                            + this.mMatchedCode.get(Integer.valueOf(phoneId))
                            + " / "
                            + this.mLastMatchedCode.get(Integer.valueOf(phoneId))
                            + " / "
                            + this.mFeatureVersion.get(Integer.valueOf(phoneId))
                            + " / "
                            + this.mCanonicalId.get(Integer.valueOf(phoneId))
                            + " -> "
                            + currentMatchedCode
                            + " / "
                            + lastMatchedCode
                            + " / "
                            + currentFeatureVersion
                            + " / "
                            + FeatureUtil.getLastCanonicalID(phoneId));
            this.mMatchedCode.put(Integer.valueOf(phoneId), currentMatchedCode);
            this.mLastMatchedCode.put(Integer.valueOf(phoneId), lastMatchedCode);
            this.mFeatureVersion.put(
                    Integer.valueOf(phoneId), Integer.valueOf(currentFeatureVersion));
            loadDefaultFeatures(phoneId);
            loadSpecificFeatures(phoneId);
            loadLastFeatures(phoneId);
        }
        if (checkLastSim) {
            if (this.mCanonicalId.get(Integer.valueOf(phoneId)).intValue()
                    > FeatureUtil.getDefaultCanonicalID()) {
                return this.mLastFeatureList.get(Integer.valueOf(phoneId)).get(key);
            }
        } else if (this.mCanonicalId.get(Integer.valueOf(phoneId)).intValue()
                        != FeatureUtil.getDefaultCanonicalID()
                && this.mCanonicalId.get(Integer.valueOf(phoneId)).intValue()
                        == FeatureUtil.getCurrentCanonicalID(phoneId)) {
            return this.mSpecificFeatureList.get(Integer.valueOf(phoneId)).get(key);
        }
        return this.mDefaultFeatureList.get(Integer.valueOf(phoneId)).get(key);
    }

    private boolean isFeatureChanged(int phoneId) {
        return isCurrentFileChanged(phoneId)
                || isLastFileChanged(phoneId)
                || isCarrierIdChanged(phoneId);
    }

    private boolean isCurrentFileChanged(int phoneId) {
        return isMatchedCodeChanged(phoneId) || isFeatureVersionChanged(phoneId);
    }

    private boolean isLastFileChanged(int phoneId) {
        return isLastMatchedCodeChanged(phoneId);
    }

    private boolean isCarrierIdChanged(int phoneId) {
        return this.mCanonicalId.get(Integer.valueOf(phoneId)).intValue()
                != FeatureUtil.getLastCanonicalID(phoneId);
    }

    private boolean isMatchedCodeChanged(int phoneId) {
        return !TextUtils.equals(
                this.mMatchedCode.get(Integer.valueOf(phoneId)),
                FeatureUtil.getMatchedCode(phoneId, false));
    }

    private boolean isLastMatchedCodeChanged(int phoneId) {
        return !TextUtils.equals(
                this.mLastMatchedCode.get(Integer.valueOf(phoneId)),
                FeatureUtil.getMatchedCode(phoneId, true));
    }

    private boolean isFeatureVersionChanged(int phoneId) {
        return this.mFeatureVersion.get(Integer.valueOf(phoneId)).intValue()
                != FeatureUtil.getLastFeatureVersion(phoneId);
    }

    private void loadDefaultFeatures(int phoneId) {
        Log.d(LOG_TAG, "update default carrier features");
        SecCarrier last = FeatureUtil.getCarrierFeature(phoneId, -1, false);
        if (last != null) {
            setSlotDefaultFeatureList(phoneId, last);
        }
    }

    private void setSlotDefaultFeatureList(int phoneId, SecCarrier carrier) {
        this.mDefaultFeatureList.put(Integer.valueOf(phoneId), carrier.getFeature());
    }

    private void loadSpecificFeatures(int phoneId) {
        this.mCanonicalId.put(
                Integer.valueOf(phoneId), Integer.valueOf(FeatureUtil.getDefaultCanonicalID()));
        int canonicalId = FeatureUtil.getLastCanonicalID(phoneId);
        Log.d(LOG_TAG, "update specific features : " + phoneId + "/" + canonicalId);
        SecCarrier last = FeatureUtil.getCarrierFeature(phoneId, canonicalId, false);
        if (last != null) {
            setSlotSpecificFeatureList(phoneId, last, canonicalId);
        }
    }

    private void setSlotSpecificFeatureList(int phoneId, SecCarrier carrier, int canonicalId) {
        this.mCanonicalId.put(Integer.valueOf(phoneId), Integer.valueOf(canonicalId));
        this.mSpecificFeatureList.put(Integer.valueOf(phoneId), carrier.getFeature());
    }

    private void loadLastFeatures(int phoneId) {
        int canonicalId = FeatureUtil.getLastCanonicalID(phoneId);
        Log.d(LOG_TAG, "update last features : " + phoneId + "/" + canonicalId);
        SecCarrier last = FeatureUtil.getCarrierFeature(phoneId, canonicalId, true);
        if (last != null) {
            setSlotLastFeatureList(phoneId, last);
        }
    }

    private void setSlotLastFeatureList(int phoneId, SecCarrier carrier) {
        this.mLastFeatureList.put(Integer.valueOf(phoneId), carrier.getFeature());
    }

    public int getCarrierId(int phoneId, boolean checkLastSim) {
        if (checkLastSim) {
            return FeatureUtil.getLastCanonicalID(phoneId);
        }
        return FeatureUtil.getCurrentCanonicalID(phoneId);
    }

    public boolean getBoolean(int phoneId, String key, boolean defaultValue, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getBoolean] key:" + key + " value:" + value);
            }
            if (value != null) {
                return Boolean.parseBoolean(value);
            }
            return defaultValue;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getBoolean] Exception" + e);
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public boolean[] getBooleanArray(int phoneId, String key, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getBooleanArray] key:" + key + " value:" + value);
            }
            if (value == null) {
                return null;
            }
            String[] parts = value.split(",");
            boolean[] arrays = new boolean[parts.length];
            for (int i = 0; i < parts.length; i++) {
                arrays[i] = Boolean.parseBoolean(parts[i].trim());
            }
            return arrays;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getBooleanArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public int getInt(int phoneId, String key, int defaultValue, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getInt] key:" + key + " value:" + value);
            }
            if (value != null) {
                return Integer.parseInt(value);
            }
            return defaultValue;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getInt] Exception" + e);
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public int[] getIntArray(int phoneId, String key, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getIntArray] key:" + key + " value:" + value);
            }
            if (value == null) {
                return null;
            }
            String[] parts = value.split(",");
            int[] arrays = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                arrays[i] = Integer.parseInt(parts[i].trim());
            }
            return arrays;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getIntArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public long getLong(int phoneId, String key, long defaultValue, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getLong] key:" + key + " value:" + value);
            }
            if (value != null) {
                return Long.parseLong(value);
            }
            return defaultValue;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getLong] Exception" + e);
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public long[] getLongArray(int phoneId, String key, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getLongArray] key:" + key + " value:" + value);
            }
            if (value == null) {
                return null;
            }
            String[] parts = value.split(",");
            long[] arrays = new long[parts.length];
            for (int i = 0; i < parts.length; i++) {
                arrays[i] = Long.parseLong(parts[i].trim());
            }
            return arrays;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getLongArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public double getDouble(int phoneId, String key, double defaultValue, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getDouble] key:" + key + " value:" + value);
            }
            if (value != null) {
                return Double.parseDouble(value);
            }
            return defaultValue;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getDouble] Exception" + e);
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public double[] getDoubleArray(int phoneId, String key, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getDoubleArray] key:" + key + " value:" + value);
            }
            if (value == null) {
                return null;
            }
            String[] parts = value.split(",");
            double[] arrays = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                arrays[i] = Double.parseDouble(parts[i].trim());
            }
            return arrays;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getDoubleArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getString(int phoneId, String key, String defaultValue, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getString] key:" + key + " value:" + value);
            }
            if (value != null) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getString] Exception" + e);
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    public String[] getStringArray(int phoneId, String key, boolean checkLastSim) {
        try {
            String value = get(phoneId, key, checkLastSim);
            if (DEBUG) {
                Log.d(LOG_TAG, "[getStringArray] key:" + key + " value:" + value);
            }
            if (value == null) {
                return null;
            }
            return value.split(",");
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(LOG_TAG, "[getStringArray] Exception" + e);
                e.printStackTrace();
            }
            return null;
        }
    }
}
