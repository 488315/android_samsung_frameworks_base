package com.samsung.android.biometrics.app.setting;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public interface AuthenticationConsumer {
    void onAuthenticationError(int i, int i2, String str);

    void onAuthenticationFailed(String str);

    void onAuthenticationHelp(int i, String str);

    void onAuthenticationSucceeded(String str);
}
