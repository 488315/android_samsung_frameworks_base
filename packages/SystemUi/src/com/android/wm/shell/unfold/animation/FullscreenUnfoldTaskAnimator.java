package com.android.wm.shell.unfold.animation;

import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Trace;
import android.util.MathUtils;
import android.util.SparseArray;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.animation.Transformation;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.unfold.UnfoldBackgroundController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FullscreenUnfoldTaskAnimator implements UnfoldTaskAnimator, DisplayInsetsController.OnInsetsChangedListener, ConfigurationChangeListener {
    public static final float[] FLOAT_9 = new float[9];
    public static final TypeEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public final SparseArray mAnimationContextByTaskId = new SparseArray();
    public final UnfoldBackgroundController mBackgroundController;
    public final Context mContext;
    public final DisplayInsetsController mDisplayInsetsController;
    public InsetsSource mExpandedTaskbarInsetsSource;
    public final ShellController mShellController;
    public float mWindowCornerRadiusPx;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationContext {
        public final Rect mCurrentCropRect;
        public final Rect mEndCropRect;
        public final SurfaceControl mLeash;
        public final Matrix mMatrix;
        public final Rect mStartCropRect;
        public TaskInfo mTaskInfo;

        public /* synthetic */ AnimationContext(FullscreenUnfoldTaskAnimator fullscreenUnfoldTaskAnimator, SurfaceControl surfaceControl, InsetsSource insetsSource, TaskInfo taskInfo, int i) {
            this(fullscreenUnfoldTaskAnimator, surfaceControl, insetsSource, taskInfo);
        }

        public final void update(InsetsSource insetsSource, TaskInfo taskInfo) {
            this.mTaskInfo = taskInfo;
            Rect bounds = taskInfo.getConfiguration().windowConfiguration.getBounds();
            Rect rect = this.mStartCropRect;
            rect.set(bounds);
            if (insetsSource != null) {
                rect.inset(insetsSource.calculateVisibleInsets(rect));
            }
            Rect rect2 = this.mEndCropRect;
            rect2.set(rect);
            int width = (int) (rect2.width() * 0.08f);
            rect.left = rect2.left + width;
            rect.right = rect2.right - width;
            int height = (int) (rect2.height() * 0.03f);
            rect.top = rect2.top + height;
            rect.bottom = rect2.bottom - height;
        }

        private AnimationContext(FullscreenUnfoldTaskAnimator fullscreenUnfoldTaskAnimator, SurfaceControl surfaceControl, InsetsSource insetsSource, TaskInfo taskInfo) {
            this.mStartCropRect = new Rect();
            this.mEndCropRect = new Rect();
            this.mCurrentCropRect = new Rect();
            this.mMatrix = new Matrix();
            new Transformation();
            this.mLeash = surfaceControl;
            update(insetsSource, taskInfo);
        }
    }

    public FullscreenUnfoldTaskAnimator(Context context, UnfoldBackgroundController unfoldBackgroundController, ShellController shellController, DisplayInsetsController displayInsetsController) {
        new Rect();
        this.mContext = context;
        this.mDisplayInsetsController = displayInsetsController;
        this.mBackgroundController = unfoldBackgroundController;
        this.mShellController = shellController;
        this.mWindowCornerRadiusPx = ScreenDecorationsUtils.getWindowCornerRadius(context);
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void applyAnimationProgress(float f, SurfaceControl.Transaction transaction) {
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        if (sparseArray.size() == 0) {
            return;
        }
        int size = sparseArray.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            AnimationContext animationContext = (AnimationContext) sparseArray.valueAt(size);
            animationContext.mCurrentCropRect.set((Rect) RECT_EVALUATOR.evaluate(f, animationContext.mStartCropRect, animationContext.mEndCropRect));
            float lerp = MathUtils.lerp(0.94f, 1.0f, f);
            Matrix matrix = animationContext.mMatrix;
            Rect rect = animationContext.mCurrentCropRect;
            matrix.setScale(lerp, lerp, rect.exactCenterX(), rect.exactCenterY());
            SurfaceControl surfaceControl = animationContext.mLeash;
            transaction.setWindowCrop(surfaceControl, rect).setMatrix(surfaceControl, matrix, FLOAT_9).setCornerRadius(surfaceControl, this.mWindowCornerRadiusPx).show(surfaceControl);
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
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            AnimationContext animationContext = (AnimationContext) sparseArray.valueAt(size);
            animationContext.update(this.mExpandedTaskbarInsetsSource, animationContext.mTaskInfo);
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final boolean isApplicableTask(TaskInfo taskInfo) {
        return (taskInfo == null || !taskInfo.isVisible() || taskInfo.realActivity == null || taskInfo.getWindowingMode() != 1 || taskInfo.getActivityType() == 2) ? false : true;
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        Trace.beginSection("FullscreenUnfoldTaskAnimator#onConfigurationChanged");
        this.mWindowCornerRadiusPx = ScreenDecorationsUtils.getWindowCornerRadius(this.mContext);
        Trace.endSection();
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskAppeared(TaskInfo taskInfo, SurfaceControl surfaceControl) {
        this.mAnimationContextByTaskId.put(taskInfo.taskId, new AnimationContext(this, surfaceControl, this.mExpandedTaskbarInsetsSource, taskInfo, 0));
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskChanged(TaskInfo taskInfo) {
        AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.get(taskInfo.taskId);
        if (animationContext != null) {
            animationContext.update(this.mExpandedTaskbarInsetsSource, taskInfo);
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void onTaskVanished(TaskInfo taskInfo) {
        this.mAnimationContextByTaskId.remove(taskInfo.taskId);
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void prepareFinishTransaction(SurfaceControl.Transaction transaction) {
        UnfoldBackgroundController unfoldBackgroundController = this.mBackgroundController;
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
        this.mBackgroundController.ensureBackground(transaction);
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetAllSurfaces(SurfaceControl.Transaction transaction) {
        SparseArray sparseArray = this.mAnimationContextByTaskId;
        int size = sparseArray.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                resetSurface((AnimationContext) sparseArray.valueAt(size), transaction);
            }
        }
    }

    @Override // com.android.wm.shell.unfold.animation.UnfoldTaskAnimator
    public final void resetSurface(TaskInfo taskInfo, SurfaceControl.Transaction transaction) {
        AnimationContext animationContext = (AnimationContext) this.mAnimationContextByTaskId.get(taskInfo.taskId);
        if (animationContext != null) {
            resetSurface(animationContext, transaction);
        }
    }

    public static void resetSurface(AnimationContext animationContext, SurfaceControl.Transaction transaction) {
        SurfaceControl.Transaction windowCrop = transaction.setWindowCrop(animationContext.mLeash, null);
        SurfaceControl surfaceControl = animationContext.mLeash;
        SurfaceControl.Transaction matrix = windowCrop.setCornerRadius(surfaceControl, 0.0f).setMatrix(animationContext.mLeash, 1.0f, 0.0f, 0.0f, 1.0f);
        Point point = animationContext.mTaskInfo.positionInParent;
        matrix.setPosition(surfaceControl, point.x, point.y);
    }
}
