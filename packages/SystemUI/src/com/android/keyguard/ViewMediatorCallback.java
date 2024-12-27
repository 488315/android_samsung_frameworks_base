package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
