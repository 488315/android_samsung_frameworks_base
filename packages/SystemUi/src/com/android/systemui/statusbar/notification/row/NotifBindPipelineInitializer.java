package com.android.systemui.statusbar.notification.row;

import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Stage set: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logStageSet$2, null);
        obtain.setStr1(name);
        logBuffer.commit(obtain);
        notifBindPipeline.mStage = rowContentBindStage;
        rowContentBindStage.mBindRequestListener = new NotifBindPipeline$$ExternalSyntheticLambda0(notifBindPipeline);
    }
}
