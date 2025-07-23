package com.android.wm.shell.transition.change;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Slog;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ChangeTransitionProvider {
    public ChangeTransitionSpec mChangeTransitionSpec;
    public final DisplayController mDisplayController;
    public float mDurationScale = 1.0f;
    public final ShellExecutor mMainExecutor;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;

    public ChangeTransitionProvider(Transitions transitions, DisplayController displayController, TransactionPool transactionPool, ShellExecutor shellExecutor, ShellExecutor shellExecutor2) {
        this.mTransitions = transitions;
        this.mDisplayController = displayController;
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
    }

    public static void applyTransformation(long j, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, float[] fArr2, Rect rect) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            Log.d("ChangeTransitionProvider", "ChangeTransitionProvider@applyTransformation invalid sc=" + surfaceControl);
            return;
        }
        animation.getTransformation(j, transformation);
        Matrix matrix = transformation.getMatrix();
        transaction.setMatrix(surfaceControl, matrix, fArr);
        transaction.setAlpha(surfaceControl, transformation.getAlpha());
        if (transformation.hasClipRect()) {
            fArr2[2] = 0.0f;
            fArr2[1] = 0.0f;
            fArr2[3] = 1.0f;
            fArr2[0] = 1.0f;
            matrix.mapVectors(fArr2);
            fArr2[0] = 1.0f / fArr2[0];
            fArr2[3] = 1.0f / fArr2[3];
            Rect clipRect = transformation.getClipRect();
            float f = clipRect.left;
            float f2 = fArr2[0];
            rect.left = (int) ((f * f2) + 0.5f);
            rect.right = (int) ((clipRect.right * f2) + 0.5f);
            float f3 = clipRect.top;
            float f4 = fArr2[3];
            rect.top = (int) ((f3 * f4) + 0.5f);
            rect.bottom = (int) ((clipRect.bottom * f4) + 0.5f);
            transaction.setCrop(surfaceControl, rect);
            if (animation.hasRoundedCorners()) {
                transaction.setCornerRadius(surfaceControl, animation.getRoundedCornerRadius());
            }
        }
        transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        transaction.apply();
    }

    public static boolean isDisplayRotating(TransitionInfo transitionInfo) {
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if (change.hasFlags(32) && change.getMode() == 6 && change.getStartRotation() != change.getEndRotation()) {
                return true;
            }
        }
        return false;
    }

    public final boolean buildChangeTransitionAnimators(ArrayList arrayList, TransitionInfo.Change change, Runnable runnable, SurfaceControl.Transaction transaction, TransitionInfo transitionInfo) {
        ChangeTransitionSpec createChangeTransitionSpecIfNeeded = createChangeTransitionSpecIfNeeded(change, transitionInfo);
        if (createChangeTransitionSpecIfNeeded == null) {
            return false;
        }
        Log.d("ChangeTransitionProvider", "buildChangeTransitionAnimators");
        buildSurfaceAnimator(arrayList, createChangeTransitionSpecIfNeeded.mBoundsChangeAnimation, change.getLeash(), runnable);
        Animation animation = createChangeTransitionSpecIfNeeded.mSnapshotAnimation;
        SurfaceControl snapshot = change.getSnapshot();
        Objects.requireNonNull(snapshot);
        buildSurfaceAnimator(arrayList, animation, snapshot, runnable);
        onChangeTransitionStarting(change, transaction);
        return true;
    }

    public final void buildSurfaceAnimator(final ArrayList arrayList, final Animation animation, final SurfaceControl surfaceControl, final Runnable runnable) {
        final SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        final MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator = new MultiTaskingTransitionProvider.SurfaceValueAnimator(surfaceControl, 0.0f, 1.0f);
        final Transformation transformation = new Transformation();
        final float[] fArr = new float[9];
        final float[] fArr2 = new float[4];
        final Rect rect = new Rect();
        surfaceValueAnimator.overrideDurationScale(1.0f);
        surfaceValueAnimator.setDuration(animation.computeDurationHint());
        final ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.transition.change.ChangeTransitionProvider$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = MultiTaskingTransitionProvider.SurfaceValueAnimator.this;
                ChangeTransitionProvider.applyTransformation(Math.min(surfaceValueAnimator2.getDuration(), surfaceValueAnimator2.getCurrentPlayTime()), acquire, surfaceControl, animation, transformation, fArr, fArr2, rect);
            }
        };
        surfaceValueAnimator.addUpdateListener(animatorUpdateListener);
        final Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.transition.change.ChangeTransitionProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ChangeTransitionProvider changeTransitionProvider = ChangeTransitionProvider.this;
                final MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator2 = surfaceValueAnimator;
                SurfaceControl.Transaction transaction = acquire;
                SurfaceControl surfaceControl2 = surfaceControl;
                Animation animation2 = animation;
                Transformation transformation2 = transformation;
                float[] fArr3 = fArr;
                float[] fArr4 = fArr2;
                Rect rect2 = rect;
                final ArrayList arrayList2 = arrayList;
                final Runnable runnable3 = runnable;
                changeTransitionProvider.getClass();
                ChangeTransitionProvider.applyTransformation(surfaceValueAnimator2.getDuration(), transaction, surfaceControl2, animation2, transformation2, fArr3, fArr4, rect2);
                changeTransitionProvider.mTransactionPool.release(transaction);
                changeTransitionProvider.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.change.ChangeTransitionProvider$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList3 = arrayList2;
                        MultiTaskingTransitionProvider.SurfaceValueAnimator surfaceValueAnimator3 = surfaceValueAnimator2;
                        Runnable runnable4 = runnable3;
                        arrayList3.remove(surfaceValueAnimator3);
                        Log.d("ChangeTransitionProvider", "Remove " + surfaceValueAnimator3 + ", num_remains=" + arrayList3.size());
                        runnable4.run();
                    }
                });
            }
        };
        surfaceValueAnimator.addListener(new AnimatorListenerAdapter(this) { // from class: com.android.wm.shell.transition.change.ChangeTransitionProvider.1
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
        Log.d("ChangeTransitionProvider", "buildSurfaceAnimator: create " + surfaceValueAnimator);
    }

    public ChangeTransitionSpec createChangeTransitionSpecIfNeeded(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        ChangeTransitionSpec dismissChangeTransitionSpec;
        if (change.getChangeTransitMode() == 0) {
            return null;
        }
        if (change.getSnapshot() == null || !change.getSnapshot().isValid()) {
            Slog.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, snapshot is null, " + change);
            return null;
        }
        if (change.getChangeLeash() == null || !change.getChangeLeash().isValid()) {
            Slog.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, changeLeash is null, " + change);
            return null;
        }
        int endDisplayId = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
        DisplayController displayController = this.mDisplayController;
        if (displayController.getDisplayLayout(endDisplayId) == null) {
            Log.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, cannot find display #" + endDisplayId + ", change=" + change);
            return null;
        }
        int changeTransitMode = change.getChangeTransitMode();
        int endDisplayId2 = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
        DisplayLayout displayLayout = displayController.getDisplayLayout(endDisplayId2);
        if (changeTransitMode == 1) {
            dismissChangeTransitionSpec = new StandardChangeTransitionSpec();
        } else if (changeTransitMode == 2 || changeTransitMode == 6) {
            dismissChangeTransitionSpec = new DismissChangeTransitionSpec();
        } else if (changeTransitMode == 4) {
            dismissChangeTransitionSpec = new NaturalSwitchingChangeTransitionSpec(change.getTaskInfo());
        } else {
            if (changeTransitMode != 5) {
                Slog.w("ChangeTransitionProvider", "createChangeTransitionSpec: failed, for " + change);
                return null;
            }
            dismissChangeTransitionSpec = new PopOverChangeTransitionSpec();
        }
        float f = isDisplayRotating(transitionInfo) ? 0.0f : this.mDurationScale;
        this.mChangeTransitionSpec = dismissChangeTransitionSpec;
        Objects.requireNonNull(displayLayout);
        Context displayContext = displayController.getDisplayContext(endDisplayId2);
        Objects.requireNonNull(displayContext);
        dismissChangeTransitionSpec.mChange = change;
        dismissChangeTransitionSpec.mTransitionInfo = transitionInfo;
        dismissChangeTransitionSpec.mStartBounds.set(change.getStartAbsBounds());
        dismissChangeTransitionSpec.mStartOutsets.set(change.getChangeStartOutsets());
        dismissChangeTransitionSpec.mEndBounds.set(change.getEndAbsBounds());
        dismissChangeTransitionSpec.mEndOutsets.set(change.getChangeEndOutsets());
        if (dismissChangeTransitionSpec.isRootOffsetNeeded()) {
            dismissChangeTransitionSpec.mRootOffsets.set(transitionInfo.getRoot(TransitionUtil.rootIndexFor(change, transitionInfo)).getOffset());
            Rect rect = dismissChangeTransitionSpec.mStartBounds;
            Point point = dismissChangeTransitionSpec.mRootOffsets;
            rect.offset(-point.x, -point.y);
            Rect rect2 = dismissChangeTransitionSpec.mEndBounds;
            Point point2 = dismissChangeTransitionSpec.mRootOffsets;
            rect2.offset(-point2.x, -point2.y);
        }
        dismissChangeTransitionSpec.mDisplayLayout = displayLayout;
        dismissChangeTransitionSpec.mDurationScale = f;
        dismissChangeTransitionSpec.reduceDurationScaleIfNeeded(transitionInfo);
        dismissChangeTransitionSpec.mContext = displayContext;
        dismissChangeTransitionSpec.mBoundsChangeAnimation = dismissChangeTransitionSpec.createBoundsChangeAnimation();
        dismissChangeTransitionSpec.mSnapshotAnimation = dismissChangeTransitionSpec.createSnapshotAnimation();
        if (dismissChangeTransitionSpec.mBoundsChangeAnimation == null) {
            throw new IllegalStateException("Invalid ChangeTransitionSpec!");
        }
        StringBuilder sb = new StringBuilder("createChangeTransitionSpec: ");
        sb.append(MultiWindowManager.changeTransitModeToString(changeTransitMode));
        sb.append(", ");
        sb.append(dismissChangeTransitionSpec);
        sb.append(", durationScale=");
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f, "ChangeTransitionProvider", sb);
        return this.mChangeTransitionSpec;
    }

    public Transitions getTransitions() {
        return this.mTransitions;
    }

    public void onChangeTransitionStarting(TransitionInfo.Change change, SurfaceControl.Transaction transaction) {
        ChangeTransitionSpec changeTransitionSpec = this.mChangeTransitionSpec;
        if (changeTransitionSpec != null) {
            changeTransitionSpec.setupChangeTransitionHierarchy(change, transaction);
            this.mChangeTransitionSpec = null;
        }
    }

    public final void startChangeTransition(WindowContainerTransaction windowContainerTransaction) {
        StringBuilder sb = new StringBuilder("startChangeTransition: handler=null, wct=");
        sb.append(windowContainerTransaction);
        sb.append(", Caller=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(1, "ChangeTransitionProvider", sb);
        this.mTransitions.startTransition(6, windowContainerTransaction, null);
    }
}
