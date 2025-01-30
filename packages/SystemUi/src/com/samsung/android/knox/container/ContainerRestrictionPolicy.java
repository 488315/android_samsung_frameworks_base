package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContainerRestrictionPolicy {
    public static String TAG = "ContainerRestrictionPolicy";
    public static IRestrictionPolicy gRestrictionService;
    public ContextInfo mContextInfo;

    public ContainerRestrictionPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public static synchronized IRestrictionPolicy getRestrictionService() {
        IRestrictionPolicy iRestrictionPolicy;
        synchronized (ContainerRestrictionPolicy.class) {
            if (gRestrictionService == null) {
                gRestrictionService = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
            }
            iRestrictionPolicy = gRestrictionService;
        }
        return iRestrictionPolicy;
    }

    public final boolean allowShareList(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerRestrictionPolicy.allowShareList");
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.allowShareList(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return false;
        }
    }

    public final boolean isCameraEnabled(boolean z) {
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return true;
        }
        try {
            return restrictionService.isCameraEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with device info policy", e);
            return true;
        }
    }

    public final boolean isScreenCaptureEnabled(boolean z) {
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return true;
        }
        try {
            return restrictionService.isScreenCaptureEnabled(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return true;
        }
    }

    public final boolean isShareListAllowed() {
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return true;
        }
        try {
            return restrictionService.isShareListAllowed(this.mContextInfo, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with restriction policy", e);
            return true;
        }
    }

    public final boolean isUseSecureKeypadEnabled() {
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.isUseSecureKeypadEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setCameraState(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerRestrictionPolicy.setCameraState");
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.setCamera(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc info policy", e);
            return false;
        }
    }

    public final boolean setScreenCapture(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerRestrictionPolicy.setScreenCapture");
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.setScreenCapture(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }

    public final boolean setUseSecureKeypad(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "ContainerRestrictionPolicy.setUseSecureKeypad");
        IRestrictionPolicy restrictionService = getRestrictionService();
        if (restrictionService == null) {
            Log.e(TAG, "ContainerRestriction PolicyService is not yet ready!!!");
            return false;
        }
        try {
            return restrictionService.setUseSecureKeypad(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with misc policy", e);
            return false;
        }
    }
}
