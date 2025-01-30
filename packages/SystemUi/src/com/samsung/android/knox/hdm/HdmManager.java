package com.samsung.android.knox.hdm;

import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.hdm.IHdmManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HdmManager {
    public static final String CURRENT_HDM_VERSION = "1.0";
    public static final int ERROR_FAIL = -1;
    public static final String TAG = "HDM - HdmManager";
    public ContextInfo mContextInfo;
    public IHdmManager mService;

    public HdmManager(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
        Log.d(TAG, "HdmManager.java Started");
    }

    public static String getHdmVersion() {
        Log.d(TAG, "getHdmVersion() on HdmManager.java");
        return "3.0 - 115";
    }

    public final String getHdmId(String str) {
        String str2 = TAG;
        Log.d(str2, "getHdmId() on HdmManager.java");
        if (getService() != null) {
            Log.d(str2, "Calling getHdmId() using HDM Service on HdmManager.java");
            return this.mService.getHdmId(this.mContextInfo, str);
        }
        Log.e(str2, "Fail to call getHdmId() using HDM Service on HdmManager.java");
        return null;
    }

    public final String getHdmPolicy(String str, String str2) {
        String str3 = TAG;
        Log.d(str3, "getHdmPolicy() on HdmManager.java");
        if (getService() != null) {
            Log.d(str3, "Calling getHdmPolicy() using HDM Service on HdmManager.java");
            return this.mService.getHdmPolicy(this.mContextInfo, str, str2);
        }
        Log.e(str3, "Fail to call getHdmPolicy() using HDM Service on HdmManager.java");
        return null;
    }

    public final IHdmManager getService() {
        if (this.mService == null) {
            this.mService = IHdmManager.Stub.asInterface(ServiceManager.getService("hdm_service"));
        }
        return this.mService;
    }

    public final boolean isNFCBlockedByHDM() {
        String str = TAG;
        Log.d(str, "isNFCBlockedByHDM() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling isNFCBlockedByHDM() using HDM Service on HdmManager.java");
            return this.mService.isNFCBlockedByHDM(this.mContextInfo);
        }
        Log.e(str, "Fail to call isNFCBlockedByHDM() using HDM Service on HdmManager.java");
        return false;
    }

    public final boolean isSwBlockEnabled() {
        String str = TAG;
        Log.d(str, "isSwBlockEnabled() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling isSwBlockEnabled() using HDM Service on HdmManager.java");
            return this.mService.isSwBlockEnabled(this.mContextInfo);
        }
        Log.e(str, "Fail to call isSwBlockEnabled() using HDM Service on HdmManager.java");
        return false;
    }

    public final String setHdmPolicy(String str) {
        String str2 = TAG;
        Log.d(str2, "setHdmPolicy() on HdmManager.java");
        if (getService() != null) {
            Log.d(str2, "Calling setHdmPolicy() using HDM Service on HdmManager.java");
            return this.mService.setHdmPolicy(this.mContextInfo, str);
        }
        Log.e(str2, "Fail to call setHdmPolicy() using HDM Service on HdmManager.java");
        return null;
    }

    public final int setHdmTaCmd(int i) {
        String str = TAG;
        Log.d(str, "setHdmTaCmd() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling setHdmTaCmd() using HDM Service on HdmManager.java");
            return this.mService.setHdmTaCmd(this.mContextInfo, i);
        }
        Log.e(str, "Fail to call setHdmTaCmd() using HDM Service on HdmManager.java");
        return -1;
    }

    public final boolean setSwBlock(boolean z) {
        String str = TAG;
        Log.d(str, "setSwBlock() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling setSwBlock() using HDM Service on HdmManager.java");
            return this.mService.setSwBlock(this.mContextInfo, z);
        }
        Log.e(str, "Fail to call setSwBlock() using HDM Service on HdmManager.java");
        return false;
    }

    public final int syncSwBlockFromBoot() {
        String str = TAG;
        Log.d(str, "syncSwBlockFromBoot() on HdmManager.java");
        if (getService() != null) {
            Log.d(str, "Calling syncSwBlockFromBoot() using HDM Service on HdmManager.java");
            return this.mService.syncSwBlockFromBoot();
        }
        Log.e(str, "Fail to call syncSwBlockFromBoot() using HDM Service on HdmManager.java");
        return -1;
    }
}
