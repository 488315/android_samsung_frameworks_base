package com.android.systemui.statusbar.notification.collection.provider;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class LaunchFullScreenIntentProvider {
    public final ListenerSet listeners = new ListenerSet();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
