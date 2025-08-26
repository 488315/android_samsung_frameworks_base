package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class RCPPolicy$$ExternalSyntheticOutline0 {
    public static void m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(Log.getStackTraceString(remoteException));
        Log.e(str, sb.toString());
    }
}
