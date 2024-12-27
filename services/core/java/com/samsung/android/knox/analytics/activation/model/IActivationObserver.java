package com.samsung.android.knox.analytics.activation.model;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface IActivationObserver {
    void onKnoxAnalyticsActivation(boolean z);

    void onKnoxAnalyticsDeactivation(boolean z);

    void onStatusChanged(int i, boolean z, String str);

    void onTriggerChanged(int i, boolean z, String str);
}
