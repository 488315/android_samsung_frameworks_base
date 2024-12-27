package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Collections;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$1 extends Lambda implements Function1 {
    public static final KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$1 INSTANCE = new KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$1();

    public KeyguardCoordinator$pickOutTopUnseenNotifs$nonSummaryEntries$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final List<NotificationEntry> invoke(ListEntry listEntry) {
        if (listEntry instanceof NotificationEntry) {
            return listEntry != null ? Collections.singletonList(listEntry) : EmptyList.INSTANCE;
        }
        if (listEntry instanceof GroupEntry) {
            return ((GroupEntry) listEntry).mUnmodifiableChildren;
        }
        throw new IllegalStateException(("unhandled type of " + listEntry).toString());
    }
}
