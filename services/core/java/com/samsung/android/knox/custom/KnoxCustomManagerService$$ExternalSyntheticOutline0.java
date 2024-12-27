package com.samsung.android.knox.custom;

import android.os.Binder;
import android.util.Log;

public abstract /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticOutline0 {
    public static int m(StringBuilder sb, String str) {
        sb.append(Integer.toString(Binder.getCallingUid()));
        sb.append(str);
        return Binder.getCallingPid();
    }

    public static Integer m(String str, Exception exc, String str2, int i) {
        Log.e(str2, str + exc);
        return Integer.valueOf(i);
    }
}
