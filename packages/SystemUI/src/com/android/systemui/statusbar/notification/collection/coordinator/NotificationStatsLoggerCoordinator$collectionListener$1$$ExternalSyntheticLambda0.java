package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        Unit onEntryUpdated$lambda$0;
        Unit onEntryRemoved$lambda$1;
        int i = this.$r8$classId;
        NotificationEntry notificationEntry = this.f$0;
        NotificationStatsLogger notificationStatsLogger = (NotificationStatsLogger) obj;
        switch (i) {
            case 0:
                onEntryUpdated$lambda$0 = NotificationStatsLoggerCoordinator$collectionListener$1.onEntryUpdated$lambda$0(notificationEntry, notificationStatsLogger);
                return onEntryUpdated$lambda$0;
            default:
                onEntryRemoved$lambda$1 = NotificationStatsLoggerCoordinator$collectionListener$1.onEntryRemoved$lambda$1(notificationEntry, notificationStatsLogger);
                return onEntryRemoved$lambda$1;
        }
    }
}
