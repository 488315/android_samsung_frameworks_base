package com.samsung.android.knox.restriction;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.restriction.IRoamingPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RoamingPolicy {
    public static String TAG = "RoamingPolicy";
    public ContextInfo mContextInfo;
    public IRoamingPolicy mService;

    public RoamingPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final IRoamingPolicy getService() {
        if (this.mService == null) {
            this.mService = IRoamingPolicy.Stub.asInterface(ServiceManager.getService("roaming_policy"));
        }
        return this.mService;
    }

    public final boolean isRoamingDataEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingDataEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public final boolean isRoamingPushEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingPushEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public final boolean isRoamingSyncEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingSyncEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public final boolean isRoamingVoiceCallsEnabled() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isRoamingVoiceCallsEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with roaming policy", e);
            return true;
        }
    }

    public final void setRoamingData(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingData");
        if (getService() != null) {
            try {
                this.mService.setRoamingData(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public final void setRoamingPush(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingPush");
        if (getService() != null) {
            try {
                this.mService.setRoamingPush(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public final void setRoamingSync(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingSync");
        if (getService() != null) {
            try {
                this.mService.setRoamingSync(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }

    public final void setRoamingVoiceCalls(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "RoamingPolicy.setRoamingVoiceCalls");
        if (getService() != null) {
            try {
                this.mService.setRoamingVoiceCalls(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with roaming policy", e);
            }
        }
    }
}
