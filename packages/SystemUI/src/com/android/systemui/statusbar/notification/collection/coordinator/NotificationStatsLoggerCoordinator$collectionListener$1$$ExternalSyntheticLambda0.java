package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationEntry f$0;

    public /* synthetic */ NotificationStatsLoggerCoordinator$collectionListener$1$$ExternalSyntheticLambda0(NotificationEntry notificationEntry, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationEntry;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = this.$r8$classId;
        NotificationEntry notificationEntry = this.f$0;
        NotificationStatsLogger notificationStatsLogger = (NotificationStatsLogger) obj;
        switch (i) {
            case 0:
                return NotificationStatsLoggerCoordinator$collectionListener$1.onEntryUpdated$lambda$0(notificationEntry, notificationStatsLogger);
            default:
                return NotificationStatsLoggerCoordinator$collectionListener$1.onEntryRemoved$lambda$1(notificationEntry, notificationStatsLogger);
        }
    }
}
