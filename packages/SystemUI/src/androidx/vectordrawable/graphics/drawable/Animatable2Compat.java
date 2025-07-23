package androidx.vectordrawable.graphics.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Animatable2Compat extends Animatable {
    void clearAnimationCallbacks();

    void registerAnimationCallback(AnimationCallback animationCallback);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class AnimationCallback {
        public AnonymousClass1 mPlatformCallback;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: androidx.vectordrawable.graphics.drawable.Animatable2Compat$AnimationCallback$1, reason: invalid class name */
        public class AnonymousClass1 extends Animatable2.AnimationCallback {
            public AnonymousClass1() {
            }

            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationEnd(Drawable drawable) {
                AnimationCallback.this.onAnimationEnd(drawable);
            }

            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public final void onAnimationStart(Drawable drawable) {
                AnimationCallback.this.onAnimationStart(drawable);
            }
        }

        public void onAnimationEnd(Drawable drawable) {
        }

        public void onAnimationStart(Drawable drawable) {
        }
    }
}
