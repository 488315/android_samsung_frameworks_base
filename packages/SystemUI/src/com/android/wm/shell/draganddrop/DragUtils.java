package com.android.wm.shell.draganddrop;

import android.app.PendingIntent;
import android.content.ClipData;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragUtils {
    public static PendingIntent getLaunchIntent(ClipData clipData, int i) {
        if ((i & 8192) == 0) {
            return null;
        }
        for (int i2 = 0; i2 < clipData.getItemCount(); i2++) {
            ClipData.Item itemAt = clipData.getItemAt(i2);
            if (itemAt.getIntentSender() != null) {
                PendingIntent pendingIntent = new PendingIntent(itemAt.getIntentSender().getTarget());
                if (pendingIntent.isActivity()) {
                    return pendingIntent;
                }
            }
        }
        return null;
    }
}
