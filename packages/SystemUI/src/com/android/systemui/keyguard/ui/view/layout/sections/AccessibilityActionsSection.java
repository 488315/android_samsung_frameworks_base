package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.AccessibilityActionsViewModel;
import com.android.systemui.util.Utils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AccessibilityActionsSection extends KeyguardSection {
    public final Context context;

    public AccessibilityActionsSection(Context context, AccessibilityActionsViewModel accessibilityActionsViewModel) {
        this.context = context;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        if (this.context.getResources().getBoolean(R.bool.config_communalServiceEnabled)) {
            Flags.communalHub();
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        constraintSet.connect(R.id.accessibility_actions_view, 3, 0, 3, Utils.getStatusBarHeaderHeightKeyguard(this.context));
        constraintSet.connect(R.id.accessibility_actions_view, 4, 0, 4);
        constraintSet.connect(R.id.accessibility_actions_view, 6, 0, 6);
        constraintSet.connect(R.id.accessibility_actions_view, 7, 0, 7);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        if (this.context.getResources().getBoolean(R.bool.config_communalServiceEnabled)) {
            Flags.communalHub();
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, R.id.accessibility_actions_view);
    }
}
