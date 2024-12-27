package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.HeadsUpManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OnUserInteractionCallbackImpl implements OnUserInteractionCallback {
    public final HeadsUpManager mHeadsUpManager;
    public final NotifCollection mNotifCollection;
    public final StatusBarStateController mStatusBarStateController;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final VisualStabilityCoordinator mVisualStabilityCoordinator;

    public OnUserInteractionCallbackImpl(NotificationVisibilityProvider notificationVisibilityProvider, NotifCollection notifCollection, HeadsUpManager headsUpManager, StatusBarStateController statusBarStateController, VisualStabilityCoordinator visualStabilityCoordinator) {
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mNotifCollection = notifCollection;
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mVisualStabilityCoordinator = visualStabilityCoordinator;
    }

    public final NotifCollection.FutureDismissal registerFutureDismissal(NotificationEntry notificationEntry, int i) {
        OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0 onUserInteractionCallbackImpl$$ExternalSyntheticLambda0 = new OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0(this);
        NotifCollection notifCollection = this.mNotifCollection;
        NotifCollection.FutureDismissal futureDismissal = (NotifCollection.FutureDismissal) notifCollection.mFutureDismissals.get(notificationEntry.mKey);
        NotifCollectionLogger notifCollectionLogger = notifCollection.mLogger;
        if (futureDismissal != null) {
            notifCollectionLogger.logFutureDismissalReused(futureDismissal);
            return futureDismissal;
        }
        NotifCollection.FutureDismissal futureDismissal2 = new NotifCollection.FutureDismissal(notifCollection, notificationEntry, i, onUserInteractionCallbackImpl$$ExternalSyntheticLambda0);
        notifCollection.mFutureDismissals.put(notificationEntry.mKey, futureDismissal2);
        notifCollectionLogger.logFutureDismissalRegistered(futureDismissal2);
        return futureDismissal2;
    }
}
