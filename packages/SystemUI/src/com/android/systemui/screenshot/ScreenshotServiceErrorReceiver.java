package com.android.systemui.screenshot;

import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ScreenshotServiceErrorReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        new ScreenshotNotificationsController(0, context, (NotificationManager) context.getSystemService(NotificationManager.class), (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).notifyScreenshotError(R.string.screenshot_failed_to_save_unknown_text);
    }
}
