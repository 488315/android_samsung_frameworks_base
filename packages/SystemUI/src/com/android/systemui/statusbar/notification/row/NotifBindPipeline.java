package com.android.systemui.statusbar.notification.row;

import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationEntryProcessorFactoryLooperImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifBindPipeline {
    public final AnonymousClass1 mCollectionListener;
    public final NotifBindPipelineLogger mLogger;
    public BindStage mStage;
    public final NotificationEntryProcessorFactoryLooperImpl.HandlerProcessor mStartProcessor;
    public final Map mBindEntries = new ArrayMap();
    public final List mScratchCallbacksList = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface BindCallback {
        void onBindFinished(NotificationEntry notificationEntry);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BindEntry {
        public final Set callbacks;
        public boolean invalidated;
        public ExpandableNotificationRow row;

        public /* synthetic */ BindEntry(NotifBindPipeline notifBindPipeline, int i) {
            this(notifBindPipeline);
        }

        private BindEntry(NotifBindPipeline notifBindPipeline) {
            this.callbacks = new ArraySet();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener, com.android.systemui.statusbar.notification.row.NotifBindPipeline$1] */
    public NotifBindPipeline(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, NotificationEntryProcessorFactory notificationEntryProcessorFactory) {
        ?? r0 = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
                ExpandableNotificationRow expandableNotificationRow = ((BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).remove(notificationEntry)).row;
                if (expandableNotificationRow != null) {
                    notifBindPipeline.mStage.abortStage(notificationEntry, expandableNotificationRow);
                }
                ((ArrayMap) notifBindPipeline.mStage.mContentParams).remove(notificationEntry);
                NotificationEntryProcessorFactoryLooperImpl.HandlerProcessor handlerProcessor = notifBindPipeline.mStartProcessor;
                handlerProcessor.getClass();
                handlerProcessor.removeMessages(1, notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryInit(NotificationEntry notificationEntry) {
                NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
                ((ArrayMap) notifBindPipeline.mBindEntries).put(notificationEntry, new BindEntry(notifBindPipeline, 0));
                BindStage bindStage = notifBindPipeline.mStage;
                ((ArrayMap) bindStage.mContentParams).put(notificationEntry, bindStage.newStageParams());
            }
        };
        this.mCollectionListener = r0;
        ((NotifPipeline) commonNotifCollection).addCollectionListener(r0);
        this.mLogger = notifBindPipelineLogger;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
                NotificationEntry notificationEntry = (NotificationEntry) obj;
                NotifBindPipelineLogger notifBindPipelineLogger2 = notifBindPipeline.mLogger;
                notifBindPipelineLogger2.getClass();
                LogLevel logLevel = LogLevel.INFO;
                NotifBindPipelineLogger$$ExternalSyntheticLambda0 notifBindPipelineLogger$$ExternalSyntheticLambda0 = new NotifBindPipelineLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer = notifBindPipelineLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                logBuffer.commit(obtain);
                if (notifBindPipeline.mStage == null) {
                    throw new IllegalStateException("No stage was ever set on the pipeline");
                }
                notifBindPipeline.mStage.executeStage(notificationEntry, ((NotifBindPipeline.BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry)).row, new NotifBindPipeline$$ExternalSyntheticLambda2(notifBindPipeline));
            }
        };
        NotificationEntryProcessorFactoryLooperImpl notificationEntryProcessorFactoryLooperImpl = (NotificationEntryProcessorFactoryLooperImpl) notificationEntryProcessorFactory;
        notificationEntryProcessorFactoryLooperImpl.getClass();
        this.mStartProcessor = new NotificationEntryProcessorFactoryLooperImpl.HandlerProcessor(notificationEntryProcessorFactoryLooperImpl.mMainLooper, consumer);
    }

    public final void requestPipelineRun(NotificationEntry notificationEntry) {
        NotifBindPipelineLogger notifBindPipelineLogger = this.mLogger;
        notifBindPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$$ExternalSyntheticLambda0 notifBindPipelineLogger$$ExternalSyntheticLambda0 = new NotifBindPipelineLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        ExpandableNotificationRow expandableNotificationRow = ((BindEntry) ((ArrayMap) this.mBindEntries).get(notificationEntry)).row;
        if (expandableNotificationRow == null) {
            LogMessage obtain2 = logBuffer.obtain("NotifBindPipeline", logLevel, new NotifBindPipelineLogger$$ExternalSyntheticLambda0(0), null);
            ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain2);
        } else {
            this.mStage.abortStage(notificationEntry, expandableNotificationRow);
            NotificationEntryProcessorFactoryLooperImpl.HandlerProcessor handlerProcessor = this.mStartProcessor;
            handlerProcessor.getClass();
            if (handlerProcessor.hasMessages(1, notificationEntry)) {
                return;
            }
            handlerProcessor.sendMessage(Message.obtain(handlerProcessor, 1, notificationEntry));
        }
    }
}
