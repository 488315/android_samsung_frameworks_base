package com.android.server.biometrics.sensors.fingerprint;

import com.android.server.biometrics.SemBiometricDisplayStateMonitor;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemUdfpsHelper$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return SemBiometricDisplayStateMonitor.InstanceHolder.INSTANCE;
            default:
                return SemUdfpsTspManager.get();
        }
    }
}
