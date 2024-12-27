package com.samsung.android.biometrics.app.setting;

import android.hardware.fingerprint.IFingerprintService;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.samsung.android.biometrics.app.setting.fingerprint.FpServiceProvider;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final class FpServiceProviderImpl implements FpServiceProvider {
    public final IFingerprintService mIFingerprintService =
            IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));

    public final boolean isKeyguardBouncerShowing() {
        return requestToFpSvc(6, 0, 0L, null) == 1;
    }

    public final int requestToFpSvc(int i, int i2, long j, String str) {
        IFingerprintService iFingerprintService = this.mIFingerprintService;
        if (iFingerprintService == null) {
            return -1;
        }
        try {
            return iFingerprintService.semBioSysUiRequest(i, i2, j, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
