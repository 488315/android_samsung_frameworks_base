package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logTopHeadsUpRow$lambda$13;
        String logTrackingUnseen$lambda$1;
        String logUnseenAdded$lambda$5;
        String logUnseenRemoved$lambda$9;
        String logShadeVisible$lambda$3;
        String logUnseenUpdated$lambda$7;
        String logHunHasBeenSeen$lambda$11;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                logTopHeadsUpRow$lambda$13 = LockScreenMinimalismCoordinatorLogger.logTopHeadsUpRow$lambda$13(logMessage);
                return logTopHeadsUpRow$lambda$13;
            case 1:
                logTrackingUnseen$lambda$1 = LockScreenMinimalismCoordinatorLogger.logTrackingUnseen$lambda$1(logMessage);
                return logTrackingUnseen$lambda$1;
            case 2:
                logUnseenAdded$lambda$5 = LockScreenMinimalismCoordinatorLogger.logUnseenAdded$lambda$5(logMessage);
                return logUnseenAdded$lambda$5;
            case 3:
                logUnseenRemoved$lambda$9 = LockScreenMinimalismCoordinatorLogger.logUnseenRemoved$lambda$9(logMessage);
                return logUnseenRemoved$lambda$9;
            case 4:
                logShadeVisible$lambda$3 = LockScreenMinimalismCoordinatorLogger.logShadeVisible$lambda$3(logMessage);
                return logShadeVisible$lambda$3;
            case 5:
                logUnseenUpdated$lambda$7 = LockScreenMinimalismCoordinatorLogger.logUnseenUpdated$lambda$7(logMessage);
                return logUnseenUpdated$lambda$7;
            default:
                logHunHasBeenSeen$lambda$11 = LockScreenMinimalismCoordinatorLogger.logHunHasBeenSeen$lambda$11(logMessage);
                return logHunHasBeenSeen$lambda$11;
        }
    }
}
