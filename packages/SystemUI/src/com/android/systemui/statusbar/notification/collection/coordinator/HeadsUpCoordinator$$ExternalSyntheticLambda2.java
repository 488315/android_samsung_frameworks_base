package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinator$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HeadsUpCoordinator$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean findBestTransferChild$lambda$22;
        boolean onBeforeFinalizeFilter$lambda$16$lambda$3;
        boolean onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                findBestTransferChild$lambda$22 = HeadsUpCoordinator.findBestTransferChild$lambda$22((Function1) obj2, (NotificationEntry) obj);
                return Boolean.valueOf(findBestTransferChild$lambda$22);
            case 1:
                onBeforeFinalizeFilter$lambda$16$lambda$3 = HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$16$lambda$3((Map) obj2, (NotificationEntry) obj);
                return Boolean.valueOf(onBeforeFinalizeFilter$lambda$16$lambda$3);
            default:
                onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13 = HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13((NotificationEntry) obj2, (HeadsUpCoordinator.PostedEntry) obj);
                return Boolean.valueOf(onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13);
        }
    }
}
