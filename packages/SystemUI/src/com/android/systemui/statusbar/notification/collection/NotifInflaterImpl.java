package com.android.systemui.statusbar.notification.collection;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifInflaterImpl implements NotifInflater {
    public final NotifInflaterLogger mLogger;
    public final NotifInflationErrorManager mNotifErrorManager;
    public NotificationRowBinderImpl mNotificationRowBinder;

    public NotifInflaterImpl(NotifInflationErrorManager notifInflationErrorManager, NotifInflaterLogger notifInflaterLogger) {
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mLogger = notifInflaterLogger;
    }

    public final boolean abortInflation(NotificationEntry notificationEntry) {
        boolean abortTask = notificationEntry.abortTask();
        if (abortTask) {
            NotifInflaterLogger notifInflaterLogger = this.mLogger;
            notifInflaterLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NotifInflaterLogger$logAbortInflationAbortedTask$2 notifInflaterLogger$logAbortInflationAbortedTask$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logAbortInflationAbortedTask$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborted task to abort inflation for ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = notifInflaterLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$logAbortInflationAbortedTask$2, null);
            ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(obtain);
        }
        return abortTask;
    }

    public final void inflateViews(NotificationEntry notificationEntry, NotifInflater.Params params, PreparationCoordinator$$ExternalSyntheticLambda0 preparationCoordinator$$ExternalSyntheticLambda0) {
        NotifInflaterLogger notifInflaterLogger = this.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$logInflatingViews$2 notifInflaterLogger$logInflatingViews$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logInflatingViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("inflating views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$logInflatingViews$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = params.reason;
        logBuffer.commit(obtain);
        inflateViewsImpl(notificationEntry, params, preparationCoordinator$$ExternalSyntheticLambda0);
        LogMessage obtain2 = logBuffer.obtain("NotifInflater", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logInflatedViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflated views for ", ((LogMessage) obj).getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.notification.collection.NotifInflaterImpl$1] */
    public final void inflateViewsImpl(NotificationEntry notificationEntry, NotifInflater.Params params, final NotifInflater.InflationCallback inflationCallback) {
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        try {
            NotificationRowBinderImpl notificationRowBinderImpl = this.mNotificationRowBinder;
            if (notificationRowBinderImpl == 0) {
                throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
            }
            notificationRowBinderImpl.inflateViews(notificationEntry, params, new NotificationRowContentBinder.InflationCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterImpl.1
                @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
                public final void handleInflationException(NotificationEntry notificationEntry2, Exception exc) {
                    NotifInflaterImpl.this.mNotifErrorManager.setInflationError(notificationEntry2, exc);
                }

                @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
                public final void onAsyncInflationFinished(NotificationEntry notificationEntry2) {
                    NotifInflaterImpl.this.mNotifErrorManager.clearInflationError(notificationEntry2);
                    NotifInflater.InflationCallback inflationCallback2 = inflationCallback;
                    if (inflationCallback2 != null) {
                        inflationCallback2.onInflationFinished(notificationEntry2, notificationEntry2.mRowController);
                    }
                }
            });
        } catch (InflationException e) {
            NotifInflaterLogger notifInflaterLogger = this.mLogger;
            notifInflaterLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            NotifInflaterLogger$logInflationException$2 notifInflaterLogger$logInflationException$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logInflationException$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return FontProvider$$ExternalSyntheticOutline0.m("exception inflating views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
                }
            };
            LogBuffer logBuffer = notifInflaterLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$logInflationException$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.str2 = ExceptionsKt__ExceptionsKt.stackTraceToString(e);
            logBuffer.commit(obtain);
            notifInflationErrorManager.setInflationError(notificationEntry, e);
        } catch (RuntimeException e2) {
            notifInflationErrorManager.setInflationError(notificationEntry, e2);
        }
    }

    public final void rebindViews(NotificationEntry notificationEntry, NotifInflater.Params params, PreparationCoordinator$$ExternalSyntheticLambda0 preparationCoordinator$$ExternalSyntheticLambda0) {
        NotifInflaterLogger notifInflaterLogger = this.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$logRebindingViews$2 notifInflaterLogger$logRebindingViews$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logRebindingViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("rebinding views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$logRebindingViews$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = params.reason;
        logBuffer.commit(obtain);
        inflateViewsImpl(notificationEntry, params, preparationCoordinator$$ExternalSyntheticLambda0);
        LogMessage obtain2 = logBuffer.obtain("NotifInflater", logLevel, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logReboundViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("rebound views for ", ((LogMessage) obj).getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain2).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain2);
    }

    public final void releaseViews(NotificationEntry notificationEntry) {
        NotifInflaterLogger notifInflaterLogger = this.mLogger;
        notifInflaterLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        NotifInflaterLogger$logReleasingViews$2 notifInflaterLogger$logReleasingViews$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.NotifInflaterLogger$logReleasingViews$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborting inflation for ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = notifInflaterLogger.buffer;
        LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$logReleasingViews$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
        NotificationRowBinderImpl notificationRowBinderImpl = this.mNotificationRowBinder;
        if (notificationRowBinderImpl == null) {
            throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
        }
        notificationRowBinderImpl.releaseViews(notificationEntry);
    }
}
