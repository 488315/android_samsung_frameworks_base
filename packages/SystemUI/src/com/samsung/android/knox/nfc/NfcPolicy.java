package com.samsung.android.knox.nfc;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IMiscPolicy;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

/* loaded from: classes4.dex */
public class NfcPolicy {
    public static String TAG = "NfcPolicy";
    public ContextInfo mContextInfo;
    public IMiscPolicy mService;

    public NfcPolicy(ContextInfo contextInfo) {
        this.mContextInfo = contextInfo;
    }

    public boolean allowNFCStateChange(boolean z) {
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

    public boolean isNFCStarted() {
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

    public boolean isNFCStateChangeAllowed() {
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

    public boolean startNFC(boolean z) {
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
