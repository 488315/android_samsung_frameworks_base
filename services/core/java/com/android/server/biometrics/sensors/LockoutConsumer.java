package com.android.server.biometrics.sensors;

public interface LockoutConsumer {
    void onLockoutPermanent();

    void onLockoutTimed(long j);
}
