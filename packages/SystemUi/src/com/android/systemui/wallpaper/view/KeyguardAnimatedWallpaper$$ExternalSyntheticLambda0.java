package com.android.systemui.wallpaper.view;

import android.animation.AnimatorSet;
import android.util.Log;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardAnimatedWallpaper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardAnimatedWallpaper f$0;

    public /* synthetic */ KeyguardAnimatedWallpaper$$ExternalSyntheticLambda0(KeyguardAnimatedWallpaper keyguardAnimatedWallpaper, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardAnimatedWallpaper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardAnimatedWallpaper keyguardAnimatedWallpaper = this.f$0;
                if (keyguardAnimatedWallpaper.mComplexAnimationBuilder != null && !keyguardAnimatedWallpaper.mIsPlayingAnimation) {
                    if (keyguardAnimatedWallpaper.mIsBlurEnabled) {
                        Log.d("KeyguardAnimatedWallpaper", "playAnimation() skip by blur");
                        break;
                    } else {
                        Log.d("KeyguardAnimatedWallpaper", "playAnimation");
                        keyguardAnimatedWallpaper.mIsPlayingAnimation = true;
                        ComplexAnimationBuilder complexAnimationBuilder = keyguardAnimatedWallpaper.mComplexAnimationBuilder;
                        AnimatorSet animatorSet = complexAnimationBuilder.mAnimatorSet;
                        FrameAnimationView frameAnimationView = complexAnimationBuilder.mFestivalSpriteView;
                        if (frameAnimationView != null) {
                            frameAnimationView.screenTurnedOn();
                        }
                        LockscreenCallback lockscreenCallback = complexAnimationBuilder.mLockscreenCallback;
                        if (lockscreenCallback != null) {
                            lockscreenCallback.screenTurnedOn();
                        }
                        try {
                            animatorSet.start();
                            break;
                        } catch (UnsupportedOperationException unused) {
                            Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred!");
                            try {
                                animatorSet.cancel();
                                animatorSet.start();
                                return;
                            } catch (UnsupportedOperationException unused2) {
                                Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred again!");
                            }
                        }
                    }
                }
                break;
            default:
                KeyguardAnimatedWallpaper keyguardAnimatedWallpaper2 = this.f$0;
                if (keyguardAnimatedWallpaper2.mComplexAnimationBuilder != null && keyguardAnimatedWallpaper2.mIsPlayingAnimation) {
                    Log.d("KeyguardAnimatedWallpaper", "stopAnimation");
                    keyguardAnimatedWallpaper2.mIsPlayingAnimation = false;
                    try {
                        ComplexAnimationBuilder complexAnimationBuilder2 = keyguardAnimatedWallpaper2.mComplexAnimationBuilder;
                        FrameAnimationView frameAnimationView2 = complexAnimationBuilder2.mFestivalSpriteView;
                        if (frameAnimationView2 != null) {
                            frameAnimationView2.screenTurnedOff();
                        }
                        LockscreenCallback lockscreenCallback2 = complexAnimationBuilder2.mLockscreenCallback;
                        if (lockscreenCallback2 != null) {
                            lockscreenCallback2.screenTurnedOff();
                        }
                        complexAnimationBuilder2.mAnimatorSet.cancel();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                break;
        }
    }
}
