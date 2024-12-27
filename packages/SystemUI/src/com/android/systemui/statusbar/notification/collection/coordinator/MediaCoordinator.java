package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Flags;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.util.Utils;

@CoordinatorScope
public class MediaCoordinator implements Coordinator {
    private static final int STATE_ICONS_ERROR = 2;
    private static final int STATE_ICONS_INFLATED = 1;
    private static final int STATE_ICONS_UNINFLATED = 0;
    private static final String TAG = "MediaCoordinator";
    private final IconManager mIconManager;
    private final Boolean mIsMediaFeatureEnabled;
    private final IStatusBarService mStatusBarService;
    private final ArrayMap<NotificationEntry, Integer> mIconsState = new ArrayMap<>();
    private final NotifFilter mMediaFilter = new NotifFilter(TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (!MediaCoordinator.this.mIsMediaFeatureEnabled.booleanValue()) {
                return false;
            }
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            MediaDataManager.Companion.getClass();
            if (!statusBarNotification.getNotification().isMediaNotification()) {
                return false;
            }
            Flags.notificationsBackgroundIcons();
            MediaCoordinator.this.inflateOrUpdateIcons(notificationEntry);
            return true;
        }
    };
    private final NotifCollectionListener mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator.2
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            Flags.notificationsBackgroundIcons();
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryCleanUp(NotificationEntry notificationEntry) {
            MediaCoordinator.this.mIconsState.remove(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryInit(NotificationEntry notificationEntry) {
            Flags.notificationsBackgroundIcons();
            MediaCoordinator.this.mIconsState.put(notificationEntry, 0);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            if (((Integer) MediaCoordinator.this.mIconsState.getOrDefault(notificationEntry, 0)).intValue() == 2) {
                MediaCoordinator.this.mIconsState.put(notificationEntry, 0);
            }
            Flags.notificationsBackgroundIcons();
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onRankingApplied() {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        @Deprecated
        public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }
    };

    public MediaCoordinator(MediaFeatureFlag mediaFeatureFlag, IStatusBarService iStatusBarService, IconManager iconManager) {
        this.mIsMediaFeatureEnabled = Boolean.valueOf(Utils.useQsMediaPlayer(mediaFeatureFlag.context));
        this.mStatusBarService = iStatusBarService;
        this.mIconManager = iconManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateOrUpdateIcons(NotificationEntry notificationEntry) {
        int intValue = this.mIconsState.getOrDefault(notificationEntry, 0).intValue();
        if (intValue == 0) {
            try {
                this.mIconManager.createIcons(notificationEntry);
                this.mIconsState.put(notificationEntry, 1);
                return;
            } catch (InflationException e) {
                reportInflationError(notificationEntry, e);
                this.mIconsState.put(notificationEntry, 2);
                return;
            }
        }
        if (intValue != 1) {
            return;
        }
        try {
            this.mIconManager.updateIcons(notificationEntry, false);
        } catch (InflationException e2) {
            reportInflationError(notificationEntry, e2);
            this.mIconsState.put(notificationEntry, 2);
        }
    }

    private void reportInflationError(NotificationEntry notificationEntry, Exception exc) {
        try {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            this.mStatusBarService.onNotificationError(statusBarNotification.getPackageName(), statusBarNotification.getTag(), statusBarNotification.getId(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), exc.getMessage(), statusBarNotification.getUser().getIdentifier());
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mMediaFilter);
        notifPipeline.addCollectionListener(this.mCollectionListener);
    }
}
