package com.android.server.power;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class LibQmg {
  public String fname;
  public long handle;

  public static native int qmgCheckSupportQmg();

  public static native int qmgClose(long j);

  public static native int qmgGetDelayTime(long j);

  public static native int qmgGetHeight(long j);

  public static native int qmgGetWidth(long j);

  public static native int qmgLoadBitmap(long j, Bitmap bitmap);

  public static native long qmgOpen(String str);

  public LibQmg(String str) {
    this.fname = str;
  }

  public void ensureQmgHandle() {
    if (this.handle == 0) {
      this.handle = qmgOpen(this.fname);
    }
  }

  public int getWidth() {
    ensureQmgHandle();
    return qmgGetWidth(this.handle);
  }

  public int getHeight() {
    ensureQmgHandle();
    return qmgGetHeight(this.handle);
  }

  public int getDelayTime() {
    ensureQmgHandle();
    int qmgGetDelayTime = qmgGetDelayTime(this.handle);
    if (qmgGetDelayTime <= 0) {
      return 33;
    }
    return qmgGetDelayTime;
  }

  public int loadFrame(Bitmap bitmap) {
    return qmgLoadBitmap(this.handle, bitmap);
  }

  public int close() {
    if (alreadyOpen()) {
      return qmgClose(this.handle);
    }
    return 0;
  }

  public final boolean alreadyOpen() {
    return this.handle != 0;
  }

  public static boolean checkSupportQmg() {
    return qmgCheckSupportQmg() == 1;
  }

  public String toString() {
    return "fname: "
        + this.fname
        + " w: "
        + getWidth()
        + " h: "
        + getHeight()
        + " d: "
        + getDelayTime()
        + " handle: "
        + this.handle;
  }
}
