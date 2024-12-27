package com.android.server.biometrics.sensors.fingerprint;

public interface SemFpTspBlockStatusHandler {
    void onTspBlocked();

    void onTspUnBlocked();
}
