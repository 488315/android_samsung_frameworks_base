package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationMenuRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecGutInflater {
    public static NotificationMenuRow.NotificationMenuItem createNotificationMenuItem(int i, Context context, int i2) {
        return new NotificationMenuRow.NotificationMenuItem(context, context.getResources().getString(i), (NotificationGuts.GutsContent) LayoutInflater.from(context).inflate(i2, (ViewGroup) null, false), R.drawable.quickpanel_ic_snooze);
    }
}
