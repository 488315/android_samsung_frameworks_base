package com.samsung.android.knox.multiuser;

import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class MultiUserManager$$ExternalSyntheticOutline0 {
    public static void m(RemoteException remoteException, StringBuilder sb, String str) {
        sb.append(remoteException.getMessage());
        Log.w(str, sb.toString());
    }
}
