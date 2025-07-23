package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String logEntryDisqualifiedFromFullScreen$lambda$13;
        String logShowPromotedNotificationHeadsUp$lambda$17;
        String logPromotedNotificationForHeadsUpNotFound$lambda$21;
        String logEntryUpdatedByRanking$lambda$9;
        String logPostedEntryWillNotEvaluate$lambda$3;
        String logEntryUpdatedToFullScreen$lambda$11;
        String logHidePromotedNotificationHeadsUp$lambda$19;
        String logEvaluatingGroup$lambda$7;
        String logPostedEntryWillEvaluate$lambda$1;
        String logEvaluatingGroups$lambda$5;
        String logSummaryMarkedInterrupted$lambda$15;
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                logEntryDisqualifiedFromFullScreen$lambda$13 = HeadsUpCoordinatorLogger.logEntryDisqualifiedFromFullScreen$lambda$13(logMessage);
                return logEntryDisqualifiedFromFullScreen$lambda$13;
            case 1:
                logShowPromotedNotificationHeadsUp$lambda$17 = HeadsUpCoordinatorLogger.logShowPromotedNotificationHeadsUp$lambda$17(logMessage);
                return logShowPromotedNotificationHeadsUp$lambda$17;
            case 2:
                logPromotedNotificationForHeadsUpNotFound$lambda$21 = HeadsUpCoordinatorLogger.logPromotedNotificationForHeadsUpNotFound$lambda$21(logMessage);
                return logPromotedNotificationForHeadsUpNotFound$lambda$21;
            case 3:
                logEntryUpdatedByRanking$lambda$9 = HeadsUpCoordinatorLogger.logEntryUpdatedByRanking$lambda$9(logMessage);
                return logEntryUpdatedByRanking$lambda$9;
            case 4:
                logPostedEntryWillNotEvaluate$lambda$3 = HeadsUpCoordinatorLogger.logPostedEntryWillNotEvaluate$lambda$3(logMessage);
                return logPostedEntryWillNotEvaluate$lambda$3;
            case 5:
                logEntryUpdatedToFullScreen$lambda$11 = HeadsUpCoordinatorLogger.logEntryUpdatedToFullScreen$lambda$11(logMessage);
                return logEntryUpdatedToFullScreen$lambda$11;
            case 6:
                logHidePromotedNotificationHeadsUp$lambda$19 = HeadsUpCoordinatorLogger.logHidePromotedNotificationHeadsUp$lambda$19(logMessage);
                return logHidePromotedNotificationHeadsUp$lambda$19;
            case 7:
                logEvaluatingGroup$lambda$7 = HeadsUpCoordinatorLogger.logEvaluatingGroup$lambda$7(logMessage);
                return logEvaluatingGroup$lambda$7;
            case 8:
                logPostedEntryWillEvaluate$lambda$1 = HeadsUpCoordinatorLogger.logPostedEntryWillEvaluate$lambda$1(logMessage);
                return logPostedEntryWillEvaluate$lambda$1;
            case 9:
                logEvaluatingGroups$lambda$5 = HeadsUpCoordinatorLogger.logEvaluatingGroups$lambda$5(logMessage);
                return logEvaluatingGroups$lambda$5;
            default:
                logSummaryMarkedInterrupted$lambda$15 = HeadsUpCoordinatorLogger.logSummaryMarkedInterrupted$lambda$15(logMessage);
                return logSummaryMarkedInterrupted$lambda$15;
        }
    }
}
