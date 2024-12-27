package com.android.server.desktopmode;

import android.util.IndentingPrintWriter;

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
