package com.android.wm.shell.fullscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import com.android.wm.shell.fullscreen.AffordanceAnimController;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FullscreenTaskListener implements ShellTaskOrganizer.TaskListener {
    public final SparseArray mAffordanceControllerList;
    public final Context mContext;
    public final Optional mDesktopWallpaperActivityTokenProviderOptional;
    public final Optional mRecentTasksOptional;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Optional mSplitScreenControllerOptional;
    public final SyncTransactionQueue mSyncQueue;
    public final SparseArray mTasks;
    public final Optional mWindowDecorViewModelOptional;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class State {
        public SurfaceControl mLeash;
        public ActivityManager.RunningTaskInfo mTaskInfo;

        public /* synthetic */ State(int i) {
            this();
        }

        private State() {
        }
    }

    public FullscreenTaskListener(SyncTransactionQueue syncTransactionQueue) {
        this(null, null, syncTransactionQueue, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), null);
    }

    public final void animForAffordance(int i, int i2) {
        final AffordanceAnimController affordanceAnimController;
        Keyframe[] keyframeArr;
        State state = (State) this.mTasks.get(i);
        if (state != null) {
            int i3 = state.mTaskInfo.displayId;
            Keyframe[] keyframeArr2 = null;
            if (this.mContext == null) {
                affordanceAnimController = null;
            } else {
                if (!this.mAffordanceControllerList.contains(i3)) {
                    this.mAffordanceControllerList.put(i3, new AffordanceAnimController(this.mContext, i3));
                }
                affordanceAnimController = (AffordanceAnimController) this.mAffordanceControllerList.get(i3);
            }
            if (affordanceAnimController != null) {
                final SurfaceControl surfaceControl = state.mLeash;
                ActivityManager.RunningTaskInfo runningTaskInfo = state.mTaskInfo;
                if (surfaceControl == null || runningTaskInfo == null) {
                    return;
                }
                affordanceAnimController.mBounds.set(runningTaskInfo.getConfiguration().windowConfiguration.getBounds());
                if (affordanceAnimController.mBounds.isEmpty()) {
                    return;
                }
                ValueAnimator valueAnimator = affordanceAnimController.mAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                affordanceAnimController.mDisplayContext.getDisplay().getMetrics(displayMetrics);
                float f = displayMetrics.density;
                if ((i2 & 4) != 0) {
                    keyframeArr = affordanceAnimController.getKeyFrames(f, true, (i2 & 1) != 0);
                } else if ((i2 & 8) != 0) {
                    keyframeArr = affordanceAnimController.getKeyFrames(f, false, (i2 & 1) != 0);
                } else {
                    keyframeArr = null;
                }
                if ((i2 & 1) != 0) {
                    keyframeArr2 = affordanceAnimController.getKeyFrames(f, true, (i2 & 12) != 0);
                } else if ((i2 & 2) != 0) {
                    keyframeArr2 = affordanceAnimController.getKeyFrames(f, false, false);
                }
                if (keyframeArr == null && keyframeArr2 == null) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    affordanceAnimController.mAnimator = ofFloat;
                    ofFloat.setDuration(affordanceAnimController.mAnimation.getDuration());
                    affordanceAnimController.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            AffordanceAnimController affordanceAnimController2 = AffordanceAnimController.this;
                            SurfaceControl surfaceControl2 = surfaceControl;
                            affordanceAnimController2.mTmpTransformation.clear();
                            affordanceAnimController2.mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), affordanceAnimController2.mTmpTransformation);
                            affordanceAnimController2.mTransaction.setMatrix(surfaceControl2, affordanceAnimController2.mTmpTransformation.getMatrix(), affordanceAnimController2.mTmpFloat9);
                            affordanceAnimController2.mTransaction.apply();
                        }
                    });
                    affordanceAnimController.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.2
                        public final /* synthetic */ SurfaceControl val$leash;

                        public AnonymousClass2(final SurfaceControl surfaceControl2) {
                            r2 = surfaceControl2;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            AffordanceAnimController.this.mAnimation.cancel();
                            AffordanceAnimController affordanceAnimController2 = AffordanceAnimController.this;
                            affordanceAnimController2.mTransaction.setMatrix(r2, Matrix.IDENTITY_MATRIX, affordanceAnimController2.mTmpFloat9);
                            affordanceAnimController2.mTransaction.apply();
                            AffordanceAnimController affordanceAnimController3 = AffordanceAnimController.this;
                            if (affordanceAnimController3.mRadius != 0.0f) {
                                affordanceAnimController3.mTransaction.setWindowCrop(r2, 0, 0);
                                AffordanceAnimController.this.mTransaction.setCornerRadius(r2, 0.0f);
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            AffordanceAnimController affordanceAnimController2 = AffordanceAnimController.this;
                            affordanceAnimController2.mAnimation.initialize(affordanceAnimController2.mBounds.width(), AffordanceAnimController.this.mBounds.height(), AffordanceAnimController.this.mBounds.width(), AffordanceAnimController.this.mBounds.height());
                            AffordanceAnimController.this.mAnimation.start();
                            AffordanceAnimController affordanceAnimController3 = AffordanceAnimController.this;
                            if (affordanceAnimController3.mRadius != 0.0f) {
                                affordanceAnimController3.mTransaction.setWindowCrop(r2, affordanceAnimController3.mBounds.width(), AffordanceAnimController.this.mBounds.height());
                                AffordanceAnimController affordanceAnimController4 = AffordanceAnimController.this;
                                affordanceAnimController4.mTransaction.setCornerRadius(r2, affordanceAnimController4.mRadius);
                            }
                        }
                    });
                    affordanceAnimController.mAnimator.start();
                    return;
                }
                ArrayList arrayList = new ArrayList();
                if (keyframeArr != null) {
                    arrayList.add(PropertyValuesHolder.ofKeyframe(AffordanceAnimController.AnimTarget.X, keyframeArr));
                }
                if (keyframeArr2 != null) {
                    arrayList.add(PropertyValuesHolder.ofKeyframe(AffordanceAnimController.AnimTarget.Y, keyframeArr2));
                }
                AffordanceAnimController.AnimTarget animTarget = new AffordanceAnimController.AnimTarget(surfaceControl2);
                ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(animTarget, (PropertyValuesHolder[]) arrayList.toArray(new PropertyValuesHolder[0])).setDuration(500L);
                affordanceAnimController.mAnimator = duration;
                duration.setInterpolator(new LinearInterpolator());
                affordanceAnimController.mAnimator.addUpdateListener(animTarget);
                affordanceAnimController.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.fullscreen.AffordanceAnimController.1
                    public final /* synthetic */ SurfaceControl val$leash;

                    public AnonymousClass1(final SurfaceControl surfaceControl2) {
                        r2 = surfaceControl2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        AffordanceAnimController.this.mTransaction.setPosition(r2, 0.0f, 0.0f);
                        AffordanceAnimController affordanceAnimController2 = AffordanceAnimController.this;
                        if (affordanceAnimController2.mRadius != 0.0f) {
                            affordanceAnimController2.mTransaction.setWindowCrop(r2, 0, 0);
                            AffordanceAnimController.this.mTransaction.setCornerRadius(r2, 0.0f);
                        }
                        AffordanceAnimController.this.mTransaction.apply();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        AffordanceAnimController affordanceAnimController2 = AffordanceAnimController.this;
                        if (affordanceAnimController2.mRadius != 0.0f) {
                            affordanceAnimController2.mTransaction.setWindowCrop(r2, affordanceAnimController2.mBounds.width(), AffordanceAnimController.this.mBounds.height());
                            AffordanceAnimController affordanceAnimController3 = AffordanceAnimController.this;
                            affordanceAnimController3.mTransaction.setCornerRadius(r2, affordanceAnimController3.mRadius);
                        }
                        AffordanceAnimController.this.mTransaction.apply();
                    }
                });
                affordanceAnimController.mAnimator.start();
            }
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface$1(i));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + this);
        printWriter.println(m + this.mTasks.size() + " Tasks");
    }

    public final SurfaceControl findTaskSurface$1(int i) {
        if (this.mTasks.contains(i)) {
            return ((State) this.mTasks.get(i)).mLeash;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "There is no surface for taskId="));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(final ActivityManager.RunningTaskInfo runningTaskInfo, final SurfaceControl surfaceControl) {
        if (this.mTasks.get(runningTaskInfo.taskId) != null) {
            throw new IllegalStateException("Task appeared more than once: #" + runningTaskInfo.taskId);
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 1427696159067159186L, 1, Long.valueOf(runningTaskInfo.taskId));
        }
        final Point point = runningTaskInfo.positionInParent;
        boolean z = false;
        State state = new State(0 == true ? 1 : 0);
        state.mLeash = surfaceControl;
        state.mTaskInfo = runningTaskInfo;
        this.mTasks.put(runningTaskInfo.taskId, state);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        this.mRecentTasksOptional.ifPresent(new FullscreenTaskListener$$ExternalSyntheticLambda1(runningTaskInfo, 2));
        if (this.mWindowDecorViewModelOptional.isPresent()) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            z = ((WindowDecorViewModel) this.mWindowDecorViewModelOptional.get()).onTaskOpening(runningTaskInfo, surfaceControl, transaction, transaction);
            transaction.apply();
        }
        if (z) {
            return;
        }
        this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.fullscreen.FullscreenTaskListener$$ExternalSyntheticLambda5
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                SurfaceControl surfaceControl2 = surfaceControl;
                Point point2 = point;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                if (surfaceControl2.isValid()) {
                    transaction2.setWindowCrop(surfaceControl2, null);
                    transaction2.setPosition(surfaceControl2, point2.x, point2.y);
                    transaction2.setAlpha(surfaceControl2, 1.0f);
                    transaction2.setMatrix(surfaceControl2, 1.0f, 0.0f, 0.0f, 1.0f);
                    if (runningTaskInfo2.isVisible) {
                        transaction2.show(surfaceControl2);
                    }
                }
            }
        });
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        final State state = (State) this.mTasks.get(runningTaskInfo.taskId);
        ActivityManager.RunningTaskInfo runningTaskInfo2 = state.mTaskInfo;
        Point point = runningTaskInfo2.positionInParent;
        boolean z = runningTaskInfo2.isVisible;
        if (this.mWindowDecorViewModelOptional.isPresent()) {
            ((WindowDecorViewModel) this.mWindowDecorViewModelOptional.get()).onTaskInfoChanged(runningTaskInfo);
        }
        state.mTaskInfo = runningTaskInfo;
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        this.mRecentTasksOptional.ifPresent(new FullscreenTaskListener$$ExternalSyntheticLambda1(runningTaskInfo, 2));
        final Point point2 = state.mTaskInfo.positionInParent;
        boolean equals = point.equals(point2);
        final boolean z2 = !z && state.mTaskInfo.isVisible;
        if (z2 || !equals) {
            this.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.fullscreen.FullscreenTaskListener$$ExternalSyntheticLambda3
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                    Point point3 = point2;
                    FullscreenTaskListener.State state2 = FullscreenTaskListener.State.this;
                    if (state2.mLeash.isValid()) {
                        if (z2) {
                            transaction.show(state2.mLeash);
                        }
                        transaction.setPosition(state2.mLeash, point3.x, point3.y);
                    }
                }
            });
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TASK_ORG_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TASK_ORG, 2639626852593250505L, 1, Long.valueOf(runningTaskInfo.taskId));
        }
        this.mTasks.remove(runningTaskInfo.taskId);
        this.mWindowDecorViewModelOptional.ifPresent(new FullscreenTaskListener$$ExternalSyntheticLambda1(runningTaskInfo, 0));
        this.mDesktopWallpaperActivityTokenProviderOptional.ifPresent(new FullscreenTaskListener$$ExternalSyntheticLambda1(runningTaskInfo, 1));
        if (!Transitions.ENABLE_SHELL_TRANSITIONS && this.mWindowDecorViewModelOptional.isPresent()) {
            ((WindowDecorViewModel) this.mWindowDecorViewModelOptional.get()).destroyWindowDecoration(runningTaskInfo);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface$1(i));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean supportCompatUI$1() {
        return true;
    }

    public final String toString() {
        return "FullscreenTaskListener:" + ShellTaskOrganizer.taskListenerTypeToString(-2);
    }

    public FullscreenTaskListener(ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, Optional<RecentTasksController> optional, Optional<WindowDecorViewModel> optional2, Optional<DesktopWallpaperActivityTokenProvider> optional3, Optional<SplitScreenController> optional4, Context context) {
        this.mTasks = new SparseArray();
        this.mAffordanceControllerList = new SparseArray();
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mRecentTasksOptional = optional;
        this.mWindowDecorViewModelOptional = optional2;
        this.mDesktopWallpaperActivityTokenProviderOptional = optional3;
        this.mSplitScreenControllerOptional = optional4;
        this.mContext = context;
        if (shellInit != null) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.fullscreen.FullscreenTaskListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FullscreenTaskListener fullscreenTaskListener = FullscreenTaskListener.this;
                    fullscreenTaskListener.mShellTaskOrganizer.addListenerForType(fullscreenTaskListener, -2);
                    fullscreenTaskListener.mSplitScreenControllerOptional.ifPresent(new FullscreenTaskListener$$ExternalSyntheticLambda1(fullscreenTaskListener, 3));
                }
            }, this);
        }
    }
}
