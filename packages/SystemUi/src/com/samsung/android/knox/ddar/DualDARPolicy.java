package com.samsung.android.knox.ddar;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ddar.IDualDARPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class DualDARPolicy {
    public static final int DD_POLICY_ENABLED = 1;
    public static final int DD_POLICY_GID_RESTRICTION = 8;
    public static final int DD_POLICY_KERNEL_CRYPTO = 4;
    public static final int DD_POLICY_USER_SPACE_CRYPTO = 2;
    public static final String DUAL_DAR_POLICY_SERVICE = "DualDARPolicy";
    private static final String DUAL_DAR_VERSION_1_3_0 = "1.3.0";
    private static final String DUAL_DAR_VERSION_1_4_0 = "1.4.0";
    private static final String DUAL_DAR_VERSION_1_4_1 = "1.4.1";
    private static final String DUAL_DAR_VERSION_1_5_0 = "1.5.0";
    private static final String DUAL_DAR_VERSION_1_5_1 = "1.5.1";
    private static final String DUAL_DAR_VERSION_1_6_0 = "1.6.0";
    public static final int ERROR_FAILURE_IN_SETTING_DATA_LOCK_TIMEOUT = -1;
    public static final int ERROR_FAILURE_IN_SETTING_DE_RESTRICTION = -3;
    public static final int ERROR_FAILURE_IN_SETTING_WHITELIST_PACKAGES = -2;
    public static final int ERROR_NONE = 0;
    public static final String KEY_CONFIG_CLIENT_LOCATION = "dualdar-config-client-location";
    public static final String KEY_CONFIG_CLIENT_PACKAGE = "dualdar-config-client-package";
    public static final String KEY_CONFIG_CLIENT_SIGNATURE = "dualdar-config-client-signature";
    public static final String KEY_CONFIG_DATA_LOCK_TIMEOUT = "dualdar-config-datalock-timeout";
    public static final String KEY_CONFIG_DE_RESTRICTION = "dualdar-config-de-restriction";
    public static final String KEY_CONFIG_WHITELISTED_DATA_LOCK_STATE_PACKAGES = "dualdar-config-datalock-whitelistpackages";
    public static final String KEY_DUAL_DAR_CONFIG = "dualdar-config";
    private static String TAG = "DualDARPolicy";
    private ContextInfo mContextInfo;
    private IDualDARPolicy mService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class DUAL_DAR_VERSION_CODES {
        public static final String DUAL_DAR_1_0 = "1.0";
    }

    public DualDARPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
        IDualDARPolicy service = getService();
        this.mService = service;
        if (service == null) {
            Log.e(TAG, "DualDARPolicy Service is NULL");
        }
    }

    public static String getDualDARVersion() {
        return DUAL_DAR_VERSION_1_6_0;
    }

    private IDualDARPolicy getService() {
        if (this.mService == null) {
            this.mService = IDualDARPolicy.Stub.asInterface(ServiceManager.getService(DUAL_DAR_POLICY_SERVICE));
        }
        return this.mService;
    }

    public static boolean isDualDarSupportedForManagedDevice() {
        return true;
    }

    public boolean clearPolicy() {
        Log.d(TAG, "clearPolicy() ");
        try {
            IDualDARPolicy iDualDARPolicy = this.mService;
            if (iDualDARPolicy != null) {
                return iDualDARPolicy.clearPolicy(this.mContextInfo);
            }
            Log.d(TAG, "getService() is null");
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "clearPolicy Remote exception", e);
            return false;
        }
    }

    public boolean clearResetPasswordTokenForInner() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.clearResetPasswordTokenForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.clearResetPasswordTokenForInner(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "clearResetPasswordTokenForInner() Remote exception: ", e);
            return false;
        }
    }

    public Bundle getConfig() {
        try {
            IDualDARPolicy iDualDARPolicy = this.mService;
            if (iDualDARPolicy != null) {
                return iDualDARPolicy.getConfig(this.mContextInfo);
            }
            Log.d(TAG, "getService() is null");
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "getConfig Remote exception", e);
            return null;
        }
    }

    public int getPasswordMinimumLengthForInner() {
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return 0;
        }
        try {
            return iDualDARPolicy.getPasswordMinimumLengthForInner(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "getPasswordMinimumLengthForInner() Remote exception: ", e);
            return 0;
        }
    }

    public boolean isActivePasswordSufficientForInner() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.isActivePasswordSufficientForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.isActivePasswordSufficientForInner(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "isActivePasswordSufficientForInner() Remote exception: ", e);
            return false;
        }
    }

    public boolean isResetPasswordTokenActiveForInner() {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.isResetPasswordTokenActiveForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.isResetPasswordTokenActiveForInner(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "isResetPasswordTokenActive() Remote exception: ", e);
            return false;
        }
    }

    public boolean resetPasswordWithTokenForInner(String str, byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.resetPasswordWithTokenForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.resetPasswordWithTokenForInner(this.mContextInfo, str, bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "resetPasswordWithTokenForInner() Remote exception: ", e);
            return false;
        }
    }

    public int setConfig(Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.setConfig");
        try {
            IDualDARPolicy iDualDARPolicy = this.mService;
            if (iDualDARPolicy != null) {
                return iDualDARPolicy.setConfig(this.mContextInfo, bundle);
            }
            Log.d(TAG, "getService() is null");
            return -1;
        } catch (RemoteException e) {
            Log.w(TAG, "setConfig Remote exception", e);
            return -1;
        }
    }

    public boolean setPasswordMinimumLengthForInner(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.setPasswordMinimumLengthForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.setPasswordMinimumLengthForInner(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "setPasswordMinimumLengthForInner() Remote exception: ", e);
            return false;
        }
    }

    public boolean setResetPasswordTokenForInner(byte[] bArr) {
        EnterpriseLicenseManager.log(this.mContextInfo, "DualDARPolicy.setResetPasswordTokenForInner");
        IDualDARPolicy iDualDARPolicy = this.mService;
        if (iDualDARPolicy == null) {
            return false;
        }
        try {
            return iDualDARPolicy.setResetPasswordTokenForInner(this.mContextInfo, bArr);
        } catch (RemoteException e) {
            Log.w(TAG, "setResetPasswordTokenForInner() Remote exception: ", e);
            return false;
        }
    }
}
