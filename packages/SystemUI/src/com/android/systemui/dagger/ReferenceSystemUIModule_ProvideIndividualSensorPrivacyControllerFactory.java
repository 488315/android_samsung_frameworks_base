package com.android.systemui.dagger;

import android.hardware.SensorPrivacyManager;
import android.util.ArraySet;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import dagger.internal.Provider;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final class ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory implements Provider {
    public final Provider sensorPrivacyManagerProvider;
    public final Provider userTrackerProvider;

    public ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory(Provider provider, Provider provider2) {
        this.sensorPrivacyManagerProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static IndividualSensorPrivacyControllerImpl provideIndividualSensorPrivacyController(SensorPrivacyManager sensorPrivacyManager, UserTracker userTracker) {
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = new IndividualSensorPrivacyControllerImpl(sensorPrivacyManager, userTracker);
        individualSensorPrivacyControllerImpl.mSensorPrivacyManager.addSensorPrivacyListener(new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl.1
            public AnonymousClass1() {
            }

            public final void onSensorPrivacyChanged(int i, boolean z) {
            }

            public final void onSensorPrivacyChanged(SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams sensorPrivacyChangedParams) {
                IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl2 = IndividualSensorPrivacyControllerImpl.this;
                int sensor = sensorPrivacyChangedParams.getSensor();
                int toggleType = sensorPrivacyChangedParams.getToggleType();
                boolean zIsEnabled = sensorPrivacyChangedParams.isEnabled();
                if (toggleType == 1) {
                    individualSensorPrivacyControllerImpl2.mSoftwareToggleState.put(sensor, zIsEnabled);
                } else if (toggleType == 2) {
                    individualSensorPrivacyControllerImpl2.mHardwareToggleState.put(sensor, zIsEnabled);
                } else {
                    individualSensorPrivacyControllerImpl2.getClass();
                }
                Iterator it = new ArraySet(individualSensorPrivacyControllerImpl2.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((IndividualSensorPrivacyController.Callback) it.next()).onSensorBlockedChanged(sensor, individualSensorPrivacyControllerImpl2.isSensorBlocked(sensor));
                }
            }
        });
        int[] iArr = IndividualSensorPrivacyControllerImpl.SENSORS;
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            boolean zIsSensorPrivacyEnabled = individualSensorPrivacyControllerImpl.mSensorPrivacyManager.isSensorPrivacyEnabled(1, i2);
            boolean zIsSensorPrivacyEnabled2 = individualSensorPrivacyControllerImpl.mSensorPrivacyManager.isSensorPrivacyEnabled(2, i2);
            individualSensorPrivacyControllerImpl.mSoftwareToggleState.put(i2, zIsSensorPrivacyEnabled);
            individualSensorPrivacyControllerImpl.mHardwareToggleState.put(i2, zIsSensorPrivacyEnabled2);
        }
        return individualSensorPrivacyControllerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideIndividualSensorPrivacyController((SensorPrivacyManager) this.sensorPrivacyManagerProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
