package com.android.server.biometrics.sensors;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.BiometricManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;

import java.util.Map;
import java.util.function.Function;

public final /* synthetic */ class MultiBiometricLockoutState$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MultiBiometricLockoutState.AuthenticatorState authenticatorState =
                (MultiBiometricLockoutState.AuthenticatorState) ((Map.Entry) obj).getValue();
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "(",
                        BiometricManager.authenticatorToStr(
                                authenticatorState.mAuthenticatorType.intValue()),
                        ", permanentLockout=",
                        authenticatorState.mPermanentlyLockedOut ? "true" : "false",
                        ", timedLockout="),
                authenticatorState.mTimedLockout ? "true" : "false",
                ")");
    }
}
