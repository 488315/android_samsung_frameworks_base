package com.samsung.android.biometrics.app.setting.fingerprint;

import android.hardware.fingerprint.IFingerprintService;
import android.os.ServiceManager;

import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */
class DisplayBrightnessMonitor$sInstanceHolder$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
    }
}
