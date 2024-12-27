package com.android.server;

import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class UiModeManagerService$13$$ExternalSyntheticOutline0 {
    public static void m(String str, RemoteException remoteException, PrintWriter printWriter) {
        printWriter.println(str + remoteException);
    }

    public static void m(StringBuilder sb, int i, String str) {
        sb.append(i);
        Log.i(str, sb.toString());
    }

    public static void m(StringBuilder sb, int i, String str, String str2) {
        sb.append(i);
        sb.append(str);
        Slog.w(str2, sb.toString());
    }
}
