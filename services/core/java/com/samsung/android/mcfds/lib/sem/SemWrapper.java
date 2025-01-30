package com.samsung.android.mcfds.lib.sem;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.UserHandle;

/* loaded from: classes2.dex */
public abstract class SemWrapper {
    public static boolean semBindServiceAsUser(Context context, Intent intent, ServiceConnection serviceConnection, int i, UserHandle userHandle) {
        return context.semBindServiceAsUser(intent, serviceConnection, i, userHandle);
    }
}
