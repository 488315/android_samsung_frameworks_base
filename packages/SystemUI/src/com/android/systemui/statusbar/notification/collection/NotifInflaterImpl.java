package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import kotlin.ExceptionsKt__ExceptionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotifInflaterImpl implements NotifInflater {
    public final NotifInflaterLogger mLogger;
    public final NotifInflationErrorManager mNotifErrorManager;
    public NotificationRowBinderImpl mNotificationRowBinder;

    public NotifInflaterImpl(NotifInflationErrorManager notifInflationErrorManager, NotifInflaterLogger notifInflaterLogger) {
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mLogger = notifInflaterLogger;
    }

    public final void inflateViewsImpl(NotificationEntry notificationEntry, NotifInflater.Params params, NotifInflater.InflationCallback inflationCallback) {
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        try {
            NotificationRowBinderImpl notificationRowBinderImpl = this.mNotificationRowBinder;
            if (notificationRowBinderImpl == null) {
                throw new RuntimeException("NotificationRowBinder must be attached before using NotifInflaterImpl.");
            }
            notificationRowBinderImpl.inflateViews(notificationEntry, params, new AnonymousClass1(notificationEntry, inflationCallback));
        } catch (InflationException e) {
            NotifInflaterLogger notifInflaterLogger = this.mLogger;
            notifInflaterLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            NotifInflaterLogger$$ExternalSyntheticLambda0 notifInflaterLogger$$ExternalSyntheticLambda0 = new NotifInflaterLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = notifInflaterLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifInflater", logLevel, notifInflaterLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logMessageImpl.str2 = ExceptionsKt__ExceptionsKt.stackTraceToString(e);
            logBuffer.commit(obtain);
            notifInflationErrorManager.setInflationError(notificationEntry, e);
        } catch (RuntimeException e2) {
            notifInflationErrorManager.setInflationError(notificationEntry, e2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.collection.NotifInflaterImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements NotificationRowContentBinder.InflationCallback {
        public final /* synthetic */ NotifInflater.InflationCallback val$callback;
        public final /* synthetic */ NotificationEntry val$entry;

        public AnonymousClass1(NotificationEntry notificationEntry, NotifInflater.InflationCallback inflationCallback) {
            this.val$entry = notificationEntry;
            this.val$callback = inflationCallback;
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            int i = NotificationBundleUi.$r8$clinit;
            NotifInflaterImpl.this.mNotifErrorManager.setInflationError(notificationEntry, exc);
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void onAsyncInflationFinished() {
            NotifInflationErrorManager notifInflationErrorManager = NotifInflaterImpl.this.mNotifErrorManager;
            NotificationEntry notificationEntry = this.val$entry;
            notifInflationErrorManager.clearInflationError(notificationEntry);
            NotifInflater.InflationCallback inflationCallback = this.val$callback;
            if (inflationCallback != null) {
                inflationCallback.onInflationFinished(notificationEntry, notificationEntry.mRowController);
            }
        }

        @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
        public final void handleInflationException(Exception exc) {
            NotifInflaterImpl.this.mNotifErrorManager.setInflationError(this.val$entry, exc);
        }
    }
}
