package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.smartspace.SmartspaceTarget;
import android.os.Parcelable;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutionImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartspaceDedupingCoordinator implements Coordinator {
    public final SystemClock clock;
    public final DelayableExecutor executor;
    public boolean isOnLockscreen;
    public final NotifPipeline notifPipeline;
    public final LockscreenSmartspaceController smartspaceController;
    public final SysuiStatusBarStateController statusBarStateController;
    public Map trackedSmartspaceTargets = new LinkedHashMap();
    public final SmartspaceDedupingCoordinator$filter$1 filter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$filter$1
        {
            super("SmartspaceDedupingFilter");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = SmartspaceDedupingCoordinator.this;
            if (!smartspaceDedupingCoordinator.isOnLockscreen) {
                return false;
            }
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) ((LinkedHashMap) smartspaceDedupingCoordinator.trackedSmartspaceTargets).get(notificationEntry.mKey);
            return trackedSmartspaceTarget != null ? trackedSmartspaceTarget.shouldFilter : false;
        }
    };
    public final SmartspaceDedupingCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = SmartspaceDedupingCoordinator.this;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) ((LinkedHashMap) smartspaceDedupingCoordinator.trackedSmartspaceTargets).get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                smartspaceDedupingCoordinator.updateFilterStatus(trackedSmartspaceTarget);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = SmartspaceDedupingCoordinator.this;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) smartspaceDedupingCoordinator.trackedSmartspaceTargets.get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                smartspaceDedupingCoordinator.getClass();
                Runnable runnable = trackedSmartspaceTarget.cancelTimeoutRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                trackedSmartspaceTarget.cancelTimeoutRunnable = null;
                trackedSmartspaceTarget.alertExceptionExpires = 0L;
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = SmartspaceDedupingCoordinator.this;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) ((LinkedHashMap) smartspaceDedupingCoordinator.trackedSmartspaceTargets).get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                smartspaceDedupingCoordinator.updateFilterStatus(trackedSmartspaceTarget);
            }
        }
    };
    public final SmartspaceDedupingCoordinator$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            SmartspaceDedupingCoordinator.this.recordStatusBarState(i);
        }
    };

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$filter$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$collectionListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$statusBarStateListener$1] */
    public SmartspaceDedupingCoordinator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenSmartspaceController lockscreenSmartspaceController, NotifPipeline notifPipeline, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.smartspaceController = lockscreenSmartspaceController;
        this.notifPipeline = notifPipeline;
        this.executor = delayableExecutor;
        this.clock = systemClock;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        notifPipeline.addCollectionListener(this.collectionListener);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.statusBarStateController;
        statusBarStateControllerImpl.addCallback((StatusBarStateController.StateListener) this.statusBarStateListener);
        BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener = new BcSmartspaceDataPlugin.SmartspaceTargetListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$attach$1
            /* JADX WARN: Removed duplicated region for block: B:16:0x0055  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x0074  */
            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onSmartspaceTargetsUpdated(List list) {
                boolean z;
                Runnable runnable;
                String sourceNotificationKey;
                SmartspaceDedupingCoordinator smartspaceDedupingCoordinator = SmartspaceDedupingCoordinator.this;
                smartspaceDedupingCoordinator.getClass();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                Map map = smartspaceDedupingCoordinator.trackedSmartspaceTargets;
                Iterator it = list.iterator();
                if (it.hasNext()) {
                    SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
                    SmartspaceTarget smartspaceTarget2 = smartspaceTarget instanceof SmartspaceTarget ? smartspaceTarget : null;
                    if (smartspaceTarget2 != null && (sourceNotificationKey = smartspaceTarget2.getSourceNotificationKey()) != null) {
                        Object obj = ((LinkedHashMap) map).get(sourceNotificationKey);
                        if (obj == null) {
                            obj = new TrackedSmartspaceTarget(sourceNotificationKey);
                        }
                        TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) obj;
                        linkedHashMap.put(sourceNotificationKey, trackedSmartspaceTarget);
                        z = smartspaceDedupingCoordinator.updateFilterStatus(trackedSmartspaceTarget);
                        LinkedHashMap linkedHashMap2 = (LinkedHashMap) map;
                        for (String str : linkedHashMap2.keySet()) {
                            if (!linkedHashMap.containsKey(str)) {
                                TrackedSmartspaceTarget trackedSmartspaceTarget2 = (TrackedSmartspaceTarget) linkedHashMap2.get(str);
                                if (trackedSmartspaceTarget2 != null && (runnable = trackedSmartspaceTarget2.cancelTimeoutRunnable) != null) {
                                    runnable.run();
                                }
                                z = true;
                            }
                        }
                        if (z) {
                            smartspaceDedupingCoordinator.filter.invalidateList("onNewSmartspaceTargets");
                        }
                        smartspaceDedupingCoordinator.trackedSmartspaceTargets = linkedHashMap;
                    }
                }
                z = false;
                LinkedHashMap linkedHashMap22 = (LinkedHashMap) map;
                while (r2.hasNext()) {
                }
                if (z) {
                }
                smartspaceDedupingCoordinator.trackedSmartspaceTargets = linkedHashMap;
            }
        };
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        ((ExecutionImpl) lockscreenSmartspaceController.execution).assertIsMainThread();
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.registerListener(smartspaceTargetListener);
        }
        recordStatusBarState(statusBarStateControllerImpl.mState);
    }

    public final void recordStatusBarState(int i) {
        boolean z = this.isOnLockscreen;
        boolean z2 = i == 1;
        this.isOnLockscreen = z2;
        if (z2 != z) {
            invalidateList("recordStatusBarState: " + StatusBarState.toString(i));
        }
    }

    public final boolean updateFilterStatus(final TrackedSmartspaceTarget trackedSmartspaceTarget) {
        boolean z = trackedSmartspaceTarget.shouldFilter;
        final NotificationEntry entry = this.notifPipeline.getEntry(trackedSmartspaceTarget.key);
        if (entry != null) {
            SystemClockImpl systemClockImpl = (SystemClockImpl) this.clock;
            systemClockImpl.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            long lastAudiblyAlertedMillis = entry.mRanking.getLastAudiblyAlertedMillis();
            long j = SmartspaceDedupingCoordinatorKt.ALERT_WINDOW;
            long j2 = lastAudiblyAlertedMillis + j;
            if (j2 != trackedSmartspaceTarget.alertExceptionExpires && j2 > currentTimeMillis) {
                Runnable runnable = trackedSmartspaceTarget.cancelTimeoutRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                trackedSmartspaceTarget.alertExceptionExpires = j2;
                trackedSmartspaceTarget.cancelTimeoutRunnable = this.executor.executeDelayed(j2 - currentTimeMillis, new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$updateAlertException$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TrackedSmartspaceTarget trackedSmartspaceTarget2 = TrackedSmartspaceTarget.this;
                        trackedSmartspaceTarget2.cancelTimeoutRunnable = null;
                        trackedSmartspaceTarget2.shouldFilter = true;
                        invalidateList("updateAlertException: " + NotificationUtilsKt.getLogKey(entry));
                    }
                });
            }
            systemClockImpl.getClass();
            trackedSmartspaceTarget.shouldFilter = !(System.currentTimeMillis() - entry.mRanking.getLastAudiblyAlertedMillis() <= j);
        }
        return trackedSmartspaceTarget.shouldFilter != z && this.isOnLockscreen;
    }
}
