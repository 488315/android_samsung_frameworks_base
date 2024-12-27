package com.android.server.power.feature.flags;

public interface FeatureFlags {
    boolean enableEarlyScreenTimeoutDetector();

    boolean improveWakelockLatency();
}
