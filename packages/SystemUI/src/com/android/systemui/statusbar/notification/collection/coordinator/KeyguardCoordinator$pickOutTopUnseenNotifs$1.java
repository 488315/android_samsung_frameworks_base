package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class KeyguardCoordinator$pickOutTopUnseenNotifs$1 extends Lambda implements Function1 {
    public static final KeyguardCoordinator$pickOutTopUnseenNotifs$1 INSTANCE = new KeyguardCoordinator$pickOutTopUnseenNotifs$1();

    public KeyguardCoordinator$pickOutTopUnseenNotifs$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(NotificationEntry notificationEntry) {
        return Boolean.valueOf(ColorizedFgsCoordinator.isRichOngoing(notificationEntry));
    }
}
