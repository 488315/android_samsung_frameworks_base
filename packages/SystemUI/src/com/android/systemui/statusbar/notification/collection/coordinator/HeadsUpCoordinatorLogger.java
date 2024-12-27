package com.android.systemui.statusbar.notification.collection.coordinator;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HeadsUpCoordinatorLogger {
    public static final int $stable = 8;
    private final LogBuffer buffer;
    private final boolean verbose;

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer, boolean z) {
        this.buffer = logBuffer;
        this.verbose = z;
    }

    public final void logEntryDisqualifiedFromFullScreen(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryDisqualifiedFromFullScreen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return FontProvider$$ExternalSyntheticOutline0.m("updated entry no longer qualifies for full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEntryUpdatedByRanking(String str, boolean z, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryUpdatedByRanking$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str22 = logMessage.getStr2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("updating entry via ranking applied: ", str1, " updated shouldHeadsUp=", " because ", bool1);
                m.append(str22);
                return m.toString();
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEntryUpdatedToFullScreen(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEntryUpdatedToFullScreen$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return FontProvider$$ExternalSyntheticOutline0.m("updating entry to launch full screen intent: ", logMessage.getStr1(), " because ", logMessage.getStr2());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logEvaluatingGroup(String str, int i, int i2) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroup$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(LogMessage logMessage) {
                    String str1 = logMessage.getStr1();
                    int int1 = logMessage.getInt1();
                    int int2 = logMessage.getInt2();
                    StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "evaluating group for alert transfer: ", str1, " numPostedEntries=", " logicalGroupSize=");
                    m.append(int2);
                    return m.toString();
                }
            }, null);
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
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logEvaluatingGroups$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(LogMessage logMessage) {
                    return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "evaluating groups for alert transfer: ");
                }
            }, null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
        }
    }

    public final void logPostedEntryWillEvaluate(HeadsUpCoordinator.PostedEntry postedEntry, String str) {
        if (this.verbose) {
            LogBuffer logBuffer = this.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillEvaluate$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(LogMessage logMessage) {
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("will evaluate posted entry ", str1, ": reason=", str2, " shouldHeadsUpEver="), logMessage.getBool1(), " shouldHeadsUpAgain=", logMessage.getBool2());
                }
            }, null);
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
            LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.VERBOSE, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logPostedEntryWillNotEvaluate$2
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(LogMessage logMessage) {
                    return FontProvider$$ExternalSyntheticOutline0.m("will not evaluate posted entry ", logMessage.getStr1(), ": reason=", logMessage.getStr2());
                }
            }, null);
            ((LogMessageImpl) obtain).str1 = postedEntry.getKey();
            ((LogMessageImpl) obtain).str2 = str;
            logBuffer.commit(obtain);
        }
    }

    public final void logSummaryMarkedInterrupted(String str, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpCoordinator", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger$logSummaryMarkedInterrupted$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(LogMessage logMessage) {
                return FontProvider$$ExternalSyntheticOutline0.m("marked group summary as interrupted: ", logMessage.getStr1(), " for alert transfer to child: ", logMessage.getStr2());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).str2 = str2;
        logBuffer.commit(obtain);
    }

    public HeadsUpCoordinatorLogger(LogBuffer logBuffer) {
        this(logBuffer, false);
    }
}
