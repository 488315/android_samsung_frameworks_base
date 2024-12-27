package com.android.server.alarm;

import android.content.IntentFilter;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class GmsAlarmManager$$ExternalSyntheticOutline0 {
    public static IntentFilter m(String str, String str2, String str3) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        intentFilter.addAction(str2);
        intentFilter.addAction(str3);
        return intentFilter;
    }

    public static void m(String str, String str2, String str3, String str4, String str5) {
        Slog.d(str5, str + str2 + str3 + str4);
    }

    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Slog.v(str, sb.toString());
    }
}
