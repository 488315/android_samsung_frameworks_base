package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.DisplayRotationKt;
import com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.DeviceEntryUdfpsAccessibilityOverlayViewModel;
import com.android.systemui.keyguard.shared.model.KeyguardSection;

/* loaded from: classes2.dex */
public final class DefaultUdfpsAccessibilityOverlaySection extends KeyguardSection {
    public final Context context;
    public final DisplayStateInteractor displayStateInteractor;
    public final int viewId = R.id.udfps_accessibility_overlay;
    public final DeviceEntryUdfpsAccessibilityOverlayViewModel viewModel;

    public DefaultUdfpsAccessibilityOverlaySection(Context context, DisplayStateInteractor displayStateInteractor, DeviceEntryUdfpsAccessibilityOverlayViewModel deviceEntryUdfpsAccessibilityOverlayViewModel) {
        this.context = context;
        this.displayStateInteractor = displayStateInteractor;
        this.viewModel = deviceEntryUdfpsAccessibilityOverlayViewModel;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
        UdfpsAccessibilityOverlay udfpsAccessibilityOverlay = new UdfpsAccessibilityOverlay(this.context);
        udfpsAccessibilityOverlay.setId(this.viewId);
        constraintLayout.addView(udfpsAccessibilityOverlay);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0045  */
    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyConstraints(ConstraintSet constraintSet) {
        int i;
        int rotation = DisplayRotationKt.toRotation((DisplayRotation) ((DisplayStateInteractorImpl) this.displayStateInteractor).currentRotation.$$delegate_0.getValue());
        boolean z = LsRune.SECURITY_FINGERPRINT_IN_DISPLAY;
        int i2 = R.id.keyguard_indication_area;
        int i3 = 4;
        int i4 = this.viewId;
        if (!z) {
            constraintSet.connect(i4, 6, 0, 6);
            constraintSet.connect(i4, 7, 0, 7);
            constraintSet.create(R.id.udfps_accessibility_overlay_top_guideline, 0);
            constraintSet.setGuidelinePercent(R.id.udfps_accessibility_overlay_top_guideline, 0.5f);
            constraintSet.connect(i4, 3, R.id.udfps_accessibility_overlay_top_guideline, 4);
            constraintSet.connect(i4, 4, R.id.keyguard_indication_area, 3);
            return;
        }
        int i5 = 1;
        int i6 = (rotation == 0 || (rotation != 1 && (rotation == 2 || rotation != 3))) ? 6 : 3;
        int i7 = (rotation == 0 || (rotation != 1 && (rotation == 2 || rotation != 3))) ? 7 : 4;
        if (rotation == 0) {
            i = 3;
        } else if (rotation == 1) {
            i = 6;
        } else if (rotation == 2) {
            i = 4;
        } else if (rotation == 3) {
            i = 7;
        }
        if (rotation != 0) {
            if (rotation == 1) {
                i3 = 7;
            } else if (rotation == 2) {
                i3 = 3;
            } else if (rotation == 3) {
                i3 = 6;
            }
        }
        if (rotation == 0 || (rotation != 1 && (rotation == 2 || rotation != 3))) {
            i5 = 0;
        }
        constraintSet.connect(i4, i6, 0, i6);
        constraintSet.connect(i4, i7, 0, i7);
        constraintSet.create(R.id.udfps_accessibility_overlay_top_guideline, i5);
        constraintSet.setGuidelinePercent(R.id.udfps_accessibility_overlay_top_guideline, 0.5f);
        constraintSet.connect(i4, i, R.id.udfps_accessibility_overlay_top_guideline, i3);
        if (rotation != 0) {
            i2 = 0;
        }
        constraintSet.connect(i4, i3, i2, i3);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        View viewFindViewById = constraintLayout.findViewById(this.viewId);
        viewFindViewById.getClass();
        UdfpsAccessibilityOverlayBinder.bind((UdfpsAccessibilityOverlay) viewFindViewById, this.viewModel);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.viewId);
    }
}
