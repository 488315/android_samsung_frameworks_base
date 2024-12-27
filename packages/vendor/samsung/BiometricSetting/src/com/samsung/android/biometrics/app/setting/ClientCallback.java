package com.samsung.android.biometrics.app.setting;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public interface ClientCallback {
    default void onClientFinished(SysUiClient sysUiClient) {}

    default void onClientStarted(SysUiClient sysUiClient) {}
}
