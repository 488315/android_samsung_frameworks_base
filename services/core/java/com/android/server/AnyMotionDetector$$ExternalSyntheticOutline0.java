package com.android.server;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
