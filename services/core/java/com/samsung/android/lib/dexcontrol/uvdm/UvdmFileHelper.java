package com.samsung.android.lib.dexcontrol.uvdm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class UvdmFileHelper {
    static {
        System.loadLibrary("dexcon-native-lib");
    }

    public native int ccic_close();

    public native int ccic_open();

    public native byte[] ioctl_longDataRead(int i);

    public native int ioctl_longDataWrite(int i, byte[] bArr);

    public native int ioctl_shortDataWrite(int i, byte[] bArr);
}
