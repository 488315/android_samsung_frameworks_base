package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return HeadsUpCoordinatorLogger.logEntryDisqualifiedFromFullScreen$lambda$13(logMessage);
            case 1:
                return HeadsUpCoordinatorLogger.logShowPromotedNotificationHeadsUp$lambda$17(logMessage);
            case 2:
                return HeadsUpCoordinatorLogger.logPromotedNotificationForHeadsUpNotFound$lambda$21(logMessage);
            case 3:
                return HeadsUpCoordinatorLogger.logEntryUpdatedByRanking$lambda$9(logMessage);
            case 4:
                return HeadsUpCoordinatorLogger.logPostedEntryWillNotEvaluate$lambda$3(logMessage);
            case 5:
                return HeadsUpCoordinatorLogger.logEntryUpdatedToFullScreen$lambda$11(logMessage);
            case 6:
                return HeadsUpCoordinatorLogger.logHidePromotedNotificationHeadsUp$lambda$19(logMessage);
            case 7:
                return HeadsUpCoordinatorLogger.logEvaluatingGroup$lambda$7(logMessage);
            case 8:
                return HeadsUpCoordinatorLogger.logPostedEntryWillEvaluate$lambda$1(logMessage);
            case 9:
                return HeadsUpCoordinatorLogger.logEvaluatingGroups$lambda$5(logMessage);
            default:
                return HeadsUpCoordinatorLogger.logSummaryMarkedInterrupted$lambda$15(logMessage);
        }
    }
}
