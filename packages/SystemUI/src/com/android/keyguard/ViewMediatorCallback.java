package com.android.keyguard;

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
