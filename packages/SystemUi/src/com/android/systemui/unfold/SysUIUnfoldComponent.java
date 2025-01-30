package com.android.systemui.unfold;

import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SysUIUnfoldComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        SysUIUnfoldComponent create(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider);
    }

    FoldAodAnimationController getFoldAodAnimationController();

    KeyguardUnfoldTransition getKeyguardUnfoldTransition();

    NotificationPanelUnfoldAnimationController getNotificationPanelUnfoldAnimationController();

    UnfoldLightRevealOverlayAnimation getUnfoldLightRevealOverlayAnimation();

    UnfoldTransitionWallpaperController getUnfoldTransitionWallpaperController();
}
