package com.samsung.android.wifi;

import android.p009os.Build;
import android.p009os.Debug;
import android.p009os.SystemProperties;
import android.util.Log;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.File;

/* loaded from: classes6.dex */
public class SemWifiApCust {
    public static boolean DBG = false;
    public static final String TAG = "SemWifiApCust";
    public static String mMHSCustomer;
    private static SemWifiApCust sInstance;

    static {
        DBG = !"user".equals(Build.TYPE) || Debug.semIsProductDev();
        mMHSCustomer = SemCscFeature.getInstance().getString("CscFeature_Wifi_ConfigOpBrandingForMobileAp", SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL);
        sInstance = null;
    }

    private SemWifiApCust() {
        readTempConfig();
    }

    public static SemWifiApCust getInstance() {
        SemWifiApCust semWifiApCust = sInstance;
        if (semWifiApCust == null) {
            sInstance = new SemWifiApCust();
            if (DBG) {
                Log.m94d(TAG, "new mMHSCustomer:" + mMHSCustomer);
            }
            return sInstance;
        }
        return semWifiApCust;
    }

    public static boolean isProvisioningNeeded() {
        String CONFIGOPBRANDINGFORMOBILEAP = SystemProperties.get("ro.csc.sales_code");
        if (isTablet() && !"ATT".equals(CONFIGOPBRANDINGFORMOBILEAP)) {
            Log.m94d(TAG, "isProvisioningNeeded: false, isTablet: true, operator:" + CONFIGOPBRANDINGFORMOBILEAP);
            return false;
        }
        String[] provisioningSalesCodes = {"VZW", "ATT", "AIO", "XAR", "TFV", "TFA", "TFN", "LLA", "DSA", "APP", "XAA"};
        for (String str : provisioningSalesCodes) {
            if (str.equals(CONFIGOPBRANDINGFORMOBILEAP)) {
                Log.m94d(TAG, "isProvisioningNeeded: true, operator:" + CONFIGOPBRANDINGFORMOBILEAP);
                return true;
            }
        }
        Log.m94d(TAG, "isProvisioningNeeded: false, operator:" + CONFIGOPBRANDINGFORMOBILEAP);
        return false;
    }

    public static boolean isTablet() {
        String deviceType = SystemProperties.get("ro.build.characteristics");
        return deviceType != null && deviceType.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    public static void readTempConfig() {
        if (!DBG) {
            return;
        }
        File file = new File("/data/misc/wifi_hostapd/testconf/dbgfalse");
        if (file.exists()) {
            DBG = false;
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/dbgfalse is exist! temp DBG: " + DBG);
        }
        File file2 = new File("/data/misc/wifi_hostapd/testconf/all");
        if (file2.exists()) {
            mMHSCustomer = "all";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/all is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file3 = new File("/data/misc/wifi_hostapd/testconf/tmo");
        if (file3.exists()) {
            mMHSCustomer = "TMO";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/tmo is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file4 = new File("/data/misc/wifi_hostapd/testconf/att");
        if (file4.exists()) {
            mMHSCustomer = "ATT";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/att is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file5 = new File("/data/misc/wifi_hostapd/testconf/spr");
        if (file5.exists()) {
            mMHSCustomer = "SPRINT";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/spr is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file6 = new File("/data/misc/wifi_hostapd/testconf/lgt");
        if (file6.exists()) {
            mMHSCustomer = "LGT";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/lgt is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file7 = new File("/data/misc/wifi_hostapd/testconf/newco");
        if (file7.exists()) {
            mMHSCustomer = "NEWCO";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/newco is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file8 = new File("/data/misc/wifi_hostapd/testconf/usc");
        if (file8.exists()) {
            mMHSCustomer = "USC";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/usc is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file9 = new File("/data/misc/wifi_hostapd/testconf/vzw");
        if (file9.exists()) {
            mMHSCustomer = "VZW";
            Log.m94d(TAG, "/data/misc/wifi_hostapd/testconf/vzw is exist! temp mMHSCustomer: " + mMHSCustomer);
        }
    }
}
