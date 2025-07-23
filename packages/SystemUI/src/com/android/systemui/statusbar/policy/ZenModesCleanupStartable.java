package com.android.systemui.statusbar.policy;

import android.app.NotificationManager;
import com.android.systemui.CoreStartable;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ZenModesCleanupStartable implements CoreStartable {
    public final CoroutineContext bgContext;
    public final NotificationManager notificationManager;

    public ZenModesCleanupStartable(CoroutineScope coroutineScope, CoroutineContext coroutineContext, NotificationManager notificationManager) {
        this.bgContext = coroutineContext;
        this.notificationManager = notificationManager;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
