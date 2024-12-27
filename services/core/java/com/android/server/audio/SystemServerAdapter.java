package com.android.server.audio;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.content.Context;
import android.content.Intent;

import com.android.server.LocalServices;

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
