package com.samsung.android.knox.kpcc;

import android.os.Process;
import android.os.ServiceManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.kpcc.IKPCCManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KPCCManager {
    public static final int DRX_1280_MSEC = 3;
    public static final int DRX_2560_MSEC = 4;
    public static final int DRX_320_MSEC = 1;
    public static final int DRX_640_MSEC = 2;
    public static final int DRX_DEFAULT = 0;
    public static final int DRX_EMPTY = -1;
    public static final int ERROR_ADMIN_ALREADY_SET = -3;
    public static final int ERROR_FAIL = -1;
    public static final int ERROR_INVALID_VALUE = -4;
    public static final int ERROR_NOT_SUPPORTED = -2;
    public static final int OFF = 0;
    public static final int ON = 1;
    public static final int SUCCESS = 0;
    public static final String TAG = "KPCCManager";
    public static final Object mSync = new Object();
    public static volatile KPCCManager sKPCCManager;
    public ContextInfo mContextInfo;
    public IKPCCManager mService;

    public KPCCManager(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static KPCCManager getInstance() {
        KPCCManager kPCCManager;
        KPCCManager kPCCManager2 = sKPCCManager;
        if (kPCCManager2 != null) {
            return kPCCManager2;
        }
        synchronized (mSync) {
            try {
                kPCCManager = sKPCCManager;
                if (kPCCManager == null) {
                    KPCCManager kPCCManager3 = new KPCCManager(new ContextInfo(Process.myUid()));
                    sKPCCManager = kPCCManager3;
                    kPCCManager = kPCCManager3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return kPCCManager;
    }

    public int allowRestrictedNetworkCapability(int i, String str, int i2) {
        return -2;
    }

    public int getDrxValue() {
        return 0;
    }

    public List<String> getPackagesAllowedOnRestrictedNetworks() {
        return null;
    }

    public final IKPCCManager getService() {
        if (this.mService == null) {
            this.mService = IKPCCManager.Stub.asInterface(ServiceManager.getService("kpcc"));
        }
        return this.mService;
    }

    public int getTelephonyDrxValue() {
        return 0;
    }

    public List<Integer> getUnrestrictedNetworkCapabilities(String str) {
        return null;
    }

    public int setDrxValue(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KPCCManager.setDrxValue");
        return -2;
    }

    public int setPackageOnRestrictedNetworks(int i, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KPCCManager.setPackageOnRestrictedNetworks");
        return -2;
    }
}
