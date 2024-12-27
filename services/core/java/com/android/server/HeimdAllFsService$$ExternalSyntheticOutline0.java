package com.android.server;

import android.util.Slog;

public abstract /* synthetic */ class HeimdAllFsService$$ExternalSyntheticOutline0 {
    public static void m(String str, String str2, String str3) {
        Slog.w(str3, str + str2);
    }

    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(z);
        Slog.i(str, sb.toString());
    }
}
