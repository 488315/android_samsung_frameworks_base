package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardCoordinator$pickOutTopUnseenNotifs$3 extends Lambda implements Function1 {
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$pickOutTopUnseenNotifs$3(KeyguardCoordinator keyguardCoordinator) {
        super(1);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(NotificationEntry notificationEntry) {
        boolean z;
        Set set;
        if (!ColorizedFgsCoordinator.isRichOngoing(notificationEntry)) {
            set = this.this$0.unseenNotifications;
            if (set.contains(notificationEntry)) {
                z = true;
                return Boolean.valueOf(z);
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
