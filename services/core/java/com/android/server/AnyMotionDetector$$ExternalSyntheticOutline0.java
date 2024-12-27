package com.android.server;

import android.util.Slog;

public abstract /* synthetic */ class AnyMotionDetector$$ExternalSyntheticOutline0 {
    public static String m(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static void m(int i, String str, String str2) {
        Slog.d(str2, str + i);
    }

    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(z);
        Slog.d(str, sb.toString());
    }
}
