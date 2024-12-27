package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.internal.view.OneShotPreDrawListener;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HideNotificationsBinder {
    public static final HideNotificationsBinder INSTANCE = new HideNotificationsBinder();

    private HideNotificationsBinder() {
    }

    public static final void access$bindHideState(HideNotificationsBinder hideNotificationsBinder, final NotificationStackScrollLayoutController notificationStackScrollLayoutController, boolean z) {
        hideNotificationsBinder.getClass();
        if (z) {
            notificationStackScrollLayoutController.updateNotificationsContainerVisibility(false, false);
            notificationStackScrollLayoutController.mView.mSuppressChildrenMeasureAndLayout = true;
            return;
        }
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.mSuppressChildrenMeasureAndLayout = false;
        notificationStackScrollLayout.requestLayout();
        OneShotPreDrawListener.add(notificationStackScrollLayoutController.mView, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.HideNotificationsBinder$bindHideState$1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationStackScrollLayoutController.this.updateNotificationsContainerVisibility(true, true);
            }
        });
    }
}
