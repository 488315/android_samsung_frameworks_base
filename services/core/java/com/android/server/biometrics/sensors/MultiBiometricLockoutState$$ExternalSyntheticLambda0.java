package com.android.server.biometrics.sensors;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.BiometricManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.MultiBiometricLockoutState;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MultiBiometricLockoutState$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        MultiBiometricLockoutState.AuthenticatorState authenticatorState = (MultiBiometricLockoutState.AuthenticatorState) ((Map.Entry) obj).getValue();
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("(", BiometricManager.authenticatorToStr(authenticatorState.mAuthenticatorType.intValue()), ", permanentLockout=", authenticatorState.mPermanentlyLockedOut ? "true" : "false", ", timedLockout="), authenticatorState.mTimedLockout ? "true" : "false", ")");
    }
}
