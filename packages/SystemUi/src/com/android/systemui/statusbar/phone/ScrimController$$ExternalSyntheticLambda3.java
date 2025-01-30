package com.android.systemui.statusbar.phone;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.util.function.TriConsumer;
import com.android.systemui.BasicRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda3 implements TriConsumer {
    public final /* synthetic */ LightBarController f$0;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda3(LightBarController lightBarController) {
        this.f$0 = lightBarController;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        boolean z;
        LightBarController lightBarController = this.f$0;
        ScrimState scrimState = (ScrimState) obj;
        float floatValue = ((Float) obj2).floatValue();
        ColorExtractor.GradientColors gradientColors = (ColorExtractor.GradientColors) obj3;
        lightBarController.getClass();
        boolean z2 = BasicRune.NAVBAR_LIGHTBAR;
        boolean z3 = z2 && lightBarController.mNavBarStateManager.isOpaqueNavigationBar();
        boolean z4 = lightBarController.mPanelExpanded;
        boolean z5 = lightBarController.mPanelHasWhiteBg;
        float f = LightBarController.NAV_BAR_INVERSION_SCRIM_ALPHA_THRESHOLD;
        if (lightBarController.mUseNewLightBarLogic) {
            boolean z6 = lightBarController.mBouncerVisible;
            boolean z7 = lightBarController.mForceDarkForScrim;
            boolean z8 = lightBarController.mForceLightForScrim;
            boolean z9 = scrimState == ScrimState.BOUNCER || scrimState == ScrimState.BOUNCER_SCRIMMED;
            lightBarController.mBouncerVisible = z9;
            boolean z10 = z9 || floatValue >= f;
            boolean supportsDarkText = gradientColors.supportsDarkText();
            boolean z11 = z10 && !supportsDarkText;
            lightBarController.mForceDarkForScrim = z11;
            z = z10 && supportsDarkText;
            lightBarController.mForceLightForScrim = z;
            if (lightBarController.mBouncerVisible != z6) {
                lightBarController.reevaluate();
                return;
            }
            if (lightBarController.mHasLightNavigationBar) {
                if (z11 != z7) {
                    lightBarController.reevaluate();
                    return;
                }
                return;
            } else {
                if (z != z8) {
                    lightBarController.reevaluate();
                    return;
                }
                return;
            }
        }
        if (z2) {
            int mainColor = gradientColors.getMainColor();
            int i = (mainColor >> 16) & 255;
            int i2 = (mainColor >> 8) & 255;
            int i3 = mainColor & 255;
            lightBarController.mPanelHasWhiteBg = ((int) Math.sqrt((((double) (i3 * i3)) * 0.068d) + ((((double) (i2 * i2)) * 0.691d) + (((double) (i * i)) * 0.241d)))) >= 130;
            lightBarController.mPanelExpanded = scrimState == ScrimState.UNLOCKED && floatValue >= f;
        }
        boolean z12 = lightBarController.mForceDarkForScrim;
        boolean z13 = scrimState != ScrimState.BOUNCER && scrimState != ScrimState.BOUNCER_SCRIMMED && floatValue >= f && (z2 || !gradientColors.supportsDarkText()) && !(z2 && z3);
        lightBarController.mForceDarkForScrim = z13;
        if (!z2) {
            if (!lightBarController.mHasLightNavigationBar || z13 == z12) {
                return;
            }
            lightBarController.reevaluate();
            return;
        }
        boolean z14 = z4 != lightBarController.mPanelExpanded;
        boolean z15 = lightBarController.mPanelHasWhiteBg != z5;
        z = lightBarController.mHasLightNavigationBar && z13 != z12;
        if (z14 || z15 || z) {
            lightBarController.reevaluate();
        }
    }
}
