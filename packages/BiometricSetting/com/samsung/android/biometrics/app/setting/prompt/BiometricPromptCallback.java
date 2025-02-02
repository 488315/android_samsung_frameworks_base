package com.samsung.android.biometrics.app.setting.prompt;

/* loaded from: classes.dex */
public interface BiometricPromptCallback {
    void onConfirmPressed();

    void onDeviceCredentialPressed();

    void onDismissed(int i, byte[] bArr);

    void onModalitySwitched(int i);

    void onNegativeButtonPressed();

    void onPromptError(int i);

    void onSystemEvent();

    void onTryAgainPressed();

    void onUserCancel(int i);
}
