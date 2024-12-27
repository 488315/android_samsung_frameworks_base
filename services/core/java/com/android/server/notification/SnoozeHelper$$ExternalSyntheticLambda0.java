package com.android.server.notification;

import android.app.PendingIntent;
import android.os.Binder;
import android.util.Slog;

import java.util.Date;

public final /* synthetic */ class SnoozeHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SnoozeHelper f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ SnoozeHelper$$ExternalSyntheticLambda0(
            SnoozeHelper snoozeHelper, String str, long j) {
        this.f$0 = snoozeHelper;
        this.f$1 = str;
        this.f$2 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SnoozeHelper snoozeHelper = this.f$0;
        String str = this.f$1;
        long j = this.f$2;
        snoozeHelper.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PendingIntent createPendingIntent = snoozeHelper.createPendingIntent(str);
            snoozeHelper.mAm.cancel(createPendingIntent);
            if (SnoozeHelper.DEBUG) {
                Slog.d("SnoozeHelper", "Scheduling evaluate for " + new Date(j));
            }
            snoozeHelper.mAm.setExactAndAllowWhileIdle(0, j, createPendingIntent);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
