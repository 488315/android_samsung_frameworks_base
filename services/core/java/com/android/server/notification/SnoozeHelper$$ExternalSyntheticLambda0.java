package com.android.server.notification;

import android.app.PendingIntent;
import android.os.Binder;
import android.util.Slog;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SnoozeHelper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SnoozeHelper f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ SnoozeHelper$$ExternalSyntheticLambda0(SnoozeHelper snoozeHelper, String str, long j) {
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
