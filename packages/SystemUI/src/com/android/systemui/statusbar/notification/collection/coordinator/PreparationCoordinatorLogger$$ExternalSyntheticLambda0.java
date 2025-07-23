package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PreparationCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PreparationCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logNotifInflated$lambda$1;
        String logGroupInflationTookTooLong$lambda$9;
        String logDelayingGroupRelease$lambda$11;
        String logDoneWaitingForGroupInflation$lambda$7;
        String logFreeNotifViews$lambda$5;
        String logInflationAborted$lambda$3;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                logNotifInflated$lambda$1 = PreparationCoordinatorLogger.logNotifInflated$lambda$1(logMessage);
                return logNotifInflated$lambda$1;
            case 1:
                logGroupInflationTookTooLong$lambda$9 = PreparationCoordinatorLogger.logGroupInflationTookTooLong$lambda$9(logMessage);
                return logGroupInflationTookTooLong$lambda$9;
            case 2:
                logDelayingGroupRelease$lambda$11 = PreparationCoordinatorLogger.logDelayingGroupRelease$lambda$11(logMessage);
                return logDelayingGroupRelease$lambda$11;
            case 3:
                logDoneWaitingForGroupInflation$lambda$7 = PreparationCoordinatorLogger.logDoneWaitingForGroupInflation$lambda$7(logMessage);
                return logDoneWaitingForGroupInflation$lambda$7;
            case 4:
                logFreeNotifViews$lambda$5 = PreparationCoordinatorLogger.logFreeNotifViews$lambda$5(logMessage);
                return logFreeNotifViews$lambda$5;
            default:
                logInflationAborted$lambda$3 = PreparationCoordinatorLogger.logInflationAborted$lambda$3(logMessage);
                return logInflationAborted$lambda$3;
        }
    }
}
