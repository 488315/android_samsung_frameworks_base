package com.android.systemui.statusbar.notification.row;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifBindPipeline {
    public final C28751 mCollectionListener;
    public final NotifBindPipelineLogger mLogger;
    public final NotifBindPipelineHandler mMainHandler;
    public BindStage mStage;
    public final Map mBindEntries = new ArrayMap();
    public final List mScratchCallbacksList = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BindCallback {
        void onBindFinished(NotificationEntry notificationEntry);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BindEntry {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotifBindPipelineHandler extends Handler {
        public NotifBindPipelineHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                throw new IllegalArgumentException("Unknown message type: " + message.what);
            }
            NotificationEntry notificationEntry = (NotificationEntry) message.obj;
            NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
            NotifBindPipelineLogger notifBindPipelineLogger = notifBindPipeline.mLogger;
            notifBindPipelineLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifBindPipelineLogger$logStartPipeline$2 notifBindPipelineLogger$logStartPipeline$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logStartPipeline$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Start pipeline for notif: ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = notifBindPipelineLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logStartPipeline$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
            if (notifBindPipeline.mStage == null) {
                throw new IllegalStateException("No stage was ever set on the pipeline");
            }
            notifBindPipeline.mStage.executeStage(notificationEntry, ((BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).get(notificationEntry)).row, new NotifBindPipeline$$ExternalSyntheticLambda0(notifBindPipeline));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener, com.android.systemui.statusbar.notification.row.NotifBindPipeline$1] */
    public NotifBindPipeline(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, Looper looper) {
        ?? r0 = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                NotifBindPipeline notifBindPipeline = NotifBindPipeline.this;
                if (((BindEntry) ((ArrayMap) notifBindPipeline.mBindEntries).remove(notificationEntry)).row != null) {
                    notifBindPipeline.mStage.abortStage(notificationEntry);
                }
                ((ArrayMap) notifBindPipeline.mStage.mContentParams).remove(notificationEntry);
                notifBindPipeline.mMainHandler.removeMessages(1, notificationEntry);
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
        this.mMainHandler = new NotifBindPipelineHandler(looper);
    }

    public final void requestPipelineRun(NotificationEntry notificationEntry) {
        NotifBindPipelineLogger notifBindPipelineLogger = this.mLogger;
        notifBindPipelineLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        NotifBindPipelineLogger$logRequestPipelineRun$2 notifBindPipelineLogger$logRequestPipelineRun$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logRequestPipelineRun$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Request pipeline run for notif: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = notifBindPipelineLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifBindPipeline", logLevel, notifBindPipelineLogger$logRequestPipelineRun$2, null);
        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
        if (((BindEntry) ((ArrayMap) this.mBindEntries).get(notificationEntry)).row == null) {
            LogMessage obtain2 = logBuffer.obtain("NotifBindPipeline", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger$logRequestPipelineRowNotSet$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Row is not set so pipeline will not run. notif = ", ((LogMessage) obj).getStr1());
                }
            }, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain2, logBuffer, obtain2);
            return;
        }
        this.mStage.abortStage(notificationEntry);
        NotifBindPipelineHandler notifBindPipelineHandler = this.mMainHandler;
        if (notifBindPipelineHandler.hasMessages(1, notificationEntry)) {
            return;
        }
        notifBindPipelineHandler.sendMessage(Message.obtain(notifBindPipelineHandler, 1, notificationEntry));
    }
}
