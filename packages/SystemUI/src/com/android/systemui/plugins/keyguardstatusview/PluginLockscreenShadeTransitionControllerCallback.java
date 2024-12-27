package com.android.systemui.plugins.keyguardstatusview;

public interface PluginLockscreenShadeTransitionControllerCallback {
    void onExpansionFinished();

    void onExpansionReset();

    void onExpansionStarted();

    void onPulseExpansionFinished();

    void setTransitionToFullShadeAmount(float f);

    void setTransitionToFullShadeAmount(float f, boolean z, long j);
}
