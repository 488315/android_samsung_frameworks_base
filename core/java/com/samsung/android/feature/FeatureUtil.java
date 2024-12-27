package com.samsung.android.feature;

import android.os.SystemProperties;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/* loaded from: classes6.dex */
class FeatureUtil {
    private static final String CARRIER_FEATURE_FILE_NAME = "customer_carrier_feature.json";
    private static final String CURRENT_MATCHED_CODE = "mdc.matched_code";
    private static final String CURRENT_SIMSLOT_COUNT = "ro.multisim.simslotcount";
    private static final String CURRENT_SIMSLOT_FEATURE = "mdc.sys.sec_feature";
    private static final String CURRENT_SIMSLOT_PARENT_CANONICAL_ID = "mdc.sys.sec_pcid";
    private static final String CURRENT_SYSTEM_FEATURE_PATH = "mdc.system.nw_path";
    private static final String FEATURE_GROUP_VALUE_UNKNOWN = "UKN";
    private static final String LAST_CARRIER_FEATURE_FILE_NAME =
            "last_customer_carrier_feature.json";
    private static final String LAST_MATCHED_CODE = "persist.sys.matched_code";
    private static final String LAST_SYSTEM_FEATURE_PATH = "persist.sys.nw_path";
    private static final String LOG_TAG = FeatureUtil.class.getSimpleName();
    private static final String PERSIST_SIMSLOT_PARENT_CANONICAL_ID = "persist.sys.sec_pcid";
    private static final String SALES_CODE = "ro.csc.sales_code";
    static final int UNKNOWN_CARRIER_ID = -1;
    private static final String UPDATE_FEATURE_PATH = "/omr/carrier/";
    static final int VERSION_DEFAULT = -1;

    FeatureUtil() {}

    private static SecCarrier getSecCarrierFeature(
            String filePath, String carrierGroup, int canonicalId) {
        if (SemCarrierFeature.DEBUG) {
            Log.d(
                    LOG_TAG,
                    "filePath "
                            + filePath
                            + " carrierGroup "
                            + carrierGroup
                            + " canonicalId "
                            + canonicalId);
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Log.w(LOG_TAG, "files does not exist from " + filePath);
                return null;
            }
            String result = TextDecoder.decode(file, SemCarrierFeature.TEST);
            if (TextUtils.isEmpty(result)) {
                Log.w(LOG_TAG, "fail to decode feature from " + filePath);
                return null;
            }
            return new SecCarrier(result, carrierGroup, canonicalId);
        } catch (Exception ex) {
            Log.e(
                    LOG_TAG,
                    "fail to read feature from " + filePath + " with exception: " + ex.toString());
            return null;
        }
    }

    static SecCarrier getCarrierFeature(int phoneId, int canonicalId, boolean checkLastSim) {
        SecCarrier systemFeature =
                getSecCarrierFeature(
                        getSystemFeaturePath(phoneId, checkLastSim),
                        getMatchedCode(phoneId, checkLastSim),
                        canonicalId);
        SecCarrier updateFeature =
                getSecCarrierFeature(
                        getUpdateFeaturePath(phoneId, checkLastSim),
                        getMatchedCode(phoneId, checkLastSim),
                        canonicalId);
        if (systemFeature != null && updateFeature != null) {
            if (SemCarrierFeature.DEBUG) {
                Log.d(
                        LOG_TAG,
                        "systemFeature version : "
                                + systemFeature.getVersion()
                                + "  mapped cid version : "
                                + systemFeature.getMappedCidVersion());
                Log.d(
                        LOG_TAG,
                        "updateFeature version : "
                                + updateFeature.getVersion()
                                + "  mapped cid version : "
                                + updateFeature.getMappedCidVersion());
            }
            int systemFeatureOsVersion = systemFeature.getMappedCidVersion() / 10000;
            int updateFeatureOsVersion = updateFeature.getMappedCidVersion() / 10000;
            boolean isUpdateFeatureValid = updateFeature.isCarrierGroupValid();
            if (!isUpdateFeatureValid
                    || systemFeatureOsVersion > updateFeatureOsVersion
                    || systemFeature.getVersion() > updateFeature.getVersion()) {
                Log.d(LOG_TAG, "delete updateFeature : " + isUpdateFeatureValid);
                deleteUpdateFeature(phoneId, checkLastSim);
                return systemFeature;
            }
            if (systemFeature.getVersion() == updateFeature.getVersion()) {
                return systemFeature;
            }
            return updateFeature;
        }
        return systemFeature;
    }

    private static boolean deleteDir(File dir) {
        try {
            File[] files = dir.listFiles();
            if (dir.isDirectory() && files != null) {
                for (File child : files) {
                    deleteDir(child);
                }
            }
            return dir.delete();
        } catch (NullPointerException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteUpdateFeature(int phoneId, boolean checkLastSim) {
        File updateFile;
        if (checkLastSim) {
            updateFile =
                    new File(
                            UPDATE_FEATURE_PATH
                                    + Integer.toString(phoneId)
                                    + "/"
                                    + LAST_CARRIER_FEATURE_FILE_NAME);
        } else {
            updateFile =
                    new File(
                            UPDATE_FEATURE_PATH
                                    + Integer.toString(phoneId)
                                    + "/"
                                    + CARRIER_FEATURE_FILE_NAME);
        }
        deleteDir(updateFile);
    }

    private static String getSystemFeaturePath(int phoneId, boolean checkLastSim) {
        String featurePath =
                SystemProperties.get(
                        CURRENT_SYSTEM_FEATURE_PATH + getReadablePhoneIDName(phoneId),
                        FEATURE_GROUP_VALUE_UNKNOWN);
        return checkLastSim
                ? SystemProperties.get(
                                LAST_SYSTEM_FEATURE_PATH + getReadablePhoneIDName(phoneId),
                                featurePath)
                        + "/"
                        + CARRIER_FEATURE_FILE_NAME
                : featurePath + "/" + CARRIER_FEATURE_FILE_NAME;
    }

    private static String getUpdateFeaturePath(int phoneId, boolean checkLastSim) {
        return checkLastSim
                ? UPDATE_FEATURE_PATH + phoneId + "/" + LAST_CARRIER_FEATURE_FILE_NAME
                : UPDATE_FEATURE_PATH + phoneId + "/" + CARRIER_FEATURE_FILE_NAME;
    }

    static String getMatchedCode(int phoneId, boolean last) {
        if (last) {
            return SystemProperties.get(
                    LAST_MATCHED_CODE + getReadablePhoneIDName(phoneId),
                    SystemProperties.get(SALES_CODE, FEATURE_GROUP_VALUE_UNKNOWN));
        }
        return SystemProperties.get(
                CURRENT_MATCHED_CODE + getReadablePhoneIDName(phoneId),
                SystemProperties.get(SALES_CODE, FEATURE_GROUP_VALUE_UNKNOWN));
    }

    static int getCurrentCanonicalID(int phoneId) {
        return SystemProperties.getInt(
                CURRENT_SIMSLOT_PARENT_CANONICAL_ID + getReadablePhoneIDName(phoneId), -1);
    }

    static int getLastCanonicalID(int phoneId) {
        return SystemProperties.getInt(
                PERSIST_SIMSLOT_PARENT_CANONICAL_ID + getReadablePhoneIDName(phoneId), -1);
    }

    static int getDefaultCanonicalID() {
        return -1;
    }

    static int getLastFeatureVersion(int phoneId) {
        String featureInfo =
                SystemProperties.get(CURRENT_SIMSLOT_FEATURE + getReadablePhoneIDName(phoneId), "");
        if (TextUtils.isEmpty(featureInfo)) {
            return -1;
        }
        String[] feature = featureInfo.split(Session.SESSION_SEPARATION_CHAR_CHILD);
        if (!TextUtils.isEmpty(feature[0]) && !TextUtils.isEmpty(feature[1])) {
            try {
                return Integer.valueOf(feature[1]).intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private static String getReadablePhoneIDName(int phoneId) {
        if (phoneId == 0) {
            return "";
        }
        return String.valueOf(phoneId + 1);
    }

    static int readSimCount() {
        return SystemProperties.getInt(CURRENT_SIMSLOT_COUNT, 1);
    }
}
