package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppOpsCoordinator implements Coordinator {
    public final ForegroundServiceController mForegroundServiceController;
    public final C27961 mNotifFilter = new NotifFilter("AppOpsCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            AppOpsCoordinator appOpsCoordinator = AppOpsCoordinator.this;
            appOpsCoordinator.mForegroundServiceController.getClass();
            if (ForegroundServiceController.isDisclosureNotification(statusBarNotification)) {
                return !appOpsCoordinator.mForegroundServiceController.isDisclosureNeededForUser(statusBarNotification.getUser().getIdentifier());
            }
            return false;
        }
    };
    public final C27972 mNotifSectioner = new NotifSectioner(this, "ForegroundService", 5) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public final boolean isInSection(ListEntry listEntry) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                Notification notification2 = representativeEntry.mSbn.getNotification();
                if (representativeEntry.getImportance() > 1 && notification2.isStyle(Notification.CallStyle.class)) {
                    return true;
                }
            }
            return false;
        }
    };

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator$1] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator$2] */
    public AppOpsCoordinator(ForegroundServiceController foregroundServiceController, AppOpsController appOpsController, DelayableExecutor delayableExecutor) {
        this.mForegroundServiceController = foregroundServiceController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mNotifFilter);
    }
}
