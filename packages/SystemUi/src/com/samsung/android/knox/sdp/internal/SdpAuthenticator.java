package com.samsung.android.knox.sdp.internal;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.sdp.core.SdpException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SdpAuthenticator {
    private static final String TAG = "SdpAuthenticator";
    private static SdpAuthenticator sInstance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));

    private SdpAuthenticator() {
    }

    public static synchronized SdpAuthenticator getInstance() {
        SdpAuthenticator sdpAuthenticator;
        synchronized (SdpAuthenticator.class) {
            if (sInstance == null) {
                sInstance = new SdpAuthenticator();
            }
            sdpAuthenticator = sInstance;
        }
        return sdpAuthenticator;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBiometricsAuthenticated(int i) {
        int i2;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDarManagerService.onBiometricsAuthenticated(i);
                i2 = 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call SDP API", e);
            }
            if (i2 == 0) {
                throw new SdpException(i2);
            }
            return;
        }
        i2 = -13;
        if (i2 == 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onDeviceOwnerLocked(int i) {
        int i2;
        IDarManagerService iDarManagerService = this.mService;
        if (iDarManagerService != null) {
            try {
                iDarManagerService.onDeviceOwnerLocked(i);
                i2 = 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to call SDP API", e);
            }
            if (i2 == 0) {
                throw new SdpException(i2);
            }
            return;
        }
        i2 = -13;
        if (i2 == 0) {
        }
    }
}
