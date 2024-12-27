package com.android.server.biometrics.sensors.face;

import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.face.IFaceService;
import android.os.IInterface;
import android.os.RemoteException;

import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.BiometricServiceRegistry;

import java.util.List;
import java.util.function.Supplier;

public final class FaceServiceRegistry extends BiometricServiceRegistry {
    public final IFaceService mService;

    public FaceServiceRegistry(IFaceService iFaceService, Supplier supplier) {
        super(supplier);
        this.mService = iFaceService;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public final void invokeRegisteredCallback(IInterface iInterface, List list) {
        ((IFaceAuthenticatorsRegisteredCallback) iInterface).onAllAuthenticatorsRegistered(list);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public final void registerService(
            IBiometricService iBiometricService,
            SensorPropertiesInternal sensorPropertiesInternal) {
        FaceSensorPropertiesInternal faceSensorPropertiesInternal =
                (FaceSensorPropertiesInternal) sensorPropertiesInternal;
        try {
            iBiometricService.registerAuthenticator(
                    faceSensorPropertiesInternal.sensorId,
                    8,
                    Utils.propertyStrengthToAuthenticatorStrength(
                            faceSensorPropertiesInternal.sensorStrength),
                    new FaceAuthenticator(this.mService, faceSensorPropertiesInternal.sensorId));
        } catch (RemoteException unused) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Remote exception when registering sensorId: "),
                    faceSensorPropertiesInternal.sensorId,
                    "FaceServiceRegistry");
        }
    }
}
