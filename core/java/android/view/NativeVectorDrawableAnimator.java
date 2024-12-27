package android.view;

import android.animation.Animator;

public interface NativeVectorDrawableAnimator {
    long getAnimatorNativePtr();

    void setThreadedRendererAnimatorListener(Animator.AnimatorListener animatorListener);
}
