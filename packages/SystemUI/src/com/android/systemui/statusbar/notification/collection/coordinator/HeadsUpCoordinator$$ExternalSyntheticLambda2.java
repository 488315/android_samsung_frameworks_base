package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.Map;
import kotlin.jvm.functions.Function1;

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
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return Boolean.valueOf(HeadsUpCoordinator.findBestTransferChild$lambda$22((Function1) obj2, (NotificationEntry) obj));
            case 1:
                return Boolean.valueOf(HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$16$lambda$3((Map) obj2, (NotificationEntry) obj));
            default:
                return Boolean.valueOf(HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$16$lambda$15$lambda$13((NotificationEntry) obj2, (HeadsUpCoordinator.PostedEntry) obj));
        }
    }
}
