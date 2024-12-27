package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
