package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.media.controls.ui.controller.KeyguardMediaController;
import com.android.systemui.shade.NotificationPanelView;

public final class SplitShadeMediaSection extends KeyguardSection {
    public SplitShadeMediaSection(Context context, NotificationPanelView notificationPanelView, KeyguardMediaController keyguardMediaController) {
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
    public final void removeViews(ConstraintLayout constraintLayout) {
        Flags.migrateClocksToBlueprint();
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }
}
