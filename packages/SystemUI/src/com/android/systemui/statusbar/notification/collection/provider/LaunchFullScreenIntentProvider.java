package com.android.systemui.statusbar.notification.collection.provider;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter$$ExternalSyntheticLambda1;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LaunchFullScreenIntentProvider {
    public final ListenerSet listeners = new ListenerSet();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
