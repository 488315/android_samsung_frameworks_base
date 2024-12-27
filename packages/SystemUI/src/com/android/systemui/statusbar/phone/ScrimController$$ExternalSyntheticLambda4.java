package com.android.systemui.statusbar.phone;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.util.function.TriConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda4 implements TriConsumer {
    public final /* synthetic */ LightBarController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda4(LightBarController lightBarController) {
        this.f$0 = lightBarController;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        LightBarController lightBarController = this.f$0;
        ScrimState scrimState = (ScrimState) obj;
        float floatValue = ((Float) obj2).floatValue();
        ColorExtractor.GradientColors gradientColors = (ColorExtractor.GradientColors) obj3;
        boolean z = lightBarController.mBouncerVisible;
        boolean z2 = lightBarController.mForceDarkForScrim;
        boolean z3 = lightBarController.mForceLightForScrim;
        boolean z4 = scrimState == ScrimState.BOUNCER || scrimState == ScrimState.BOUNCER_SCRIMMED;
        lightBarController.mBouncerVisible = z4;
        boolean z5 = z4 || floatValue >= LightBarController.NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD;
        boolean supportsDarkText = gradientColors.supportsDarkText();
        boolean z6 = z5 && !supportsDarkText;
        lightBarController.mForceDarkForScrim = z6;
        boolean z7 = z5 && supportsDarkText;
        lightBarController.mForceLightForScrim = z7;
        if (lightBarController.mBouncerVisible != z) {
            lightBarController.reevaluate();
            return;
        }
        if (lightBarController.mHasLightNavigationBar) {
            if (z6 != z2) {
                lightBarController.reevaluate();
            }
        } else if (z7 != z3) {
            lightBarController.reevaluate();
        }
    }
}
