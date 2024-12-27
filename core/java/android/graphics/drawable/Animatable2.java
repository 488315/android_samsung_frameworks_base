package android.graphics.drawable;

/* loaded from: classes.dex */
public interface Animatable2 extends Animatable {
    void clearAnimationCallbacks();

    void registerAnimationCallback(AnimationCallback animationCallback);

    boolean unregisterAnimationCallback(AnimationCallback animationCallback);

    public abstract static class AnimationCallback {
        public void onAnimationStart(Drawable drawable) {}

        public void onAnimationEnd(Drawable drawable) {}
    }
}
