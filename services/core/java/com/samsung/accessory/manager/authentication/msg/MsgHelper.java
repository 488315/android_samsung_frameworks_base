package com.samsung.accessory.manager.authentication.msg;

public class MsgHelper {
    static {
        System.loadLibrary("accauthentication_jni");
    }

    public native int ccic_close();

    public native int ccic_open();

    public native byte[] genRandom();

    public native byte[] ioctl_longDataRead();

    public native byte[] ioctl_longDataRead_batt();

    public native int ioctl_longDataWrite(byte[] bArr);

    public native int ioctl_longDataWrite_batt(byte[] bArr);

    public native boolean verify_certificate(int i, String str, String str2, String str3);

    public native boolean verify_rand_signature(String str, byte[] bArr, String str2, String str3);

    public native int wirelesscharger_open();
}
