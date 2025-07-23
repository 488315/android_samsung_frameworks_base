package android.graphics.drawable;

/* loaded from: classes.dex */
public interface Animatable2 extends Animatable {

    public static abstract class AnimationCallback {
        public void onAnimationEnd(Drawable drawable) {
        }

        public void onAnimationStart(Drawable drawable) {
        }
    }

    void clearAnimationCallbacks();

    void registerAnimationCallback(AnimationCallback animationCallback);

    boolean unregisterAnimationCallback(AnimationCallback animationCallback);
}
