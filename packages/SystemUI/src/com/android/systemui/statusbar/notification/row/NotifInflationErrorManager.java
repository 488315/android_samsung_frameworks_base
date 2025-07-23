package com.android.systemui.statusbar.notification.row;

import androidx.collection.ArraySet;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotifInflationErrorManager {
    public final ArraySet mErroredNotifs = new ArraySet();
    public final List mListeners = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
