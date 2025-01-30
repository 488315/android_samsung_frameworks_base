package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.animation.DecelerateInterpolator;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeVisualIndicator {
    public final DisplayController mDisplayController;
    public boolean mIsFullscreen;
    public SurfaceControl mLeash;
    public final ActivityManager.RunningTaskInfo mTaskInfo;
    public View mView;
    public SurfaceControlViewHost mViewHost;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VisualIndicatorAnimator extends ValueAnimator {
        public final Rect mEndBounds;
        public final RectEvaluator mRectEvaluator;
        public final Rect mStartBounds;
        public final View mView;

        private VisualIndicatorAnimator(View view, Rect rect, Rect rect2) {
            this.mView = view;
            this.mStartBounds = new Rect(rect);
            this.mEndBounds = rect2;
            setFloatValues(0.0f, 1.0f);
            this.mRectEvaluator = new RectEvaluator(new Rect());
        }

        public static Rect getMaxBounds(DisplayLayout displayLayout) {
            int i = displayLayout.stableInsets(false).top;
            int i2 = i * 2;
            float f = i;
            float f2 = (displayLayout.mWidth - i2) * 0.015f;
            float f3 = (displayLayout.mHeight - i2) * 0.015f;
            return new Rect((int) (f - f2), (int) (f - f3), (int) ((displayLayout.mWidth - i) + f2), (int) ((displayLayout.mHeight - i) + f3));
        }

        public static void setupIndicatorAnimation(final VisualIndicatorAnimator visualIndicatorAnimator) {
            visualIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.DesktopModeVisualIndicator$VisualIndicatorAnimator$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DesktopModeVisualIndicator.VisualIndicatorAnimator visualIndicatorAnimator2 = DesktopModeVisualIndicator.VisualIndicatorAnimator.this;
                    if (visualIndicatorAnimator2.mView == null) {
                        visualIndicatorAnimator2.cancel();
                        return;
                    }
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    View view = visualIndicatorAnimator2.mView;
                    if (!visualIndicatorAnimator2.mStartBounds.equals(visualIndicatorAnimator2.mEndBounds)) {
                        view.getBackground().setBounds(visualIndicatorAnimator2.mRectEvaluator.evaluate(animatedFraction, visualIndicatorAnimator2.mStartBounds, visualIndicatorAnimator2.mEndBounds));
                    }
                    visualIndicatorAnimator2.mView.setAlpha(valueAnimator.getAnimatedFraction() * 0.7f);
                }
            });
            visualIndicatorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.desktopmode.DesktopModeVisualIndicator.VisualIndicatorAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    VisualIndicatorAnimator.this.mView.getBackground().setBounds(VisualIndicatorAnimator.this.mEndBounds);
                }
            });
            visualIndicatorAnimator.setDuration(200L);
        }

        public static VisualIndicatorAnimator toFreeformAnimator(View view, DisplayLayout displayLayout) {
            float f = (displayLayout.mWidth * 0.39999998f) / 2.0f;
            float f2 = (displayLayout.mHeight * 0.39999998f) / 2.0f;
            VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorAnimator(view, getMaxBounds(displayLayout), new Rect((int) f, (int) f2, (int) (displayLayout.mWidth - f), (int) (displayLayout.mHeight - f2)));
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            setupIndicatorAnimation(visualIndicatorAnimator);
            return visualIndicatorAnimator;
        }

        public static VisualIndicatorAnimator toFullscreenAnimator(View view, DisplayLayout displayLayout) {
            Rect maxBounds = getMaxBounds(displayLayout);
            VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorAnimator(view, maxBounds, maxBounds);
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            setupIndicatorAnimation(visualIndicatorAnimator);
            return visualIndicatorAnimator;
        }

        public static VisualIndicatorAnimator toFullscreenAnimatorWithAnimatedBounds(View view, DisplayLayout displayLayout) {
            int i = displayLayout.stableInsets(false).top;
            Rect rect = new Rect(i, i, displayLayout.mWidth - i, displayLayout.mHeight - i);
            view.getBackground().setBounds(rect);
            VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorAnimator(view, rect, getMaxBounds(displayLayout));
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            setupIndicatorAnimation(visualIndicatorAnimator);
            return visualIndicatorAnimator;
        }
    }

    public DesktopModeVisualIndicator(SyncTransactionQueue syncTransactionQueue, ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController, Context context, SurfaceControl surfaceControl, ShellTaskOrganizer shellTaskOrganizer, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mTaskInfo = runningTaskInfo;
        this.mDisplayController = displayController;
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        this.mView = new View(context);
        SurfaceControl.Builder builder = new SurfaceControl.Builder();
        builder.setParent((SurfaceControl) rootTaskDisplayAreaOrganizer.mLeashes.get(runningTaskInfo.displayId));
        SurfaceControl build = builder.setName("Fullscreen Indicator").setContainerLayer().build();
        this.mLeash = build;
        transaction.show(build);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i, i2, 2, 8, -2);
        layoutParams.setTitle("Fullscreen indicator for Task=" + runningTaskInfo.taskId);
        layoutParams.setTrustedOverlay();
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, displayController.getDisplay(runningTaskInfo.displayId), new WindowlessWindowManager(runningTaskInfo.configuration, this.mLeash, (IBinder) null), "FullscreenVisualIndicator");
        this.mViewHost = surfaceControlViewHost;
        surfaceControlViewHost.setView(this.mView, layoutParams);
        transaction.setRelativeLayer(this.mLeash, surfaceControl, -1);
        syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeVisualIndicator$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                SurfaceControl.Transaction transaction3 = transaction;
                transaction2.merge(transaction3);
                transaction3.close();
            }
        });
    }
}
