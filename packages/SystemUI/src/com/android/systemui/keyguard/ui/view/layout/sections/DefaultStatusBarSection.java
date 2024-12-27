package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.util.Utils;

public final class DefaultStatusBarSection extends KeyguardSection {
    public final Context context;
    public final int statusBarViewId = R.id.keyguard_header;

    public DefaultStatusBarSection(Context context, NotificationPanelView notificationPanelView, KeyguardStatusBarViewComponent.Factory factory) {
        this.context = context;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.sceneContainer();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int statusBarHeaderHeightKeyguard = Utils.getStatusBarHeaderHeightKeyguard(this.context);
        int i = this.statusBarViewId;
        constraintSet.constrainHeight(i, statusBarHeaderHeightKeyguard);
        constraintSet.connect(i, 3, 0, 3);
        constraintSet.connect(i, 6, 0, 6);
        constraintSet.connect(i, 7, 0, 7);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.sceneContainer();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.statusBarViewId);
    }
}
