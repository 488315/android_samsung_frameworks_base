package com.android.systemui.wallpaper.theme.builder;

import android.animation.AnimatorSet;
import android.util.Log;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;

/* loaded from: classes3.dex */
public class ComplexAnimationBuilder {
    public final AnimatorSet mAnimatorSet = new AnimatorSet();
    public FrameAnimationView mFestivalSpriteView = null;
    public LockscreenCallback mLockscreenCallback = null;

    public final void playAnimation() {
        FrameAnimationView frameAnimationView = this.mFestivalSpriteView;
        if (frameAnimationView != null) {
            frameAnimationView.screenTurnedOn();
        }
        LockscreenCallback lockscreenCallback = this.mLockscreenCallback;
        if (lockscreenCallback != null) {
            lockscreenCallback.screenTurnedOn();
        }
        try {
            this.mAnimatorSet.start();
        } catch (UnsupportedOperationException unused) {
            Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred!");
            try {
                this.mAnimatorSet.start();
            } catch (UnsupportedOperationException unused2) {
                Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred again!");
            }
        }
    }
}
