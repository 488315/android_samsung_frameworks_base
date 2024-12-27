package com.android.systemui.statusbar.notification.collection;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.util.Assert;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda4 implements NotifDismissInterceptor.OnEndDismissInterception, NotifLifetimeExtender.OnEndLifetimeExtensionCallback {
    public final /* synthetic */ NotifCollection f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda4(NotifCollection notifCollection) {
        this.f$0 = notifCollection;
    }

    public void onEndLifetimeExtension(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender) {
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
            if (!((ArrayList) notificationEntry.mLifetimeExtenders).remove(notifLifetimeExtender)) {
                IllegalStateException illegalStateException = new IllegalStateException(ComponentActivity$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Cannot end lifetime extension for extender \"", notifLifetimeExtender.getName(), "\" of entry ", logKey, " (collection entry is "), str, ")"));
                notifCollection.mEulogizer.record(illegalStateException);
                throw illegalStateException;
            }
            notifCollectionLogger.logLifetimeExtensionEnded(notificationEntry, notifLifetimeExtender, ((ArrayList) notificationEntry.mLifetimeExtenders).size());
            if (((ArrayList) notificationEntry.mLifetimeExtenders).size() <= 0 && notifCollection.tryRemoveNotification(notificationEntry)) {
                notifCollection.dispatchEventsAndRebuildList("onEndLifetimeExtension");
            }
        }
    }
}
