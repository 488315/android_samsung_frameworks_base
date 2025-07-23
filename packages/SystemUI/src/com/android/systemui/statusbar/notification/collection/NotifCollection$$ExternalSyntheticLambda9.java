package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.notifcollection.BindEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.util.Assert;
import java.util.ArrayDeque;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda9(NotifCollection notifCollection, StatusBarNotification statusBarNotification, String str, String str2) {
        this.f$0 = notifCollection;
        this.f$1 = statusBarNotification;
        this.f$2 = str;
        this.f$3 = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotifCollection notifCollection = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        String str = this.f$2;
        String str2 = this.f$3;
        int i = NotifCollection.$r8$clinit;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        NotificationEntry notificationEntry = (NotificationEntry) ((ArrayMap) notifCollection.mNotificationSet).get(statusBarNotification.getKey());
        NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
        if (notificationEntry == null) {
            notifCollectionLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda3 = new NotifCollectionLogger$$ExternalSyntheticLambda3(2);
            LogBuffer logBuffer = notifCollectionLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda3, null);
            String key = statusBarNotification.getKey();
            String replace = key != null ? key.replace("\n", "") : null;
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = replace;
            logMessageImpl.str2 = str;
            logMessageImpl.str3 = str2;
            logBuffer.commit(obtain);
            return;
        }
        notifCollectionLogger.getClass();
        LogLevel logLevel2 = LogLevel.INFO;
        NotifCollectionLogger$$ExternalSyntheticLambda3 notifCollectionLogger$$ExternalSyntheticLambda32 = new NotifCollectionLogger$$ExternalSyntheticLambda3(1);
        LogBuffer logBuffer2 = notifCollectionLogger.buffer;
        LogMessage obtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda32, null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.str1 = NotificationUtils.logKey(notificationEntry);
        logMessageImpl2.str2 = str;
        logMessageImpl2.str3 = str2;
        logBuffer2.commit(obtain2);
        notificationEntry.setSbn(statusBarNotification);
        ((ArrayDeque) notifCollection.mEventQueue).add(new BindEntryEvent(notificationEntry, statusBarNotification));
        LogMessage obtain3 = logBuffer2.obtain("NotifCollection", logLevel2, new NotifCollectionLogger$$ExternalSyntheticLambda3(4), null);
        ((LogMessageImpl) obtain3).str1 = NotificationUtils.logKey(notificationEntry);
        logBuffer2.commit(obtain3);
        ((ArrayDeque) notifCollection.mEventQueue).add(new EntryUpdatedEvent(notificationEntry, UpdateSource.SystemUi));
        notifCollection.dispatchEventsAndRebuildList("updateNotificationInternally");
    }
}
