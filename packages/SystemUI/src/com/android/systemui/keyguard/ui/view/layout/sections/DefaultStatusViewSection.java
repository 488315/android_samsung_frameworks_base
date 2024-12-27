package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardViewConfigurator;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.Utils;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DefaultStatusViewSection extends KeyguardSection {
    public final Context context;
    public final Lazy keyguardViewConfigurator;
    public final SplitShadeStateController splitShadeStateController;
    public final int statusViewId = R.id.keyguard_status_view;

    public DefaultStatusViewSection(Context context, NotificationPanelView notificationPanelView, KeyguardStatusViewComponent.Factory factory, Lazy lazy, Lazy lazy2, KeyguardMediaController keyguardMediaController, SplitShadeStateController splitShadeStateController) {
        this.context = context;
        this.keyguardViewConfigurator = lazy;
        this.splitShadeStateController = splitShadeStateController;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int i = this.statusViewId;
        constraintSet.constrainWidth(i, 0);
        constraintSet.constrainHeight(i, -2);
        constraintSet.connect(i, 3, 0, 3);
        constraintSet.connect(i, 6, 0, 6);
        constraintSet.connect(i, 7, 0, 7);
        this.context.getResources();
        ((SplitShadeStateControllerImpl) this.splitShadeStateController).shouldUseSplitNotificationShade();
        constraintSet.setMargin(i, 3, Utils.getStatusBarHeaderHeightKeyguard(this.context) + this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_clock_top_margin));
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.statusViewId);
        ((KeyguardViewConfigurator) this.keyguardViewConfigurator.get()).getClass();
    }
}
