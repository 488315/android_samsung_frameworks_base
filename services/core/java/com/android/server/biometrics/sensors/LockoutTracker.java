package com.android.server.biometrics.sensors;

public interface LockoutTracker {
    default void addFailedAttemptForUser(int i) {}

    int getLockoutModeForUser(int i);

    default void resetFailedAttemptsForUser(int i, boolean z) {}

    void setLockoutModeForUser(int i, int i2);
}
