package com.android.systemui.statusbar.phone;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.util.function.TriConsumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda2 implements TriConsumer {
    public final /* synthetic */ LightBarController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda2(LightBarController lightBarController) {
        this.f$0 = lightBarController;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        LightBarController lightBarController = this.f$0;
        ScrimState scrimState = (ScrimState) obj;
        float fFloatValue = ((Float) obj2).floatValue();
        ColorExtractor.GradientColors gradientColors = (ColorExtractor.GradientColors) obj3;
        LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) lightBarController;
        boolean z = lightBarControllerImpl.mBouncerVisible;
        boolean z2 = lightBarControllerImpl.mForceDarkForScrim;
        boolean z3 = lightBarControllerImpl.mForceLightForScrim;
        boolean z4 = scrimState == ScrimState.BOUNCER || scrimState == ScrimState.BOUNCER_SCRIMMED;
        lightBarControllerImpl.mBouncerVisible = z4;
        boolean z5 = z4 || fFloatValue >= 0.1f;
        boolean zSupportsDarkText = gradientColors.supportsDarkText();
        boolean z6 = z5 && !zSupportsDarkText;
        lightBarControllerImpl.mForceDarkForScrim = z6;
        boolean z7 = z5 && zSupportsDarkText;
        lightBarControllerImpl.mForceLightForScrim = z7;
        if (lightBarControllerImpl.mBouncerVisible != z) {
            lightBarControllerImpl.reevaluate();
            return;
        }
        if (lightBarControllerImpl.mHasLightNavigationBar) {
            if (z6 != z2) {
                lightBarControllerImpl.reevaluate();
            }
        } else if (z7 != z3) {
            lightBarControllerImpl.reevaluate();
        }
    }
}
