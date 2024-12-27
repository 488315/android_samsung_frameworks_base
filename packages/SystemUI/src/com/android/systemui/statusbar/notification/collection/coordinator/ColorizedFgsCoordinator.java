package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public class ColorizedFgsCoordinator implements Coordinator {
    private static final String TAG = "ColorizedCoordinator";
    private final NotifSectioner mNotifSectioner = new NotifSectioner("ColorizedSectioner", 8) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ColorizedFgsCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                return false;
            }
            if (NotiRune.NOTI_INSIGNIFICANT && representativeEntry.isInsignificant()) {
                return false;
            }
            return ColorizedFgsCoordinator.isCall(representativeEntry);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCall(NotificationEntry notificationEntry) {
        return notificationEntry.mRanking.getImportance() > 1 && notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class);
    }

    private static boolean isColorizedForegroundService(NotificationEntry notificationEntry) {
        Notification notification2 = notificationEntry.mSbn.getNotification();
        return notification2.isForegroundService() && notification2.isColorized() && notificationEntry.mRanking.getImportance() > 1;
    }

    public static boolean isRichOngoing(NotificationEntry notificationEntry) {
        return isColorizedForegroundService(notificationEntry) || isCall(notificationEntry);
    }

    public NotifSectioner getSectioner() {
        return this.mNotifSectioner;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }
}
