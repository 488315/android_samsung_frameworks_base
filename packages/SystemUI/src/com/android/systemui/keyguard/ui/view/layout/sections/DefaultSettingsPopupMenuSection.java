package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.animation.view.LaunchableLinearLayout;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.binder.KeyguardSettingsViewBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardTouchHandlingViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;

/* loaded from: classes2.dex */
public final class DefaultSettingsPopupMenuSection extends KeyguardSection {
    public final ActivityStarter activityStarter;
    public final KeyguardRootViewModel keyguardRootViewModel;
    public final KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel;
    public final KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel;
    public final Resources resources;
    public RepeatWhenAttachedKt.C09181 settingsPopupMenuHandle;
    public final VibratorHelper vibratorHelper;

    public DefaultSettingsPopupMenuSection(Resources resources, KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, KeyguardRootViewModel keyguardRootViewModel, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        this.resources = resources;
        this.keyguardSettingsMenuViewModel = keyguardSettingsMenuViewModel;
        this.keyguardTouchHandlingViewModel = keyguardTouchHandlingViewModel;
        this.keyguardRootViewModel = keyguardRootViewModel;
        this.vibratorHelper = vibratorHelper;
        this.activityStarter = activityStarter;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        View viewInflate = LayoutInflater.from(constraintLayout.getContext()).inflate(R.layout.keyguard_settings_popup_menu, (ViewGroup) constraintLayout, false);
        viewInflate.setId(R.id.keyguard_settings_button);
        viewInflate.setVisibility(8);
        viewInflate.setAlpha(0.0f);
        constraintLayout.addView((LaunchableLinearLayout) viewInflate);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) throws Resources.NotFoundException {
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
        KeyguardSettingsViewBinder keyguardSettingsViewBinder = KeyguardSettingsViewBinder.INSTANCE;
        View viewRequireViewById = constraintLayout.requireViewById(R.id.keyguard_settings_button);
        keyguardSettingsViewBinder.getClass();
        this.settingsPopupMenuHandle = KeyguardSettingsViewBinder.bind(viewRequireViewById, this.keyguardSettingsMenuViewModel, this.keyguardTouchHandlingViewModel, this.keyguardRootViewModel, this.vibratorHelper, this.activityStarter);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        RepeatWhenAttachedKt.C09181 c09181 = this.settingsPopupMenuHandle;
        if (c09181 != null) {
            c09181.dispose();
        }
        ExtensionsKt.removeView(constraintLayout, R.id.keyguard_settings_button);
    }
}
