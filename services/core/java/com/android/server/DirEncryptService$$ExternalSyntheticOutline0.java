package com.android.server;

import android.util.Log;

public abstract /* synthetic */ class DirEncryptService$$ExternalSyntheticOutline0 {
    public static StringBuilder m(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        return sb;
    }

    public static void m(int i, String str, String str2) {
        Log.i(str2, str + i);
    }
}
