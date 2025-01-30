package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0 implements NotifCollection.DismissedByUserStatsCreator {
    public final /* synthetic */ OnUserInteractionCallbackImpl f$0;

    public /* synthetic */ OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0(OnUserInteractionCallbackImpl onUserInteractionCallbackImpl) {
        this.f$0 = onUserInteractionCallbackImpl;
    }

    public final DismissedByUserStats createDismissedByUserStats(NotificationEntry notificationEntry) {
        int i;
        OnUserInteractionCallbackImpl onUserInteractionCallbackImpl = this.f$0;
        onUserInteractionCallbackImpl.getClass();
        if (onUserInteractionCallbackImpl.mHeadsUpManager.isAlerting(notificationEntry.mKey)) {
            i = 1;
        } else {
            StatusBarStateController statusBarStateController = onUserInteractionCallbackImpl.mStatusBarStateController;
            i = statusBarStateController.isDozing() ? 2 : statusBarStateController.getState() == 1 ? 5 : 3;
        }
        return new DismissedByUserStats(i, 1, ((NotificationVisibilityProviderImpl) onUserInteractionCallbackImpl.mVisibilityProvider).obtain(notificationEntry));
    }
}
