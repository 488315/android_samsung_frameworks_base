package com.android.systemui.statusbar.notification.collection.provider;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LaunchFullScreenIntentProvider {
    public final ListenerSet listeners = new ListenerSet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            ((StatusBarNotificationActivityStarter$$ExternalSyntheticLambda0) it.next()).f$0.launchFullScreenIntent(notificationEntry);
        }
    }
}
