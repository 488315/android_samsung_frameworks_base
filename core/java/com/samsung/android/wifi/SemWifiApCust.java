package com.samsung.android.wifi;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.io.File;

/* loaded from: classes6.dex */
public class SemWifiApCust {
    public static boolean DBG = false;
    public static final String TAG = "SemWifiApCust";
    public static String mMHSCustomer;
    public static String mSalescode;
    private static SemWifiApCust sInstance;

    static {
        DBG = !"user".equals(Build.TYPE) || Debug.semIsProductDev();
        mSalescode = readSalesCode();
        mMHSCustomer = setMHSCustomer();
        sInstance = null;
    }

    private SemWifiApCust() {
        readTempConfig();
    }

    public static SemWifiApCust getInstance() {
        if (sInstance == null) {
            sInstance = new SemWifiApCust();
            if (DBG) {
                Log.d(TAG, "new mMHSCustomer:" + mMHSCustomer);
            }
            return sInstance;
        }
        return sInstance;
    }

    public static String setMHSCustomer() {
        if (mSalescode.equals("ATT") || mSalescode.equals("APP")) {
            return "ATT";
        }
        if (mSalescode.equals("DSH") || mSalescode.equals("TMB")) {
            return "TMO";
        }
        if (mSalescode.equals("LUC")) {
            return "LGT";
        }
        if (mSalescode.equals("TMK") || mSalescode.equals("ASR") || mSalescode.equals("TMB")) {
            return "NEWCO";
        }
        return mSalescode.equals("USC") ? "USC" : (mSalescode.equals("VZW") || mSalescode.equals("VPP")) ? "VZW" : SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL;
    }

    public static String readSalesCode() {
        String sales_code = "";
        try {
            sales_code = SystemProperties.get("ro.csc.sales_code");
            if (TextUtils.isEmpty(sales_code)) {
                sales_code = SystemProperties.get("ro.boot.sales_code");
            }
        } catch (Exception e) {
        }
        Log.d(TAG, "readSalesCode:" + sales_code);
        return sales_code;
    }

    public static boolean isProvisioningNeeded() {
        if (isTablet() && !"ATT".equals(mSalescode)) {
            Log.d(TAG, "isProvisioningNeeded: false, isTablet: true, operator:" + mSalescode);
            return false;
        }
        String[] provisioningSalesCodes = {"VZW", "ATT", "AIO", "XAR", "TFV", "TFA", "TFN", "LLA", "DSA", "APP", "XAA", "VPP", "SBM"};
        for (String str : provisioningSalesCodes) {
            if (str.equals(mSalescode)) {
                Log.d(TAG, "isProvisioningNeeded: true, operator:" + mSalescode);
                return true;
            }
        }
        Log.d(TAG, "isProvisioningNeeded: false, operator:" + mSalescode);
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
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/dbgfalse is exist! temp DBG: " + DBG);
        }
        File file2 = new File("/data/misc/wifi_hostapd/testconf/all");
        if (file2.exists()) {
            mMHSCustomer = "all";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/all is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file3 = new File("/data/misc/wifi_hostapd/testconf/tmo");
        if (file3.exists()) {
            mMHSCustomer = "TMO";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/tmo is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file4 = new File("/data/misc/wifi_hostapd/testconf/att");
        if (file4.exists()) {
            mMHSCustomer = "ATT";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/att is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file5 = new File("/data/misc/wifi_hostapd/testconf/spr");
        if (file5.exists()) {
            mMHSCustomer = "SPRINT";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/spr is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file6 = new File("/data/misc/wifi_hostapd/testconf/lgt");
        if (file6.exists()) {
            mMHSCustomer = "LGT";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/lgt is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file7 = new File("/data/misc/wifi_hostapd/testconf/newco");
        if (file7.exists()) {
            mMHSCustomer = "NEWCO";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/newco is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file8 = new File("/data/misc/wifi_hostapd/testconf/usc");
        if (file8.exists()) {
            mMHSCustomer = "USC";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/usc is exist! temp mMHSCustomer: " + mMHSCustomer);
            return;
        }
        File file9 = new File("/data/misc/wifi_hostapd/testconf/vzw");
        if (file9.exists()) {
            mMHSCustomer = "VZW";
            Log.d(TAG, "/data/misc/wifi_hostapd/testconf/vzw is exist! temp mMHSCustomer: " + mMHSCustomer);
        }
    }
}
