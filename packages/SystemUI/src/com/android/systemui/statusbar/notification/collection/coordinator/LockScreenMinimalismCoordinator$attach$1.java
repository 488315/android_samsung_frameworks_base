package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class LockScreenMinimalismCoordinator$attach$1 implements OnBeforeTransformGroupsListener {
    final /* synthetic */ LockScreenMinimalismCoordinator $tmp0;

    public LockScreenMinimalismCoordinator$attach$1(LockScreenMinimalismCoordinator lockScreenMinimalismCoordinator) {
        this.$tmp0 = lockScreenMinimalismCoordinator;
    }

    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
    public final void onBeforeTransformGroups(List<? extends PipelineEntry> list) {
        this.$tmp0.pickOutTopUnseenNotifs(list);
    }
}
