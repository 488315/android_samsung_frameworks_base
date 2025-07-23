package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class QSSettingsRestoredBroadcastRepository$restoreData$2 extends AdaptedFunctionReference implements Function2 {
    public QSSettingsRestoredBroadcastRepository$restoreData$2(Object obj) {
        super(2, obj, QSPipelineLogger.class, "logSettingsRestored", "logSettingsRestored(Lcom/android/systemui/qs/pipeline/data/model/RestoreData;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        RestoreData restoreData = (RestoreData) obj;
        QSPipelineLogger qSPipelineLogger = (QSPipelineLogger) this.receiver;
        QSSettingsRestoredBroadcastRepository.Companion companion = QSSettingsRestoredBroadcastRepository.Companion;
        qSPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = qSPipelineLogger.restoreLogBuffer;
        LogMessage obtain = logBuffer.obtain("QSRestoreLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = restoreData.userId;
        logMessageImpl.str1 = restoreData.restoredTiles.toString();
        logMessageImpl.str2 = restoreData.restoredAutoAddedTiles.toString();
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
