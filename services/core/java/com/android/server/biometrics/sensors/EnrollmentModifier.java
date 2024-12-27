package com.android.server.biometrics.sensors;

public interface EnrollmentModifier {
    boolean hasEnrollmentStateChanged();

    boolean hasEnrollments();
}
