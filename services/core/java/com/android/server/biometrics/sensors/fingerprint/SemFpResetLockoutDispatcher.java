package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;

/* loaded from: classes.dex */
public class SemFpResetLockoutDispatcher implements SemFpResetLockoutListener {
    public final Pair mProviderPair;

    public SemFpResetLockoutDispatcher(Pair pair) {
        this.mProviderPair = pair;
    }

    public void start() {
        ((ServiceProvider) this.mProviderPair.second).semAddResetLockoutListener(this);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpResetLockoutListener
    public void onResetLockout(int i, int i2) {
        Pair pair = this.mProviderPair;
        ((ServiceProvider) pair.second).semRequest(((Integer) pair.first).intValue(), 42, 0, null, null);
    }
}
