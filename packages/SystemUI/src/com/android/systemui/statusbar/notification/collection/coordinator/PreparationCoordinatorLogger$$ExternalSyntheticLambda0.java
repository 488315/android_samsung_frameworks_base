package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class PreparationCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PreparationCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return PreparationCoordinatorLogger.logNotifInflated$lambda$1(logMessage);
            case 1:
                return PreparationCoordinatorLogger.logGroupInflationTookTooLong$lambda$9(logMessage);
            case 2:
                return PreparationCoordinatorLogger.logDelayingGroupRelease$lambda$11(logMessage);
            case 3:
                return PreparationCoordinatorLogger.logDoneWaitingForGroupInflation$lambda$7(logMessage);
            case 4:
                return PreparationCoordinatorLogger.logFreeNotifViews$lambda$5(logMessage);
            default:
                return PreparationCoordinatorLogger.logInflationAborted$lambda$3(logMessage);
        }
    }
}
