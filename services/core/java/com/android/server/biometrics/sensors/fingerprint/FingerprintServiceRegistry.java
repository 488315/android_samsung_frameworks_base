package com.android.server.biometrics.sensors.fingerprint;

import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IFingerprintService;
import android.os.IInterface;
import android.os.RemoteException;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BiometricServiceRegistry;
import com.android.server.biometrics.sensors.fingerprint.FingerprintService;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintServiceRegistry extends BiometricServiceRegistry {
    public final IFingerprintService mService;

    public FingerprintServiceRegistry(FingerprintService.AnonymousClass1 anonymousClass1, Supplier supplier) {
        super(supplier);
        this.mService = anonymousClass1;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public final void invokeRegisteredCallback(IInterface iInterface, List list) {
        ((IFingerprintAuthenticatorsRegisteredCallback) iInterface).onAllAuthenticatorsRegistered(list);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public final void registerService(IBiometricService iBiometricService, SensorPropertiesInternal sensorPropertiesInternal) {
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) sensorPropertiesInternal;
        try {
            iBiometricService.registerAuthenticator(fingerprintSensorPropertiesInternal.sensorId, 2, Utils.propertyStrengthToAuthenticatorStrength(fingerprintSensorPropertiesInternal.sensorStrength), new FingerprintAuthenticator(this.mService, fingerprintSensorPropertiesInternal.sensorId));
        } catch (RemoteException unused) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Remote exception when registering sensorId: "), fingerprintSensorPropertiesInternal.sensorId, "FingerprintServiceRegistry");
        }
    }
}
