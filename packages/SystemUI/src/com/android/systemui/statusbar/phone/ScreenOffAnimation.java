package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.LightRevealScrim;

/* loaded from: classes3.dex */
public interface ScreenOffAnimation {
    default void animateInKeyguard(View view, KeyguardSecVisibilityHelper$mSetVisibleEndRunnable$1 keyguardSecVisibilityHelper$mSetVisibleEndRunnable$1) {
        keyguardSecVisibilityHelper$mSetVisibleEndRunnable$1.run();
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

    boolean startAnimation();

    default void onAlwaysOnChanged(boolean z) {
    }

    default void onScrimOpaqueChanged(boolean z) {
    }
}
