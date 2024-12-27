package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RowContentBindStage extends BindStage {
    public final NotificationRowContentBinder mBinder;
    public final RowContentBindStageLogger mLogger;
    public final NotifInflationErrorManager mNotifInflationErrorManager;

    public RowContentBindStage(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger) {
        this.mBinder = notificationRowContentBinder;
        this.mNotifInflationErrorManager = notifInflationErrorManager;
        this.mLogger = rowContentBindStageLogger;
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final void abortStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        if (this.mBinder.cancelBind(notificationEntry)) {
            RowContentBindStageLogger rowContentBindStageLogger = this.mLogger;
            rowContentBindStageLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            RowContentBindStageLogger$logAbortStageCancelledBind$2 rowContentBindStageLogger$logAbortStageCancelledBind$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$logAbortStageCancelledBind$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("cancelled bind to abort stage for ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = rowContentBindStageLogger.buffer;
            LogMessage obtain = logBuffer.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$logAbortStageCancelledBind$2, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.statusbar.notification.row.RowContentBindStage$1] */
    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, final NotifBindPipeline$$ExternalSyntheticLambda1 notifBindPipeline$$ExternalSyntheticLambda1) {
        RowContentBindParams rowContentBindParams = (RowContentBindParams) getStageParams(notificationEntry);
        RowContentBindStageLogger rowContentBindStageLogger = this.mLogger;
        rowContentBindStageLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        RowContentBindStageLogger$logExecutingStage$2 rowContentBindStageLogger$logExecutingStage$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStageLogger$logExecutingStage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("executing bind stage for ", logMessage.getStr1(), " with params ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = rowContentBindStageLogger.buffer;
        LogMessage obtain = logBuffer.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$logExecutingStage$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = rowContentBindParams.toString();
        logBuffer.commit(obtain);
        int i = rowContentBindParams.mContentViews;
        int i2 = rowContentBindParams.mDirtyContentViews & i;
        NotificationRowContentBinder notificationRowContentBinder = this.mBinder;
        notificationRowContentBinder.unbindContent(notificationEntry, expandableNotificationRow, i ^ 127);
        NotificationRowContentBinder.BindParams bindParams = new NotificationRowContentBinder.BindParams();
        bindParams.isMinimized = rowContentBindParams.mUseMinimized;
        bindParams.usesIncreasedHeight = rowContentBindParams.mUseIncreasedHeight;
        bindParams.usesIncreasedHeadsUpHeight = rowContentBindParams.mUseIncreasedHeadsUpHeight;
        boolean z = rowContentBindParams.mViewsNeedReinflation;
        ?? r9 = new NotificationRowContentBinder.InflationCallback() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStage.1
            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void handleInflationException(NotificationEntry notificationEntry2, Exception exc) {
                RowContentBindStage.this.mNotifInflationErrorManager.setInflationError(notificationEntry2, exc);
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void onAsyncInflationFinished(NotificationEntry notificationEntry2) {
                RowContentBindStage rowContentBindStage = RowContentBindStage.this;
                rowContentBindStage.mNotifInflationErrorManager.clearInflationError(notificationEntry2);
                ((RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry2)).mDirtyContentViews = 0;
                NotifBindPipeline notifBindPipeline = ((NotifBindPipeline$$ExternalSyntheticLambda1) notifBindPipeline$$ExternalSyntheticLambda1).f$0;
                NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry2);
                ArraySet arraySet = (ArraySet) bindEntry.callbacks;
                int size = arraySet.size();
                NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
                notifBindPipelineLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotifBindPipelineLogger$logFinishedPipeline$2 notifBindPipelineLogger$logFinishedPipeline$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logFinishedPipeline$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return "Finished pipeline for notif " + logMessage.getStr1() + " with " + logMessage.getInt1() + " callbacks";
                    }
                };
                LogBuffer logBuffer2 = notifBindPipelineLogger.buffer;
                LogMessage obtain2 = logBuffer2.obtain("NotifBindPipeline", logLevel2, notifBindPipelineLogger$logFinishedPipeline$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logMessageImpl2.int1 = size;
                logBuffer2.commit(obtain2);
                bindEntry.invalidated = false;
                ((ArrayList) notifBindPipeline.mScratchCallbacksList).addAll(arraySet);
                arraySet.clear();
                for (int i3 = 0; i3 < ((ArrayList) notifBindPipeline.mScratchCallbacksList).size(); i3++) {
                    ((NotifBindPipeline.BindCallback) ((ArrayList) notifBindPipeline.mScratchCallbacksList).get(i3)).onBindFinished(notificationEntry2);
                }
                ((ArrayList) notifBindPipeline.mScratchCallbacksList).clear();
            }
        };
        notificationRowContentBinder.cancelBind(notificationEntry);
        this.mBinder.bindContent(notificationEntry, expandableNotificationRow, i2, bindParams, z, r9);
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final RowContentBindParams newStageParams() {
        return new RowContentBindParams();
    }
}
