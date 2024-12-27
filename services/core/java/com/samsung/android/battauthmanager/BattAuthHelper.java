package com.samsung.android.battauthmanager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class BattAuthHelper {
    static {
        System.loadLibrary("authentication_jni");
    }

    public native byte[] makeChallengeReq(int i, int i2);

    public native byte[] makeGetCertReq(int i, int i2, int i3, int i4, int i5, int i6);

    public native byte[] makeGetDigestsReq(byte b, int i);

    public native byte[] readDataFromBattMisc();

    public native boolean verifyChallengeAuth(
            byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4);

    public native byte[] verifyWpcCertChain(byte[] bArr);

    public native int writeDataToBattMisc(byte[] bArr);
}
