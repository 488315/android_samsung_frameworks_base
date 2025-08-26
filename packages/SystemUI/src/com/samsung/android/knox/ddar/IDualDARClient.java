package com.samsung.android.knox.ddar;

/* loaded from: classes4.dex */
public interface IDualDARClient {
    boolean isSupported(int i);

    void onClearResetPasswordToken(int i, long j);

    boolean onClientBringup();

    void onDataLockStateChange(int i, boolean z);

    boolean onDualDARDestroyForUser(int i);

    boolean onDualDARSetupForUser(int i);

    boolean onPasswordAuth(int i, byte[] bArr);

    boolean onPasswordChange(int i, byte[] bArr, byte[] bArr2);

    boolean onResetPasswordWithToken(int i, byte[] bArr, long j, byte[] bArr2);

    boolean onSetResetPasswordToken(int i, byte[] bArr, long j, byte[] bArr2);
}
