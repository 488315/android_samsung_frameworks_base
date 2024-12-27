package com.android.server;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class HeimdAllFsService$$ExternalSyntheticOutline0 {
    public static void m(String str, String str2, String str3) {
        Slog.w(str3, str + str2);
    }

    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(z);
        Slog.i(str, sb.toString());
    }
}
