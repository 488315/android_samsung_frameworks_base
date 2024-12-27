package com.android.systemui.statusbar.notification.row;

import android.net.Uri;
import android.os.SystemClock;
import java.util.function.Consumer;

public final /* synthetic */ class NotificationInlineImageResolver$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ NotificationInlineImageResolver f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ NotificationInlineImageResolver$$ExternalSyntheticLambda1(NotificationInlineImageResolver notificationInlineImageResolver, long j) {
        this.f$0 = notificationInlineImageResolver;
        this.f$1 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        NotificationInlineImageResolver notificationInlineImageResolver = this.f$0;
        long j = this.f$1;
        notificationInlineImageResolver.getClass();
        notificationInlineImageResolver.loadImageFromCache((Uri) obj, j - SystemClock.elapsedRealtime());
    }
}
