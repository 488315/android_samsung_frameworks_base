package com.android.systemui.qs.logging;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$toString$1$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function1;

public final class QSLogger {
    public final /* synthetic */ ConstantStringsLoggerImpl $$delegate_0;
    public final LogBuffer buffer;

    public QSLogger(LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.buffer = logBuffer;
        this.$$delegate_0 = new ConstantStringsLoggerImpl(logBuffer, "QSLog");
    }

    public static String toStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "wrong state" : SystemUIAnalytics.QPBSE_KEY_ACTIVE : "inactive" : "unavailable";
    }

    public final void d(Object obj, final String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$d$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, ": ", ((LogMessage) obj2).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, function1, null);
        ((LogMessageImpl) obtain).str1 = obj.toString();
        logBuffer.commit(obtain);
    }

    public final void logAllTilesChangeListening(String str, String str2, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logAllTilesChangeListening$2 qSLogger$logAllTilesChangeListening$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logAllTilesChangeListening$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Tiles listening=" + logMessage.getBool1() + " in " + logMessage.getStr1() + ". " + logMessage.getStr2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logAllTilesChangeListening$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logException(final String str, Exception exc) {
        LogLevel logLevel = LogLevel.ERROR;
        Function1 function1 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logException$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return str;
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("QSLog", logLevel, function1, exc));
    }

    public final void logHandleClick(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleClick$2 qSLogger$logHandleClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "[" + logMessage.getStr1() + "][" + logMessage.getInt1() + "] Tile handling click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleClick$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logHandleLongClick(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleLongClick$2 qSLogger$logHandleLongClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleLongClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "[" + logMessage.getStr1() + "][" + logMessage.getInt1() + "] Tile handling long click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleLongClick$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logHandleSecondaryClick(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logHandleSecondaryClick$2 qSLogger$logHandleSecondaryClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logHandleSecondaryClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "[" + logMessage.getStr1() + "][" + logMessage.getInt1() + "] Tile handling secondary click.";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logHandleSecondaryClick$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logInternetTileUpdate(int i, String str, String str2) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logInternetTileUpdate$2 qSLogger$logInternetTileUpdate$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logInternetTileUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "[", str1, "] mLastTileState=", ", Callback="), logMessage.getStr2(), ".");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logInternetTileUpdate$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logPanelExpanded(String str, boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logPanelExpanded$2 qSLogger$logPanelExpanded$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logPanelExpanded$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return logMessage.getStr1() + " expanded=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logPanelExpanded$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logTileAdded(String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileAdded$2 qSLogger$logTileAdded$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileAdded$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", ((LogMessage) obj).getStr1(), "] Tile added");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileAdded$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logTileChangeListening(String str, boolean z) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logTileChangeListening$2 qSLogger$logTileChangeListening$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileChangeListening$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "[" + logMessage.getStr1() + "] Tile listening=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileChangeListening$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logTileClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileClick$2 qSLogger$logTileClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "[", str1, "][", "] Tile clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileClick$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i3;
        logMessageImpl.str2 = StatusBarState.toString(i);
        logMessageImpl.str3 = toStateString(i2);
        logBuffer.commit(obtain);
    }

    public final void logTileDestroyed(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDestroyed$2 qSLogger$logTileDestroyed$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDestroyed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] Tile destroyed. Reason: ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDestroyed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
    }

    public final void logTileDistributed(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDistributed$2 qSLogger$logTileDistributed$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDistributed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Adding ", logMessage.getStr1(), " to page number ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDistributed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logTileDistributionInProgress(int i, int i2) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileDistributionInProgress$2 qSLogger$logTileDistributionInProgress$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileDistributionInProgress$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Distributing tiles: [tilesPerPageCount=", "] [totalTilesCount=", "]");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileDistributionInProgress$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logTileLongClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileLongClick$2 qSLogger$logTileLongClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileLongClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "[", str1, "][", "] Tile long clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileLongClick$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i3;
        logMessageImpl.str2 = StatusBarState.toString(i);
        logMessageImpl.str3 = toStateString(i2);
        logBuffer.commit(obtain);
    }

    public final void logTileSecondaryClick(String str, int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSLogger$logTileSecondaryClick$2 qSLogger$logTileSecondaryClick$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileSecondaryClick$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return MutablePreferences$toString$1$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "[", str1, "][", "] Tile secondary clicked. StatusBarState="), logMessage.getStr2(), ". TileState=", logMessage.getStr3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileSecondaryClick$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i3;
        logMessageImpl.str2 = StatusBarState.toString(i);
        logMessageImpl.str3 = toStateString(i2);
        logBuffer.commit(obtain);
    }

    public final void logTileUpdated(QSTile.State state, String str) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSLogger$logTileUpdated$2 qSLogger$logTileUpdated$2 = new Function1() { // from class: com.android.systemui.qs.logging.QSLogger$logTileUpdated$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str1, "] Tile updated. Label=", str2, ". State=");
                m.append(int1);
                m.append(". Icon=");
                m.append(str3);
                m.append(".");
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("QSLog", logLevel, qSLogger$logTileUpdated$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        CharSequence charSequence = state.label;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = charSequence != null ? charSequence.toString() : null;
        QSTile.Icon icon = state.icon;
        logMessageImpl.str3 = icon != null ? icon.toString() : null;
        logMessageImpl.int1 = state.state;
        logBuffer.commit(obtain);
    }
}
