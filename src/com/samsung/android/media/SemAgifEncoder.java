package com.samsung.android.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SemAgifEncoder {
    private static final String TAG = "SemAgifEncoder";
    private long mHandle = 0;

    private native boolean nativeAddFrame(long j, Bitmap bitmap);

    private native boolean nativeAddFrameMP(long j, Bitmap bitmap);

    private native boolean nativeAddFrameTP(long j, Bitmap bitmap);

    private native boolean nativeFinish(long j);

    private native byte[] nativeFinishByteArray(long j);

    private native void nativeInitHandle(SemAgifEncoder semAgifEncoder);

    private static native ByteArrayOutputStream nativeMakeContactBuffer(byte[] bArr, int i, int i2, int i3);

    private static native int nativeMakeContactImage(String str, String str2, int i);

    private static native int nativeMakeContactImageRect(String str, String str2, int i, int i2, int i3, int i4);

    private static native ByteArrayOutputStream nativeMakeContactRectBuffer(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    private static native ByteArrayOutputStream nativeMakeContactRectResizeBuffer(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeMakeContactResizeImageRect(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6);

    private native void nativeSetDelay(long j, int i);

    private native void nativeSetDispose(long j, int i);

    private native void nativeSetFrameRate(long j, float f);

    private native void nativeSetGlobalSize(long j, int i, int i2);

    private native void nativeSetMaxResolution(long j, int i);

    private native void nativeSetMaxTask(long j, int i);

    private native void nativeSetMaxTaskTP(long j, int i);

    private native void nativeSetPosition(long j, int i, int i2);

    private native void nativeSetQuality(long j, int i);

    private native void nativeSetRepeat(long j, int i);

    private native void nativeSetSize(long j, int i, int i2);

    private native void nativeSetThreshold(long j, int i);

    private native void nativeSetTransPair(long j, int i);

    private native void nativeSetTransparent(long j, int i);

    private native void nativeSetWriteFunc(long j, int i);

    private native boolean nativeStart(long j, String str);

    private native boolean nativeStartByteArray(long j);

    private native boolean nativeStartFD(long j, FileDescriptor fileDescriptor);

    private native void nativeTest(String str);

    public native void nativeSetDither(long j, int i);

    static {
        loadLib();
    }

    public static void loadLib() {
        try {
            System.loadLibrary("agifcodec.media.quram");
            Log.v(TAG, "Load library success : ");
        } catch (Exception e) {
            Log.e(TAG, "Load library fail : " + e.toString());
        }
    }

    public SemAgifEncoder() {
        nativeInitHandle(this);
    }

    public void setDelay(int i) {
        nativeSetDelay(this.mHandle, i);
    }

    public void setDispose(int i) {
        nativeSetDispose(this.mHandle, i);
    }

    public void setDither(int i) {
        nativeSetDither(this.mHandle, i);
    }

    public void setRepeat(int i) {
        nativeSetRepeat(this.mHandle, i);
    }

    public void setTransparent(int i) {
        nativeSetTransparent(this.mHandle, i);
    }

    public void setFrameRate(float f) {
        nativeSetFrameRate(this.mHandle, f);
    }

    public void setMaxTask(int i) {
        nativeSetMaxTask(this.mHandle, i);
    }

    public void setMaxTaskTP(int i) {
        nativeSetMaxTaskTP(this.mHandle, i);
    }

    public void setMaxResolution(int i) {
        nativeSetMaxResolution(this.mHandle, i);
    }

    public void setTransPair(int i) {
        nativeSetTransPair(this.mHandle, i);
    }

    public void setGlobalSize(int i, int i2) {
        nativeSetGlobalSize(this.mHandle, i, i2);
    }

    public void setSize(int i, int i2) {
        nativeSetSize(this.mHandle, i, i2);
    }

    public void setPosition(int i, int i2) {
        nativeSetPosition(this.mHandle, i, i2);
    }

    public boolean start(String str) {
        return nativeStart(this.mHandle, str);
    }

    public boolean startByteArray() {
        return nativeStartByteArray(this.mHandle);
    }

    public boolean startFD(FileDescriptor fileDescriptor) {
        return nativeStartFD(this.mHandle, fileDescriptor);
    }

    public boolean addFrame(Bitmap bitmap) {
        if (bitmap != null) {
            setSize(bitmap.getWidth(), bitmap.getHeight());
        }
        return nativeAddFrame(this.mHandle, bitmap);
    }

    public boolean addFrameMP(Bitmap bitmap) {
        if (bitmap != null) {
            setSize(bitmap.getWidth(), bitmap.getHeight());
        }
        return nativeAddFrameMP(this.mHandle, bitmap);
    }

    public boolean addFrameTP(Bitmap bitmap) {
        if (bitmap != null) {
            setSize(bitmap.getWidth(), bitmap.getHeight());
        }
        return nativeAddFrameTP(this.mHandle, bitmap);
    }

    public void setWriteFunc(int i) {
        nativeSetWriteFunc(this.mHandle, i);
    }

    public void setThreshold(int i) {
        nativeSetThreshold(this.mHandle, i);
    }

    public boolean finish() {
        boolean zNativeFinish = nativeFinish(this.mHandle);
        this.mHandle = 0L;
        return zNativeFinish;
    }

    public byte[] finishByteArray() {
        byte[] bArrNativeFinishByteArray = nativeFinishByteArray(this.mHandle);
        this.mHandle = 0L;
        return bArrNativeFinishByteArray;
    }

    public boolean finishFileDescriptor(FileDescriptor fileDescriptor) throws IOException {
        byte[] bArrNativeFinishByteArray = nativeFinishByteArray(this.mHandle);
        this.mHandle = 0L;
        if (bArrNativeFinishByteArray == null) {
            return false;
        }
        try {
            new FileOutputStream(fileDescriptor).write(bArrNativeFinishByteArray);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean finishURI(Context context, Uri uri) throws IOException {
        FileDescriptor fileDescriptor;
        byte[] bArrNativeFinishByteArray = nativeFinishByteArray(this.mHandle);
        this.mHandle = 0L;
        if (bArrNativeFinishByteArray == null) {
            return false;
        }
        try {
            fileDescriptor = context.getContentResolver().openFileDescriptor(uri, "rw").getFileDescriptor();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileDescriptor = null;
        }
        try {
            new FileOutputStream(fileDescriptor).write(bArrNativeFinishByteArray);
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            return true;
        }
    }
}
