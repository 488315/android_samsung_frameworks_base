package com.android.systemui.keyguard.ui.view.layout.sections;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;

public final class SplitShadeGuidelines extends KeyguardSection {
    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        constraintSet.create(R.id.split_shade_guideline, 1);
        constraintSet.setGuidelinePercent(R.id.split_shade_guideline, 0.5f);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
    }
}
