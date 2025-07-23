package com.android.systemui.statusbar.notification.collection.provider;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LaunchFullScreenIntentProvider {
    public final ListenerSet listeners = new ListenerSet();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final void launchFullScreenIntent(NotificationEntry notificationEntry) {
        ListenerSet listenerSet = this.listeners;
        if (listenerSet.isEmpty()) {
            Log.wtf("LaunchFullScreenIntentProvider", "no listeners found when launchFullScreenIntent requested");
        }
        Iterator it = listenerSet.iterator();
        while (it.hasNext()) {
            ((StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1) it.next()).f$0.launchFullScreenIntent(notificationEntry);
        }
    }
}
