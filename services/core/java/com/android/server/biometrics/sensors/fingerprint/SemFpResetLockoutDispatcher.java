package com.android.server.biometrics.sensors.fingerprint;

import android.util.Pair;

public final class SemFpResetLockoutDispatcher {
    public final Pair mProviderPair;

    public SemFpResetLockoutDispatcher(Pair pair) {
        this.mProviderPair = pair;
    }
}
