package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinator$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HeadsUpCoordinator f$0;

    public /* synthetic */ HeadsUpCoordinator$$ExternalSyntheticLambda5(HeadsUpCoordinator headsUpCoordinator, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpCoordinator;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Unit onBeforeTransformGroups$lambda$1;
        Comparable findBestTransferChild$lambda$23;
        int i = this.$r8$classId;
        HeadsUpCoordinator headsUpCoordinator = this.f$0;
        switch (i) {
            case 0:
                onBeforeTransformGroups$lambda$1 = HeadsUpCoordinator.onBeforeTransformGroups$lambda$1(headsUpCoordinator, (HunMutator) obj);
                return onBeforeTransformGroups$lambda$1;
            default:
                findBestTransferChild$lambda$23 = HeadsUpCoordinator.findBestTransferChild$lambda$23(headsUpCoordinator, (NotificationEntry) obj);
                return findBestTransferChild$lambda$23;
        }
    }
}
