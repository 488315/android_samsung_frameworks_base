package com.android.server;

import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
