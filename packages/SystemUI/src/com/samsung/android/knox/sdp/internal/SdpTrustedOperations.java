package com.samsung.android.knox.sdp.internal;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.sdp.core.SdpException;

/* loaded from: classes4.dex */
public class SdpTrustedOperations {
    private static final double SDK_NOT_SUPPORTED = 0.0d;
    private static final String TAG = "SdpTrustedOperations";
    private static SdpTrustedOperations _instance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));

    private SdpTrustedOperations() {
    }

    public static SdpTrustedOperations getInstance() {
        if (_instance == null) {
            _instance = new SdpTrustedOperations();
        }
        return _instance;
    }

    public boolean deleteTokenFromTrusted(String str) {
        int iDeleteToeknFromTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDeleteToeknFromTrusted = iDarManagerService.deleteToeknFromTrusted(str);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
        } else {
            iDeleteToeknFromTrusted = -13;
        }
        if (iDeleteToeknFromTrusted == 0) {
            return true;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(iDeleteToeknFromTrusted, "deleteToeknFromTrusted failed ", TAG);
        return false;
    }

    public String getSupportedSDKVersion() {
        double supportedSDKVersion;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                supportedSDKVersion = iDarManagerService.getSupportedSDKVersion();
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to connect sdp service...", e);
            }
        } else {
            supportedSDKVersion = SDK_NOT_SUPPORTED;
        }
        return String.valueOf(supportedSDKVersion);
    }

    public boolean saveTokenIntoTrusted(String str, String str2) {
        int iSaveTokenIntoTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iSaveTokenIntoTrusted = iDarManagerService.saveTokenIntoTrusted(str, str2);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
        } else {
            iSaveTokenIntoTrusted = -13;
        }
        if (iSaveTokenIntoTrusted == 0) {
            return true;
        }
        ClockEventController$$ExternalSyntheticOutline0.m(iSaveTokenIntoTrusted, "saveTokenIntoTrusted failed ", TAG);
        return false;
    }

    public void unlockViaTrusted(String str, String str2) throws SdpException {
        int iUnlockViaTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iUnlockViaTrusted = iDarManagerService.unlockViaTrusted(str, str2);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
        } else {
            iUnlockViaTrusted = -13;
        }
        if (iUnlockViaTrusted == 0) {
            return;
        }
        Log.e(TAG, "unlockViaTrusted failed " + iUnlockViaTrusted);
        throw new SdpException(iUnlockViaTrusted);
    }
}
