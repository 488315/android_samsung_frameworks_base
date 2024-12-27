package com.android.server.backup.transport;

import android.util.Log;
import android.util.Slog;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

public abstract class TransportUtils {
    public static String formatMessage(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(" ");
        }
        if (str2 != null) {
            RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, "[", str2, "] ");
        }
        sb.append(str3);
        return sb.toString();
    }

    public static void log(int i, String str, String str2) {
        if (i == -1) {
            Slog.wtf(str, str2);
        } else if (Log.isLoggable(str, i)) {
            Slog.println(i, str, str2);
        }
    }
}
