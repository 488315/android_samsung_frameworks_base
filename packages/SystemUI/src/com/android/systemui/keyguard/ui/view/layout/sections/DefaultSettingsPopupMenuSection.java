package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.res.Resources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardLongPressViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;

public final class DefaultSettingsPopupMenuSection extends KeyguardSection {
    public final ActivityStarter activityStarter;
    public final Resources resources;

    public DefaultSettingsPopupMenuSection(Resources resources, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, KeyguardLongPressViewModel keyguardLongPressViewModel, KeyguardRootViewModel keyguardRootViewModel, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        this.resources = resources;
        this.activityStarter = activityStarter;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_horizontal_offset);
        constraintSet.constrainWidth(R.id.keyguard_settings_button, -2);
        constraintSet.constrainHeight(R.id.keyguard_settings_button, -2);
        constraintSet.constrainMinHeight(R.id.keyguard_settings_button, this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_height));
        constraintSet.connect(R.id.keyguard_settings_button, 6, 0, 6, dimensionPixelSize);
        constraintSet.connect(R.id.keyguard_settings_button, 7, 0, 7, dimensionPixelSize);
        constraintSet.connect(R.id.keyguard_settings_button, 4, 0, 4, this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_vertical_offset));
        constraintSet.setVisibilityMode(R.id.keyguard_settings_button, 1);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, R.id.keyguard_settings_button);
    }
}
