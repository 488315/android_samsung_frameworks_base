package com.samsung.android.knox.nfc;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NfcPolicy {
    public static String TAG = "NfcPolicy";
    public ContextInfo mContextInfo;
    public IMiscPolicy mService;

    public NfcPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public final boolean allowNFCStateChange(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NfcPolicy.allowNFCStateChange");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.allowNFCStateChange(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with MiscPolicy", e);
            return false;
        }
    }

    public final IMiscPolicy getService() {
        if (this.mService == null) {
            this.mService = IMiscPolicy.Stub.asInterface(ServiceManager.getService("misc_policy"));
        }
        return this.mService;
    }

    public final boolean isNFCStarted() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isNFCStarted();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with MiscPolicy", e);
            return false;
        }
    }

    public final boolean isNFCStateChangeAllowed() {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isNFCStateChangeAllowed();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with MiscPolicy", e);
            return true;
        }
    }

    public final boolean startNFC(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NfcPolicy.startNFC");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.startNFC(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with MiscPolicy", e);
            return false;
        }
    }
}
