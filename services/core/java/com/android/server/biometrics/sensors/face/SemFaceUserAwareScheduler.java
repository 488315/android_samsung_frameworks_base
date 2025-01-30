package com.android.server.biometrics.sensors.face;

import android.hardware.biometrics.IBiometricService;
import android.os.Handler;
import android.os.ServiceManager;
import com.android.server.biometrics.sensors.UserAwareBiometricScheduler;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;

/* loaded from: classes.dex */
public class SemFaceUserAwareScheduler extends UserAwareBiometricScheduler {
    public SemFaceUserAwareScheduler(String str, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, UserAwareBiometricScheduler.CurrentUserRetriever currentUserRetriever, UserAwareBiometricScheduler.UserSwitchCallback userSwitchCallback) {
        super(str, new Handler(SemFaceMainThread.get().getLooper()), i, gestureAvailabilityDispatcher, IBiometricService.Stub.asInterface(ServiceManager.getService("biometric")), currentUserRetriever, userSwitchCallback);
    }
}
