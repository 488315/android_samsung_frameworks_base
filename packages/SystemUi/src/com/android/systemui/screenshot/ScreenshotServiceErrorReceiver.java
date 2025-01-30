package com.android.systemui.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ScreenshotServiceErrorReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        new ScreenshotNotificationsController(context, (WindowManager) context.getSystemService("window")).notifyScreenshotError(R.string.screenshot_failed_to_save_unknown_text);
    }
}
