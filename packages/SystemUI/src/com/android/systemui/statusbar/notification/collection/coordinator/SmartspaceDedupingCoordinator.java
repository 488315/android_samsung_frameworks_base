package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.app.smartspace.SmartspaceTarget;
import android.os.Parcelable;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
/* loaded from: classes2.dex */
public final class SmartspaceDedupingCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final SystemClock clock;
    private final DelayableExecutor executor;
    private boolean isOnLockscreen;
    private final NotifPipeline notifPipeline;
    private final LockscreenSmartspaceController smartspaceController;
    private final SysuiStatusBarStateController statusBarStateController;
    private Map<String, TrackedSmartspaceTarget> trackedSmartspaceTargets = new LinkedHashMap();
    private final SmartspaceDedupingCoordinator$filter$1 filter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$filter$1
        {
            super("SmartspaceDedupingFilter");
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z;
            boolean isDupedWithSmartspaceContent;
            z = SmartspaceDedupingCoordinator.this.isOnLockscreen;
            if (z) {
                isDupedWithSmartspaceContent = SmartspaceDedupingCoordinator.this.isDupedWithSmartspaceContent(notificationEntry);
                if (isDupedWithSmartspaceContent) {
                    return true;
                }
            }
            return false;
        }
    };
    private final SmartspaceDedupingCoordinator$collectionListener$1 collectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$collectionListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            Map map;
            map = SmartspaceDedupingCoordinator.this.trackedSmartspaceTargets;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) map.get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                SmartspaceDedupingCoordinator.this.updateFilterStatus(trackedSmartspaceTarget);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            Map map;
            map = SmartspaceDedupingCoordinator.this.trackedSmartspaceTargets;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) map.get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                SmartspaceDedupingCoordinator.this.cancelExceptionTimeout(trackedSmartspaceTarget);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            Map map;
            map = SmartspaceDedupingCoordinator.this.trackedSmartspaceTargets;
            TrackedSmartspaceTarget trackedSmartspaceTarget = (TrackedSmartspaceTarget) map.get(notificationEntry.mKey);
            if (trackedSmartspaceTarget != null) {
                SmartspaceDedupingCoordinator.this.updateFilterStatus(trackedSmartspaceTarget);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };
    private final SmartspaceDedupingCoordinator$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onStateChanged(int i) {
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelExceptionTimeout(TrackedSmartspaceTarget trackedSmartspaceTarget) {
        Runnable cancelTimeoutRunnable = trackedSmartspaceTarget.getCancelTimeoutRunnable();
        if (cancelTimeoutRunnable != null) {
            cancelTimeoutRunnable.run();
        }
        trackedSmartspaceTarget.setCancelTimeoutRunnable(null);
        trackedSmartspaceTarget.setAlertExceptionExpires(0L);
    }

    private final boolean hasRecentlyAlerted(NotificationEntry notificationEntry) {
        long j;
        long currentTimeMillis = this.clock.currentTimeMillis() - notificationEntry.mRanking.getLastAudiblyAlertedMillis();
        j = SmartspaceDedupingCoordinatorKt.ALERT_WINDOW;
        return currentTimeMillis <= j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDupedWithSmartspaceContent(NotificationEntry notificationEntry) {
        TrackedSmartspaceTarget trackedSmartspaceTarget = this.trackedSmartspaceTargets.get(notificationEntry.mKey);
        if (trackedSmartspaceTarget != null) {
            return trackedSmartspaceTarget.getShouldFilter();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onNewSmartspaceTargets(List<? extends Parcelable> list) {
        Runnable cancelTimeoutRunnable;
        String sourceNotificationKey;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Map<String, TrackedSmartspaceTarget> map = this.trackedSmartspaceTargets;
        Iterator<? extends Parcelable> it = list.iterator();
        boolean z = false;
        if (it.hasNext()) {
            SmartspaceTarget smartspaceTarget = (Parcelable) it.next();
            SmartspaceTarget smartspaceTarget2 = smartspaceTarget instanceof SmartspaceTarget ? smartspaceTarget : null;
            if (smartspaceTarget2 != null && (sourceNotificationKey = smartspaceTarget2.getSourceNotificationKey()) != null) {
                TrackedSmartspaceTarget trackedSmartspaceTarget = map.get(sourceNotificationKey);
                if (trackedSmartspaceTarget == null) {
                    trackedSmartspaceTarget = new TrackedSmartspaceTarget(sourceNotificationKey);
                }
                TrackedSmartspaceTarget trackedSmartspaceTarget2 = trackedSmartspaceTarget;
                linkedHashMap.put(sourceNotificationKey, trackedSmartspaceTarget2);
                z = updateFilterStatus(trackedSmartspaceTarget2);
            }
        }
        for (String str : map.keySet()) {
            if (!linkedHashMap.containsKey(str)) {
                TrackedSmartspaceTarget trackedSmartspaceTarget3 = map.get(str);
                if (trackedSmartspaceTarget3 != null && (cancelTimeoutRunnable = trackedSmartspaceTarget3.getCancelTimeoutRunnable()) != null) {
                    cancelTimeoutRunnable.run();
                }
                z = true;
            }
        }
        if (z) {
            invalidateList("onNewSmartspaceTargets");
        }
        this.trackedSmartspaceTargets = linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void recordStatusBarState(int i) {
        boolean z = this.isOnLockscreen;
        boolean z2 = i == 1;
        this.isOnLockscreen = z2;
        if (z2 != z) {
            invalidateList("recordStatusBarState: " + StatusBarState.toString(i));
        }
    }

    private final void updateAlertException(final TrackedSmartspaceTarget trackedSmartspaceTarget, final NotificationEntry notificationEntry) {
        long j;
        long currentTimeMillis = this.clock.currentTimeMillis();
        long lastAudiblyAlertedMillis = notificationEntry.mRanking.getLastAudiblyAlertedMillis();
        j = SmartspaceDedupingCoordinatorKt.ALERT_WINDOW;
        long j2 = j + lastAudiblyAlertedMillis;
        if (j2 == trackedSmartspaceTarget.getAlertExceptionExpires() || j2 <= currentTimeMillis) {
            return;
        }
        Runnable cancelTimeoutRunnable = trackedSmartspaceTarget.getCancelTimeoutRunnable();
        if (cancelTimeoutRunnable != null) {
            cancelTimeoutRunnable.run();
        }
        trackedSmartspaceTarget.setAlertExceptionExpires(j2);
        trackedSmartspaceTarget.setCancelTimeoutRunnable(this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$updateAlertException$1
            @Override // java.lang.Runnable
            public final void run() {
                SmartspaceDedupingCoordinator$filter$1 smartspaceDedupingCoordinator$filter$1;
                TrackedSmartspaceTarget.this.setCancelTimeoutRunnable(null);
                TrackedSmartspaceTarget.this.setShouldFilter(true);
                smartspaceDedupingCoordinator$filter$1 = this.filter;
                smartspaceDedupingCoordinator$filter$1.invalidateList("updateAlertException: " + NotificationUtilsKt.getLogKey(notificationEntry));
            }
        }, j2 - currentTimeMillis));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean updateFilterStatus(TrackedSmartspaceTarget trackedSmartspaceTarget) {
        boolean shouldFilter = trackedSmartspaceTarget.getShouldFilter();
        NotificationEntry entry = this.notifPipeline.mNotifCollection.getEntry(trackedSmartspaceTarget.getKey());
        if (entry != null) {
            updateAlertException(trackedSmartspaceTarget, entry);
            trackedSmartspaceTarget.setShouldFilter(!hasRecentlyAlerted(entry));
        }
        return trackedSmartspaceTarget.getShouldFilter() != shouldFilter && this.isOnLockscreen;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.filter);
        notifPipeline.addCollectionListener(this.collectionListener);
        ((StatusBarStateControllerImpl) this.statusBarStateController).addCallback((StatusBarStateController.StateListener) this.statusBarStateListener);
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener = new BcSmartspaceDataPlugin.SmartspaceTargetListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator$attach$1
            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceTargetListener
            public final void onSmartspaceTargetsUpdated(List<? extends Parcelable> list) {
                SmartspaceDedupingCoordinator.this.onNewSmartspaceTargets(list);
            }
        };
        lockscreenSmartspaceController.execution.assertIsMainThread();
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.registerListener(smartspaceTargetListener);
        }
        recordStatusBarState(((StatusBarStateControllerImpl) this.statusBarStateController).mState);
    }
}
