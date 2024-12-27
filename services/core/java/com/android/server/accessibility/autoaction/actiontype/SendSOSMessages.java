package com.android.server.accessibility.autoaction.actiontype;

import android.app.Instrumentation;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SendSOSMessages extends CornerActionType {
    public Context mContext;
    public int mUserId;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        new Thread(new Runnable() { // from class: com.android.server.accessibility.autoaction.actiontype.SendSOSMessages.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Thread.sleep(100L);
                    int intForUser = Settings.System.getIntForUser(SendSOSMessages.this.mContext.getContentResolver(), "send_emergency_message_power_number", 5, SendSOSMessages.this.mUserId);
                    for (int i2 = 0; i2 < intForUser; i2++) {
                        new Instrumentation().sendKeyDownUpSync(26);
                    }
                } catch (Exception e) {
                    Log.w("SendSOSMessages", "Exception!", e);
                }
            }
        }).start();
    }
}
