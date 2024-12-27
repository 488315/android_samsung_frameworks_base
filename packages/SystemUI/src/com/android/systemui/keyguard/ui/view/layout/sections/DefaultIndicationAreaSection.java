package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.statusbar.KeyguardIndicationController;

public final class DefaultIndicationAreaSection extends KeyguardSection {
    public final Context context;
    public final int indicationAreaViewId = R.id.keyguard_indication_area;

    public DefaultIndicationAreaSection(Context context, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, KeyguardIndicationController keyguardIndicationController) {
        this.context = context;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int i = this.indicationAreaViewId;
        constraintSet.constrainWidth(i, -1);
        constraintSet.constrainHeight(i, -2);
        constraintSet.connect(this.indicationAreaViewId, 4, 0, 4, this.context.getResources().getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom));
        constraintSet.connect(i, 6, 0, 6);
        constraintSet.connect(i, 7, 0, 7);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        Flags.keyguardBottomAreaRefactor();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.indicationAreaViewId);
    }
}
