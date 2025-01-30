package com.samsung.android.knox.sdp.internal;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.sdp.core.SdpException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean deleteTokenFromTrusted(String str) {
        int deleteToeknFromTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                deleteToeknFromTrusted = iDarManagerService.deleteToeknFromTrusted(str);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
            if (deleteToeknFromTrusted != 0) {
                return true;
            }
            NestedScrollView$$ExternalSyntheticOutline0.m34m("deleteToeknFromTrusted failed ", deleteToeknFromTrusted, TAG);
            return false;
        }
        deleteToeknFromTrusted = -13;
        if (deleteToeknFromTrusted != 0) {
        }
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
            return String.valueOf(supportedSDKVersion);
        }
        supportedSDKVersion = SDK_NOT_SUPPORTED;
        return String.valueOf(supportedSDKVersion);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean saveTokenIntoTrusted(String str, String str2) {
        int saveTokenIntoTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                saveTokenIntoTrusted = iDarManagerService.saveTokenIntoTrusted(str, str2);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
            if (saveTokenIntoTrusted != 0) {
                return true;
            }
            NestedScrollView$$ExternalSyntheticOutline0.m34m("saveTokenIntoTrusted failed ", saveTokenIntoTrusted, TAG);
            return false;
        }
        saveTokenIntoTrusted = -13;
        if (saveTokenIntoTrusted != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void unlockViaTrusted(String str, String str2) {
        int unlockViaTrusted;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                unlockViaTrusted = iDarManagerService.unlockViaTrusted(str, str2);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call save token to the trusted", e);
            }
            if (unlockViaTrusted != 0) {
                return;
            }
            Log.e(TAG, "unlockViaTrusted failed " + unlockViaTrusted);
            throw new SdpException(unlockViaTrusted);
        }
        unlockViaTrusted = -13;
        if (unlockViaTrusted != 0) {
        }
    }
}
