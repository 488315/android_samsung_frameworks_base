package com.android.systemui.plugins.keyguardstatusview;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface PluginLockscreenShadeTransitionControllerCallback {
    void onExpansionFinished();

    void onExpansionReset();

    void onExpansionStarted();

    void onPulseExpansionFinished();

    void setTransitionToFullShadeAmount(float f);

    void setTransitionToFullShadeAmount(float f, boolean z, long j);
}
