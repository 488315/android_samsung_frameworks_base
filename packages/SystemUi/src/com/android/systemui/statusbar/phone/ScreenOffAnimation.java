package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.LightRevealScrim;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ScreenOffAnimation {
    void animateInKeyguard(View view, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0);

    void initialize(CentralSurfaces centralSurfaces, LightRevealScrim lightRevealScrim);

    boolean isAnimationPlaying();

    boolean isKeyguardHideDelayed();

    boolean isKeyguardShowDelayed();

    void onAlwaysOnChanged(boolean z);

    void onScrimOpaqueChanged(boolean z);

    boolean overrideNotificationsDozeAmount();

    boolean shouldAnimateAodIcons();

    boolean shouldAnimateClockChange();

    boolean shouldAnimateDozingChange();

    boolean shouldAnimateInKeyguard();

    boolean shouldDelayDisplayDozeTransition();

    boolean shouldDelayKeyguardShow();

    boolean shouldHideScrimOnWakeUp();

    boolean shouldPlayAnimation();

    boolean shouldShowAodIconsWhenShade();

    boolean startAnimation();
}
