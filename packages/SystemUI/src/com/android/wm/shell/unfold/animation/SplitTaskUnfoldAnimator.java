package com.android.wm.shell.unfold.animation;

import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Trace;
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
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SplitTaskUnfoldAnimator implements UnfoldTaskAnimator, DisplayInsetsController.OnInsetsChangedListener, SplitScreen.SplitScreenListener, ConfigurationChangeListener {
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

    public class AnimationContext {
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
            Insets insetsOf;
            int i2;
            int i3 = this.mStageType;
            SplitTaskUnfoldAnimator splitTaskUnfoldAnimator = SplitTaskUnfoldAnimator.this;
            this.mStartCropRect.set(i3 == 0 ? splitTaskUnfoldAnimator.mMainStageBounds : splitTaskUnfoldAnimator.mSideStageBounds);
            InsetsSource insetsSource = splitTaskUnfoldAnimator.mExpandedTaskbarInsetsSource;
            int i4 = 0;
            boolean z = insetsSource != null;
            if (z) {
                Rect rect = this.mStartCropRect;
                rect.inset(insetsSource.calculateVisibleInsets(rect));
            }
            this.mStartCropRect.offsetTo(0, 0);
            this.mEndCropRect.set(this.mStartCropRect);
            int iMax = (int) (Math.max(this.mEndCropRect.width(), this.mEndCropRect.height()) * 0.05f);
            if (((SplitScreenController) ((Optional) splitTaskUnfoldAnimator.mSplitScreenController.get()).get()).isLeftRightSplit()) {
                int i5 = z ? 0 : iMax;
                if ((this.mStageType == 0 ? splitTaskUnfoldAnimator.mMainStagePosition : splitTaskUnfoldAnimator.mSideStagePosition) == 0) {
                    i2 = 0;
                    i4 = iMax;
                } else {
                    i2 = iMax;
                }
                insetsOf = Insets.of(i4, iMax, i2, i5);
            } else {
                if ((this.mStageType == 0 ? splitTaskUnfoldAnimator.mMainStagePosition : splitTaskUnfoldAnimator.mSideStagePosition) == 0) {
                    i = 0;
                    i4 = iMax;
                } else {
                    i = z ? 0 : iMax;
                }
                insetsOf = Insets.of(iMax, i4, iMax, i);
            }
            this.mStartCropRect.inset(insetsOf);
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

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void applyAnimationProgress(float f, SurfaceControl.Transaction transaction) {
        for (int size = this.mAnimationContextByTaskId.size() - 1; size >= 0; size--) {
            AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.valueAt(size);
            if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
                this.mUnfoldAnimation.getTransformationAt(f, animationContext.mTransformation);
                transaction.setMatrix(animationContext.mLeash, animationContext.mTransformation.getMatrix(), FLOAT_9).setAlpha(animationContext.mLeash, animationContext.mTransformation.getAlpha());
            } else if (animationContext.mStageType != -1) {
                animationContext.mCurrentCropRect.set((Rect) RECT_EVALUATOR.evaluate(f, animationContext.mStartCropRect, animationContext.mEndCropRect));
                transaction.setWindowCrop(animationContext.mLeash, animationContext.mCurrentCropRect).setCornerRadius(animationContext.mLeash, this.mWindowCornerRadiusPx);
            }
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void clearTasks() {
        this.mAnimationContextByTaskId.clear();
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final boolean hasActiveTasks() {
        return this.mAnimationContextByTaskId.size() > 0;
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void init() {
        this.mDisplayInsetsController.addInsetsChangedListener(0, this);
        this.mShellController.addConfigurationChangeListener(this);
    }

    @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
    public final void insetsChanged(InsetsState insetsState) {
        InsetsSource insetsSourceSourceAt;
        int iSourceSize = insetsState.sourceSize() - 1;
        while (true) {
            if (iSourceSize < 0) {
                insetsSourceSourceAt = null;
                break;
            }
            insetsSourceSourceAt = insetsState.sourceAt(iSourceSize);
            if (insetsSourceSourceAt.getType() == WindowInsets.Type.navigationBars() && insetsSourceSourceAt.hasFlags(2)) {
                break;
            } else {
                iSourceSize--;
            }
        }
        this.mExpandedTaskbarInsetsSource = insetsSourceSourceAt;
        updateContexts();
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final boolean isApplicableTask(TaskInfo taskInfo) {
        if (!CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            return taskInfo.hasParentTask() && taskInfo.isRunning && taskInfo.realActivity != null && taskInfo.getWindowingMode() == 6;
        }
        SplitScreenController splitScreenController = (SplitScreenController) ((Optional) this.mSplitScreenController.get()).get();
        return splitScreenController != null && splitScreenController.isTaskRoot(taskInfo.taskId);
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
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

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
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

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
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

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskVanished(TaskInfo taskInfo) {
        this.mAnimationContextByTaskId.remove(taskInfo.taskId);
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
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

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void prepareStartTransaction(SurfaceControl.Transaction transaction) {
        this.mUnfoldBackgroundController.ensureBackground(transaction);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            for (int size = this.mAnimationContextByTaskId.size() - 1; size >= 0; size--) {
                AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.valueAt(size);
                this.mUnfoldAnimation.getTransformationAt(0.0f, animationContext.mTransformation);
                transaction.setMatrix(animationContext.mLeash, animationContext.mTransformation.getMatrix(), FLOAT_9).setAlpha(animationContext.mLeash, animationContext.mTransformation.getAlpha());
            }
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetAllSurfaces(SurfaceControl.Transaction transaction) {
        for (int size = this.mAnimationContextByTaskId.size() - 1; size >= 0; size--) {
            AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.valueAt(size);
            transaction.setWindowCrop(animationContext.mLeash, null).setCornerRadius(animationContext.mLeash, 0.0f);
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetSurface(TaskInfo taskInfo, SurfaceControl.Transaction transaction) {
        AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.get(taskInfo.taskId);
        if (animationContext != null) {
            transaction.setWindowCrop(animationContext.mLeash, null).setCornerRadius(animationContext.mLeash, 0.0f);
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void start() {
        final SplitScreenController.SplitScreenImpl splitScreenImpl = ((SplitScreenController) ((Optional) this.mSplitScreenController.get()).get()).mImpl;
        final Executor executor = this.mExecutor;
        if (splitScreenImpl.mExecutors.containsKey(this)) {
            return;
        }
        SplitScreenController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                SplitScreenController.SplitScreenImpl splitScreenImpl2 = splitScreenImpl;
                SplitTaskUnfoldAnimator splitTaskUnfoldAnimator = this;
                Executor executor2 = executor;
                if (splitScreenImpl2.mExecutors.size() == 0) {
                    SplitScreenController.this.mStageCoordinator.registerSplitScreenListener(splitScreenImpl2.mListener);
                }
                splitScreenImpl2.mExecutors.put(splitTaskUnfoldAnimator, executor2);
            }
        });
        executor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1(splitScreenImpl, this, 1));
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void stop() {
        SplitScreenController.SplitScreenImpl splitScreenImpl = ((SplitScreenController) ((Optional) this.mSplitScreenController.get()).get()).mImpl;
        SplitScreenController.this.mMainExecutor.execute(new SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1(splitScreenImpl, this, 0));
    }

    public final void updateContexts() {
        for (int size = this.mAnimationContextByTaskId.size() - 1; size >= 0; size--) {
            ((AnimationContext) this.mAnimationContextByTaskId.valueAt(size)).update();
        }
    }
}
