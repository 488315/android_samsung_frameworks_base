package com.samsung.android.ims;

/* loaded from: classes5.dex */
public interface SemAutoConfigurationListener {
    void onAutoConfigurationCompleted(boolean z);

    void onIidTokenNeeded();

    void onMsisdnNumberNeeded();

    void onVerificationCodeNeeded();
}
