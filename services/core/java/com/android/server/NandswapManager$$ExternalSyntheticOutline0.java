package com.android.server;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class NandswapManager$$ExternalSyntheticOutline0 {
    public static String m(int i, String str) {
        return i + str;
    }

    public static void m(int i, String str, String str2) {
        Slog.e(str2, str + i);
    }

    public static void m(Exception exc, StringBuilder sb, String str) {
        sb.append(exc.getMessage());
        Slog.e(str, sb.toString());
    }

    public static void m(StringBuilder sb, int i, String str, String str2) {
        sb.append(i);
        sb.append(str);
        Slog.e(str2, sb.toString());
    }
}
