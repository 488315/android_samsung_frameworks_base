package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.content.res.Resources;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import dagger.Lazy;

/* loaded from: classes2.dex */
public final class DefaultNotificationStackScrollLayoutSection extends NotificationStackScrollLayoutSection {
    public final Lazy largeScreenHeaderHelperLazy;

    public DefaultNotificationStackScrollLayoutSection(Context context, NotificationPanelView notificationPanelView, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder, Lazy lazy) {
        super(context, notificationPanelView, sharedNotificationContainer, sharedNotificationContainerViewModel, sharedNotificationContainerBinder);
        this.largeScreenHeaderHelperLazy = lazy;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) throws Resources.NotFoundException {
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_status_view_bottom_margin);
        boolean z = this.context.getResources().getBoolean(R.bool.config_use_large_screen_shade_header);
        int largeScreenHeaderHeight = ((LargeScreenHeaderHelper) this.largeScreenHeaderHelperLazy.get()).getLargeScreenHeaderHeight();
        if (!z) {
            largeScreenHeaderHeight = 0;
        }
        constraintSet.connect(R.id.nssl_placeholder, 3, R.id.smart_space_barrier_bottom, 4, dimensionPixelSize + largeScreenHeaderHeight);
        constraintSet.connect(R.id.nssl_placeholder, 6, 0, 6);
        constraintSet.connect(R.id.nssl_placeholder, 7, 0, 7);
        constraintSet.createBarrier(R.id.nssl_placeholder_barrier_bottom, 2, 0, R.id.device_entry_icon_view, R.id.ambient_indication_container);
        constraintSet.connect(this.placeHolderId, 4, R.id.nssl_placeholder_barrier_bottom, 3);
    }
}
