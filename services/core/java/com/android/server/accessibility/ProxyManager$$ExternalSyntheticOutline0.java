package com.android.server.accessibility;

import android.util.Slog;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class ProxyManager$$ExternalSyntheticOutline0 {
    public static void m(int i, String str, String str2) {
        Slog.v(str2, str + i);
    }

    public static void m(PrintWriter printWriter, String str, String str2, StringBuilder sb) {
        sb.append(str);
        sb.append(str2);
        printWriter.println(sb.toString());
    }

    public static void m(String str, StringBuilder sb, boolean z) {
        sb.append(z);
        Slog.v(str, sb.toString());
    }
}
