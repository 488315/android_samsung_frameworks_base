package com.samsung.vekit.External;

import android.p009os.Build;
import android.util.Log;

/* loaded from: classes6.dex */
class NativeLibSetup {
  private static final String TAG = "NativeLibSetup";
  private static final String list_tag = ".videoeditor.samsung";

  NativeLibSetup() {}

  public void init() {
    setCurrentDevice();
    loadNativeLibraries();
  }

  private void loadNativeLibraries() {
    System.loadLibrary("veframework.videoeditor.samsung");
    Log.m94d(TAG, "USED LIBS veframework.videoeditor.samsung");
  }

  private void setCurrentDevice() {
    StringBuffer buf = new StringBuffer();
    buf.append("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
    buf.append("\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
    buf.append("\nVERSION.SDK_INT {" + Build.VERSION.SDK_INT + "}");
    buf.append("\nFINGERPRINT {" + Build.FINGERPRINT + "}");
    buf.append("\nBOARD {" + Build.BOARD + "}");
    buf.append("\nBRAND {" + Build.BRAND + "}");
    buf.append("\nDEVICE {" + Build.DEVICE + "}");
    buf.append("\nMANUFACTURER {" + Build.MANUFACTURER + "}");
    buf.append("\nMODEL {" + Build.MODEL + "}");
    Log.m94d(TAG, " " + buf.toString());
  }
}
