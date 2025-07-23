package com.android.systemui.statusbar.notification.collection.coordinator;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;
    private final boolean verbose;

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer, boolean z) {
        this.buffer = logBuffer;
        this.verbose = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryDisqualifiedFromFullScreen$lambda$13(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("updated entry no longer qualifies for full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryUpdatedByRanking$lambda$9(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        boolean bool1 = logMessage.getBool1();
        String str2 = logMessage.getStr2();
        StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("updating entry via ranking applied: ", str1, " updated shouldHeadsUp=", " because ", bool1);
        m.append(str2);
        return m.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryUpdatedToFullScreen$lambda$11(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("updating entry to launch full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEvaluatingGroup$lambda$7(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        int int1 = logMessage.getInt1();
        int int2 = logMessage.getInt2();
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "evaluating group for alert transfer: ", str1, " numPostedEntries=", " logicalGroupSize=");
        m888m.append(int2);
        return m888m.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEvaluatingGroups$lambda$5(LogMessage logMessage) {
        return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "evaluating groups for alert transfer: ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logHidePromotedNotificationHeadsUp$lambda$19(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("requesting promoted entry to hide heads up: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPostedEntryWillEvaluate$lambda$1(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("will evaluate posted entry ", str1, ": reason=", str2, " shouldHeadsUpEver="), logMessage.getBool1(), " shouldHeadsUpAgain=", logMessage.getBool2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPostedEntryWillNotEvaluate$lambda$3(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("will not evaluate posted entry ", logMessage.getStr1(), ": reason=", logMessage.getStr2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logPromotedNotificationForHeadsUpNotFound$lambda$21(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("could not find promoted entry, so not showing heads up: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logShowPromotedNotificationHeadsUp$lambda$17(LogMessage logMessage) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("requesting promoted entry to show heads up: ", logMessage.getStr1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logSummaryMarkedInterrupted$lambda$15(LogMessage logMessage) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("marked group summary as interrupted: ", logMessage.getStr1(), " for alert transfer to child: ", logMessage.getStr2());
    }

    public final void logEntryDisqualifiedFromFullScreen(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(0), null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEntryUpdatedByRanking(String str, boolean z, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(3), null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEntryUpdatedToFullScreen(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(5), null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEvaluatingGroup(String str, int i, int i2) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(7), null);
            ((LogMessageImpl) obtain).str1 = str;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
            logMessageImpl.int2 = i2;
            logBuffer.commit(obtain);
        }
    }

    public final void logEvaluatingGroups(int i) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(9), null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
        }
    }

    public final void logHidePromotedNotificationHeadsUp(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(6), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logPostedEntryWillEvaluate(HeadsUpCoordinator.PostedEntry postedEntry, String str) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(8), null);
            ((LogMessageImpl) obtain).str1 = postedEntry.getKey();
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str2 = str;
            logMessageImpl.bool1 = postedEntry.getShouldHeadsUpEver();
            logMessageImpl.bool2 = postedEntry.getShouldHeadsUpAgain();
            logBuffer.commit(obtain);
        }
    }

    public final void logPostedEntryWillNotEvaluate(HeadsUpCoordinator.PostedEntry postedEntry, String str) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(4), null);
            ((LogMessageImpl) obtain).str1 = postedEntry.getKey();
            ((LogMessageImpl) obtain).str2 = str;
            logBuffer.commit(obtain);
        }
    }

    public final void logPromotedNotificationForHeadsUpNotFound(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(2), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logShowPromotedNotificationHeadsUp(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(1), null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logSummaryMarkedInterrupted(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new HeadsUpCoordinatorLogger$$ExternalSyntheticLambda0(10), null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer) {
        this(logBuffer, false);
    }
}
