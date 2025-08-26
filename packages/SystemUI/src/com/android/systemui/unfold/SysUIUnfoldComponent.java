package com.android.systemui.unfold;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.Set;

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
