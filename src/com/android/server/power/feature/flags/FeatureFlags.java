package com.android.server.power.feature.flags;

/* loaded from: classes6.dex */
public interface FeatureFlags {
    boolean disableFrozenProcessWakelocks();

    boolean enableEarlyScreenTimeoutDetector();

    boolean enableScreenTimeoutPolicyListenerApi();

    boolean frameworkWakelockInfo();

    boolean improveWakelockLatency();

    boolean moveWscLoggingToNotifier();

    boolean perDisplayWakeByTouch();

    boolean policyReasonInDisplayPowerRequest();

    boolean wakelockAttributionViaWorkchain();
}
