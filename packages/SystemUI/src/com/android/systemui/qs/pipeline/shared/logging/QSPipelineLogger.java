package com.android.systemui.qs.pipeline.shared.logging;

import com.android.systemui.ScRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Collection;
import java.util.List;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class QSPipelineLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer restoreLogBuffer;
    public final LogBuffer tileAutoAddLogBuffer;
    public final LogBuffer tileListLogBuffer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class RestorePreprocessorStep {
        public static final /* synthetic */ RestorePreprocessorStep[] $VALUES;
        public static final RestorePreprocessorStep POSTPROCESSING;
        public static final RestorePreprocessorStep PREPROCESSING;

        static {
            RestorePreprocessorStep restorePreprocessorStep = new RestorePreprocessorStep("PREPROCESSING", 0);
            PREPROCESSING = restorePreprocessorStep;
            RestorePreprocessorStep restorePreprocessorStep2 = new RestorePreprocessorStep("POSTPROCESSING", 1);
            POSTPROCESSING = restorePreprocessorStep2;
            RestorePreprocessorStep[] restorePreprocessorStepArr = {restorePreprocessorStep, restorePreprocessorStep2};
            $VALUES = restorePreprocessorStepArr;
            EnumEntriesKt.enumEntries(restorePreprocessorStepArr);
        }

        private RestorePreprocessorStep(String str, int i) {
        }

        public static RestorePreprocessorStep valueOf(String str) {
            return (RestorePreprocessorStep) Enum.valueOf(RestorePreprocessorStep.class, str);
        }

        public static RestorePreprocessorStep[] values() {
            return (RestorePreprocessorStep[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TileCreatedReason {
        public static final /* synthetic */ TileCreatedReason[] $VALUES;
        public static final TileCreatedReason BAR_TILE_CREATED;
        public static final TileCreatedReason TILE_CREATED;
        private final String readable;

        static {
            TileCreatedReason tileCreatedReason = new TileCreatedReason("TILE_CREATED", 0, "From SecQSTileInstanceManager, Tile created.");
            TILE_CREATED = tileCreatedReason;
            TileCreatedReason tileCreatedReason2 = new TileCreatedReason("BAR_TILE_CREATED", 1, "From SecQSTileInstanceManager, Bar tile created.");
            BAR_TILE_CREATED = tileCreatedReason2;
            TileCreatedReason[] tileCreatedReasonArr = {tileCreatedReason, tileCreatedReason2};
            $VALUES = tileCreatedReasonArr;
            EnumEntriesKt.enumEntries(tileCreatedReasonArr);
        }

        private TileCreatedReason(String str, int i, String str2) {
            this.readable = str2;
        }

        public static TileCreatedReason valueOf(String str) {
            return (TileCreatedReason) Enum.valueOf(TileCreatedReason.class, str);
        }

        public static TileCreatedReason[] values() {
            return (TileCreatedReason[]) $VALUES.clone();
        }

        public final String getReadable() {
            return this.readable;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TileDestroyedReason {
        public static final /* synthetic */ TileDestroyedReason[] $VALUES;
        public static final TileDestroyedReason CUSTOM_TILE_USER_CHANGED;
        public static final TileDestroyedReason EXISTING_TILE_NOT_AVAILABLE;
        public static final TileDestroyedReason NEW_TILE_NOT_AVAILABLE;
        public static final TileDestroyedReason RELEASE_CUSTOM_TILE_USER_CHANGED;
        public static final TileDestroyedReason TILE_NOT_PRESENT_IN_NEW_USER;
        public static final TileDestroyedReason TILE_REMOVED;
        private final String readable;

        static {
            TileDestroyedReason tileDestroyedReason = new TileDestroyedReason("TILE_REMOVED", 0, "Tile removed from  current set");
            TILE_REMOVED = tileDestroyedReason;
            TileDestroyedReason tileDestroyedReason2 = new TileDestroyedReason("CUSTOM_TILE_USER_CHANGED", 1, "User changed for custom tile");
            CUSTOM_TILE_USER_CHANGED = tileDestroyedReason2;
            TileDestroyedReason tileDestroyedReason3 = new TileDestroyedReason("NEW_TILE_NOT_AVAILABLE", 2, "New tile not available");
            NEW_TILE_NOT_AVAILABLE = tileDestroyedReason3;
            TileDestroyedReason tileDestroyedReason4 = new TileDestroyedReason("EXISTING_TILE_NOT_AVAILABLE", 3, "Existing tile not available");
            EXISTING_TILE_NOT_AVAILABLE = tileDestroyedReason4;
            TileDestroyedReason tileDestroyedReason5 = new TileDestroyedReason("TILE_NOT_PRESENT_IN_NEW_USER", 4, "Tile not present in new user");
            TILE_NOT_PRESENT_IN_NEW_USER = tileDestroyedReason5;
            TileDestroyedReason tileDestroyedReason6 = new TileDestroyedReason("RELEASE_CUSTOM_TILE_USER_CHANGED", 5, "From SecQSTileInstanceManager, Destroy custom tile by user change.");
            RELEASE_CUSTOM_TILE_USER_CHANGED = tileDestroyedReason6;
            TileDestroyedReason[] tileDestroyedReasonArr = {tileDestroyedReason, tileDestroyedReason2, tileDestroyedReason3, tileDestroyedReason4, tileDestroyedReason5, tileDestroyedReason6};
            $VALUES = tileDestroyedReasonArr;
            EnumEntriesKt.enumEntries(tileDestroyedReasonArr);
        }

        private TileDestroyedReason(String str, int i, String str2) {
            this.readable = str2;
        }

        public static TileDestroyedReason valueOf(String str) {
            return (TileDestroyedReason) Enum.valueOf(TileDestroyedReason.class, str);
        }

        public static TileDestroyedReason[] values() {
            return (TileDestroyedReason[]) $VALUES.clone();
        }

        public final String getReadable() {
            return this.readable;
        }
    }

    static {
        new Companion(null);
    }

    public QSPipelineLogger(LogBuffer logBuffer, LogBuffer logBuffer2, LogBuffer logBuffer3) {
        this.tileListLogBuffer = logBuffer;
        this.tileAutoAddLogBuffer = logBuffer2;
        this.restoreLogBuffer = logBuffer3;
    }

    public final void logParsedTiles(List list, boolean z, int i) {
        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
            logParsedTiles(list, z, i, QSType.QS);
            return;
        }
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(16);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = list.toString();
        logMessageImpl.bool1 = z;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
    }

    public final void logProcessTileChange(UserTileSpecRepository.ChangeAction changeAction, List list, int i, QSType qSType) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(23);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = changeAction.toString();
        String string = list.toString();
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str2 = string;
        logMessageImpl.int1 = i;
        logMessageImpl.str3 = qSType.name();
        logBuffer.commit(logMessageObtain);
    }

    public final void logRestoreProcessorApplied(String str, RestorePreprocessorStep restorePreprocessorStep) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(9);
        LogBuffer logBuffer = this.restoreLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSRestoreLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        ((LogMessageImpl) logMessageObtain).str2 = restorePreprocessorStep.name();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTileCreated(TileSpec tileSpec) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(21);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = tileSpec.toString();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTileDestroyed(TileSpec tileSpec, TileDestroyedReason tileDestroyedReason) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = tileSpec.toString();
        logMessageImpl.str2 = tileDestroyedReason.getReadable();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTileNotFoundInFactory(TileSpec tileSpec) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = tileSpec.toString();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTileUserChanged(TileSpec tileSpec, int i) {
        LogLevel logLevel = LogLevel.VERBOSE;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = tileSpec.toString();
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
    }

    public final void logTileVisibilityUpdated(boolean z, TileSpec.CustomTileSpec customTileSpec, int i, QSType qSType) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(20);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.str1 = customTileSpec.toString();
        logMessageImpl.int1 = i;
        logMessageImpl.str2 = qSType.name();
        logBuffer.commit(logMessageObtain);
    }

    public final void logTilesFotaUpdatedAndRecalculated(List list, List list2, List list3, boolean z, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(18);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = list.toString();
        logMessageImpl.str2 = list2.toString();
        logMessageImpl.str3 = list3.toString();
        logMessageImpl.bool1 = z;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
    }

    public final void logTilesNotInstalled(int i, Collection collection) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(19);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = collection.toString();
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
    }

    public final void logUsingRetailTiles() {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.tileListLogBuffer;
        logBuffer.commit(logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null));
    }

    public final void logTileCreated(TileSpec tileSpec, TileCreatedReason tileCreatedReason) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(11);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = tileSpec.toString();
        logMessageImpl.str2 = tileCreatedReason.getReadable();
        logBuffer.commit(logMessageObtain);
    }

    public final void logParsedTiles(List list, boolean z, int i, QSType qSType) {
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(17);
        LogBuffer logBuffer = this.tileListLogBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = qSType.name();
        logMessageImpl.str2 = list.toString();
        logMessageImpl.bool1 = z;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
    }
}
