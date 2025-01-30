package com.samsung.android.knox.ddar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
