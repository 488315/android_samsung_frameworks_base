package com.android.wm.shell.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.internal.policy.TransitionAnimation;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MultiTaskingTransitionProvider implements MultiTaskingTransitions {
    public static final ArrayList sForceHidingAnimators = new ArrayList();
    public final ShellExecutor mAnimExecutor;
    public final DisplayController mDisplayController;
    public final ShellExecutor mMainExecutor;
    public final MultiTaskingTransitionState mState;
    public final TransactionPool mTransactionPool;
    public final TransitionAnimation mTransitionAnimation;
    public float mDurationScale = 1.0f;
    public final SparseArray mAnimationLoaderMap = new SparseArray();

    public class SurfaceValueAnimator extends ValueAnimator {
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
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.mLeashName, "}");
        }
    }

    public MultiTaskingTransitionProvider(TransitionAnimation transitionAnimation, DisplayController displayController, TransactionPool transactionPool, ShellExecutor shellExecutor, ShellExecutor shellExecutor2) {
        this.mState = new MultiTaskingTransitionState(transitionAnimation, displayController);
        this.mDisplayController = displayController;
        this.mTransitionAnimation = transitionAnimation;
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            registerAnimationLoader(1);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            registerAnimationLoader(3);
        }
        registerAnimationLoader(2);
        registerAnimationLoader(5);
    }

    public static void applyTransformation(long j, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect) {
        if (!surfaceControl.isValid()) {
            Log.w("MultiTaskingTransitionProvider", "applyTransformation: invalid leash=" + surfaceControl);
            return;
        }
        animation.getTransformation(j, transformation);
        transformation.getMatrix().postTranslate(point.x, point.y);
        transaction.setMatrix(surfaceControl, transformation.getMatrix(), fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        Rect rect2 = new Rect(rect);
        Insets insets = transformation.getInsets();
        Insets insets2 = Insets.NONE;
        Insets insetsMin = Insets.min(insets, insets2);
        if (!insetsMin.equals(insets2) && !rect2.isEmpty()) {
            rect2.inset(insetsMin);
            transaction.setCrop(surfaceControl, rect2);
        }
        if (animation.hasRoundedCorners() && f > 0.0f) {
            transaction.setCrop(surfaceControl, rect2);
            transaction.setCornerRadius(surfaceControl, f);
        }
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public static boolean buildForceHideAnimationIfNeeded(final String str, TransitionInfo.Change change, MultiTaskingTransitions multiTaskingTransitions) {
        int forceHidingTransit = change.getForceHidingTransit();
        if (forceHidingTransit == 0) {
            return false;
        }
        if ((CoreRune.MW_CAPTION_FREEFORM_STASH && change.getFreeformStashScale() != 1.0f) || change.isForceHidingWithoutAnimation()) {
            return true;
        }
        SurfaceControl leash = change.getLeash();
        final String str2 = "leash=" + leash + ", " + MultiWindowManager.forceHidingTransitToString(forceHidingTransit);
        int i = forceHidingTransit == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit_delay;
        Slog.d(str, "buildForceHideAnimationIfNeeded: " + str2);
        Rect endAbsBounds = change.getEndAbsBounds();
        MultiTaskingTransitionProvider multiTaskingTransitionProvider = (MultiTaskingTransitionProvider) multiTaskingTransitions;
        Animation animationLoadAnimationFromResources = multiTaskingTransitionProvider.loadAnimationFromResources(i, endAbsBounds);
        animationLoadAnimationFromResources.setInterpolator(InterpolatorUtils.SINE_OUT_60);
        Point point = new Point(endAbsBounds.left, endAbsBounds.top);
        Rect rect = new Rect(endAbsBounds);
        rect.offsetTo(0, 0);
        multiTaskingTransitionProvider.buildSurfaceAnimator(sForceHidingAnimators, animationLoadAnimationFromResources, leash, new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                String str3 = str;
                String str4 = str2;
                ArrayList arrayList = MultiTaskingTransitionProvider.sForceHidingAnimators;
                StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onForceHideAnimationFinished: ", str4, ", num_remains=");
                sbM.append(MultiTaskingTransitionProvider.sForceHidingAnimators.size());
                Slog.d(str3, sbM.toString());
            }
        }, point, 0.0f, rect, true);
        return true;
    }

    public static void cancelForceHideAnimationsIfNeeded(String str, ShellExecutor shellExecutor) {
        ArrayList arrayList = sForceHidingAnimators;
        if (arrayList.isEmpty()) {
            return;
        }
        Slog.d(str, "cancelForceHideAnimationsIfNeeded: animators=" + new ArrayList(arrayList) + ", Callers=" + Debug.getCallers(5));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            shellExecutor.execute(new MultiTaskingTransitionProvider$$ExternalSyntheticLambda0((Animator) obj));
        }
    }

    public static boolean isMovingBackFromRemovingDesktopDisplay(TransitionInfo.Change change) {
        if (!TransitionUtil.isClosingMode(change.getMode()) || change.getTaskInfo() == null || change.getStartDisplayId() == change.getEndDisplayId()) {
            return false;
        }
        int startDisplayId = change.getStartDisplayId();
        DesktopStateImpl.Companion.getClass();
        return DesktopStateImpl.Companion.inDesktopWindowing(startDisplayId);
    }

    public final SurfaceValueAnimator buildSurfaceAnimator(final ArrayList arrayList, final Animation animation, final SurfaceControl surfaceControl, final Runnable runnable, final Point point, final float f, final Rect rect, boolean z) {
        final SurfaceControl.Transaction transactionAcquire = this.mTransactionPool.acquire();
        final SurfaceValueAnimator surfaceValueAnimator = new SurfaceValueAnimator(surfaceControl, 0.0f, 1.0f);
        final Transformation transformation = new Transformation();
        final float[] fArr = new float[9];
        surfaceValueAnimator.overrideDurationScale(1.0f);
        surfaceValueAnimator.setDuration(animation.computeDurationHint());
        final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = surfaceValueAnimator;
                SurfaceControl.Transaction transaction = transactionAcquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                Animation animation2 = animation;
                Transformation transformation2 = transformation;
                float[] fArr2 = fArr;
                Point point2 = point;
                float f2 = f;
                Rect rect2 = rect;
                ArrayList arrayList2 = MultiTaskingTransitionProvider.sForceHidingAnimators;
                MultiTaskingTransitionProvider.applyTransformation(Math.min(surfaceValueAnimator2.getDuration(), surfaceValueAnimator2.getCurrentPlayTime()), transaction, surfaceControl2, animation2, transformation2, fArr2, point2, f2, rect2);
            }
        };
        surfaceValueAnimator.addUpdateListener(animatorUpdateListener);
        final Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.f$0;
                final MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = surfaceValueAnimator;
                SurfaceControl.Transaction transaction = transactionAcquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                Animation animation2 = animation;
                Transformation transformation2 = transformation;
                float[] fArr2 = fArr;
                Point point2 = point;
                float f2 = f;
                Rect rect2 = rect;
                final ArrayList arrayList2 = arrayList;
                final Runnable runnable3 = runnable;
                ArrayList arrayList3 = MultiTaskingTransitionProvider.sForceHidingAnimators;
                multiTaskingTransitionProvider.getClass();
                MultiTaskingTransitionProvider.applyTransformation(surfaceValueAnimator2.getDuration(), transaction, surfaceControl2, animation2, transformation2, fArr2, point2, f2, rect2);
                multiTaskingTransitionProvider.mTransactionPool.release(transaction);
                multiTaskingTransitionProvider.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.MultiTaskingTransitionProvider$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList4 = arrayList2;
                        MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator3 = surfaceValueAnimator2;
                        Runnable runnable4 = runnable3;
                        ArrayList arrayList5 = MultiTaskingTransitionProvider.sForceHidingAnimators;
                        arrayList4.remove(surfaceValueAnimator3);
                        Log.d("MultiTaskingTransitionProvider", "Remove " + surfaceValueAnimator3 + ", num_remains=" + arrayList4.size());
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
            this.mAnimExecutor.execute(new MultiTaskingTransitionProvider$$ExternalSyntheticLambda0(surfaceValueAnimator));
        }
        Log.d("MultiTaskingTransitionProvider", "buildSurfaceAnimator: create " + surfaceValueAnimator + ", shouldStart=" + z);
        return surfaceValueAnimator;
    }

    public SparseArray<AnimationLoader> getAnimationLoaderMap() {
        return this.mAnimationLoaderMap;
    }

    public MultiTaskingTransitionState getState() {
        return this.mState;
    }

    public final Animation loadAnimationFromResources(int i, Rect rect) {
        Animation animationLoadAnimationRes = this.mTransitionAnimation.loadAnimationRes("android", i);
        if (animationLoadAnimationRes == null) {
            Log.d("MultiTaskingTransitionProvider", "loadAnimationFromResources: failed, Callers=" + Debug.getCallers(5));
            animationLoadAnimationRes = new AlphaAnimation(1.0f, 1.0f);
            animationLoadAnimationRes.setDuration(336L);
        }
        if (!animationLoadAnimationRes.isInitialized()) {
            int iWidth = rect.width();
            int iHeight = rect.height();
            animationLoadAnimationRes.initialize(iWidth, iHeight, iWidth, iHeight);
        }
        animationLoadAnimationRes.restrictDuration(10000L);
        animationLoadAnimationRes.scaleCurrentDuration(this.mDurationScale);
        return animationLoadAnimationRes;
    }

    public void registerAnimationLoader(int i) {
        AnimationLoader popOverAnimationLoader;
        boolean z = CoreRune.MW_SPLIT_SHELL_TRANSITION;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (z && i == 1) {
            popOverAnimationLoader = new SplitAnimationLoader(multiTaskingTransitionState);
        } else if (i == 3) {
            popOverAnimationLoader = new FreeformAnimationLoader(multiTaskingTransitionState);
        } else if (i == 2) {
            popOverAnimationLoader = new DesktopAnimationLoader(multiTaskingTransitionState);
        } else if (i == 4) {
            popOverAnimationLoader = new SplitActivityAnimationLoader(multiTaskingTransitionState);
        } else {
            if (i != 5) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid animation type="));
            }
            popOverAnimationLoader = new PopOverAnimationLoader(multiTaskingTransitionState);
        }
        this.mAnimationLoaderMap.put(i, popOverAnimationLoader);
    }
}
