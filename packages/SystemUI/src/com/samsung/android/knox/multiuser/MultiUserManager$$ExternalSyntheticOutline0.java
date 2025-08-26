package com.samsung.android.knox.multiuser;

import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes4.dex */
public abstract /* synthetic */ class MultiUserManager$$ExternalSyntheticOutline0 {
    public static void m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(remoteException.getMessage());
        Log.w(str, sb.toString());
    }
}
