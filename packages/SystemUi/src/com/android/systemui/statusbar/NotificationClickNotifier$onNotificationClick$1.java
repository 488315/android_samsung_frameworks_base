package com.android.systemui.statusbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationClickNotifier$onNotificationClick$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ NotificationClickNotifier this$0;

    public NotificationClickNotifier$onNotificationClick$1(NotificationClickNotifier notificationClickNotifier, String str) {
        this.this$0 = notificationClickNotifier;
        this.$key = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationClickNotifier.access$notifyListenersAboutInteraction(this.this$0, this.$key);
    }
}
