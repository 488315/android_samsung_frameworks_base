package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifCollectionLogger {
    public final LogBuffer buffer;

    public NotifCollectionLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logAlreadyDismissedNotification(String str) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logAlreadyDismissedNotification$2 notifCollectionLogger$logAlreadyDismissedNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logAlreadyDismissedNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("MAYBE ALREADY_REMOVED_ENTRY but new posted: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logAlreadyDismissedNotification$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logCancelLocalDismissalNotDismissedNotif(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logCancelLocalDismissalNotDismissedNotif$2 notifCollectionLogger$logCancelLocalDismissalNotDismissedNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logCancelLocalDismissalNotDismissedNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CANCEL LOCAL DISMISS Not Dismissed ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logCancelLocalDismissalNotDismissedNotif$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logCanceledNotification(String str) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logCanceledNotification$2 notifCollectionLogger$logCanceledNotification$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logCanceledNotification$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("ENTER CANCELED_ENTRY: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logCanceledNotification$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logDismissAll(int i) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logDismissAll$2 notifCollectionLogger$logDismissAll$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logDismissAll$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "DISMISS ALL notifications for user ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logDismissAll$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logDismissAlreadyDismissedNotif(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logDismissAlreadyDismissedNotif$2 notifCollectionLogger$logDismissAlreadyDismissedNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logDismissAlreadyDismissedNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DISMISS Already Dismissed ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logDismissAlreadyDismissedNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logDismissAlreadyParentDismissedNotif(NotificationEntry notificationEntry, int i, int i2) {
        String str;
        NotificationEntry notificationEntry2;
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logDismissAlreadyParentDismissedNotif$2 notifCollectionLogger$logDismissAlreadyParentDismissedNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logDismissAlreadyParentDismissedNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "DISMISS Already Parent-Dismissed ", str1, " (", "/");
                m.append(int2);
                m.append(") with summary ");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logDismissAlreadyParentDismissedNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        GroupEntry parent = notificationEntry.getParent();
        if (parent == null || (notificationEntry2 = parent.mSummary) == null || (str = NotificationUtilsKt.getLogKey(notificationEntry2)) == null) {
            str = "(null)";
        }
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logDismissNonExistentNotif(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logDismissNonExistentNotif$2 notifCollectionLogger$logDismissNonExistentNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logDismissNonExistentNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DISMISS Non Existent ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logDismissNonExistentNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logEntryBeingExtendedNotInCollection(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender, String str) {
        LogLevel logLevel = LogLevel.WARNING;
        NotifCollectionLogger$logEntryBeingExtendedNotInCollection$2 notifCollectionLogger$logEntryBeingExtendedNotInCollection$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logEntryBeingExtendedNotInCollection$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("While ending lifetime extension by ", str2, " of ", str1, ", entry in collection is ");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logEntryBeingExtendedNotInCollection$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = notifLifetimeExtender.getName();
        logMessageImpl.str3 = str;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalAlreadyCancelledByServer(NotifCollection.FutureDismissal futureDismissal) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logFutureDismissalAlreadyCancelledByServer$2 notifCollectionLogger$logFutureDismissalAlreadyCancelledByServer$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalAlreadyCancelledByServer$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Ignoring: entry already cancelled by server: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalAlreadyCancelledByServer$2, null);
        ((LogMessageImpl) obtain).str1 = futureDismissal.mLabel;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalDismissing(NotifCollection.FutureDismissal futureDismissal, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logFutureDismissalDismissing$2 notifCollectionLogger$logFutureDismissalDismissing$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalDismissing$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Dismissing ", logMessage.getStr2(), " for: ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalDismissing$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = futureDismissal.mLabel;
        logMessageImpl.str2 = str;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalDoubleCancelledByServer(NotifCollection.FutureDismissal futureDismissal) {
        LogLevel logLevel = LogLevel.WARNING;
        NotifCollectionLogger$logFutureDismissalDoubleCancelledByServer$2 notifCollectionLogger$logFutureDismissalDoubleCancelledByServer$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalDoubleCancelledByServer$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("System server double cancelled: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalDoubleCancelledByServer$2, null);
        ((LogMessageImpl) obtain).str1 = futureDismissal.mLabel;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalDoubleRun(NotifCollection.FutureDismissal futureDismissal) {
        LogLevel logLevel = LogLevel.WARNING;
        NotifCollectionLogger$logFutureDismissalDoubleRun$2 notifCollectionLogger$logFutureDismissalDoubleRun$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalDoubleRun$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Double run: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalDoubleRun$2, null);
        ((LogMessageImpl) obtain).str1 = futureDismissal.mLabel;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalGotSystemServerCancel(NotifCollection.FutureDismissal futureDismissal, int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logFutureDismissalGotSystemServerCancel$2 notifCollectionLogger$logFutureDismissalGotSystemServerCancel$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalGotSystemServerCancel$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("SystemServer cancelled: ", logMessage.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1()));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalGotSystemServerCancel$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = futureDismissal.mLabel;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalMismatchedEntry(NotifCollection.FutureDismissal futureDismissal, String str, NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.WARNING;
        NotifCollectionLogger$logFutureDismissalMismatchedEntry$2 notifCollectionLogger$logFutureDismissalMismatchedEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalMismatchedEntry$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                String str1 = logMessage.getStr1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Mismatch: current ", str2, " is ", str3, " for: ");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalMismatchedEntry$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = futureDismissal.mLabel;
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalRegistered(NotifCollection.FutureDismissal futureDismissal) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logFutureDismissalRegistered$2 notifCollectionLogger$logFutureDismissalRegistered$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalRegistered$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Registered: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalRegistered$2, null);
        ((LogMessageImpl) obtain).str1 = futureDismissal.mLabel;
        logBuffer.commit(obtain);
    }

    public final void logFutureDismissalReused(NotifCollection.FutureDismissal futureDismissal) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logFutureDismissalReused$2 notifCollectionLogger$logFutureDismissalReused$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logFutureDismissalReused$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Reusing existing registration: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logFutureDismissalReused$2, null);
        ((LogMessageImpl) obtain).str1 = futureDismissal.mLabel;
        logBuffer.commit(obtain);
    }

    public final void logLifetimeExtended(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLifetimeExtended$2 notifCollectionLogger$logLifetimeExtended$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLifetimeExtended$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("LIFETIME EXTENDED: ", logMessage.getStr1(), " by ", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLifetimeExtended$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = notifLifetimeExtender.getName();
        logBuffer.commit(obtain);
    }

    public final void logLifetimeExtensionEnded(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender, int i) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLifetimeExtensionEnded$2 notifCollectionLogger$logLifetimeExtensionEnded$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLifetimeExtensionEnded$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt1(), " remaining extensions", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LIFETIME EXTENSION ENDED for ", logMessage.getStr1(), " by '", logMessage.getStr2(), "'; "));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLifetimeExtensionEnded$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        String name = notifLifetimeExtender.getName();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = name;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissAlreadyDismissedChild(int i, int i2, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissAlreadyDismissedChild$2 notifCollectionLogger$logLocallyDismissAlreadyDismissedChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissAlreadyDismissedChild$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISS Already Dismissed Child ", str1, " of parent ", str2, " (");
                m.append(int1);
                m.append("/");
                m.append(int2);
                m.append(")");
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissAlreadyDismissedChild$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissAlreadyDismissedNotif(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissAlreadyDismissedNotif$2 notifCollectionLogger$logLocallyDismissAlreadyDismissedNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissAlreadyDismissedNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "LOCALLY DISMISS Already Dismissed ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissAlreadyDismissedNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissAlreadyParentDismissedChild(int i, int i2, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissAlreadyParentDismissedChild$2 notifCollectionLogger$logLocallyDismissAlreadyParentDismissedChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissAlreadyParentDismissedChild$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISS Already Parent-Dismissed Child ", str1, " of parent ", str2, " (");
                m.append(int1);
                m.append("/");
                m.append(int2);
                m.append(")");
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissAlreadyParentDismissedChild$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissAlreadyParentDismissedNotif(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissAlreadyParentDismissedNotif$2 notifCollectionLogger$logLocallyDismissAlreadyParentDismissedNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissAlreadyParentDismissedNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "LOCALLY DISMISS Already Dismissed ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissAlreadyParentDismissedNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissMismatchedEntry(int i, int i2, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissMismatchedEntry$2 notifCollectionLogger$logLocallyDismissMismatchedEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissMismatchedEntry$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "LOCALLY DISMISS Mismatch ", str1, " (", "/");
                m.append(int2);
                m.append("): dismissing @");
                m.append(str2);
                m.append(" but stored @");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissMismatchedEntry$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logMessageImpl.str2 = Integer.toHexString(notificationEntry.hashCode());
        logMessageImpl.str3 = Integer.toHexString(notificationEntry2.hashCode());
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissNonExistentNotif(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissNonExistentNotif$2 notifCollectionLogger$logLocallyDismissNonExistentNotif$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissNonExistentNotif$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "LOCALLY DISMISS Non Existent ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissNonExistentNotif$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissed(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logLocallyDismissed$2 notifCollectionLogger$logLocallyDismissed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "LOCALLY DISMISSED ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissedAlreadyCanceledEntry(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logLocallyDismissedAlreadyCanceledEntry$2 notifCollectionLogger$logLocallyDismissedAlreadyCanceledEntry$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissedAlreadyCanceledEntry$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("LOCALLY DISMISSED Already Canceled ", ((LogMessage) obj).getStr1(), ". Trying to remove.");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissedAlreadyCanceledEntry$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logLocallyDismissedChild(int i, int i2, NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotifCollectionLogger$logLocallyDismissedChild$2 notifCollectionLogger$logLocallyDismissedChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logLocallyDismissedChild$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("LOCALLY DISMISSED CHILD (inferred): ", str1, " of parent ", str2, " (");
                m.append(int1);
                m.append("/");
                m.append(int2);
                m.append(")");
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logLocallyDismissedChild$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = NotificationUtilsKt.getLogKey(notificationEntry2);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logNoNotificationToRemoveWithKey(StatusBarNotification statusBarNotification, int i) {
        LogLevel logLevel = LogLevel.ERROR;
        NotifCollectionLogger$logNoNotificationToRemoveWithKey$2 notifCollectionLogger$logNoNotificationToRemoveWithKey$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNoNotificationToRemoveWithKey$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("No notification to remove with key ", logMessage.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1()));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNoNotificationToRemoveWithKey$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(statusBarNotification);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logNotifClearAllDismissalIntercepted(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifClearAllDismissalIntercepted$2 notifCollectionLogger$logNotifClearAllDismissalIntercepted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifClearAllDismissalIntercepted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "CLEAR ALL DISMISSAL INTERCEPTED ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifClearAllDismissalIntercepted$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logNotifDismissedIntercepted(NotificationEntry notificationEntry, int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifDismissedIntercepted$2 notifCollectionLogger$logNotifDismissedIntercepted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifDismissedIntercepted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                return Anchor$$ExternalSyntheticOutline0.m(logMessage.getInt2(), ")", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DISMISS INTERCEPTED ", str1, " (", "/"));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifDismissedIntercepted$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logNotifGroupPosted(int i, String str) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifGroupPosted$2 notifCollectionLogger$logNotifGroupPosted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifGroupPosted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "POSTED GROUP " + logMessage.getStr1() + " (" + logMessage.getInt1() + " events)";
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifGroupPosted$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtils.logKey(str);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logNotifInternalUpdate(NotificationEntry notificationEntry, String str, String str2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifInternalUpdate$2 notifCollectionLogger$logNotifInternalUpdate$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifInternalUpdate$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("UPDATED INTERNALLY ", str1, " BY ", str22, " BECAUSE ");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifInternalUpdate$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = str2;
        logBuffer.commit(obtain);
    }

    public final void logNotifInternalUpdateFailed(StatusBarNotification statusBarNotification, String str, String str2) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifInternalUpdateFailed$2 notifCollectionLogger$logNotifInternalUpdateFailed$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifInternalUpdateFailed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("FAILED INTERNAL UPDATE ", str1, " BY ", str22, " BECAUSE ");
                m.append(str3);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifInternalUpdateFailed$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(statusBarNotification);
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = str2;
        logBuffer.commit(obtain);
    }

    public final void logNotifPosted(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifPosted$2 notifCollectionLogger$logNotifPosted$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifPosted$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("POSTED ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifPosted$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logNotifReleased(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifReleased$2 notifCollectionLogger$logNotifReleased$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifReleased$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RELEASED ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifReleased$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logNotifRemoved(StatusBarNotification statusBarNotification, int i) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifRemoved$2 notifCollectionLogger$logNotifRemoved$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("REMOVED ", logMessage.getStr1(), " reason=", NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1()));
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifRemoved$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(statusBarNotification);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logNotifUpdated(NotificationEntry notificationEntry) {
        LogLevel logLevel = LogLevel.INFO;
        NotifCollectionLogger$logNotifUpdated$2 notifCollectionLogger$logNotifUpdated$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logNotifUpdated$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("UPDATED ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logNotifUpdated$2, null);
        ((LogMessageImpl) obtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logBuffer.commit(obtain);
    }

    public final void logRemoteExceptionOnClearAllNotifications(RemoteException remoteException) {
        LogLevel logLevel = LogLevel.WTF;
        NotifCollectionLogger$logRemoteExceptionOnClearAllNotifications$2 notifCollectionLogger$logRemoteExceptionOnClearAllNotifications$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logRemoteExceptionOnClearAllNotifications$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RemoteException while attempting to clear all notifications:\n", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logRemoteExceptionOnClearAllNotifications$2, null);
        ((LogMessageImpl) obtain).str1 = remoteException.toString();
        logBuffer.commit(obtain);
    }

    public final void logRemoteExceptionOnNotificationClear(NotificationEntry notificationEntry, int i, int i2, RemoteException remoteException) {
        LogLevel logLevel = LogLevel.WTF;
        NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2 notifCollectionLogger$logRemoteExceptionOnNotificationClear$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$logRemoteExceptionOnNotificationClear$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str2 = logMessage.getStr2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "RemoteException while attempting to clear ", str1, " (", "/");
                m.append(int2);
                m.append("):\n");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$logRemoteExceptionOnNotificationClear$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logMessageImpl.str2 = remoteException.toString();
        logBuffer.commit(obtain);
    }
}
