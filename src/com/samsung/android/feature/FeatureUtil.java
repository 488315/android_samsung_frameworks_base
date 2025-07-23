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
    private static final String LAST_CARRIER_FEATURE_FILE_NAME = "last_customer_carrier_feature.json";
    private static final String LAST_MATCHED_CODE = "persist.sys.matched_code";
    private static final String LAST_SYSTEM_FEATURE_PATH = "persist.sys.nw_path";
    private static final String LOG_TAG = "FeatureUtil";
    private static final String PERSIST_SIMSLOT_PARENT_CANONICAL_ID = "persist.sys.sec_pcid";
    private static final String SALES_CODE = "ro.csc.sales_code";
    static final int UNKNOWN_CARRIER_ID = -1;
    private static final String UPDATE_FEATURE_PATH = "/omr/carrier/";
    static final int VERSION_DEFAULT = -1;

    static int getDefaultCanonicalID() {
        return -1;
    }

    FeatureUtil() {
    }

    private static SecCarrier getSecCarrierFeature(String str, String str2, int i) {
        if (SemCarrierFeature.DEBUG) {
            Log.d(LOG_TAG, "filePath " + str + " carrierGroup " + str2 + " canonicalId " + i);
        }
        try {
            File file = new File(str);
            if (!file.exists()) {
                Log.w(LOG_TAG, "files does not exist from " + str);
                return null;
            }
            String decode = TextDecoder.decode(file, SemCarrierFeature.TEST);
            if (TextUtils.isEmpty(decode)) {
                Log.w(LOG_TAG, "fail to decode feature from " + str);
                return null;
            }
            return new SecCarrier(decode, str2, i);
        } catch (Exception e) {
            Log.e(LOG_TAG, "fail to read feature from " + str + " with exception: " + e.toString());
            return null;
        }
    }

    static SecCarrier getCarrierFeature(int i, int i2, boolean z) {
        SecCarrier secCarrierFeature = getSecCarrierFeature(getSystemFeaturePath(i, z), getMatchedCode(i, z), i2);
        SecCarrier secCarrierFeature2 = getSecCarrierFeature(getUpdateFeaturePath(i, z), getMatchedCode(i, z), i2);
        if (secCarrierFeature != null && secCarrierFeature2 != null) {
            if (SemCarrierFeature.DEBUG) {
                String str = LOG_TAG;
                Log.d(str, "systemFeature version : " + secCarrierFeature.getVersion() + "  mapped cid version : " + secCarrierFeature.getMappedCidVersion());
                Log.d(str, "updateFeature version : " + secCarrierFeature2.getVersion() + "  mapped cid version : " + secCarrierFeature2.getMappedCidVersion());
            }
            int mappedCidVersion = secCarrierFeature.getMappedCidVersion() / 10000;
            int mappedCidVersion2 = secCarrierFeature2.getMappedCidVersion() / 10000;
            boolean isCarrierGroupValid = secCarrierFeature2.isCarrierGroupValid();
            if (!isCarrierGroupValid || mappedCidVersion > mappedCidVersion2 || secCarrierFeature.getVersion() > secCarrierFeature2.getVersion()) {
                Log.d(LOG_TAG, "delete updateFeature : " + isCarrierGroupValid);
                deleteUpdateFeature(i, z);
            } else if (secCarrierFeature.getVersion() != secCarrierFeature2.getVersion()) {
                return secCarrierFeature2;
            }
        }
        return secCarrierFeature;
    }

    private static boolean deleteDir(File file) {
        try {
            File[] listFiles = file.listFiles();
            if (file.isDirectory() && listFiles != null) {
                for (File file2 : listFiles) {
                    deleteDir(file2);
                }
            }
            return file.delete();
        } catch (NullPointerException | SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteUpdateFeature(int i, boolean z) {
        File file;
        if (z) {
            file = new File(UPDATE_FEATURE_PATH + Integer.toString(i) + "/last_customer_carrier_feature.json");
        } else {
            file = new File(UPDATE_FEATURE_PATH + Integer.toString(i) + "/customer_carrier_feature.json");
        }
        deleteDir(file);
    }

    static String getSystemFeaturePath(int i, boolean z) {
        String str = SystemProperties.get(CURRENT_SYSTEM_FEATURE_PATH + getReadablePhoneIDName(i), FEATURE_GROUP_VALUE_UNKNOWN);
        if (z) {
            return SystemProperties.get(LAST_SYSTEM_FEATURE_PATH + getReadablePhoneIDName(i), str) + "/customer_carrier_feature.json";
        }
        return str + "/customer_carrier_feature.json";
    }

    private static String getUpdateFeaturePath(int i, boolean z) {
        if (z) {
            return UPDATE_FEATURE_PATH + i + "/last_customer_carrier_feature.json";
        }
        return UPDATE_FEATURE_PATH + i + "/customer_carrier_feature.json";
    }

    private static String getMatchedCode(int i, boolean z) {
        if (z) {
            return SystemProperties.get(LAST_MATCHED_CODE + getReadablePhoneIDName(i), SystemProperties.get(SALES_CODE, FEATURE_GROUP_VALUE_UNKNOWN));
        }
        return SystemProperties.get(CURRENT_MATCHED_CODE + getReadablePhoneIDName(i), SystemProperties.get(SALES_CODE, FEATURE_GROUP_VALUE_UNKNOWN));
    }

    static int getCurrentCanonicalID(int i) {
        return SystemProperties.getInt(CURRENT_SIMSLOT_PARENT_CANONICAL_ID + getReadablePhoneIDName(i), -1);
    }

    static int getLastCanonicalID(int i) {
        return SystemProperties.getInt(PERSIST_SIMSLOT_PARENT_CANONICAL_ID + getReadablePhoneIDName(i), -1);
    }

    static int getLastFeatureVersion(int i) {
        String str = SystemProperties.get(CURRENT_SIMSLOT_FEATURE + getReadablePhoneIDName(i), "");
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        String[] split = str.split(Session.SESSION_SEPARATION_CHAR_CHILD);
        if (!TextUtils.isEmpty(split[0]) && !TextUtils.isEmpty(split[1])) {
            try {
                return Integer.valueOf(split[1]).intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private static String getReadablePhoneIDName(int i) {
        if (i == 0) {
            return "";
        }
        return String.valueOf(i + 1);
    }

    static int readSimCount() {
        return SystemProperties.getInt(CURRENT_SIMSLOT_COUNT, 1);
    }
}
