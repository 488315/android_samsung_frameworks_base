package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.res.Resources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.VibratorHelper;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DefaultShortcutsSection extends BaseShortcutSection {
    public final FalsingManager falsingManager;
    public final KeyguardIndicationController indicationController;
    public final Lazy keyguardBlueprintInteractor;
    public final Resources resources;
    public final int safeInsetBottom;

    public DefaultShortcutsSection(Resources resources, KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, KeyguardRootViewModel keyguardRootViewModel, FalsingManager falsingManager, KeyguardIndicationController keyguardIndicationController, VibratorHelper vibratorHelper, Lazy lazy) {
        this.resources = resources;
        this.falsingManager = falsingManager;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_width);
        int dimensionPixelSize2 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_height);
        int dimensionPixelSize3 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_horizontal_offset);
        int dimensionPixelSize4 = this.resources.getDimensionPixelSize(R.dimen.keyguard_affordance_vertical_offset);
        constraintSet.constrainWidth(R.id.start_button, dimensionPixelSize);
        constraintSet.constrainHeight(R.id.start_button, dimensionPixelSize2);
        constraintSet.connect(R.id.start_button, 1, 0, 1, dimensionPixelSize3);
        constraintSet.connect(R.id.start_button, 4, 0, 4, dimensionPixelSize4 + this.safeInsetBottom);
        constraintSet.constrainWidth(R.id.end_button, dimensionPixelSize);
        constraintSet.constrainHeight(R.id.end_button, dimensionPixelSize2);
        constraintSet.connect(R.id.end_button, 2, 0, 2, dimensionPixelSize3);
        constraintSet.connect(R.id.end_button, 4, 0, 4, dimensionPixelSize4 + this.safeInsetBottom);
        constraintSet.setVisibilityMode(R.id.start_button, 1);
        constraintSet.setVisibilityMode(R.id.end_button, 1);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }
}
