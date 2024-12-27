package com.android.server.biometrics.sensors.iris;

import android.content.Context;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.iris.IIrisService;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.Watchdog$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IrisService extends SystemService {
    public final IrisServiceWrapper mServiceWrapper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IrisServiceWrapper extends IIrisService.Stub {
        public IrisServiceWrapper() {}

        public final void registerAuthenticators(final List list) {
            registerAuthenticators_enforcePermission();
            new Handler(Watchdog$$ExternalSyntheticOutline0.m(10, "IrisService", true).getLooper())
                    .post(
                            new Runnable() { // from class:
                                             // com.android.server.biometrics.sensors.iris.IrisService$IrisServiceWrapper$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    IrisService.IrisServiceWrapper irisServiceWrapper =
                                            IrisService.IrisServiceWrapper.this;
                                    List<SensorPropertiesInternal> list2 = list;
                                    irisServiceWrapper.getClass();
                                    IBiometricService asInterface =
                                            IBiometricService.Stub.asInterface(
                                                    ServiceManager.getService("biometric"));
                                    for (SensorPropertiesInternal sensorPropertiesInternal :
                                            list2) {
                                        int i = sensorPropertiesInternal.sensorId;
                                        int propertyStrengthToAuthenticatorStrength =
                                                Utils.propertyStrengthToAuthenticatorStrength(
                                                        sensorPropertiesInternal.sensorStrength);
                                        IrisService.IrisServiceWrapper irisServiceWrapper2 =
                                                IrisService.this.mServiceWrapper;
                                        try {
                                            asInterface.registerAuthenticator(
                                                    i,
                                                    4,
                                                    propertyStrengthToAuthenticatorStrength,
                                                    new IrisAuthenticator());
                                        } catch (RemoteException unused) {
                                            NandswapManager$$ExternalSyntheticOutline0.m(
                                                    i,
                                                    "Remote exception when registering sensorId: ",
                                                    "IrisService");
                                        }
                                    }
                                }
                            });
        }
    }

    public IrisService(Context context) {
        super(context);
        this.mServiceWrapper = new IrisServiceWrapper();
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("iris", this.mServiceWrapper);
    }
}
