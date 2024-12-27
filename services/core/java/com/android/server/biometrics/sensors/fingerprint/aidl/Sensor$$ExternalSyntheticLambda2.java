package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.biometrics.fingerprint.SensorLocation;

import java.util.function.Function;

public final /* synthetic */ class Sensor$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SensorLocation sensorLocation = (SensorLocation) obj;
        return new SensorLocationInternal(
                sensorLocation.display,
                sensorLocation.sensorLocationX,
                sensorLocation.sensorLocationY,
                sensorLocation.sensorRadius);
    }
}
