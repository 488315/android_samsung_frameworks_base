package com.android.server.am;

import android.content.IntentFilter;
import android.os.Binder;
import android.os.Debug;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract /* synthetic */ class ActivityManagerService$$ExternalSyntheticOutline0 {
    public static String m(int i, int i2, String str, String str2, StringBuilder sb) {
        sb.append(i);
        sb.append(str);
        sb.append(i2);
        sb.append(str2);
        return sb.toString();
    }

    public static String m(StringBuilder sb, String str, String str2, String str3) {
        sb.append(Binder.getCallingPid());
        sb.append(str);
        sb.append(Binder.getCallingUid());
        sb.append(str2);
        String sb2 = sb.toString();
        Slog.w(str3, sb2);
        return sb2;
    }

    public static void m(int i, StringBuilder sb, String str) {
        sb.append(Debug.getCallers(i));
        Slog.d(str, sb.toString());
    }

    public static void m(
            IntentFilter intentFilter, String str, String str2, String str3, String str4) {
        intentFilter.addAction(str);
        intentFilter.addAction(str2);
        intentFilter.addAction(str3);
        intentFilter.addAction(str4);
    }

    public static void m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(remoteException.getMessage());
        Log.e(str, sb.toString());
    }
}
