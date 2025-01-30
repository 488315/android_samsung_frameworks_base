package com.samsung.android.lib.dexcontrol.uvdm;

/* loaded from: classes2.dex */
public class UvdmFileHelper {
    public native int ccic_close();

    public native int ccic_open();

    public native byte[] ioctl_longDataRead(int i);

    public native int ioctl_longDataWrite(int i, byte[] bArr);

    public native int ioctl_shortDataWrite(int i, byte[] bArr);

    static {
        System.loadLibrary("dexcon-native-lib");
    }
}
