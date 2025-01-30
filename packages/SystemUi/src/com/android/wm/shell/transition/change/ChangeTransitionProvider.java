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
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.TransitionUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ChangeTransitionProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ChangeTransitionSpec mChangeTransitionSpec;
    public final DisplayController mDisplayController;
    public float mDurationScale = 1.0f;
    public final ShellExecutor mMainExecutor;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;

    static {
        boolean z = CoreRune.SAFE_DEBUG;
    }

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
        buildSurfaceAnimator(arrayList, createChangeTransitionSpecIfNeeded.mSnapshotAnimation, change.getSnapshot(), runnable);
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
                ((HandlerExecutor) changeTransitionProvider.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.change.ChangeTransitionProvider$$ExternalSyntheticLambda2
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x008a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x015c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ChangeTransitionSpec createChangeTransitionSpecIfNeeded(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        boolean z;
        ChangeTransitionSpec dismissChangeTransitionSpec;
        float f;
        int changeTransitMode = change.getChangeTransitMode();
        boolean z2 = false;
        DisplayController displayController = this.mDisplayController;
        if (changeTransitMode != 0) {
            if (change.getSnapshot() == null || !change.getSnapshot().isValid()) {
                Slog.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, snapshot is null, " + change);
            } else if (change.getChangeLeash() == null || !change.getChangeLeash().isValid()) {
                Slog.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, changeLeash is null, " + change);
            } else {
                int endDisplayId = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
                if (displayController.getDisplayLayout(endDisplayId) != null) {
                    z = true;
                    if (z) {
                        return null;
                    }
                    int changeTransitMode2 = change.getChangeTransitMode();
                    int endDisplayId2 = change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
                    DisplayLayout displayLayout = displayController.getDisplayLayout(endDisplayId2);
                    if (changeTransitMode2 == 1) {
                        dismissChangeTransitionSpec = new StandardChangeTransitionSpec();
                    } else if (changeTransitMode2 == 2 || changeTransitMode2 == 6) {
                        dismissChangeTransitionSpec = new DismissChangeTransitionSpec();
                    } else if (changeTransitMode2 == 3) {
                        dismissChangeTransitionSpec = new CaptionChangeTransitionSpec();
                    } else if (changeTransitMode2 == 4) {
                        dismissChangeTransitionSpec = new NaturalSwitchingChangeTransitionSpec(change.getTaskInfo());
                    } else if (changeTransitMode2 == 5) {
                        dismissChangeTransitionSpec = new PopOverChangeTransitionSpec();
                    } else {
                        if (!CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE || changeTransitMode2 != 7) {
                            Slog.w("ChangeTransitionProvider", "createChangeTransitionSpec: failed, for " + change);
                            return null;
                        }
                        dismissChangeTransitionSpec = new NewDexCaptionChangeTransitionSpec();
                    }
                    if (!isDisplayRotating(transitionInfo)) {
                        Iterator it = transitionInfo.getChanges().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            TransitionInfo.Change change2 = (TransitionInfo.Change) it.next();
                            if (change2.hasFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) && change2.getConfiguration().isDexMode()) {
                                z2 = true;
                                break;
                            }
                        }
                        if (!z2) {
                            f = this.mDurationScale;
                            this.mChangeTransitionSpec = dismissChangeTransitionSpec;
                            Context displayContext = displayController.getDisplayContext(endDisplayId2);
                            dismissChangeTransitionSpec.mChange = change;
                            Rect startAbsBounds = change.getStartAbsBounds();
                            Rect rect = dismissChangeTransitionSpec.mStartBounds;
                            rect.set(startAbsBounds);
                            dismissChangeTransitionSpec.mStartOutsets.set(change.getChangeStartOutsets());
                            Rect endAbsBounds = change.getEndAbsBounds();
                            Rect rect2 = dismissChangeTransitionSpec.mEndBounds;
                            rect2.set(endAbsBounds);
                            dismissChangeTransitionSpec.mEndOutsets.set(change.getChangeEndOutsets());
                            if (dismissChangeTransitionSpec.isRootOffsetNeeded()) {
                                TransitionInfo.Root root = transitionInfo.getRoot(TransitionUtil.rootIndexFor(change, transitionInfo));
                                Point point = dismissChangeTransitionSpec.mRootOffsets;
                                point.set(root.getOffset());
                                rect.offset(-point.x, -point.y);
                                rect2.offset(-point.x, -point.y);
                            }
                            dismissChangeTransitionSpec.mDisplayLayout = displayLayout;
                            dismissChangeTransitionSpec.mDurationScale = f;
                            dismissChangeTransitionSpec.reduceDurationScaleIfNeeded(transitionInfo);
                            dismissChangeTransitionSpec.mContext = displayContext;
                            dismissChangeTransitionSpec.mBoundsChangeAnimation = dismissChangeTransitionSpec.createBoundsChangeAnimation();
                            Animation createSnapshotAnimation = dismissChangeTransitionSpec.createSnapshotAnimation();
                            dismissChangeTransitionSpec.mSnapshotAnimation = createSnapshotAnimation;
                            if (dismissChangeTransitionSpec.mBoundsChangeAnimation != null || createSnapshotAnimation == null) {
                                throw new IllegalStateException("Invalid ChangeTransitionSpec!");
                            }
                            Log.d("ChangeTransitionProvider", "createChangeTransitionSpec: " + MultiWindowManager.changeTransitModeToString(changeTransitMode2) + ", " + dismissChangeTransitionSpec + ", durationScale=" + f);
                            return this.mChangeTransitionSpec;
                        }
                    }
                    f = 0.0f;
                    this.mChangeTransitionSpec = dismissChangeTransitionSpec;
                    Context displayContext2 = displayController.getDisplayContext(endDisplayId2);
                    dismissChangeTransitionSpec.mChange = change;
                    Rect startAbsBounds2 = change.getStartAbsBounds();
                    Rect rect3 = dismissChangeTransitionSpec.mStartBounds;
                    rect3.set(startAbsBounds2);
                    dismissChangeTransitionSpec.mStartOutsets.set(change.getChangeStartOutsets());
                    Rect endAbsBounds2 = change.getEndAbsBounds();
                    Rect rect22 = dismissChangeTransitionSpec.mEndBounds;
                    rect22.set(endAbsBounds2);
                    dismissChangeTransitionSpec.mEndOutsets.set(change.getChangeEndOutsets());
                    if (dismissChangeTransitionSpec.isRootOffsetNeeded()) {
                    }
                    dismissChangeTransitionSpec.mDisplayLayout = displayLayout;
                    dismissChangeTransitionSpec.mDurationScale = f;
                    dismissChangeTransitionSpec.reduceDurationScaleIfNeeded(transitionInfo);
                    dismissChangeTransitionSpec.mContext = displayContext2;
                    dismissChangeTransitionSpec.mBoundsChangeAnimation = dismissChangeTransitionSpec.createBoundsChangeAnimation();
                    Animation createSnapshotAnimation2 = dismissChangeTransitionSpec.createSnapshotAnimation();
                    dismissChangeTransitionSpec.mSnapshotAnimation = createSnapshotAnimation2;
                    if (dismissChangeTransitionSpec.mBoundsChangeAnimation != null) {
                    }
                    throw new IllegalStateException("Invalid ChangeTransitionSpec!");
                }
                Log.w("ChangeTransitionProvider", "canCreateChangeTransitionSpec: failed, cannot find display #" + endDisplayId + ", change=" + change);
            }
        }
        z = false;
        if (z) {
        }
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
}
