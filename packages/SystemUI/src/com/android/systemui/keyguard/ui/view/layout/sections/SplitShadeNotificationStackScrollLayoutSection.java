package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SplitShadeNotificationStackScrollLayoutSection extends NotificationStackScrollLayoutSection {
    public SplitShadeNotificationStackScrollLayoutSection(Context context, NotificationPanelView notificationPanelView, SharedNotificationContainer sharedNotificationContainer, SharedNotificationContainerViewModel sharedNotificationContainerViewModel, SharedNotificationContainerBinder sharedNotificationContainerBinder) {
        super(context, notificationPanelView, sharedNotificationContainer, sharedNotificationContainerViewModel, sharedNotificationContainerBinder);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        constraintSet.connect(R.id.nssl_placeholder, 3, 0, 3, this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin));
        constraintSet.connect(R.id.nssl_placeholder, 6, 0, 6);
        constraintSet.connect(R.id.nssl_placeholder, 7, 0, 7);
        constraintSet.createBarrier(R.id.nssl_placeholder_barrier_bottom, 2, 0, R.id.device_entry_icon_view, R.id.ambient_indication_container);
        constraintSet.connect(this.placeHolderId, 4, R.id.nssl_placeholder_barrier_bottom, 3);
    }
}
