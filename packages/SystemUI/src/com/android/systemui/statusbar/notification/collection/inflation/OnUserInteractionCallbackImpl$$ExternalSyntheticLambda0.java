package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;

public final /* synthetic */ class OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0 implements NotifCollection.DismissedByUserStatsCreator {
    public final /* synthetic */ OnUserInteractionCallbackImpl f$0;

    public /* synthetic */ OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0(OnUserInteractionCallbackImpl onUserInteractionCallbackImpl) {
        this.f$0 = onUserInteractionCallbackImpl;
    }

    public final DismissedByUserStats createDismissedByUserStats(NotificationEntry notificationEntry) {
        int i;
        OnUserInteractionCallbackImpl onUserInteractionCallbackImpl = this.f$0;
        onUserInteractionCallbackImpl.getClass();
        if (((BaseHeadsUpManager) onUserInteractionCallbackImpl.mHeadsUpManager).isHeadsUpEntry(notificationEntry.mKey)) {
            i = 1;
        } else {
            StatusBarStateController statusBarStateController = onUserInteractionCallbackImpl.mStatusBarStateController;
            i = statusBarStateController.isDozing() ? 2 : statusBarStateController.getState() == 1 ? 5 : 3;
        }
        return new DismissedByUserStats(i, 1, ((NotificationVisibilityProviderImpl) onUserInteractionCallbackImpl.mVisibilityProvider).obtain(notificationEntry));
    }
}
