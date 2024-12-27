package com.android.systemui.wallpaper.theme.builder;

import android.animation.AnimatorSet;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;

public final class ComplexAnimationBuilder {
    public final AnimatorSet mAnimatorSet = new AnimatorSet();
    public FrameAnimationView mFestivalSpriteView = null;
    public LockscreenCallback mLockscreenCallback = null;
}
