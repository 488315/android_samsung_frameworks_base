package com.android.systemui.statusbar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationClickNotifier$onNotificationActionClick$2 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ NotificationClickNotifier this$0;

    public NotificationClickNotifier$onNotificationActionClick$2(NotificationClickNotifier notificationClickNotifier, String str) {
        this.this$0 = notificationClickNotifier;
        this.$key = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationClickNotifier.access$notifyListenersAboutInteraction(this.this$0, this.$key);
    }
}
