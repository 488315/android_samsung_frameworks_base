package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.SparseArray;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HideNotifsForOtherUsersCoordinator implements Coordinator {
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final C28101 mFilter = new NotifFilter("NotCurrentUserFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return !((NotificationLockscreenUserManagerImpl) HideNotifsForOtherUsersCoordinator.this.mLockscreenUserManager).isCurrentProfile(notificationEntry.mSbn.getUser().getIdentifier());
        }
    };
    public final C28112 mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator.2
        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public final void onCurrentProfilesChanged(SparseArray sparseArray) {
            StringBuilder sb = new StringBuilder("onCurrentProfilesChanged: user=");
            HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator = HideNotifsForOtherUsersCoordinator.this;
            sb.append(((NotificationLockscreenUserManagerImpl) hideNotifsForOtherUsersCoordinator.mLockscreenUserManager).mCurrentUserId);
            sb.append(" profiles={");
            for (int i = 0; i < sparseArray.size(); i++) {
                if (i != 0) {
                    sb.append(",");
                }
                sb.append(sparseArray.keyAt(i));
            }
            sb.append("}");
            hideNotifsForOtherUsersCoordinator.mFilter.invalidateList(sb.toString());
        }
    };

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator$2] */
    public HideNotifsForOtherUsersCoordinator(NotificationLockscreenUserManager notificationLockscreenUserManager) {
        this.mLockscreenUserManager = notificationLockscreenUserManager;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mFilter);
        ((ArrayList) ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mListeners).add(this.mUserChangedListener);
    }
}
