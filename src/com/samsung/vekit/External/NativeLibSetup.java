package com.samsung.vekit.External;

import android.os.Build;
import android.util.Log;

/* loaded from: classes6.dex */
class NativeLibSetup {
    private static final String TAG = "NativeLibSetup";
    private static final String list_tag = ".videoeditor.samsung";

    NativeLibSetup() {
    }

    public void init() {
        setCurrentDevice();
        loadNativeLibraries();
    }

    private void loadNativeLibraries() {
        System.loadLibrary("veframework.videoeditor.samsung");
        Log.d(TAG, "USED LIBS veframework.videoeditor.samsung");
    }

    private void setCurrentDevice() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        stringBuffer.append("\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
        stringBuffer.append("\nVERSION.SDK_INT {" + Build.VERSION.SDK_INT + "}");
        stringBuffer.append("\nFINGERPRINT {" + Build.FINGERPRINT + "}");
        stringBuffer.append("\nBOARD {" + Build.BOARD + "}");
        stringBuffer.append("\nBRAND {" + Build.BRAND + "}");
        stringBuffer.append("\nDEVICE {" + Build.DEVICE + "}");
        stringBuffer.append("\nMANUFACTURER {" + Build.MANUFACTURER + "}");
        stringBuffer.append("\nMODEL {" + Build.MODEL + "}");
        StringBuilder sb = new StringBuilder(" ");
        sb.append(stringBuffer.toString());
        Log.d(TAG, sb.toString());
    }
}
