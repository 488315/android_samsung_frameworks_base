package com.samsung.android.battauthmanager;

/* loaded from: classes5.dex */
public class BattAuthHelper {
    public native int close_batt_misc();

    public native byte[] ioctl_longDataRead_batt();

    public native int ioctl_longDataWrite_batt(byte[] bArr);

    public native byte[] makeChallengeReq(int i, int i2);

    public native byte[] makeGetCertReq(int i, int i2, int i3, int i4, int i5, int i6);

    public native byte[] makeGetDigestsReq(byte b, int i);

    public native int open_batt_misc();

    public native int verifyChallengeAuth(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public native byte[] verifyWpcCertChain(byte[] bArr);

    static {
        System.loadLibrary("authentication_jni");
    }
}
