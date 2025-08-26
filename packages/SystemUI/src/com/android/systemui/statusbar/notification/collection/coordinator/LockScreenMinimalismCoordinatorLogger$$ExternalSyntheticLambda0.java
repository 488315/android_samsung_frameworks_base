package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LockScreenMinimalismCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return LockScreenMinimalismCoordinatorLogger.logTopHeadsUpRow$lambda$13(logMessage);
            case 1:
                return LockScreenMinimalismCoordinatorLogger.logTrackingUnseen$lambda$1(logMessage);
            case 2:
                return LockScreenMinimalismCoordinatorLogger.logUnseenAdded$lambda$5(logMessage);
            case 3:
                return LockScreenMinimalismCoordinatorLogger.logUnseenRemoved$lambda$9(logMessage);
            case 4:
                return LockScreenMinimalismCoordinatorLogger.logShadeVisible$lambda$3(logMessage);
            case 5:
                return LockScreenMinimalismCoordinatorLogger.logUnseenUpdated$lambda$7(logMessage);
            default:
                return LockScreenMinimalismCoordinatorLogger.logHunHasBeenSeen$lambda$11(logMessage);
        }
    }
}
