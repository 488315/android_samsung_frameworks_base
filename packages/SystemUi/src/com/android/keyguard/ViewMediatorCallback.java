package com.android.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ViewMediatorCallback {
    CharSequence consumeCustomMessage();

    int getBouncerPromptReason();

    boolean isScreenOn();

    void keyguardDone(int i);

    void keyguardDoneDrawing();

    void keyguardDonePending(int i);

    void keyguardGone();

    void onCancelClicked();

    void playTrustedSound();

    void readyForKeyguardDone();

    void resetKeyguard();

    void setCustomMessage(CharSequence charSequence);

    void setNeedsInput(boolean z);

    void userActivity();
}
