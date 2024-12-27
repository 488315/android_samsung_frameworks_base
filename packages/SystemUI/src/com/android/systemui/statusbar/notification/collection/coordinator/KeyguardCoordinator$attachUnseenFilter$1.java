package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import java.util.List;

final /* synthetic */ class KeyguardCoordinator$attachUnseenFilter$1 implements OnBeforeTransformGroupsListener {
    final /* synthetic */ KeyguardCoordinator $tmp0;

    public KeyguardCoordinator$attachUnseenFilter$1(KeyguardCoordinator keyguardCoordinator) {
        this.$tmp0 = keyguardCoordinator;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
    public final void onBeforeTransformGroups(List<? extends ListEntry> list) {
        this.$tmp0.pickOutTopUnseenNotifs(list);
    }
}
