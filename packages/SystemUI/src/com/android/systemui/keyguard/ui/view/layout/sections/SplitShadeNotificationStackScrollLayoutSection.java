package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;

public final class SplitShadeNotificationStackScrollLayoutSection extends NotificationStackScrollLayoutSection {
    public SplitShadeNotificationStackScrollLayoutSection(Context context, NotificationPanelView notificationPanelView, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder) {
        super(context, notificationPanelView, sharedNotificationContainer, sharedNotificationContainerViewModel, sharedNotificationContainerBinder);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Flags.migrateClocksToBlueprint();
    }
}
