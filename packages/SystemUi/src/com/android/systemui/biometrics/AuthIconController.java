package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AuthIconController extends Animatable2.AnimationCallback {
    public final Context context;
    public boolean deactivated;
    public final LottieAnimationView iconView;

    public AuthIconController(Context context, LottieAnimationView lottieAnimationView) {
        this.context = context;
        this.iconView = lottieAnimationView;
    }

    public final void animateIcon(int i, boolean z) {
        if (this.deactivated) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) this.context.getDrawable(i);
        this.iconView.setImageDrawable(animatedVectorDrawable);
        animatedVectorDrawable.forceAnimationOnUI();
        if (z) {
            animatedVectorDrawable.registerAnimationCallback(this);
        }
        animatedVectorDrawable.start();
    }

    public boolean getActsAsConfirmButton() {
        return false;
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        super.onAnimationEnd(drawable);
        if (this.deactivated) {
            return;
        }
        handleAnimationEnd();
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationStart(Drawable drawable) {
        super.onAnimationStart(drawable);
    }

    public abstract void updateIcon(int i, int i2);

    public void handleAnimationEnd() {
    }
}
