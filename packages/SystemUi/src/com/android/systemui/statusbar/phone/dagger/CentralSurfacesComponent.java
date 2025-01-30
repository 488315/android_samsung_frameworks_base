package com.android.systemui.statusbar.phone.dagger;

import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CapturedBlurContainerController;
import com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks;
import com.android.systemui.statusbar.phone.SecPanelBackgroundController;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CentralSurfacesComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        CentralSurfacesComponent create();
    }

    CollapsedStatusBarFragment createCollapsedStatusBarFragment();

    NotificationRowBinderImpl.BindRowCallback getBindRowCallback();

    CapturedBlurContainerController getCapturedBlurContainerController();

    CentralSurfacesCommandQueueCallbacks getCentralSurfacesCommandQueueCallbacks();

    NotificationActivityStarter getNotificationActivityStarter();

    NotificationListContainer getNotificationListContainer();

    NotificationPanelViewController getNotificationPanelViewController();

    NotificationPresenter getNotificationPresenter();

    NotificationShadeWindowView getNotificationShadeWindowView();

    NotificationShadeWindowViewController getNotificationShadeWindowViewController();

    NotificationShelfController getNotificationShelfController();

    NotificationStackScrollLayoutController getNotificationStackScrollLayoutController();

    QuickSettingsController getQuickSettingsController();

    SecPanelBackgroundController getSecPanelBackgroundController();

    StatusBarHeadsUpChangeListener getStatusBarHeadsUpChangeListener();
}
