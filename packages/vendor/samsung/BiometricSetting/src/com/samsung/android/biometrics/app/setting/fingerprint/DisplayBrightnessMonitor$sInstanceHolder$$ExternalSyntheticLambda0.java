package com.samsung.android.biometrics.app.setting.fingerprint;

import android.hardware.fingerprint.IFingerprintService;
import android.os.ServiceManager;

import java.util.function.Supplier;

public final /* synthetic */
class DisplayBrightnessMonitor$sInstanceHolder$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
    }
}
