package com.android.systemui.edgelighting.backup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightingBRReceiver extends BroadcastReceiver {
    public EdgeLightingBRThread mKiesThread = null;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("com.samsung.android.intent.action.REQUEST_RESTORE_EDGELIGHTING")) {
            if (intent.getIntExtra("ACTION", 0) != 2) {
                EdgeLightingBRThread edgeLightingBRThread = new EdgeLightingBRThread(context, intent);
                this.mKiesThread = edgeLightingBRThread;
                edgeLightingBRThread.start();
            } else {
                EdgeLightingBRThread edgeLightingBRThread2 = this.mKiesThread;
                if (edgeLightingBRThread2 == null || !edgeLightingBRThread2.isAlive()) {
                    return;
                }
                Slog.e("EdgeLightingBRReceiver", "Cancel request");
                this.mKiesThread.mHandler.sendEmptyMessage(2);
            }
        }
    }
}
