package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecGutInflater {
    public static NotificationMenuRow.NotificationMenuItem createNotificationMenuItem(Context context, int i, int i2) {
        return new NotificationMenuRow.NotificationMenuItem(context, context.getResources().getString(i), (NotificationGuts.GutsContent) LayoutInflater.from(context).inflate(i2, (ViewGroup) null, false), R.drawable.quickpanel_ic_snooze);
    }
}
