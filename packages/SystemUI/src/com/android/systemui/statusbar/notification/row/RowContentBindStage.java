package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.BindStage;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class RowContentBindStage extends BindStage {
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
        if (this.mBinder.cancelBind(notificationEntry, expandableNotificationRow)) {
            RowContentBindStageLogger rowContentBindStageLogger = this.mLogger;
            rowContentBindStageLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            RowContentBindStageLogger$$ExternalSyntheticLambda0 rowContentBindStageLogger$$ExternalSyntheticLambda0 = new RowContentBindStageLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = rowContentBindStageLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtils.logKey(notificationEntry);
            logBuffer.commit(logMessageObtain);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.statusbar.notification.row.RowContentBindStage$1] */
    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotifBindPipeline$$ExternalSyntheticLambda2 notifBindPipeline$$ExternalSyntheticLambda2) {
        RowContentBindParams rowContentBindParams = (RowContentBindParams) getStageParams(notificationEntry);
        RowContentBindStageLogger rowContentBindStageLogger = this.mLogger;
        rowContentBindStageLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        RowContentBindStageLogger$$ExternalSyntheticLambda0 rowContentBindStageLogger$$ExternalSyntheticLambda0 = new RowContentBindStageLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = rowContentBindStageLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("RowContentBindStage", logLevel, rowContentBindStageLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = rowContentBindParams.toString();
        logBuffer.commit(logMessageObtain);
        int i = rowContentBindParams.mContentViews;
        int i2 = rowContentBindParams.mDirtyContentViews & i;
        NotificationRowContentBinder notificationRowContentBinder = this.mBinder;
        notificationRowContentBinder.unbindContent(notificationEntry, expandableNotificationRow, i ^ 511);
        NotificationRowContentBinder.BindParams bindParams = new NotificationRowContentBinder.BindParams(rowContentBindParams.mUseMinimized, rowContentBindParams.mRedactionType);
        boolean z = rowContentBindParams.mViewsNeedReinflation;
        if (z) {
            rowContentBindParams.mViewsNeedReinflation = false;
            rowContentBindParams.mDirtyContentViews = rowContentBindParams.mContentViews | rowContentBindParams.mDirtyContentViews;
        }
        ?? r9 = new NotificationRowContentBinder.InflationCallback(notificationEntry, notifBindPipeline$$ExternalSyntheticLambda2) { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStage.1
            public final /* synthetic */ BindStage.StageCallback val$callback;

            {
                this.val$callback = notifBindPipeline$$ExternalSyntheticLambda2;
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void handleInflationException(Exception exc) {
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void onAsyncInflationFinished() {
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void handleInflationException(NotificationEntry notificationEntry2, Exception exc) {
                int i3 = NotificationBundleUi.$r8$clinit;
                RowContentBindStage.this.mNotifInflationErrorManager.setInflationError(notificationEntry2, exc);
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public final void onAsyncInflationFinished(NotificationEntry notificationEntry2) {
                int i3 = NotificationBundleUi.$r8$clinit;
                RowContentBindStage rowContentBindStage = RowContentBindStage.this;
                rowContentBindStage.mNotifInflationErrorManager.clearInflationError(notificationEntry2);
                ((RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry2)).mDirtyContentViews = 0;
                NotifBindPipeline notifBindPipeline = ((NotifBindPipeline$$ExternalSyntheticLambda2) this.val$callback).f$0;
                NotifBindPipeline.BindEntry bindEntry = (NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry2);
                ArraySet arraySet = (ArraySet) bindEntry.callbacks;
                int size = arraySet.size();
                NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
                notifBindPipelineLogger.getClass();
                LogLevel logLevel2 = LogLevel.INFO;
                NotifBindPipelineLogger$$ExternalSyntheticLambda0 notifBindPipelineLogger$$ExternalSyntheticLambda0 = new NotifBindPipelineLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer2 = notifBindPipelineLogger.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("NotifBindPipeline", logLevel2, notifBindPipelineLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.str1 = NotificationUtilsKt.getLogKey(notificationEntry2);
                logMessageImpl2.int1 = size;
                logBuffer2.commit(logMessageObtain2);
                bindEntry.invalidated = false;
                ((ArrayList) notifBindPipeline.mScratchCallbacksList).addAll(arraySet);
                arraySet.clear();
                for (int i4 = 0; i4 < ((ArrayList) notifBindPipeline.mScratchCallbacksList).size(); i4++) {
                    ((NotifBindPipeline.BindCallback) ((ArrayList) notifBindPipeline.mScratchCallbacksList).get(i4)).onBindFinished(notificationEntry2);
                }
                ((ArrayList) notifBindPipeline.mScratchCallbacksList).clear();
            }
        };
        notificationRowContentBinder.cancelBind(notificationEntry, expandableNotificationRow);
        this.mBinder.bindContent(notificationEntry, expandableNotificationRow, i2, bindParams, z, r9);
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public final RowContentBindParams newStageParams() {
        return new RowContentBindParams();
    }
}
