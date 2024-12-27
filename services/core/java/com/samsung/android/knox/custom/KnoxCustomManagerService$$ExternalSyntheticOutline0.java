package com.samsung.android.knox.custom;

import android.os.Binder;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
