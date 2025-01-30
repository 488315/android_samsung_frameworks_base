package com.samsung.android.media.heif.jni;

import android.util.Log;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
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
    Log.m98i(TAG, "setup : " + this.mNativeHandle);
  }

  protected void finalize() throws Throwable {
    nativeFinalize();
    super.finalize();
  }

  public void setInt(String key, int val) {
    nativeSetInt(key, val);
  }

  public void setByteBuffer(String key, ByteBuffer buffer) {
    if (buffer == null) {
      throw new IllegalArgumentException("buffer cannot be null");
    }
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException(
          "ByteBuffer is not allocated direct, please allocate direct");
    }
    nativeSetByteBuffer(key, buffer);
  }

  public void setFileDescriptor(String key, FileDescriptor fd) {
    if (fd == null) {
      throw new IllegalArgumentException("fd cannot be null");
    }
    nativeSetFileDescriptor(key, fd);
  }

  public void setMessage(String key, AMessageJNI msg) {
    if (msg == null) {
      throw new IllegalArgumentException("msg cannot be null");
    }
    nativeSetMessage(key, msg);
  }

  public int getInt(String paramString) {
    return nativeGetInt(paramString);
  }

  static {
    System.loadLibrary("heifcapture_jni.media.samsung");
  }
}
