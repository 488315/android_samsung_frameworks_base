package com.android.systemui.statusbar.notification.row;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifBindPipelineInitializer {
    public final NotifBindPipeline mNotifBindPipeline;
    public final RowContentBindStage mRowContentBindStage;

    public NotifBindPipelineInitializer(NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage) {
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
    }

    public final void initialize() {
        NotifBindPipeline notifBindPipeline = this.mNotifBindPipeline;
        notifBindPipeline.getClass();
        RowContentBindStage rowContentBindStage = this.mRowContentBindStage;
        String name = rowContentBindStage.getClass().getName();
        NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
        notifBindPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logStageSet$2 notifBindPipelineLogger$logStageSet$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logStageSet$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Stage set: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logStageSet$2, null);
        ((LogMessageImpl) obtain).str1 = name;
        logBuffer.commit(obtain);
        notifBindPipeline.mStage = rowContentBindStage;
        rowContentBindStage.mBindRequestListener = new NotifBindPipeline$$ExternalSyntheticLambda1(notifBindPipeline);
    }
}
