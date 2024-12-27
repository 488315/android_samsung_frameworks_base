package com.android.server.alarm;

import android.content.IntentFilter;
import android.util.Slog;

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
