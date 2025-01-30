package com.android.p038wm.shell.unfold.animation;

import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p038wm.shell.common.DisplayInsetsController;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3;
import com.android.p038wm.shell.sysui.ConfigurationChangeListener;
import com.android.p038wm.shell.sysui.ShellController;
import com.android.p038wm.shell.unfold.UnfoldBackgroundController;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitTaskUnfoldAnimator implements UnfoldTaskAnimator, DisplayInsetsController.OnInsetsChangedListener, SplitScreen.SplitScreenListener, ConfigurationChangeListener {
    public final Context mContext;
    public final DisplayInsetsController mDisplayInsetsController;
    public final Executor mExecutor;
    public InsetsSource mExpandedTaskbarInsetsSource;
    public final ShellController mShellController;
    public final Lazy mSplitScreenController;
    public final Animation mUnfoldAnimation;
    public final UnfoldBackgroundController mUnfoldBackgroundController;
    public float mWindowCornerRadiusPx;
    public static final TypeEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public static final float[] FLOAT_9 = new float[9];
    public final SparseArray mAnimationContextByTaskId = new SparseArray();
    public final Rect mMainStageBounds = new Rect();
    public final Rect mSideStageBounds = new Rect();
    public final Rect mRootStageBounds = new Rect();
    public int mMainStagePosition = -1;
    public int mSideStagePosition = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationContext {
        public final Rect mCurrentCropRect;
        public final Rect mEndCropRect;
        public final SurfaceControl mLeash;
        public int mStageType;
        public final Rect mStartCropRect;
        public final Transformation mTransformation;

        public /* synthetic */ AnimationContext(SplitTaskUnfoldAnimator splitTaskUnfoldAnimator, SurfaceControl surfaceControl, int i) {
            this(surfaceControl);
        }

        public final void update() {
            int i;
            Insets of;
            int i2;
            int i3 = this.mStageType;
            SplitTaskUnfoldAnimator splitTaskUnfoldAnimator = SplitTaskUnfoldAnimator.this;
            Rect rect = i3 == 0 ? splitTaskUnfoldAnimator.mMainStageBounds : splitTaskUnfoldAnimator.mSideStageBounds;
            Rect rect2 = this.mStartCropRect;
            rect2.set(rect);
            InsetsSource insetsSource = splitTaskUnfoldAnimator.mExpandedTaskbarInsetsSource;
            int i4 = 0;
            boolean z = insetsSource != null;
            if (z) {
                rect2.inset(insetsSource.calculateVisibleInsets(rect2));
            }
            rect2.offsetTo(0, 0);
            this.mEndCropRect.set(rect2);
            int max = (int) (Math.max(r0.width(), r0.height()) * 0.05f);
            if (splitTaskUnfoldAnimator.mRootStageBounds.width() > splitTaskUnfoldAnimator.mRootStageBounds.height()) {
                int i5 = z ? 0 : max;
                if ((this.mStageType == 0 ? splitTaskUnfoldAnimator.mMainStagePosition : splitTaskUnfoldAnimator.mSideStagePosition) == 0) {
                    i2 = 0;
                    i4 = max;
                } else {
                    i2 = max;
                }
                of = Insets.of(i4, max, i2, i5);
            } else {
                if ((this.mStageType == 0 ? splitTaskUnfoldAnimator.mMainStagePosition : splitTaskUnfoldAnimator.mSideStagePosition) == 0) {
                    i = 0;
                    i4 = max;
                } else {
                    i = z ? 0 : max;
                }
                of = Insets.of(max, i4, max, i);
            }
            rect2.inset(of);
        }

        private AnimationContext(SurfaceControl surfaceControl) {
            this.mStartCropRect = new Rect();
            this.mEndCropRect = new Rect();
            this.mCurrentCropRect = new Rect();
            this.mStageType = -1;
            this.mTransformation = new Transformation();
            this.mLeash = surfaceControl;
            update();
        }
    }

    public SplitTaskUnfoldAnimator(Context context, Executor executor, Lazy lazy, ShellController shellController, UnfoldBackgroundController unfoldBackgroundController, DisplayInsetsController displayInsetsController) {
        this.mDisplayInsetsController = displayInsetsController;
        this.mExecutor = executor;
        this.mContext = context;
        this.mShellController = shellController;
        this.mUnfoldBackgroundController = unfoldBackgroundController;
        this.mSplitScreenController = lazy;
        this.mWindowCornerRadiusPx = ScreenDecorationsUtils.getWindowCornerRadius(context);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            this.mUnfoldAnimation = alphaAnimation;
            alphaAnimation.setDuration(200L);
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void applyAnimationProgress(float f, SurfaceControl.Transaction transaction) {
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            AnimationContext animationContext = (AnimationContext) sparseArray.valueAt(size);
            if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
                this.mUnfoldAnimation.getTransformationAt(f, animationContext.mTransformation);
                Transformation transformation = animationContext.mTransformation;
                Matrix matrix = transformation.getMatrix();
                SurfaceControl surfaceControl = animationContext.mLeash;
                transaction.setMatrix(surfaceControl, matrix, FLOAT_9).setAlpha(surfaceControl, transformation.getAlpha());
            } else if (animationContext.mStageType != -1) {
                Rect rect = (Rect) RECT_EVALUATOR.evaluate(f, animationContext.mStartCropRect, animationContext.mEndCropRect);
                Rect rect2 = animationContext.mCurrentCropRect;
                rect2.set(rect);
                SurfaceControl surfaceControl2 = animationContext.mLeash;
                transaction.setWindowCrop(surfaceControl2, rect2).setCornerRadius(surfaceControl2, this.mWindowCornerRadiusPx);
            }
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void clearTasks() {
        this.mAnimationContextByTaskId.clear();
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final boolean hasActiveTasks() {
        return this.mAnimationContextByTaskId.size() > 0;
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void init() {
        this.mDisplayInsetsController.addInsetsChangedListener(0, this);
        this.mShellController.addConfigurationChangeListener(this);
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsChanged(InsetsState insetsState) {
        InsetsSource insetsSource;
        int sourceSize = insetsState.sourceSize();
        while (true) {
            sourceSize--;
            if (sourceSize < 0) {
                insetsSource = null;
                break;
            }
            insetsSource = insetsState.sourceAt(sourceSize);
            if (insetsSource.getType() == WindowInsets.Type.navigationBars() && insetsSource.insetsRoundedCornerFrame()) {
                break;
            }
        }
        this.mExpandedTaskbarInsetsSource = insetsSource;
        updateContexts();
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final boolean isApplicableTask(TaskInfo taskInfo) {
        if (!CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            return taskInfo.hasParentTask() && taskInfo.isRunning && taskInfo.realActivity != null && taskInfo.getWindowingMode() == 6;
        }
        SplitScreenController splitScreenController = (SplitScreenController) ((Optional) this.mSplitScreenController.get()).get();
        return splitScreenController != null && splitScreenController.isTaskRoot(taskInfo.taskId);
    }

    @Override // com.android.p038wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        Trace.beginSection("SplitTaskUnfoldAnimator#onConfigurationChanged");
        this.mWindowCornerRadiusPx = ScreenDecorationsUtils.getWindowCornerRadius(this.mContext);
        Trace.endSection();
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
    public final void onSplitBoundsChanged(Rect rect, Rect rect2, Rect rect3) {
        this.mRootStageBounds.set(rect);
        this.mMainStageBounds.set(rect2);
        this.mSideStageBounds.set(rect3);
        updateContexts();
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onSplitScreenTransitionMerged(SurfaceControl.Transaction transaction) {
        Lazy lazy = this.mSplitScreenController;
        if (((SplitScreenController) ((Optional) lazy.get()).get()).isSplitScreenVisible()) {
            Log.d("SplitTaskUnfoldAnimator", "onSplitScreenTransitionMerged: t=" + transaction + ", Callers=" + Debug.getCallers(10));
            ((SplitScreenController) ((Optional) lazy.get()).get()).updateSplitScreenSurfaces(transaction);
        }
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
    public final void onSplitVisibilityChanged(boolean z) {
        this.mUnfoldBackgroundController.mSplitScreenVisible = z;
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
    public final void onStagePositionChanged(int i, int i2) {
        if (i == 0) {
            this.mMainStagePosition = i2;
        } else {
            this.mSideStagePosition = i2;
        }
        updateContexts();
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskAppeared(TaskInfo taskInfo, SurfaceControl surfaceControl) {
        this.mAnimationContextByTaskId.put(taskInfo.taskId, new AnimationContext(this, surfaceControl, 0));
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener
    public final void onTaskStageChanged(int i, int i2, boolean z) {
        AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.get(i);
        if (animationContext != null) {
            animationContext.mStageType = i2;
            animationContext.update();
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskVanished(TaskInfo taskInfo) {
        this.mAnimationContextByTaskId.remove(taskInfo.taskId);
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void prepareFinishTransaction(SurfaceControl.Transaction transaction) {
        UnfoldBackgroundController unfoldBackgroundController = this.mUnfoldBackgroundController;
        SurfaceControl surfaceControl = unfoldBackgroundController.mBackgroundLayer;
        if (surfaceControl == null) {
            return;
        }
        if (surfaceControl.isValid()) {
            transaction.remove(unfoldBackgroundController.mBackgroundLayer);
        }
        unfoldBackgroundController.mBackgroundLayer = null;
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void prepareStartTransaction(SurfaceControl.Transaction transaction) {
        this.mUnfoldBackgroundController.ensureBackground(transaction);
        ((SplitScreenController) ((Optional) this.mSplitScreenController.get()).get()).updateSplitScreenSurfaces(transaction);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            SparseArray sparseArray = this.mAnimationContextByTaskId;
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                AnimationContext animationContext = (AnimationContext) sparseArray.valueAt(size);
                this.mUnfoldAnimation.getTransformationAt(0.0f, animationContext.mTransformation);
                Transformation transformation = animationContext.mTransformation;
                Matrix matrix = transformation.getMatrix();
                float[] fArr = FLOAT_9;
                SurfaceControl surfaceControl = animationContext.mLeash;
                transaction.setMatrix(surfaceControl, matrix, fArr).setAlpha(surfaceControl, transformation.getAlpha());
            }
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetAllSurfaces(SurfaceControl.Transaction transaction) {
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        int size = sparseArray.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            AnimationContext animationContext = (AnimationContext) sparseArray.valueAt(size);
            transaction.setWindowCrop(animationContext.mLeash, null).setCornerRadius(animationContext.mLeash, 0.0f);
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetSurface(TaskInfo taskInfo, SurfaceControl.Transaction transaction) {
        AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.get(taskInfo.taskId);
        if (animationContext != null) {
            SurfaceControl surfaceControl = animationContext.mLeash;
            transaction.setWindowCrop(surfaceControl, null).setCornerRadius(surfaceControl, 0.0f);
        }
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void start() {
        final SplitScreenController.SplitScreenImpl splitScreenImpl = ((SplitScreenController) ((Optional) this.mSplitScreenController.get()).get()).mImpl;
        if (splitScreenImpl.mExecutors.containsKey(this)) {
            return;
        }
        ShellExecutor shellExecutor = SplitScreenController.this.mMainExecutor;
        final Executor executor = this.mExecutor;
        ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = SplitScreenController.SplitScreenImpl.this;
                SplitScreen.SplitScreenListener splitScreenListener = this;
                Executor executor2 = executor;
                ArrayMap arrayMap = splitScreenImpl2.mExecutors;
                if (arrayMap.size() == 0) {
                    StageCoordinator stageCoordinator = SplitScreenController.this.mStageCoordinator;
                    ArrayList arrayList = (ArrayList) stageCoordinator.mListeners;
                    SplitScreenController.SplitScreenImpl.C41111 c41111 = splitScreenImpl2.mListener;
                    if (!arrayList.contains(c41111)) {
                        arrayList.add(c41111);
                        stageCoordinator.sendStatusToListener(c41111);
                    }
                }
                arrayMap.put(splitScreenListener, executor2);
            }
        });
        executor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3(splitScreenImpl, this, 1));
    }

    @Override // com.android.p038wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void stop() {
        SplitScreenController.SplitScreenImpl splitScreenImpl = ((SplitScreenController) ((Optional) this.mSplitScreenController.get()).get()).mImpl;
        ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda3(splitScreenImpl, this, 0));
    }

    public final void updateContexts() {
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ((AnimationContext) sparseArray.valueAt(size)).update();
        }
    }
}
