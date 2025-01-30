package com.samsung.android.knox.kpcc;

import android.os.Process;
import android.os.ServiceManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.kpcc.IKPCCManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KPCCManager {
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

    /* renamed from: ON */
    public static final int f485ON = 1;
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
        KPCCManager kPCCManager = sKPCCManager;
        if (kPCCManager == null) {
            synchronized (mSync) {
                kPCCManager = sKPCCManager;
                if (kPCCManager == null) {
                    KPCCManager kPCCManager2 = new KPCCManager(new ContextInfo(Process.myUid()));
                    sKPCCManager = kPCCManager2;
                    kPCCManager = kPCCManager2;
                }
            }
        }
        return kPCCManager;
    }

    public final int allowRestrictedNetworkCapability(int i, String str, int i2) {
        return -2;
    }

    public final int getDrxValue() {
        return 0;
    }

    public final List<String> getPackagesAllowedOnRestrictedNetworks() {
        return null;
    }

    public final IKPCCManager getService() {
        if (this.mService == null) {
            this.mService = IKPCCManager.Stub.asInterface(ServiceManager.getService("kpcc"));
        }
        return this.mService;
    }

    public final int getTelephonyDrxValue() {
        return 0;
    }

    public final List<Integer> getUnrestrictedNetworkCapabilities(String str) {
        return null;
    }

    public final int setDrxValue(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KPCCManager.setDrxValue");
        return -2;
    }

    public final int setPackageOnRestrictedNetworks(int i, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "KPCCManager.setPackageOnRestrictedNetworks");
        return -2;
    }
}
