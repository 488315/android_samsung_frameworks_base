package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.deviceentry.ui.binder.UdfpsAccessibilityOverlayBinder;
import com.android.systemui.deviceentry.ui.view.UdfpsAccessibilityOverlay;
import com.android.systemui.deviceentry.ui.viewmodel.DeviceEntryUdfpsAccessibilityOverlayViewModel;
import com.android.systemui.keyguard.shared.model.KeyguardSection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0075  */
    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyConstraints(androidx.constraintlayout.widget.ConstraintSet r15) {
        /*
            r14 = this;
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor r0 = r14.displayStateInteractor
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl r0 = (com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl) r0
            kotlinx.coroutines.flow.ReadonlyStateFlow r0 = r0.currentRotation
            kotlinx.coroutines.flow.StateFlow r0 = r0.$$delegate_0
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.biometrics.shared.model.DisplayRotation r0 = (com.android.systemui.biometrics.shared.model.DisplayRotation) r0
            int r0 = com.android.systemui.biometrics.shared.model.DisplayRotationKt.toRotation(r0)
            boolean r1 = com.android.systemui.LsRune.SECURITY_FINGERPRINT_IN_DISPLAY
            r2 = 2131363351(0x7f0a0617, float:1.8346508E38)
            r3 = 1056964608(0x3f000000, float:0.5)
            r4 = 2131365521(0x7f0a0e91, float:1.835091E38)
            r5 = 4
            r6 = 7
            r7 = 6
            int r14 = r14.viewId
            r8 = 0
            r9 = 3
            if (r1 == 0) goto L7a
            r1 = 2
            r10 = 1
            if (r0 == 0) goto L2f
            if (r0 == r10) goto L31
            if (r0 == r1) goto L2f
            if (r0 == r9) goto L31
        L2f:
            r11 = r7
            goto L32
        L31:
            r11 = r9
        L32:
            if (r0 == 0) goto L3a
            if (r0 == r10) goto L3c
            if (r0 == r1) goto L3a
            if (r0 == r9) goto L3c
        L3a:
            r12 = r6
            goto L3d
        L3c:
            r12 = r5
        L3d:
            if (r0 == 0) goto L45
            if (r0 == r10) goto L4b
            if (r0 == r1) goto L49
            if (r0 == r9) goto L47
        L45:
            r13 = r9
            goto L4c
        L47:
            r13 = r6
            goto L4c
        L49:
            r13 = r5
            goto L4c
        L4b:
            r13 = r7
        L4c:
            if (r0 == 0) goto L5a
            if (r0 == r10) goto L59
            if (r0 == r1) goto L57
            if (r0 == r9) goto L55
            goto L5a
        L55:
            r5 = r7
            goto L5a
        L57:
            r5 = r9
            goto L5a
        L59:
            r5 = r6
        L5a:
            if (r0 == 0) goto L62
            if (r0 == r10) goto L63
            if (r0 == r1) goto L62
            if (r0 == r9) goto L63
        L62:
            r10 = r8
        L63:
            r15.connect(r14, r11, r8, r11)
            r15.connect(r14, r12, r8, r12)
            r15.create(r4, r10)
            r15.setGuidelinePercent(r4, r3)
            r15.connect(r14, r13, r4, r5)
            if (r0 != 0) goto L75
            goto L76
        L75:
            r2 = r8
        L76:
            r15.connect(r14, r5, r2, r5)
            return
        L7a:
            r15.connect(r14, r7, r8, r7)
            r15.connect(r14, r6, r8, r6)
            r15.create(r4, r8)
            r15.setGuidelinePercent(r4, r3)
            r15.connect(r14, r9, r4, r5)
            r15.connect(r14, r5, r2, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.view.layout.sections.DefaultUdfpsAccessibilityOverlaySection.applyConstraints(androidx.constraintlayout.widget.ConstraintSet):void");
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
        View findViewById = constraintLayout.findViewById(this.viewId);
        findViewById.getClass();
        UdfpsAccessibilityOverlayBinder.bind((UdfpsAccessibilityOverlay) findViewById, this.viewModel);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.viewId);
    }
}
