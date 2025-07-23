package com.android.systemui.statusbar.notification.collection;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.Assert;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda4 implements NotifDismissInterceptor.OnEndDismissInterception, NotifLifetimeExtender.OnEndLifetimeExtensionCallback {
    public final /* synthetic */ NotifCollection f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda4(NotifCollection notifCollection) {
        this.f$0 = notifCollection;
    }

    public void onEndLifetimeExtension(NotifLifetimeExtender notifLifetimeExtender, NotificationEntry notificationEntry) {
        int i = NotifCollection.$r8$clinit;
        NotifCollection notifCollection = this.f$0;
        notifCollection.getClass();
        Assert.isMainThread();
        if (notifCollection.mAttached) {
            NotificationEntry entry = notifCollection.getEntry(notificationEntry.mKey);
            String logKey = NotificationUtils.logKey(notificationEntry);
            String str = entry == null ? "null" : notificationEntry == entry ? "same" : "different";
            NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
            if (notificationEntry != entry) {
                notifCollectionLogger.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda0 = new NotifCollectionLogger$$ExternalSyntheticLambda0(20);
                LogBuffer logBuffer = notifCollectionLogger.buffer;
                LogMessage obtain = logBuffer.obtain("NotifCollection", logLevel, notifCollectionLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = NotificationUtils.logKey(notificationEntry);
                logMessageImpl.str2 = notifLifetimeExtender.getName();
                logMessageImpl.str3 = str;
                logBuffer.commit(obtain);
            }
            if (!((ArrayList) notificationEntry.mLifetimeExtenders).remove(notifLifetimeExtender)) {
                IllegalStateException illegalStateException = new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Cannot end lifetime extension for extender \"", notifLifetimeExtender.getName(), "\" of entry ", logKey, " (collection entry is "), str, ")"));
                notifCollection.mEulogizer.record(illegalStateException);
                throw illegalStateException;
            }
            int size = ((ArrayList) notificationEntry.mLifetimeExtenders).size();
            notifCollectionLogger.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            NotifCollectionLogger$$ExternalSyntheticLambda0 notifCollectionLogger$$ExternalSyntheticLambda02 = new NotifCollectionLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer2 = notifCollectionLogger.buffer;
            LogMessage obtain2 = logBuffer2.obtain("NotifCollection", logLevel2, notifCollectionLogger$$ExternalSyntheticLambda02, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
            logMessageImpl2.str1 = NotificationUtils.logKey(notificationEntry);
            logMessageImpl2.str2 = notifLifetimeExtender.getName();
            logMessageImpl2.int1 = size;
            logBuffer2.commit(obtain2);
            if (((ArrayList) notificationEntry.mLifetimeExtenders).size() <= 0 && notifCollection.tryRemoveNotification(notificationEntry)) {
                notifCollection.dispatchEventsAndRebuildList("onEndLifetimeExtension");
            }
        }
    }
}
