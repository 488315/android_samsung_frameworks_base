package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.face.Face;

import java.util.function.Function;

public final /* synthetic */ class FaceProvider$$ExternalSyntheticLambda3 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((Face) obj).getBiometricId());
    }
}
