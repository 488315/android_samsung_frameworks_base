package com.samsung.android.knox.analytics.activation.model;

/* loaded from: classes2.dex */
public interface IActivationObserver {
    void onKnoxAnalyticsActivation(boolean z);

    void onKnoxAnalyticsDeactivation(boolean z);

    void onStatusChanged(int i, boolean z, String str);

    void onTriggerChanged(int i, boolean z, String str);
}
