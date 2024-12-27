package com.android.systemui.edgelighting.backup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Slog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
