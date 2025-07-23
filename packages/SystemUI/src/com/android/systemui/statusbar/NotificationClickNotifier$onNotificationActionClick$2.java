package com.android.systemui.statusbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
