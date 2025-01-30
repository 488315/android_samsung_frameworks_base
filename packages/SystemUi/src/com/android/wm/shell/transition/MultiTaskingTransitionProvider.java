package com.android.wm.shell.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultiTaskingTransitionProvider {
    public final ShellExecutor mAnimExecutor;
    public final ShellExecutor mMainExecutor;
    public final MultiTaskingTransitionState mState;
    public final TransactionPool mTransactionPool;
    public final TransitionAnimation mTransitionAnimation;
    public float mDurationScale = 1.0f;
    public final SparseArray mAnimationLoaderMap = new SparseArray();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SurfaceValueAnimator extends ValueAnimator {
        public final String mLeashName;

        public SurfaceValueAnimator(SurfaceControl surfaceControl, float... fArr) {
            this.mLeashName = surfaceControl.toString();
            setFloatValues(fArr);
        }

        @Override // android.animation.ValueAnimator
        public final String toString() {
            StringBuilder sb = new StringBuilder("SurfaceValueAnimator{@");
            sb.append(Integer.toHexString(hashCode()));
            sb.append(" / leash=");
            return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.mLeashName, "}");
        }
    }

    public MultiTaskingTransitionProvider(TransitionAnimation transitionAnimation, DisplayController displayController, TransactionPool transactionPool, ShellExecutor shellExecutor, ShellExecutor shellExecutor2) {
        this.mState = new MultiTaskingTransitionState(transitionAnimation, displayController);
        this.mTransitionAnimation = transitionAnimation;
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            registerAnimationLoader(1);
        }
        registerAnimationLoader(2);
        if (CoreRune.MD_DEX_SHELL_TRANSITION) {
            registerAnimationLoader(3);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            registerAnimationLoader(4);
        }
        registerAnimationLoader(6);
    }

    public static void applyTransformation(long j, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect) {
        if (!surfaceControl.isValid()) {
            Log.w("MultiTaskingTransitionProvider", "applyTransformation: invalid leash=" + surfaceControl);
            return;
        }
        animation.getTransformation(j, transformation);
        if (point != null) {
            transformation.getMatrix().postTranslate(point.x, point.y);
        }
        transaction.setMatrix(surfaceControl, transformation.getMatrix(), fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        Rect rect2 = rect == null ? null : new Rect(rect);
        Insets min = Insets.min(transformation.getInsets(), Insets.NONE);
        if (!min.equals(Insets.NONE) && rect2 != null && !rect2.isEmpty()) {
            rect2.inset(min);
            transaction.setCrop(surfaceControl, rect2);
        }
        if (animation.hasRoundedCorners() && f > 0.0f && rect2 != null) {
            transaction.setCrop(surfaceControl, rect2);
            transaction.setCornerRadius(surfaceControl, f);
        }
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public final void buildSurfaceAnimator(final ArrayList arrayList, final Animation animation, final SurfaceControl surfaceControl, final Runnable runnable, final Point point, final Rect rect, boolean z) {
        final SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        final SurfaceValueAnimator surfaceValueAnimator = new SurfaceValueAnimator(surfaceControl, 0.0f, 1.0f);
        final Transformation transformation = new Transformation();
        final float[] fArr = new float[9];
        surfaceValueAnimator.overrideDurationScale(1.0f);
        surfaceValueAnimator.setDuration(animation.computeDurationHint());
        final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda0
            public final /* synthetic */ float f$7 = 0.0f;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = MultiTaskingTransitionProvider.SurfaceValueAnimator.this;
                MultiTaskingTransitionProvider.applyTransformation(Math.min(surfaceValueAnimator2.getDuration(), surfaceValueAnimator2.getCurrentPlayTime()), acquire, surfaceControl, animation, transformation, fArr, point, this.f$7, rect);
            }
        };
        surfaceValueAnimator.addUpdateListener(animatorUpdateListener);
        final Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda1
            public final /* synthetic */ float f$8 = 0.0f;

            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingTransitionProvider multiTaskingTransitionProvider = MultiTaskingTransitionProvider.this;
                final MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = surfaceValueAnimator;
                SurfaceControl.Transaction transaction = acquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                Animation animation2 = animation;
                Transformation transformation2 = transformation;
                float[] fArr2 = fArr;
                Point point2 = point;
                float f = this.f$8;
                Rect rect2 = rect;
                final ArrayList arrayList2 = arrayList;
                final Runnable runnable3 = runnable;
                multiTaskingTransitionProvider.getClass();
                MultiTaskingTransitionProvider.applyTransformation(surfaceValueAnimator2.getDuration(), transaction, surfaceControl2, animation2, transformation2, fArr2, point2, f, rect2);
                multiTaskingTransitionProvider.mTransactionPool.release(transaction);
                ((HandlerExecutor) multiTaskingTransitionProvider.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList3 = arrayList2;
                        MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator3 = surfaceValueAnimator2;
                        Runnable runnable4 = runnable3;
                        arrayList3.remove(surfaceValueAnimator3);
                        Log.d("MultiTaskingTransitionProvider", "Remove " + surfaceValueAnimator3 + ", num_remains=" + arrayList3.size());
                        runnable4.run();
                    }
                });
            }
        };
        surfaceValueAnimator.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider.1
            public boolean mFinished = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                if (this.mFinished) {
                    return;
                }
                this.mFinished = true;
                runnable2.run();
                surfaceValueAnimator.removeUpdateListener(animatorUpdateListener);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (this.mFinished) {
                    return;
                }
                this.mFinished = true;
                runnable2.run();
                surfaceValueAnimator.removeUpdateListener(animatorUpdateListener);
            }
        });
        arrayList.add(surfaceValueAnimator);
        if (z) {
            ((HandlerExecutor) this.mAnimExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MultiTaskingTransitionProvider.SurfaceValueAnimator.this.start();
                }
            });
        }
        Log.d("MultiTaskingTransitionProvider", "buildSurfaceAnimator: create " + surfaceValueAnimator + ", shouldStart=" + z);
    }

    public SparseArray<AnimationLoader> getAnimationLoaderMap() {
        return this.mAnimationLoaderMap;
    }

    public MultiTaskingTransitionState getState() {
        return this.mState;
    }

    public final Animation loadAnimationFromResources(int i, Rect rect) {
        Animation loadAnimationRes = this.mTransitionAnimation.loadAnimationRes("android", i);
        if (loadAnimationRes == null) {
            Log.d("MultiTaskingTransitionProvider", "loadAnimationFromResources: failed, Callers=" + Debug.getCallers(5));
            loadAnimationRes = new AlphaAnimation(1.0f, 1.0f);
            loadAnimationRes.setDuration(336L);
        }
        if (!loadAnimationRes.isInitialized()) {
            int width = rect.width();
            int height = rect.height();
            loadAnimationRes.initialize(width, height, width, height);
        }
        loadAnimationRes.restrictDuration(10000L);
        loadAnimationRes.scaleCurrentDuration(this.mDurationScale);
        return loadAnimationRes;
    }

    public void registerAnimationLoader(int i) {
        AnimationLoader popOverAnimationLoader;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (i == 1) {
            popOverAnimationLoader = new SplitAnimationLoader(multiTaskingTransitionState);
        } else if (i == 2) {
            popOverAnimationLoader = new DexCompatAnimationLoader(multiTaskingTransitionState);
        } else if (i == 3) {
            popOverAnimationLoader = new DexAnimationLoader(multiTaskingTransitionState);
        } else if (i == 4) {
            popOverAnimationLoader = new FreeformAnimationLoader(multiTaskingTransitionState);
        } else if (i == 5) {
            popOverAnimationLoader = new SplitActivityAnimationLoader(multiTaskingTransitionState);
        } else {
            if (i != 6) {
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid animation type=", i));
            }
            popOverAnimationLoader = new PopOverAnimationLoader(multiTaskingTransitionState);
        }
        this.mAnimationLoaderMap.put(i, popOverAnimationLoader);
    }
}
