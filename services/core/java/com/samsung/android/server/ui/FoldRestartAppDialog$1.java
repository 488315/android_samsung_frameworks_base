package com.samsung.android.server.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes2.dex */
public class FoldRestartAppDialog$1 extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
            throw null;
        }
    }
}
