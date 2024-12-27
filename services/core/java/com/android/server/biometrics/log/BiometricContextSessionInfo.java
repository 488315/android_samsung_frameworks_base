package com.android.server.biometrics.log;

import com.android.internal.logging.InstanceId;

import java.util.concurrent.atomic.AtomicInteger;

public final class BiometricContextSessionInfo {
    public final InstanceId mId;
    public final AtomicInteger mOrder = new AtomicInteger(0);

    public BiometricContextSessionInfo(InstanceId instanceId) {
        this.mId = instanceId;
    }

    public final String toString() {
        return "[sid: " + this.mId.getId() + "]";
    }
}
