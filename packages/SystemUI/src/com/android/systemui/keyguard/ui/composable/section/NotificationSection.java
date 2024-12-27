package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.Flags;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LockscreenContentViewModel;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationsPlaceholderViewModel;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final LockscreenContentViewModel lockscreenContentViewModel;
    public final Lazy stackScrollView;
    public final NotificationsPlaceholderViewModel viewModel;

    public NotificationSection(Lazy lazy, NotificationsPlaceholderViewModel notificationsPlaceholderViewModel, AodBurnInViewModel aodBurnInViewModel, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, NotificationStackScrollLayout notificationStackScrollLayout, SharedNotificationContainerBinder sharedNotificationContainerBinder, LockscreenContentViewModel lockscreenContentViewModel) {
        Flags.migrateClocksToBlueprint();
        throw new IllegalStateException("this requires MigrateClocksToBlueprint.isEnabled");
    }
}
