package com.samsung.android.knox.container;

import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class RCPPolicy$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static void m244m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(Log.getStackTraceString(remoteException));
        Log.e(str, sb.toString());
    }
}
