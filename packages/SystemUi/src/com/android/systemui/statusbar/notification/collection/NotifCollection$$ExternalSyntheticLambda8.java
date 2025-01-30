package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda8 {
    public final /* synthetic */ NotifCollection f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda8(NotifCollection notifCollection) {
        this.f$0 = notifCollection;
    }

    public final void onEndLifetimeExtension(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender) {
        NotifCollection notifCollection = this.f$0;
        notifCollection.getClass();
        Assert.isMainThread();
        if (notifCollection.mAttached) {
            NotificationEntry entry = notifCollection.getEntry(notificationEntry.mKey);
            String logKey = NotificationUtils.logKey(notificationEntry);
            String str = entry == null ? "null" : notificationEntry == entry ? "same" : "different";
            NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
            if (notificationEntry != entry) {
                notifCollectionLogger.logEntryBeingExtendedNotInCollection(notificationEntry, notifLifetimeExtender, str);
            }
            List list = notificationEntry.mLifetimeExtenders;
            ArrayList arrayList = (ArrayList) list;
            if (!arrayList.remove(notifLifetimeExtender)) {
                IllegalStateException illegalStateException = new IllegalStateException(String.format("Cannot end lifetime extension for extender \"%s\" of entry %s (collection entry is %s)", notifLifetimeExtender.getName(), logKey, str));
                notifCollection.mEulogizer.record(illegalStateException);
                throw illegalStateException;
            }
            notifCollectionLogger.logLifetimeExtensionEnded(notificationEntry, notifLifetimeExtender, arrayList.size());
            if ((((ArrayList) list).size() > 0) || !notifCollection.tryRemoveNotification(notificationEntry)) {
                return;
            }
            notifCollection.dispatchEventsAndRebuildList("onEndLifetimeExtension");
        }
    }
}
