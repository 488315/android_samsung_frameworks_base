package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinator$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HeadsUpCoordinator$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(HeadsUpCoordinator.findHeadsUpOverride$lambda$17((HeadsUpCoordinator.PostedEntry) obj));
            case 1:
                return Boolean.valueOf(HeadsUpCoordinator.findBestTransferChild$lambda$21((NotificationEntry) obj));
            default:
                return HeadsUpCoordinator.findBestTransferChild$lambda$24((NotificationEntry) obj);
        }
    }
}
