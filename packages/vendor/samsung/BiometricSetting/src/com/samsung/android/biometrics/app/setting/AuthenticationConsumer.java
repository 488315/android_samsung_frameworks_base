package com.samsung.android.biometrics.app.setting;

public interface AuthenticationConsumer {
    void onAuthenticationError(int i, int i2, String str);

    void onAuthenticationFailed(String str);

    void onAuthenticationHelp(int i, String str);

    void onAuthenticationSucceeded(String str);
}
