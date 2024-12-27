package com.android.systemui.statusbar.notification.row;

import androidx.collection.ArraySet;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifInflationErrorManager {
    public final ArraySet mErroredNotifs = new ArraySet();
    public final List mListeners = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface NotifInflationErrorListener {
        void onNotifInflationError(NotificationEntry notificationEntry, Exception exc);

        void onNotifInflationErrorCleared(NotificationEntry notificationEntry);
    }

    public final void clearInflationError(NotificationEntry notificationEntry) {
        if (this.mErroredNotifs.contains(notificationEntry.mKey)) {
            this.mErroredNotifs.remove(notificationEntry.mKey);
            for (int i = 0; i < ((ArrayList) this.mListeners).size(); i++) {
                ((NotifInflationErrorListener) ((ArrayList) this.mListeners).get(i)).onNotifInflationErrorCleared(notificationEntry);
            }
        }
    }

    public final void setInflationError(NotificationEntry notificationEntry, Exception exc) {
        this.mErroredNotifs.add(notificationEntry.mKey);
        for (int i = 0; i < ((ArrayList) this.mListeners).size(); i++) {
            ((NotifInflationErrorListener) ((ArrayList) this.mListeners).get(i)).onNotifInflationError(notificationEntry, exc);
        }
    }
}
