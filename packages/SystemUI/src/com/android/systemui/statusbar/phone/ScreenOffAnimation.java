package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.LightRevealScrim;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface ScreenOffAnimation {
    default void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0) {
        keyguardVisibilityHelper$$ExternalSyntheticLambda0.run();
    }

    void initialize(CentralSurfaces centralSurfaces, ShadeViewController shadeViewController, LightRevealScrim lightRevealScrim);

    boolean isAnimationPlaying();

    default boolean isKeyguardHideDelayed() {
        return false;
    }

    default boolean isKeyguardShowDelayed() {
        return false;
    }

    default boolean overrideNotificationsDozeAmount() {
        return false;
    }

    default boolean shouldAnimateClockChange() {
        return true;
    }

    default boolean shouldAnimateDozingChange() {
        return true;
    }

    default boolean shouldAnimateInKeyguard() {
        return false;
    }

    boolean shouldDelayDisplayDozeTransition();

    default boolean shouldDelayKeyguardShow() {
        return false;
    }

    default boolean shouldHideScrimOnWakeUp() {
        return false;
    }

    boolean shouldPlayAnimation();

    boolean shouldShowAodIconsWhenShade();

    boolean startAnimation();

    default void onAlwaysOnChanged(boolean z) {
    }

    default void onScrimOpaqueChanged(boolean z) {
    }
}
