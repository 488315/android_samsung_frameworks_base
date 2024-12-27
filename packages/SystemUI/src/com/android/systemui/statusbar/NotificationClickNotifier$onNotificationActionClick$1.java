package com.android.systemui.statusbar;

import android.app.Flags;
import android.app.Notification;
import android.os.RemoteException;
import com.android.internal.statusbar.NotificationVisibility;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationClickNotifier$onNotificationActionClick$1 implements Runnable {
    public final /* synthetic */ Notification.Action $action;
    public final /* synthetic */ int $actionIndex;
    public final /* synthetic */ boolean $generatedByAssistant;
    public final /* synthetic */ String $key;
    public final /* synthetic */ NotificationVisibility $visibility;
    public final /* synthetic */ NotificationClickNotifier this$0;

    public NotificationClickNotifier$onNotificationActionClick$1(NotificationClickNotifier notificationClickNotifier, String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) {
        this.this$0 = notificationClickNotifier;
        this.$key = str;
        this.$actionIndex = i;
        this.$action = action;
        this.$visibility = notificationVisibility;
        this.$generatedByAssistant = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.this$0.barService.onNotificationActionClick(this.$key, this.$actionIndex, this.$action, this.$visibility, this.$generatedByAssistant);
        } catch (RemoteException unused) {
        }
        if (Flags.lifetimeExtensionRefactor()) {
            NotificationClickNotifier.access$notifyListenersAboutInteraction(this.this$0, this.$key);
        }
    }
}
