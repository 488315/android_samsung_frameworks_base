package com.samsung.android.knox.sdp.internal;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.sdp.core.SdpException;

/* loaded from: classes4.dex */
public class SdpAuthenticator {
    private static final String TAG = "SdpAuthenticator";
    private static SdpAuthenticator sInstance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));

    private SdpAuthenticator() {
    }

    public static synchronized SdpAuthenticator getInstance() {
        try {
            if (sInstance == null) {
                sInstance = new SdpAuthenticator();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public void onBiometricsAuthenticated(int i) throws SdpException {
        int i2;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDarManagerService.onBiometricsAuthenticated(i);
                i2 = 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call SDP API", e);
            }
        } else {
            i2 = -13;
        }
        if (i2 != 0) {
            throw new SdpException(i2);
        }
    }

    public void onDeviceOwnerLocked(int i) throws SdpException {
        int i2;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDarManagerService.onDeviceOwnerLocked(i);
                i2 = 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call SDP API", e);
            }
        } else {
            i2 = -13;
        }
        if (i2 != 0) {
            throw new SdpException(i2);
        }
    }
}
