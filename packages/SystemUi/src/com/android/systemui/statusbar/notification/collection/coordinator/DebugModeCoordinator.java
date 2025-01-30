package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DebugModeCoordinator implements Coordinator {
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final DebugModeCoordinator$filter$1 filter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$filter$1
        {
            super("DebugModeFilter");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (DebugModeCoordinator.this.debugModeFilterProvider.allowedPackages.isEmpty()) {
                return false;
            }
            return !r0.allowedPackages.contains(notificationEntry.mSbn.getPackageName());
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$filter$1] */
    public DebugModeCoordinator(DebugModeFilterProvider debugModeFilterProvider) {
        this.debugModeFilterProvider = debugModeFilterProvider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$attach$1] */
    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        this.debugModeFilterProvider.registerInvalidationListener(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$attach$1
            @Override // java.lang.Runnable
            public final void run() {
                invalidateList(null);
            }
        });
    }
}
