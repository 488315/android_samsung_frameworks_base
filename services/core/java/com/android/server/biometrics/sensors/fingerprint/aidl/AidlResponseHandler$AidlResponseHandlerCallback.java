package com.android.server.biometrics.sensors.fingerprint.aidl;

public interface AidlResponseHandler$AidlResponseHandlerCallback {
    void onEnrollSuccess();

    void onHardwareUnavailable();
}
