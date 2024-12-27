package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.AlwaysOnDisplayNotificationIconViewStore;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.StatusBarIconViewBindingFailureTracker;
import com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconContainerAlwaysOnDisplayViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.ui.SystemBarUtilsState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AodNotificationIconsSection extends KeyguardSection {
    public final int nicId = R.id.aod_notification_icon_container;

    public AodNotificationIconsSection(Context context, ConfigurationState configurationState, StatusBarIconViewBindingFailureTracker statusBarIconViewBindingFailureTracker, NotificationIconContainerAlwaysOnDisplayViewModel notificationIconContainerAlwaysOnDisplayViewModel, AlwaysOnDisplayNotificationIconViewStore alwaysOnDisplayNotificationIconViewStore, NotificationIconAreaController notificationIconAreaController, SystemBarUtilsState systemBarUtilsState, KeyguardRootViewModel keyguardRootViewModel) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.nicId);
    }
}
