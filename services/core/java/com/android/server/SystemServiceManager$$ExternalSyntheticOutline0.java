package com.android.server;

import android.util.Slog;

public abstract /* synthetic */ class SystemServiceManager$$ExternalSyntheticOutline0 {
    public static StringBuilder m(int i, String str, long j, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(j);
        sb.append(str2);
        sb.append(i);
        return sb;
    }

    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Slog.i(str, sb.toString());
    }
}
