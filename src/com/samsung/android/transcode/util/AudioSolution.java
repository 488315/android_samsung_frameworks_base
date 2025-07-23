package com.samsung.android.transcode.util;

import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class AudioSolution {
    public native boolean NAACEncoderDeInit(long j);

    public native int NAACEncoderExe(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    public native long NAACEncoderInit(int i, int i2);

    public native long SRCCreate();

    public native void SRCDestroy(long j);

    public native int SRCExe(long j, byte[] bArr, byte[] bArr2, int i);

    public native int SRCExe2(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    public native int SRCInit(long j, int i, int i2, int i3, int i4, int i5);

    public native long VSPCreate();

    public native void VSPDestroy(long j);

    public native int VSPExe(long j, byte[] bArr, byte[] bArr2, int i);

    public native int VSPExe2(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    public native int VSPExe_buffered(long j, byte[] bArr, byte[] bArr2, int i);

    public native int VSPExe_buffered2(long j, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i);

    public native void VSPInit(long j, int i, int i2);

    public native void VSPSetPar(long j, float f);

    static {
        LogS.d("AudioSolution", "starting to loadLibrary v2");
        System.loadLibrary("snaace");
        System.loadLibrary("_SoundAlive_SRC384_ver320");
        System.loadLibrary("SoundAlive_VSP_ver316c_ARMCpp");
        System.loadLibrary("audiosolution_jni");
    }
}
