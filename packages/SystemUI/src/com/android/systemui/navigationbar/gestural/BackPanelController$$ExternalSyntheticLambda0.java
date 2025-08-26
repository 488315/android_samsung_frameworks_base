package com.android.systemui.navigationbar.gestural;

import android.util.MathUtils;
import android.view.VelocityTracker;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class BackPanelController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BackPanelController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        float fSmoothStep;
        switch (this.$r8$classId) {
            case 0:
                Float f = (Float) this.f$0;
                return Float.valueOf(f != null ? f.floatValue() : 0.0f);
            default:
                BackPanelController backPanelController = (BackPanelController) this.f$0;
                if (backPanelController.velocityTracker == null) {
                    backPanelController.velocityTracker = VelocityTracker.obtain();
                }
                VelocityTracker velocityTracker = backPanelController.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.computeCurrentVelocity(1);
                    fSmoothStep = MathUtils.smoothStep(0.5f, 1.0f, Math.abs(velocityTracker.getXVelocity()));
                } else {
                    fSmoothStep = 10.0f;
                }
                return Float.valueOf(MathUtils.lerp(10.0f, 100.0f, 1 - fSmoothStep));
        }
    }
}
