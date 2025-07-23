package com.samsung.android.media.heif.jni;

import android.util.Log;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class AMessageJNI {
    private static final String TAG = "AMessageJNI";
    private long mNativeHandle;

    private native void nativeFinalize();

    private native void nativeSetup();

    public native int nativeGetInt(String str);

    public native void nativeSetByteBuffer(String str, ByteBuffer byteBuffer);

    public native void nativeSetFileDescriptor(String str, FileDescriptor fileDescriptor);

    public native void nativeSetInt(String str, int i);

    public native void nativeSetMessage(String str, AMessageJNI aMessageJNI);

    public AMessageJNI() {
        nativeSetup();
        Log.i(TAG, "setup : " + this.mNativeHandle);
    }

    protected void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public void setInt(String str, int i) {
        nativeSetInt(str, i);
    }

    public void setByteBuffer(String str, ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("buffer cannot be null");
        }
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer is not allocated direct, please allocate direct");
        }
        nativeSetByteBuffer(str, byteBuffer);
    }

    public void setFileDescriptor(String str, FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) {
            throw new IllegalArgumentException("fd cannot be null");
        }
        nativeSetFileDescriptor(str, fileDescriptor);
    }

    public void setMessage(String str, AMessageJNI aMessageJNI) {
        if (aMessageJNI == null) {
            throw new IllegalArgumentException("msg cannot be null");
        }
        nativeSetMessage(str, aMessageJNI);
    }

    public int getInt(String str) {
        return nativeGetInt(str);
    }

    static {
        System.loadLibrary("heifcapture_jni.media.samsung");
    }
}
