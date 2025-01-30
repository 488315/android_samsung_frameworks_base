package com.android.server.biometrics.sensors.face;

import com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler;

/* loaded from: classes.dex */
public class SemFaceScheduler extends SemConcurrentBiometricScheduler {
    public SemFaceScheduler(String str) {
        super(str, 1, SemFaceMainThread.get().getHandler(), null);
    }
}
