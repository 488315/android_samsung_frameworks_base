package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;

public final class SecGutInflater {
    public static NotificationMenuRow.NotificationMenuItem createNotificationMenuItem(Context context, int i, int i2) {
        return new NotificationMenuRow.NotificationMenuItem(context, context.getResources().getString(i), (NotificationGuts.GutsContent) LayoutInflater.from(context).inflate(i2, (ViewGroup) null, false), R.drawable.quickpanel_ic_snooze);
    }
}
