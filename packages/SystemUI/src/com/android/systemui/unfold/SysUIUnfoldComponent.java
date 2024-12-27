package com.android.systemui.unfold;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
