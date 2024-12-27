package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;

@CoordinatorScope
public class SemPriorityCoordinator implements Coordinator {
    private static final String TAG = "SemPriorityCoordinator";
    private final NotifSectioner mNotifSectioner = new NotifSectioner("SemPriority", 9) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SemPriorityCoordinator.1
        private boolean isSemPriority(NotificationEntry notificationEntry) {
            return notificationEntry.mSbn.getNotification().semPriority != 0;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if ((NotiRune.NOTI_INSIGNIFICANT && representativeEntry != null && representativeEntry.isInsignificant()) || representativeEntry == null) {
                return false;
            }
            return isSemPriority(representativeEntry);
        }
    };

    public NotifSectioner getSectioner() {
        return this.mNotifSectioner;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }
}
