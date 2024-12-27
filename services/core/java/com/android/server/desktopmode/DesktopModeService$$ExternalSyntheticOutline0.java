package com.android.server.desktopmode;

import android.util.IndentingPrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class DesktopModeService$$ExternalSyntheticOutline0 {
    public static StringBuilder m(
            StringBuilder sb, boolean z, IndentingPrintWriter indentingPrintWriter, String str) {
        sb.append(z);
        indentingPrintWriter.println(sb.toString());
        return new StringBuilder(str);
    }

    public static void m(int i, String str, String str2) {
        Log.d(str2, str + i);
    }

    public static void m(String str, String str2, String str3, boolean z) {
        Log.d(str3, str + z + str2);
    }
}
