package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import com.android.server.biometrics.sensors.BiometricUtils;
import com.android.server.biometrics.sensors.InternalEnumerateClient;
import com.android.server.biometrics.sensors.fingerprint.SemFpBaseRequestClient;
import java.util.List;
import java.util.function.Supplier;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;

/* loaded from: classes.dex */
public class FingerprintInternalEnumerateClient extends InternalEnumerateClient {
    public FingerprintInternalEnumerateClient(Context context, Supplier supplier, IBinder iBinder, int i, String str, List list, BiometricUtils biometricUtils, int i2, BiometricLogger biometricLogger, BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void startHalOperation() {
        if (!canUseEnumerateOperation()) {
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            ((AidlSession) getFreshDaemon()).getSession().enumerateEnrollments();
        } catch (RemoteException | NullPointerException e) {
            Slog.e("FingerprintInternalEnumerateClient", "Remote exception when requesting enumerate", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    public final boolean canUseEnumerateOperation() {
        return ((AidlSession) getFreshDaemon()).getSehFingerprint() == null || new SemFpBaseRequestClient.Builder(getContext(), getBiometricContext(), new Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInternalEnumerateClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                ISehFingerprint lambda$canUseEnumerateOperation$0;
                lambda$canUseEnumerateOperation$0 = FingerprintInternalEnumerateClient.this.lambda$canUseEnumerateOperation$0();
                return lambda$canUseEnumerateOperation$0;
            }
        }, getSensorId()).setCommand(11).build().startWithoutScheduler() >= 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ISehFingerprint lambda$canUseEnumerateOperation$0() {
        return ((AidlSession) getFreshDaemon()).getSehFingerprint();
    }
}
