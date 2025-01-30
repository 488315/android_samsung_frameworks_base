package com.android.systemui.statusbar.notification.interruption;

import android.util.ArrayMap;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.os.CancellationSignal;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.NotificationClicker$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$bindForAsyncHeadsUp$2;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpViewBinder {
    public final HeadsUpViewBinderLogger mLogger;
    public final NotificationMessagingUtil mNotificationMessagingUtil;
    public NotificationPresenter mNotificationPresenter;
    public final Map mOngoingBindCallbacks = new ArrayMap();
    public final RowContentBindStage mStage;

    public HeadsUpViewBinder(NotificationMessagingUtil notificationMessagingUtil, RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        this.mNotificationMessagingUtil = notificationMessagingUtil;
        this.mStage = rowContentBindStage;
        this.mLogger = headsUpViewBinderLogger;
    }

    public final void abortBindCallback(NotificationEntry notificationEntry) {
        CancellationSignal cancellationSignal = (CancellationSignal) ((ArrayMap) this.mOngoingBindCallbacks).remove(notificationEntry);
        if (cancellationSignal != null) {
            HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
            headsUpViewBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpViewBinderLogger$currentOngoingBindingAborted$2 headsUpViewBinderLogger$currentOngoingBindingAborted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$currentOngoingBindingAborted$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PathParser$$ExternalSyntheticOutline0.m29m("aborted potential ongoing heads up entry binding ", ((LogMessage) obj).getStr1(), " ");
                }
            };
            LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$currentOngoingBindingAborted$2, null);
            obtain.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
            logBuffer.commit(obtain);
            cancellationSignal.cancel();
        }
    }

    public final void bindHeadsUpView(final NotificationEntry notificationEntry, final HeadsUpCoordinator$bindForAsyncHeadsUp$2 headsUpCoordinator$bindForAsyncHeadsUp$2) {
        RowContentBindStage rowContentBindStage = this.mStage;
        final RowContentBindParams rowContentBindParams = (RowContentBindParams) rowContentBindStage.getStageParams(notificationEntry);
        boolean z = this.mNotificationMessagingUtil.isImportantMessaging(notificationEntry.mSbn, notificationEntry.getImportance()) && !((StatusBarNotificationPresenter) this.mNotificationPresenter).isPresenterFullyCollapsed();
        if (rowContentBindParams.mUseIncreasedHeadsUpHeight != z) {
            rowContentBindParams.mDirtyContentViews |= 4;
        }
        rowContentBindParams.mUseIncreasedHeadsUpHeight = z;
        rowContentBindParams.requireContentViews(4);
        CancellationSignal requestRebind = rowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                HeadsUpViewBinder headsUpViewBinder = HeadsUpViewBinder.this;
                HeadsUpViewBinderLogger headsUpViewBinderLogger = headsUpViewBinder.mLogger;
                headsUpViewBinderLogger.getClass();
                LogLevel logLevel = LogLevel.INFO;
                HeadsUpViewBinderLogger$entryBoundSuccessfully$2 headsUpViewBinderLogger$entryBoundSuccessfully$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$entryBoundSuccessfully$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return PathParser$$ExternalSyntheticOutline0.m29m("heads up entry bound successfully ", ((LogMessage) obj).getStr1(), " ");
                    }
                };
                LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
                LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$entryBoundSuccessfully$2, null);
                NotificationEntry notificationEntry3 = notificationEntry;
                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry3, obtain, logBuffer, obtain);
                notificationEntry2.row.mUseIncreasedHeadsUpHeight = rowContentBindParams.mUseIncreasedHeadsUpHeight;
                ((ArrayMap) headsUpViewBinder.mOngoingBindCallbacks).remove(notificationEntry3);
                NotifBindPipeline.BindCallback bindCallback = headsUpCoordinator$bindForAsyncHeadsUp$2;
                if (bindCallback != null) {
                    bindCallback.onBindFinished(notificationEntry2);
                }
            }
        });
        abortBindCallback(notificationEntry);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
        headsUpViewBinderLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        HeadsUpViewBinderLogger$startBindingHun$2 headsUpViewBinderLogger$startBindingHun$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$startBindingHun$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PathParser$$ExternalSyntheticOutline0.m29m("start binding heads up entry ", ((LogMessage) obj).getStr1(), " ");
            }
        };
        LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
        LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$startBindingHun$2, null);
        NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
        ((ArrayMap) this.mOngoingBindCallbacks).put(notificationEntry, requestRebind);
    }

    public final void unbindHeadsUpView(NotificationEntry notificationEntry) {
        abortBindCallback(notificationEntry);
        RowContentBindStage rowContentBindStage = this.mStage;
        RowContentBindParams rowContentBindParams = (RowContentBindParams) ((ArrayMap) rowContentBindStage.mContentParams).get(notificationEntry);
        HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
        if (rowContentBindParams == null) {
            headsUpViewBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpViewBinderLogger$entryBindStageParamsNullOnUnbind$2 headsUpViewBinderLogger$entryBindStageParamsNullOnUnbind$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$entryBindStageParamsNullOnUnbind$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return PathParser$$ExternalSyntheticOutline0.m29m("heads up entry bind stage params null on unbind ", ((LogMessage) obj).getStr1(), " ");
                }
            };
            LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
            LogMessage obtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$entryBindStageParamsNullOnUnbind$2, null);
            NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry, obtain, logBuffer, obtain);
            return;
        }
        rowContentBindParams.markContentViewsFreeable(4);
        headsUpViewBinderLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        HeadsUpViewBinderLogger$entryContentViewMarkedFreeable$2 headsUpViewBinderLogger$entryContentViewMarkedFreeable$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$entryContentViewMarkedFreeable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return PathParser$$ExternalSyntheticOutline0.m29m("start unbinding heads up entry ", ((LogMessage) obj).getStr1(), " ");
            }
        };
        LogBuffer logBuffer2 = headsUpViewBinderLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("HeadsUpViewBinder", logLevel2, headsUpViewBinderLogger$entryContentViewMarkedFreeable$2, null);
        obtain2.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logBuffer2.commit(obtain2);
        rowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                HeadsUpViewBinderLogger headsUpViewBinderLogger2 = HeadsUpViewBinder.this.mLogger;
                headsUpViewBinderLogger2.getClass();
                LogLevel logLevel3 = LogLevel.INFO;
                HeadsUpViewBinderLogger$entryUnbound$2 headsUpViewBinderLogger$entryUnbound$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger$entryUnbound$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return PathParser$$ExternalSyntheticOutline0.m29m("heads up entry unbound successfully ", ((LogMessage) obj).getStr1(), " ");
                    }
                };
                LogBuffer logBuffer3 = headsUpViewBinderLogger2.buffer;
                LogMessage obtain3 = logBuffer3.obtain("HeadsUpViewBinder", logLevel3, headsUpViewBinderLogger$entryUnbound$2, null);
                NotificationClicker$$ExternalSyntheticOutline0.m202m(notificationEntry2, obtain3, logBuffer3, obtain3);
            }
        });
    }
}
