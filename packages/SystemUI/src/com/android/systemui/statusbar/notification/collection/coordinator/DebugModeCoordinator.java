package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;

@CoordinatorScope
public final class DebugModeCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final DebugModeFilterProvider debugModeFilterProvider;
    private final DebugModeCoordinator$filter$1 filter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$filter$1
        {
            super("DebugModeFilter");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            DebugModeFilterProvider debugModeFilterProvider;
            debugModeFilterProvider = DebugModeCoordinator.this.debugModeFilterProvider;
            if (debugModeFilterProvider.allowedPackages.isEmpty()) {
                return false;
            }
            return !debugModeFilterProvider.allowedPackages.contains(notificationEntry.mSbn.getPackageName());
        }
    };

    public DebugModeCoordinator(DebugModeFilterProvider debugModeFilterProvider) {
        this.debugModeFilterProvider = debugModeFilterProvider;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        this.debugModeFilterProvider.registerInvalidationListener(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$attach$1
            @Override // java.lang.Runnable
            public final void run() {
                DebugModeCoordinator$filter$1 debugModeCoordinator$filter$1;
                debugModeCoordinator$filter$1 = DebugModeCoordinator.this.filter;
                debugModeCoordinator$filter$1.invalidateList(null);
            }
        });
    }
}
