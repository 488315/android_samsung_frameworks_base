package com.android.systemui.qs.pipeline.data.repository;

import com.android.systemui.qs.pipeline.data.model.RestoreData;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class QSSettingsRestoredBroadcastRepository$restoreData$2 extends AdaptedFunctionReference implements Function2 {
    public QSSettingsRestoredBroadcastRepository$restoreData$2(Object obj) {
        super(2, obj, QSPipelineLogger.class, "logSettingsRestored", "logSettingsRestored(Lcom/android/systemui/qs/pipeline/data/model/RestoreData;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        QSPipelineLogger qSPipelineLogger = (QSPipelineLogger) this.receiver;
        QSSettingsRestoredBroadcastRepository.Companion companion = QSSettingsRestoredBroadcastRepository.Companion;
        qSPipelineLogger.logSettingsRestored((RestoreData) obj);
        return Unit.INSTANCE;
    }
}
