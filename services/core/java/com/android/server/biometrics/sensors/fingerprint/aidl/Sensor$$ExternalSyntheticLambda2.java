package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.biometrics.fingerprint.SensorLocation;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Sensor$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SensorLocation sensorLocation = (SensorLocation) obj;
        return new SensorLocationInternal(sensorLocation.display, sensorLocation.sensorLocationX, sensorLocation.sensorLocationY, sensorLocation.sensorRadius);
    }
}
