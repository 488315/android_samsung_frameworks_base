package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

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
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        HeadsUpCoordinator headsUpCoordinator = this.f$0;
        switch (i) {
            case 0:
                return HeadsUpCoordinator.onBeforeTransformGroups$lambda$1(headsUpCoordinator, (HunMutator) obj);
            default:
                return HeadsUpCoordinator.findBestTransferChild$lambda$23(headsUpCoordinator, (NotificationEntry) obj);
        }
    }
}
