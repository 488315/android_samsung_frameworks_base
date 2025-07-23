package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public class SemPriorityCoordinator implements Coordinator {
    private static final String TAG = "SemPriorityCoordinator";
    private final NotifSectioner mNotifSectioner = new NotifSectioner(this, "SemPriority", 9) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SemPriorityCoordinator.1
        private boolean isSemPriority(NotificationEntry notificationEntry) {
            return notificationEntry.mSbn.getNotification().semPriority != 0;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(PipelineEntry pipelineEntry) {
            NotificationEntry representativeEntry = pipelineEntry.getRepresentativeEntry();
            if ((representativeEntry == null || !representativeEntry.isInsignificant()) && representativeEntry != null) {
                return isSemPriority(representativeEntry);
            }
            return false;
        }
    };

    public NotifSectioner getSectioner() {
        return this.mNotifSectioner;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
    }
}
