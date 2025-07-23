package android.view;

import android.animation.Animator;

/* loaded from: classes4.dex */
public interface NativeVectorDrawableAnimator {
    long getAnimatorNativePtr();

    void setThreadedRendererAnimatorListener(Animator.AnimatorListener animatorListener);
}
