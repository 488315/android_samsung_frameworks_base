package com.android.systemui.unfold;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SysUIUnfoldComponent {
    FoldAodAnimationController getFoldAodAnimationController();

    Set getFullScreenLightRevealAnimations();

    KeyguardUnfoldTransition getKeyguardUnfoldTransition();

    NaturalRotationUnfoldProgressProvider getNaturalRotationUnfoldProgressProvider();

    NotificationPanelUnfoldAnimationController getNotificationPanelUnfoldAnimationController();

    UnfoldHapticsPlayer getUnfoldHapticsPlayer();

    UnfoldLatencyTracker getUnfoldLatencyTracker();

    UnfoldTransitionWallpaperController getUnfoldTransitionWallpaperController();
}
