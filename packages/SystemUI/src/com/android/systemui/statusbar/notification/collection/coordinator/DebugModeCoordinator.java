package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Build;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider.NotifFilterCommand;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
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
            return debugModeFilterProvider.shouldFilterOut(notificationEntry);
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$filter$1] */
    public DebugModeCoordinator(DebugModeFilterProvider debugModeFilterProvider) {
        this.debugModeFilterProvider = debugModeFilterProvider;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        final DebugModeFilterProvider debugModeFilterProvider = this.debugModeFilterProvider;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator$attach$1
            @Override // java.lang.Runnable
            public final void run() {
                DebugModeCoordinator$filter$1 debugModeCoordinator$filter$1;
                debugModeCoordinator$filter$1 = DebugModeCoordinator.this.filter;
                debugModeCoordinator$filter$1.invalidateList(null);
            }
        };
        debugModeFilterProvider.getClass();
        Assert.isMainThread();
        if (Build.isDebuggable()) {
            ListenerSet listenerSet = debugModeFilterProvider.listeners;
            boolean isEmpty = listenerSet.isEmpty();
            listenerSet.addIfAbsent(runnable);
            if (isEmpty) {
                debugModeFilterProvider.commandRegistry.registerCommand("notif-filter", new Function0() { // from class: com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i = DebugModeFilterProvider.$r8$clinit;
                        return DebugModeFilterProvider.this.new NotifFilterCommand();
                    }
                });
                Log.d("DebugModeFilterProvider", "Registered notif-filter command");
            }
        }
    }
}
