package androidx.appcompat.animation;

import android.animation.ValueAnimator;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SeslRecoilAnimator$Holder$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SeslRecoilAnimator seslRecoilAnimator = (SeslRecoilAnimator) obj;
        if ((seslRecoilAnimator.mIsPressed || seslRecoilAnimator.mAnimator.isRunning()) && seslRecoilAnimator.mIsPressed) {
            seslRecoilAnimator.mIsPressed = false;
            if (seslRecoilAnimator.mAnimator.isRunning()) {
                seslRecoilAnimator.mAnimator.cancel();
            }
            ValueAnimator valueAnimator = seslRecoilAnimator.mAnimator;
            valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
            seslRecoilAnimator.mAnimator.setDuration(350L);
            seslRecoilAnimator.mAnimator.setInterpolator(SeslRecoilAnimator.sReleaseInterpolator);
            seslRecoilAnimator.mAnimator.start();
        }
    }
}
