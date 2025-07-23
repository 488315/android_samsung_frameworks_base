package com.samsung.android.knox.sdp.internal;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean deleteTokenFromTrusted(java.lang.String r2) {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            java.lang.String r0 = "SdpTrustedOperations"
            if (r1 == 0) goto L11
            int r1 = r1.deleteToeknFromTrusted(r2)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r1 = move-exception
            java.lang.String r2 = "Failed to call save token to the trusted"
            android.util.Log.e(r0, r2, r1)
        L11:
            r1 = -13
        L13:
            if (r1 == 0) goto L1c
            java.lang.String r2 = "deleteToeknFromTrusted failed "
            com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0.m(r1, r2, r0)
            r1 = 0
            return r1
        L1c:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.internal.SdpTrustedOperations.deleteTokenFromTrusted(java.lang.String):boolean");
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean saveTokenIntoTrusted(java.lang.String r2, java.lang.String r3) {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            java.lang.String r0 = "SdpTrustedOperations"
            if (r1 == 0) goto L11
            int r1 = r1.saveTokenIntoTrusted(r2, r3)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r1 = move-exception
            java.lang.String r2 = "Failed to call save token to the trusted"
            android.util.Log.e(r0, r2, r1)
        L11:
            r1 = -13
        L13:
            if (r1 == 0) goto L1c
            java.lang.String r2 = "saveTokenIntoTrusted failed "
            com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0.m(r1, r2, r0)
            r1 = 0
            return r1
        L1c:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.internal.SdpTrustedOperations.saveTokenIntoTrusted(java.lang.String, java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void unlockViaTrusted(java.lang.String r2, java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            java.lang.String r0 = "SdpTrustedOperations"
            if (r1 == 0) goto L11
            int r1 = r1.unlockViaTrusted(r2, r3)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r1 = move-exception
            java.lang.String r2 = "Failed to call save token to the trusted"
            android.util.Log.e(r0, r2, r1)
        L11:
            r1 = -13
        L13:
            if (r1 != 0) goto L16
            return
        L16:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "unlockViaTrusted failed "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            com.samsung.android.knox.sdp.core.SdpException r2 = new com.samsung.android.knox.sdp.core.SdpException
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.internal.SdpTrustedOperations.unlockViaTrusted(java.lang.String, java.lang.String):void");
    }
}
