package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.DeviceEntryUdfpsAccessibilityOverlayViewModel;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import kotlin.jvm.internal.Intrinsics;

public final class DefaultUdfpsAccessibilityOverlaySection extends KeyguardSection {
    public final Context context;
    public final int viewId = R.id.udfps_accessibility_overlay;
    public final DeviceEntryUdfpsAccessibilityOverlayViewModel viewModel;

    public DefaultUdfpsAccessibilityOverlaySection(Context context, DeviceEntryUdfpsAccessibilityOverlayViewModel deviceEntryUdfpsAccessibilityOverlayViewModel) {
        this.context = context;
        this.viewModel = deviceEntryUdfpsAccessibilityOverlayViewModel;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        UdfpsAccessibilityOverlay udfpsAccessibilityOverlay = new UdfpsAccessibilityOverlay(this.context);
        udfpsAccessibilityOverlay.setId(this.viewId);
        constraintLayout.addView(udfpsAccessibilityOverlay);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        int i = this.viewId;
        constraintSet.connect(i, 6, 0, 6);
        constraintSet.connect(i, 7, 0, 7);
        constraintSet.create(R.id.udfps_accessibility_overlay_top_guideline, 0);
        constraintSet.setGuidelinePercent(R.id.udfps_accessibility_overlay_top_guideline, 0.5f);
        constraintSet.connect(i, 3, R.id.udfps_accessibility_overlay_top_guideline, 4);
        Flags.keyguardBottomAreaRefactor();
        constraintSet.connect(i, 4, 0, 4);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        View findViewById = constraintLayout.findViewById(this.viewId);
        Intrinsics.checkNotNull(findViewById);
        UdfpsAccessibilityOverlayBinder.bind((UdfpsAccessibilityOverlay) findViewById, this.viewModel);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.viewId);
    }
}
