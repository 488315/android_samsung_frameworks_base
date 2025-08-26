package com.android.wm.shell.draganddrop;

import android.app.PendingIntent;
import android.content.ClipData;

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
