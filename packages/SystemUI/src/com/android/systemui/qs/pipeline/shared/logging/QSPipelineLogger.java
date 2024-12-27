package com.android.systemui.qs.pipeline.shared.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSPipelineLogger {
    public final LogBuffer restoreLogBuffer;
    public final LogBuffer tileAutoAddLogBuffer;
    public final LogBuffer tileListLogBuffer;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QSPipelineLogger(LogBuffer logBuffer, LogBuffer logBuffer2, LogBuffer logBuffer3) {
        this.tileListLogBuffer = logBuffer;
        this.restoreLogBuffer = logBuffer3;
    }

    public final void logParsedTiles(List list, boolean z, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logParsedTiles$2 qSPipelineLogger$logParsedTiles$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logParsedTiles$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("Parsed tiles (default=", int1, ", user=", bool1, "): ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$logParsedTiles$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = list.toString();
        logMessageImpl.bool1 = z;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logProcessTileChange(UserTileSpecRepository.RestoreTiles restoreTiles, List list, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logProcessTileChange$2 qSPipelineLogger$logProcessTileChange$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logProcessTileChange$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "Processing ", str1, " for user ", "\nNew list: ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$logProcessTileChange$2, null);
        ((LogMessageImpl) obtain).str1 = restoreTiles.toString();
        String obj = list.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = obj;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logSettingsRestored(RestoreData restoreData) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logSettingsRestored$2 qSPipelineLogger$logSettingsRestored$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logSettingsRestored$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Restored settings data for user ", "\n\tRestored tiles: ", str1, "\n\tRestored auto added tiles: ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.restoreLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSRestoreLog", logLevel, qSPipelineLogger$logSettingsRestored$2, null);
        ((LogMessageImpl) obtain).int1 = restoreData.userId;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = restoreData.restoredTiles.toString();
        logMessageImpl.str2 = restoreData.restoredAutoAddedTiles.toString();
        logBuffer.commit(obtain);
    }

    public final void logSettingsRestoredOnUserSetupComplete(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logSettingsRestoredOnUserSetupComplete$2 qSPipelineLogger$logSettingsRestoredOnUserSetupComplete$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logSettingsRestoredOnUserSetupComplete$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Restored from single intent after user setup complete for user ");
            }
        };
        LogBuffer logBuffer = this.restoreLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSRestoreLog", logLevel, qSPipelineLogger$logSettingsRestoredOnUserSetupComplete$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logTilesRestoredAndReconciled(List list, List list2, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logTilesRestoredAndReconciled$2 qSPipelineLogger$logTilesRestoredAndReconciled$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logTilesRestoredAndReconciled$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Tiles restored and reconciled for user: ", "\nWas: ", str1, "\nSet to: ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$logTilesRestoredAndReconciled$2, null);
        ((LogMessageImpl) obtain).str1 = list.toString();
        String obj = list2.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = obj;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logUsingRetailTiles() {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$logUsingRetailTiles$2 qSPipelineLogger$logUsingRetailTiles$2 = new Function1() { // from class: com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$logUsingRetailTiles$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Using retail tiles";
            }
        };
        LogBuffer logBuffer = this.tileListLogBuffer;
        logBuffer.commit(logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$logUsingRetailTiles$2, null));
    }
}
