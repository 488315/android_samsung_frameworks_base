package com.android.wm.shell.common;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.window.WindowContainerTransaction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HomeIntentProvider {
    public final Context context;

    public HomeIntentProvider(Context context) {
        this.context = context;
    }

    public final void addLaunchHomePendingIntent(WindowContainerTransaction windowContainerTransaction, int i, Integer num) {
        UserHandle of = UserHandle.of(num != null ? num.intValue() : ActivityManager.getCurrentUser());
        Intent intent = new Intent("android.intent.action.MAIN");
        if (i != 0) {
            intent.addCategory("android.intent.category.SECONDARY_HOME");
        } else {
            intent.addCategory("android.intent.category.HOME");
        }
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(1);
        makeBasic.setPendingIntentBackgroundActivityStartMode(3);
        windowContainerTransaction.sendPendingIntent(PendingIntent.getActivityAsUser(this.context, 0, intent, 67108864, null, of), intent, makeBasic.toBundle());
    }
}
