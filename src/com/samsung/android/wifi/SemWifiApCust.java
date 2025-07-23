package com.samsung.android.wifi;

import android.os.Build;
import android.os.Debug;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
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
        SemWifiApCust semWifiApCust = sInstance;
        if (semWifiApCust != null) {
            return semWifiApCust;
        }
        sInstance = new SemWifiApCust();
        if (DBG) {
            Log.d(TAG, "new mMHSCustomer:" + mMHSCustomer);
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
        return mSalescode.equals("USC") ? "USC" : (mSalescode.equals("VZW") || mSalescode.equals("VPP")) ? "VZW" : "SBM".equals(mSalescode) ? "SBM" : "ALL";
    }

    public static String readSalesCode() {
        String str = "";
        try {
            str = SystemProperties.get("ro.csc.sales_code");
            if (TextUtils.isEmpty(str)) {
                str = SystemProperties.get("ro.boot.sales_code");
            }
        } catch (Exception unused) {
        }
        Log.d(TAG, "readSalesCode:" + str);
        return str;
    }

    public static boolean isProvisioningNeeded() {
        if (isTablet() && !"ATT".equals(mSalescode) && !"VZW".equals(mSalescode)) {
            Log.d(TAG, "isProvisioningNeeded: false, isTablet: true, operator:" + mSalescode);
            return false;
        }
        String[] strArr = {"VZW", "ATT", "AIO", "XAR", "TFV", "TFA", "TFN", "LLA", "DSA", "APP", "XAA", "VPP", "SBM"};
        for (int i = 0; i < 13; i++) {
            if (strArr[i].equals(mSalescode)) {
                Log.d(TAG, "isProvisioningNeeded: true, operator:" + mSalescode);
                return true;
            }
        }
        Log.d(TAG, "isProvisioningNeeded: false, operator:" + mSalescode);
        return false;
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains(BnRConstants.DEVICETYPE_TABLET);
    }

    public static void readTempConfig() {
        if (DBG) {
            if (new File("/data/misc/wifi_hostapd/testconf/dbgfalse").exists()) {
                DBG = false;
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/dbgfalse is exist! temp DBG: " + DBG);
            }
            if (new File("/data/misc/wifi_hostapd/testconf/all").exists()) {
                mMHSCustomer = "all";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/all is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/tmo").exists()) {
                mMHSCustomer = "TMO";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/tmo is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/att").exists()) {
                mMHSCustomer = "ATT";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/att is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/spr").exists()) {
                mMHSCustomer = "SPRINT";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/spr is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/lgt").exists()) {
                mMHSCustomer = "LGT";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/lgt is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/newco").exists()) {
                mMHSCustomer = "NEWCO";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/newco is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/usc").exists()) {
                mMHSCustomer = "USC";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/usc is exist! temp mMHSCustomer: " + mMHSCustomer);
                return;
            }
            if (new File("/data/misc/wifi_hostapd/testconf/vzw").exists()) {
                mMHSCustomer = "VZW";
                Log.d(TAG, "/data/misc/wifi_hostapd/testconf/vzw is exist! temp mMHSCustomer: " + mMHSCustomer);
            }
        }
    }
}
