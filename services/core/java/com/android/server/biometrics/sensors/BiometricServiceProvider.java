package com.android.server.biometrics.sensors;

import java.util.List;

public interface BiometricServiceProvider {
    boolean containsSensor(int i);

    List getSensorProperties();

    boolean hasEnrollments(int i, int i2);
}
