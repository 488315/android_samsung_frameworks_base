package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinator$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HeadsUpCoordinator$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean findHeadsUpOverride$lambda$17;
        boolean findBestTransferChild$lambda$21;
        Comparable findBestTransferChild$lambda$24;
        switch (this.$r8$classId) {
            case 0:
                findHeadsUpOverride$lambda$17 = HeadsUpCoordinator.findHeadsUpOverride$lambda$17((HeadsUpCoordinator.PostedEntry) obj);
                return Boolean.valueOf(findHeadsUpOverride$lambda$17);
            case 1:
                findBestTransferChild$lambda$21 = HeadsUpCoordinator.findBestTransferChild$lambda$21((NotificationEntry) obj);
                return Boolean.valueOf(findBestTransferChild$lambda$21);
            default:
                findBestTransferChild$lambda$24 = HeadsUpCoordinator.findBestTransferChild$lambda$24((NotificationEntry) obj);
                return findBestTransferChild$lambda$24;
        }
    }
}
