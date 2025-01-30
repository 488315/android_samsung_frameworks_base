package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SemPriorityCoordinator implements Coordinator {
    public final C28331 mNotifSectioner = new NotifSectioner(this, "SemPriority", 6) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SemPriorityCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            return (representativeEntry == null || representativeEntry.mSbn.getNotification().semPriority == 0) ? false : true;
        }
    };

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
    }
}
