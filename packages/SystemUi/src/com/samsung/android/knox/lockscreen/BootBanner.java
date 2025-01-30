package com.samsung.android.knox.lockscreen;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ISecurityPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class BootBanner {
    public static String TAG = "BootBanner";
    public ContextInfo mContextInfo;
    public ISecurityPolicy mService;

    public BootBanner(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean enableRebootBanner(boolean z, String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BootBanner.enableRebootBanner(boolean, String)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableRebootBannerWithText(this.mContextInfo, z, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final String getDeviceLastAccessDate() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getDeviceLastAccessDate(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return null;
        }
    }

    public final String getRebootBannerText() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getRebootBannerText(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return null;
        }
    }

    public final ISecurityPolicy getService() {
        if (this.mService == null) {
            this.mService = ISecurityPolicy.Stub.asInterface(ServiceManager.getService("security_policy"));
        }
        return this.mService;
    }

    public final boolean isDodBannerVisible() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isDodBannerVisible(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final boolean isRebootBannerEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isRebootBannerEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final void onKeyguardLaunched() {
        if (getService() != null) {
            try {
                this.mService.onKeyguardLaunched();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with security policy", e);
            }
        }
    }

    public final boolean setDeviceLastAccessDate(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setDeviceLastAccessDate(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final boolean setDodBannerVisibleStatus(boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.setDodBannerVisibleStatus(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }

    public final boolean enableRebootBanner(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "BootBanner.enableRebootBanner(boolean)");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableRebootBanner(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return false;
        }
    }
}
