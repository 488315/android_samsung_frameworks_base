package com.android.server.audio;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.content.Context;
import android.content.Intent;

import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemServerAdapter {
    public final Context mContext;

    public SystemServerAdapter(Context context) {
        this.mContext = context;
    }

    public void broadcastStickyIntentToCurrentProfileGroup(Intent intent) {
        for (int i :
                ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class))
                        .getCurrentProfileIds()) {
            ActivityManager.broadcastStickyIntent(intent, i);
        }
    }
}
