package com.android.server;

import android.util.Slog;

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
