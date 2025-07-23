package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.internal.view.OneShotPreDrawListener;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
