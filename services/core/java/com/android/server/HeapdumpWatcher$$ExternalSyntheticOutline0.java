package com.android.server;

import android.util.Slog;

public abstract /* synthetic */ class HeapdumpWatcher$$ExternalSyntheticOutline0 {
    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Slog.w(str, sb.toString());
    }
}
